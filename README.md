# Info Progetto

Il software verte alla comunicazione con due sorgenti differenti (Api del The Guardian e un file CSV) per poter interfacciarsi e quindi scaricare
centinaia di articoli per poi effettuare delle operazioni di conteggio statistico sulle parole
utilizzate in ciascun estratto.

In risposta alle richieste software abbiamo adottato le seguenti implementazioni:

1. Per far si che il sistema possa supportare nuove sorgenti abbiamo ideato un Facotry Pattern denominato __SourceFactory__ che permette l'introduzione di nuovi source file diversidovendo solo aggiungere una classe relativa alla nuova sorgente ma di fatto senza ade andare ad intaccare la struttura portante del proggetto.
   <br></br>
   In particolar modo con il metodo `createSource()` facciamo in modo di settare la sorgente derivante dal _The Guardian_ e quella relativa al _New York Times_
   <br></br>
2. In seguito alla fase di Download e quindi alla creazione di oggetti `ArticleJSON` o `ArtcileXML` il sistema apporta la serializzazione in file di estensione `.xml` passando per la classe `XmlSerializer` che offre anche la possibilità di attuare il procedimento inverso, atraverso una deserializzazione in oggetti di tipo `Article` e dunque in un formato _"universale"_. Ecco nel dettaglio i metodi fondamentali per quanto detto scritti grazie all'ausilio della libreria `org.simpleframework.xml`:

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
       public List<Article> deserialize() throws Exception {
           // Lambda function: accept(File dir, String name) -> collects all files .xml
           File[] files = this.directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
           List<Article> allArticles = new ArrayList<>();
   
           if(files != null && files.length != 0) {
           for (File file : files) {
           // read(Class<? extends T> type, File source) -> throws Exception if the object cannot be fully deserialized
           allArticles.add(serializer.read(ArticleXml.class, file).initializedArticle());
           }
           return allArticles;
           }
           else { return null; }
           }
   ```
3. ...
4. Nel punto __2.__ abbiamo descritto il procedimento di serializzazione e deserializzazione, in particolar modo dopo il secondo di questi ultimi vengono creati degli oggetti `ArticleXml` con cui poi è possibile lavorare per ottenere le informazioni necessari per l'estrazione dei termini ed il conteggio effettivo
   <br></br>
5. Per fare in modo che il sistema possa supportare nuove strutture di memorizzazione avendo accesso ai termini più importanti e conseguentemente alla possibilità di adottare nuove tecniche algoritmiche per la ricerca dei caratteri dati degli specifici criteri, abbiamo implementato uno __StrategyPattern__: Nel nostro caso il pattern è stato denominato come `WordCountStrategy` che si relaziona con la classe di "appoggio" `WordCounter` per fare si che si possano selezionare nuove strategie in base alle eseigenze momentaneee dell'utente.
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
   Ad esempio noi abbiamo implementato `FrequencyPerArticleStrategy` che va a calcolare il peso-frequenza delle parole degli articoli dome da inizziali richieste del progetto.
   <br></br>

6. Abbiamo ideato attraverso la libreria `org.apache.commons.cli` dei prompt che l'utente può utilizzare per decidere cosa vuole fare e come vuole utilizzare il programma.
    1. Nel dettaglio l'utente può scegliere se eseguire solo il download con il comando `-d`.
    2. In sequenza l'utente può anche decidere se effettuare l'estrazione partendo dai file dove sono stati memorizzati gli articoli attraverso il comando `-e`
    3. Altrimenti l'ulteriore alternativa è quella di scaricare e fare l'estrazione contemporaneamente con il comando `-de`
    4. Ulteriori comandi possibili sono elencati qua sotto:
       ```bash 
       usage: App -{ak} -{d,de} [OPTION]...
       -ak,--api-key <arg>       Set the guardian API
       -csv,--csv-input <arg>    Set new york times .csv file input path
       -d,--download             Dowload all articles form all the resources
       -de,--download-extract    Download and extract terms
       -h,--help                 Print this help message
       -xml,--xml-output <arg>   Set xml files output path
       ```  
# Come installare correttamente ed utilizzare il software

### Installazione
Per compilare il programma digitare :
```terminal
mvn package
```

### Esecuzione
Per eseguire il programma digitare :
```terminal
java -cp ./target/progetto-1.0-SNAPSHOT-jar-with-dependencies.jar -ak it.unipd.dei.eis.App 
```
