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

# Come installare correttamente ed utilizzare il software

### Installazione e compilazione del progetto Maven
Dopo essere entrati nella directory relativa al proggetto (`Progetto_Ids`), per creare il file jar e compilare il codice è necessario digitare da terminale il seguente comando:
```terminal
mvn package
```
__NOTA__: I file jar verranno creati in automatico da Maven nella directory `Progetto_Ids/target`.
In particolare verranno create
- `progetto-1.0-SNAPSHOT.jar`
- `progetto-1.0-SNAPSHOT-jar-with-dependencies.jar`

Inoltre verrà generata la cartella `output` che inizialmente dopo la compilazione conterrà solo quelli relativi ai test del proegetto.
### javadocs
Per generare i javadocs

    mvn javadoc:javadoc
### Mavensite
Abbiamo creato la cartella `Progetto_Ids/src/site` che contiene i file sorgenti per creare il sito.
Le istruzioni necessarie per creare e rendere accessibile il sito sono:

    mvn site
    mvn site:run

Il sito sarà quindi disponibile al seguente indirizzo: [http://localhost:8080/](http://localhost:8080/)

Inoltre per generare l'output relativo ai test tramite il plug-in `surefire-report` nella cartella `Proggetto_Ids/target/site` dopo aver compilato i test, è necessaria la digitazione del seguente comando:
```terminal
mvn test
mvn surefire-report:report
```
### Esecuzione del programma
Per eseguire il programma sono possibili diversi comandi prompt sotto elencati:
```terminal 
 usage: App -{ak} -{d,de} [OPTION]...
 -ak,--api-key <arg>       Set the guardian API
 -csv,--csv-input <arg>    Set new york times .csv file input path
 -d,--download             Dowload all articles form all the resources
 -de,--download-extract    Download and extract terms
 -h,--help                 Print this help message
 -o,--output <arg>         Set results output file path          
 -xml,--xml-output <arg>   Set xml files input path (deserialize from) or output path (serialize in)
   ```  
In particolar modo come già anticipato nel file delle info sul progetto, l'utente può richiedere di effettuare solo il download con il comando:
```terminal
java -cp ./target/progetto-1.0-SNAPSHOT-jar-with-dependencies.jar it.unipd.dei.eis.App -ak <API-KEY> -d
```
In seguito può richiedere di effettuare l'estrazione e dunque procedere con la fase di deserializzazione e avviare l'algoritmo di conteggio specifico delle parole con il comando:
```terminal
java -cp ./target/progetto-1.0-SNAPSHOT-jar-with-dependencies.jar it.unipd.dei.eis.App -e
```
In alternativa a questi due comandi si può scegliere l'azione unica tramite:
```terminal
java -cp ./target/progetto-1.0-SNAPSHOT-jar-with-dependencies.jar it.unipd.dei.eis.App -ak <API-KEY> -de
```
Dopo aver eseguito il programma in base al comando digitato, i file generati verranno inseriti nella cartella `output` che conterrà le sottocartelle
`outputJsonTheGuardian` e `outputXml`. La prima si occuperà della memorizzazione dei file provenienti dall' API del _The Guardian_, mentre la seconda cartella conterrà tutti i file __JSON__ e __CSV__ nel formato standard `.xml`.

Il parametro `-xml` può essere aggiunto nella fase di download per specificare la cartella in cui inserire i file serializzati degli articoli. In alternativa può essere utilizzato nella fase di estrazione per impostare la cartella specifica dove estrarre gli articoli:
```terminal
java -cp ./target/progetto-1.0-SNAPSHOT-jar-with-dependencies.jar it.unipd.dei.eis.App -xml <Path> -d
```
Infine con il parametro `-csv` si può importare un ulteriore file esterno, ovviamente compatibile con gli header prefissati:
```terminal
java -cp ./target/progetto-1.0-SNAPSHOT-jar-with-dependencies.jar it.unipd.dei.eis.App -csv <Path> -d
```

# Librerie e versioni

1. `junit` version : 4.12
2. `com.fasterxml.jackson.core` version :  2.8.8.1 (_Libreria per leggere e scrivere da/in file JSON_)
3. `org.apache.commons` version : 1.10.0 (_Libreria per leggere e scrivere da/in file CSV_)
4. `org.simpleframework` version : 2.7.1 (_Libreria per leggere e scrivere da/in file xml_)
5. `commons-cli` version : 1.5.0 (_Libreria per gestire le opzioni specificate da linea di comando_)

### Dipendenze
1. `curl` version : _qualsiasi_
