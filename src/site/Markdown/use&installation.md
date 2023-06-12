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

