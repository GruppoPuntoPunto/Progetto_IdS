# Info Progetto
## Use case, Domain model, Desing Model
La parte dedicata agli use case, al domain model e al desing model è stata separata dal manuale per motivi di formattazione dato che generalmente i grafici __UML__ occupano spazio di rilevante importanza.

## Sistema Software
Il software verte sulla comunicazione con due sorgenti differenti: The Guardian e New York Times. Vengono scaricati ed analizzati in totale 2000 articli (1000 per sorgente). L'analisi consiste nel contare in quanti articoli compare ogni parola presente.

In risposta alle richieste software abbiamo adottato le seguenti scelte:

### Gestione di eventuali nuove sorgenti
Per far si che il sistema possa supportare nuove sorgenti abbiamo sfruttato il __Facotry Pattern__ (chiamando la nostra classe `SourceFactory`). Il vantaggio è che l'introduzione di nuove sorgenti necessita solamente l'aggiunta della classe che modella la nuova sorgente e di una classe che modella gli articoli di tale sorgente. In questo modo non serve andare ad intaccare la struttura portante del progetto. Inoltre nell'implementare la `SourceFactory` abbiamo utilizzato il __Singleton Pattern__.
<br></br>
In particolar modo con il metodo `createSource()` facciamo in modo di generare la sorgente derivante dal _The Guardian_ e quella relativa al _New York Times_:
```java
public Source createSource(String sourceType, Object... args) {
     if (sourceType.equals("GuardianJsonSource") && args[0] instanceof String && args[1] instanceof String)
         return new GuardianJsonSource((String) args[0], (String) args[1]);
     else if (sourceType.equals("NewYorkTimesCsvSource") && args[0] instanceof FileReader)
         return new NewYorkTimesCsvSource((FileReader) args[0]);
     return null;
 }
```

### La persistenza su file degli articoli
In seguito alla fase di Download (fase in cui vengono crati gli oggetti `ArticleJsonGuardian` o `ArtcileCsvNYTimes`) il sistema serializza tutti gli articoli in file di formato `.xml` attraverso la classe `XmlSerializer`.  La stessa classe offre anche la possibilità di attuare il procedimento inverso, ovvero permette di deserializzare i file e creare degli oggetti che implementano l'interfaccia `Article`. La classe `XmlSerializer` di fatto implementa l'__Adapter Pattern__: abbiamo convertito l'interfaccia del serializzatore fornita dalla libreria utilizzata (`org.simpleframework.xml`) per il nostro scopo, ovvero serializzare articoli in file xml.
<br></br>
 Di seguito vengono riportati i due metodi che permettono di serializzare e deserializzare gli articoli.

```java
 public void serialize(List<? extends Article> list) {
     try {
         for(Article article : list)
             serializer.write(new ArticleXml(article.getTitle(), article.getBody()), new File(this.directory, productionCount++ + ".xml"));
     } catch (Exception e) {
        System.out.println(e.getMessage());
     }
 }
```
```java
 public List<Article> deserialize() throws Exception {
     File[] files = this.directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
     List<Article> allArticles = new ArrayList<>();

     if(files != null && files.length != 0) {
         for (File file : files) {
             allArticles.add(initializedArticle(serializer.read(ArticleXml.class, file)));
         }
         return allArticles;
     }
     else { return null; }
 }
```
### Supporto a nuove modalità di memorizzazione ed accesso agli articoli
Nel paragrafo precedente è presente la funzione che permette di serializzare gli articoli in file xml. In particolare la funzione prende in input una lista di `Article`, quindi l'unico vincolo imposto alle classi che modellano gli articoli di una sorgente è che implementino l'interfaccia `Article`. Per quanto riguarda il formato in cui il serializzatore salva i file (ovvero xml), questo aspetto non può essere cambiato o impostato. Tuttavia il serializzatore è un componente intercambiabile del sistema.
    
### Supporto nuove strutture per memorizzare ed avere accesso ai termini più importanti
Dato che il sistema deve poter supportare nuove strutture per memorizzare e poter accedere ai termini più importanti, abbiamo utilizzato lo __Strategy Pattern__. Quest'ultimo consente l'aggiunta di nuove strutture di memorizzazione e l'utilizzo di nuove tecniche algoritmiche per il conteggio delle parole in base a qualsiasi criterio. Abbiamo quindi creato l'interfaccia `WordCountStrategy`, questa deve essere implementata da tutte le strategie di conteggio. Il _context object_ è rappresentato dalla classe `WordCounter`, questo permette di settare la strategia e/o di cambiarla dinamicamente. Nel progetto abbiamo implementato la strategia `FrequencyPerArticleStrategy` che associa ad ogni termine il numero di articoli in cui appare.
   ```java
    public class WordCounter {

       private WordCountStrategy strategy;

       public WordCounter(WordCountStrategy strategy) {
           this.strategy = strategy;
       }

       public void setCountStrategy(WordCountStrategy strategy) {
           this.strategy = strategy;
       }

       public  List<Map.Entry<String, Integer>> count(List<Article> articles) {
           return strategy.execute(articles);
       }
   }
```
   
### Dowload ed estrazione
Per permettere all'utente di specificare se eseguire il download degli articoli, l'estrazione dei termini o entrambe le azioni in sequenza, abbiamo sfruttato la libreria `org.apache.commons.cli`. In particolare:

1. L'utente può scegliere se eseguire solo il download degli articoli con il comando `-d`.
3. L'utente può decidere se effettuare l'estrazione a partire dai file dove sono stati memorizzati gli articoli tramite il comando `-e`.
4. L'utente può eseguire entrambe le operazioni in sequenza con il comando `-de`.

Ulteriori informazioni si trovano nella pagina dedicata all'installazione e uso del software.
   
- [Back to home](index.html)

