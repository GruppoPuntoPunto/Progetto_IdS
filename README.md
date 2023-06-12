# Info Progetto
### Use case, Domain model, Desing Model
La parte dedicata agli use case, al domain model e al desing model è stata separata dal manuale per motivi pratici di formattazione essendo che generalmente i grafici __UML__ occupano uno spazio di rilveante importanza.

### Sistema Software
Il software verte sulla comunicazione con due sorgenti differenti (Api del The Guardian e un file CSV) per poter interfacciarsi e quindi scaricare
centinaia di articoli per poi effettuare delle operazioni di conteggio statistico sulle parole
utilizzate in ciascun estratto.

In risposta alle richieste software abbiamo adottato le seguenti implementazioni:

1. Per far si che il sistema possa supportare nuove sorgenti abbiamo ideato un __Facotry Pattern__ denominato `SourceFactory` che permette l'introduzione di nuovi source file diversi dovendo solo aggiungere una classe relativa alla nuova sorgente ma di fatto senza andare ad intaccare la struttura portante del proggetto.
   <br></br>
   In particolar modo con il metodo `createSource()` facciamo in modo di creare la sorgente derivante dal _The Guardian_ e quella relativa al _New York Times_
   ```java
   public Source createSource(String sourceType, Object... args) {
        if (sourceType.equals("GuardianJSONSource") && args[0] instanceof String && args[1] instanceof String)
            return new GuardianJSONSource((String) args[0], (String) args[1]);
        else if (sourceType.equals("NewYorkTimesCSVSource") && args[0] instanceof FileReader)
            return new NewYorkTimesCSVSource((FileReader) args[0]);
        return null;
    }
   ```
2. In seguito alla fase di Download e quindi alla creazione di oggetti `ArticleJSON` o `ArtcileCSV` il sistema apporta la serializzazione in file di estensione `.xml` passando per la classe `XmlSerializer` che offre anche la possibilità di attuare il procedimento inverso, atraverso una deserializzazione in oggetti di tipo `Article` e dunque in un formato _"universale"_. Il nostro `XmlSerializer` può essere di fatto interpretato come un __Adapter Pattern__ in quanto di fatto adatta alle nostre necessità l'interfaccia presa dalla libreria che abbiamo importato. ------
   <br></br>
   Ecco nel dettaglio i metodi fondamentali per quanto detto scritti grazie all'ausilio della libreria `org.simpleframework.xml`:

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
3.  Come già definito nel punto precedente nel nostro caso specifico otteniamo un processo di serializzazione in articoli in `ArticleJSON` e `ArticleCSV` (vedremmo anche `ArticleXML` che sarà in seguito spiegato  nel punto __4.__), tutte classi che implementano l'interfaccia `Article`, una soluzione dinamica che ci permette quindi di creare in futuro ulteriori possibili classi articolo in cui memorizzare la serializzazione proveniente da altri tipi di sorgente. Tutto ciò è possibile con una semplice nuova implementazione dell'interfaccia `Article`.
    <br></br>
4. Nel punto __2.__ abbiamo descritto il procedimento di serializzazione e deserializzazione, in particolar modo dopo il secondo di questi ultimi vengono creati degli oggetti `ArticleXml` con cui poi è possibile lavorare per ottenere le informazioni necessarie per l'estrazione dei termini ed il conteggio effettivo.
   <br></br>
5. Dato che il sistema deve poter supportare nuove strutture per memorizzare e poter accedere ai termini più importanti, abbiamo aggiunto uno __StrategyPattern__; quest'ultimo consente l'aggiunta di nuove strutture di memorizzazione e l'utilizzo di nuove tecniche algoritmiche per la ricerca dei caratteri in base a criteri specifici. Nel nostro caso il pattern è stato denominato come `WordCountStrategy` che si relaziona al _context object_ `WordCounter`, per far si che si possano selezionare nuove strategie in base alle eseigenze momentaneee dell'utente.
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
   Ad esempio noi abbiamo implementato `FrequencyPerArticleStrategy` che va a calcolare il peso-frequenza delle parole degli articoli come da iniziali richieste del progetto.
   <br></br>
6. Abbiamo ideato attraverso la libreria `org.apache.commons.cli` dei prompt che l'utente può utilizzare per decidere cosa vuole fare e come vuole utilizzare il programma.

   1. Nel dettaglio l'utente può scegliere se eseguire solo il download con il comando `-d`.
   2. In sequenza l'utente può anche decidere se effettuare l'estrazione a partire dai file dove sono stati memorizzati gli articoli tramite il comando `-e`
   3. Altrimenti un'ulteriore alternativa è quella di scaricare e fare l'estrazione contemporaneamente con il comando `-de`
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

### Installazione e compilazione del progetto Maven
Dopo essere entrati nella directory relativa al proggetto (`> Progetto_Ids`), per creare il file jar e compilare il codice è necessario digitare nel terminale il seguente comando
```terminal
mvn package
```
__NOTA__: I file jar verranno creati in automatico da Maven nella directory `Progetto_Ids > target`.
In particolare verranno create
- `progetto-1.0-SNAPSHOT.jar`
- `progetto-1.0-SNAPSHOT-jar-with-dependencies.jar`

Inoltre verrà generata la cartella `output` che inizzialmente dopo la compilazione conterrà solo quelli relativi ai test del proegetto.
### javadocs
Per generare i javadocs

    mvn javadoc:javadoc
### Mavensite
Abbiamo creato la cartella site che contiene i file sorgenti per creare il sito.
Le istruzioni necessarie per creare e rendere disponibile il sito sono:

    mvn site
    mvn site:run

Il sito sarà quindi disponibile al seguente indirizzo: [http://localhost:8080/](http://localhost:8080/)
### Esecuzione del programma
Per eseguire il programma sono possibili diversi prompt tra quelli qua sotto elencati :
```bash
usage: App -{ak} -{d,de} [OPTION]...
-ak,--api-key <arg>       Set the guardian API
-csv,--csv-input <arg>    Set new york times .csv file input path
-d,--download             Dowload all articles form all the resources
-de,--download-extract    Download and extract terms
-h,--help                 Print this help message
-xml,--xml-output <arg>   Set xml files output path
```
In particolar modo come già anticipato nel file delle info sul progetto l'utente può richiedere solo di effettuare il download con il comando:
```terminal
java -cp ./target/progetto-1.0-SNAPSHOT-jar-with-dependencies.jar it.unipd.dei.eis.App -ak <API-KEY> -d
```
In seguito può richiedere di effettuare l'estrazione e dunque procedere con la fase di serializzazione, deserializzazione e avviare l'algoritmo di conteggio specifico delle parole con il comando:
```terminal
java -cp ./target/progetto-1.0-SNAPSHOT-jar-with-dependencies.jar it.unipd.dei.eis.App -ak <API-KEY> -e
```
In alternativa a questi due comandi può direttamente farli insieme con il comando:
```terminal
java -cp ./target/progetto-1.0-SNAPSHOT-jar-with-dependencies.jar it.unipd.dei.eis.App -ak <API-KEY> -de
```
Dopo aver eseguito il programma in base al comando digitato verranno inseriti nella cartella `output` che conterrà le sotto cartelle
`outputJsonTheGuardian` e `outputXml`. La prima si occuperà della memorizzazione dei file provenienti dall' API del _The Guardian_, mentre la seconda cartella conterrà tutti i file __JSON__ e __CSV__ nel formato "universale" `.xml`.
# Librerie e versioni

1. `junit` version : 4.12
2. `com.fasterxml.jackson.core` version :  2.8.8.1 (_Libreria per leggere e scrivere da/in file JSON_)
3. `org.apache.commons` version : 1.10.0 (_Libreria per leggere e scrivere da/in file CSV_)
4. `org.simpleframework` version : 2.7.1 (_Libreria per leggere e scrivere da/in file xml_)
5. `commons-cli` version : 1.5.0 (_Libreria per gestire le opzioni specificate da linea di comando_)