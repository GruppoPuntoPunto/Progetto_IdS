# Come installare correttamente ed utilizzare il software

## Installazione e compilazione del progetto Maven
Per prima cosa bisogna modificare il file java `GuardianJsonSourceTest` inserendo la propria chiave api.

Successivamente, dopo essere entrati nella directory relativa al proggetto (`Progetto_Ids`), per creare il file jar e compilare il codice, è necessario digitare da terminale il seguente comando:
```terminal
mvn package
```
__NOTA__: I file jar verranno creati in automatico da Maven nella directory `Progetto_Ids/target`.
In particolare verranno create
- `progetto-1.0-SNAPSHOT.jar`
- `progetto-1.0-SNAPSHOT-jar-with-dependencies.jar`

Inoltre verrà generata la cartella `output` che inizialmente dopo la compilazione conterrà solo quelli relativi ai test del proegetto.

## Generare Javadoc
Per generare i javadocs

    mvn javadoc:javadoc

## Mavensite
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
## Esecuzione del programma
Per eseguire il programma sono possibili diversi comandi prompt sotto elencati:

```terminal 
 usage: App -{d,e,de,h} [OPTION]...
 -ak,--api-key <arg>       Set the guardian API
 -csv,--csv-input <arg>    Set new york times .csv file input path
 -d,--download             Dowload all articles form all the sources
 -de,--download-extract    Download and extract terms
 -h,--help                 Print this help message
 -o,--output <arg>         Set results output file path          
 -xml,--xml-output <arg>   Set xml files input path (deserialize from) or output path (serialize in)
```

###  Download ed estrazione

In particolar modo l'utente può richiedere di effettuare solo il download con il comando:
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
Dopo aver eseguito il programma i file generati verranno inseriti nella cartella `output` che conterrà a sua volta le sottocartelle
`outputJsonTheGuardian` e `outputXml`. La prima conterrà i file risposta delle chiamate API del _The Guardian_, la seconda cartella conterrà tutti i file __JSON__ e __CSV__ nel formato standard `.xml`.
Il risultato del conteggio si troverà in `output/results.txt'.


### Comandi extra

Il parametro `-xml` può essere aggiunto nella fase di download per specificare la cartella in cui inserire i file serializzati degli articoli. In alternativa può essere utilizzato nella fase di estrazione per impostare la cartella da cui deserializzare gli articoli:
```terminal
java -cp ./target/progetto-1.0-SNAPSHOT-jar-with-dependencies.jar it.unipd.dei.eis.App -xml <Path> -d
```
Infine con il parametro `-csv` si può importare un ulteriore file csv del New York Times che sia però compatibile con gli header prefissati:
```terminal
java -cp ./target/progetto-1.0-SNAPSHOT-jar-with-dependencies.jar it.unipd.dei.eis.App -csv <Path> -d
```

- [Back to home](index.html)
