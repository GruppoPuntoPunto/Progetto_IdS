# Info Progetto
### Use case, Domain model, Desing Model
La parte dedicata agli use case, al domain model e al desing model è stata separata dal manuale per motivi di formattazione dato che generalmente i grafici __UML__ occupano spazio di rilevante importanza.

### Sistema Software
Il software verte sulla comunicazione con due sorgenti differenti (Api del The Guardian e un file CSV) per poter interfacciarsi e quindi scaricare
centinaia di articoli per poi effettuare delle operazioni di conteggio statistico sulle parole
utilizzate in ciascun estratto.

In risposta alle richieste software abbiamo adottato le seguenti scelte:

1. Per far si che il sistema possa supportare nuove sorgenti abbiamo sfruttato un __Facotry Pattern__ (chiamando la nostra classe `SourceFactory`) che permette l'introduzione di nuovi source file diversi dovendo solo aggiungere una classe relativa alla nuova sorgente ma di fatto senza andare ad intaccare la struttura portante del progetto. Inoltre nell'implementare la `SourceFactory` abbiamo utilizzato un __Singleton Pattern__ come di consueta prassi.

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
2. In seguito alla fase di Download e quindi alla creazione di oggetti `ArticleJsonGuardian` o `ArtcileCsvNYTimes` il sistema apporta la serializzazione in file di estensione `.xml` passando per la classe `XmlSerializer` che offre anche la possibilità di attuare il procedimento inverso, attraverso una deserializzazione in oggetti di tipo `Article` e dunque in un formato _"universale"_. Il nostro `XmlSerializer` può essere  interpretato come un __Adapter Pattern__ in quanto di fatto abbiamo convertito l'interfaccia del serializzatore fornita dalla libreria utilizzata (`org.simpleframework.xml`).
   <br></br>
   Ecco nel dettaglio i metodi fondamentali scritti grazie all'ausilio della libreria:

   ```java
    public void serialize(List<? extends Article> list) {
        try {
            for(Article article : list)
                // write(Object source, File out) - > throws Exception if the schema(source) for the object is not valid
                // new File(File parent, String child) -> throws NullPointerException if child is null
                serializer.write(new ArticleXml(article.getTitle(), article.getBody()), new File(this.directory, productionCount++ + ".xml"));
        } catch (Exception e) { // not supposed to be reached -> guaranteed safe operation before
           System.out.println(e.getMessage());
        }
    }
   ```
   ```java
    public void serialize(List<? extends Article> list) {
        try {
            for(Article article : list)
                // write(Object source, File out) - > throws Exception if the schema(source) for the object is not valid
                // new File(File parent, String child) -> throws NullPointerException if child is null
                serializer.write(new ArticleXml(article.getTitle(), article.getBody()), new File(this.directory, productionCount++ + ".xml"));
        } catch (Exception e) { // not supposed to be reached -> guaranteed safe operation before
           System.out.println(e.getMessage());
        }
    }  
   ```
3.  Come già definito nel punto precedente nel nostro caso specifico trattiamo un processo di serializzazione in articoli in `ArticleJSON` e `ArticleCSV` (vedremo anche `ArticleXML` che sarà in seguito spiegato  nel punto __4.__), tutte classi che implementano l'interfaccia `Article`, una soluzione dinamica che ci permetterà di creare in futuro ulteriori possibili classi articolo con cui memorizzare la serializzazione di altri tipi di sorgente. Tutto ciò sarà possibile con una semplice nuova implementazione dell'interfaccia `Article`.
    <br></br>
4. Nel punto __2.__ abbiamo descritto il procedimento di serializzazione e deserializzazione, in particolar modo, dopo il secondo, vengono creati degli oggetti `ArticleXml` con cui poi è possibile lavorare per ottenere le informazioni necessarie per l'estrazione dei termini ed il conteggio effettivo.
   <br></br>
5. Dato che il sistema deve poter supportare nuove strutture per memorizzare e poter accedere ai termini più importanti, abbiamo aggiunto uno __StrategyPattern__; quest'ultimo consente l'aggiunta di nuove strutture di memorizzazione e l'utilizzo di nuove tecniche algoritmiche per la ricerca dei caratteri in base a criteri specifici. Nel nostro caso il pattern è stato denominato come `WordCountStrategy` in relazione al _context object_ `WordCounter`, per far si che si possano selezionare nuove strategie in base alle eseigenze momentaneee dell'utente.
   ```java   
   public class WordCounter {
   private WordCountStrategy strategy; //Creazione strategia
   
       //Costruttore
       public WordCounter(WordCountStrategy strategy) {
           this.strategy = strategy;
       }
       //Metodo per il setting della strategia di conteggio
       public void setCountStrategy(WordCountStrategy strategy) {
           this.strategy = strategy;
       }
       //Metodo per eseguire il tipo di strategia selezionata
       public  List<Map.Entry<String, Integer>> count(List<Article> articles) {
           return strategy.execute(articles);
       }
   
   }
   ```
   Ad esempio abbiamo implementato `FrequencyPerArticleStrategy` che va a calcolare il peso-frequenza delle parole degli articoli come dalle richieste iniziali del progetto.
   <br></br>
6. Abbiamo ideato attraverso la libreria `org.apache.commons.cli` dei comandi prompt che l'utente può digitare per decidere cosa far fare, o come utilizzare, al programma. Nel dettaglio:
   
   1. L'utente può scegliere se eseguire solo il download del materiale con il comando `-d`.
   3. In sequenza, l'utente può anche decidere se effettuare l'estrazione a partire dai file dove sono stati memorizzati gli articoli tramite il comando `-e`
   4. Altrimenti un'ulteriore alternativa è quella di scaricare e fare l'estrazione sequenzialmente con il comando `-de`
   5. Ulteriori comandi possibili sono elencati qua sotto:

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
   
- [Back to home](index.html)

