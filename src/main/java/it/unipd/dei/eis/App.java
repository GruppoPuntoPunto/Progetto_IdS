package it.unipd.dei.eis;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.apache.commons.cli.*;

public class App {
    // torna l'argomento dell'opzione se presente, altrimenti torna default
    public static String getOptionValueOrDefault(CommandLine cmd, String opt, String def) {
        if (cmd.hasOption(opt))
            return cmd.getOptionValue(opt);
        return def;
    }

    public static void main(String[] args) {
        // getione argomenti linea di comando
        Options opt = new Options();

        // definisco i gruppi di opzioni
        OptionGroup grp = new OptionGroup(); // gruppo per download e download+estrazione
        grp.addOption(new Option("d", "download", false, "Dowload articles form all the sources"));
        grp.addOption(new Option("e", "extract", false, "Extracts terms from all the downloaded files"));
        grp.addOption(new Option("de", "download-extract", false, "Download articles and extracts terms"));
        grp.addOption(new Option("h", "help", false, "Print this help message"));

        grp.setRequired(true);
        opt.addOptionGroup(grp);

        // opzioni possibili 
        opt.addOption(new Option("ak", "api-key", true, "Set the guardian API"));
        opt.addOption(new Option("csv", "csv-input", true, "Set new york times .csv file input path"));
        opt.addOption(new Option("xml", "xml-output", true, "Set xml articles files input path (deserialize from) or output path (serialize in)"));
        opt.addOption(new Option("o", "output", true, "Set results output file path"));

        // parse
        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try { 
            // controllo se sono state passate le opzioni obbligatorie
            cmd = parser.parse(opt, args); 
        }  catch (org.apache.commons.cli.ParseException e) {
            System.err.println("ERROR - parsing command line:");
            System.err.println(e.getMessage());
            formatter.printHelp("App -{d,e,de,h} [OPTION]...", opt);
            return;
        }

        // controllo se tra i comandi passati è presente help
        if (cmd.hasOption("h")) {
            formatter.printHelp("App -{d,e,de,h} [OPTION]...", opt);
            return;
        }

        // controllo passaggio file path csv nytimes
        String nytCsvPath = getOptionValueOrDefault(cmd, "csv", "src/main/resources/nytimes_articles_v2.csv");

        // controllo passaggio path files xml
        String xmlOutputPath = getOptionValueOrDefault(cmd, "xml", "output/outputXml/");

        // controllo passaggio file path output risultati
        String resultsOutputPath = getOptionValueOrDefault(cmd, "o", "output/results.txt");

        // definisco il serializzatore
        XmlSerializer serializer = new XmlSerializer(xmlOutputPath);

        // effettuo il download dalle sorgenti e serializzo
        if (cmd.hasOption("d") || cmd.hasOption("de")) {
            // prelevo l'api
            String apiKey = getOptionValueOrDefault(cmd, "ak", null);
            if (apiKey == null) {
                System.err.println("ERROR - Missing -ak <api-key>");
                return;
            }

            // creo le sorgenti del The Guardian e del New York Times
            SourceFactory factory = SourceFactory.getInstance();
            Source guardianContentApi = factory.createSource("GuardianJsonSource", apiKey, "output/outputJsonTheGuardian");
            Source nyTimesCSV = null;
            try { 
                nyTimesCSV = factory.createSource("NewYorkTimesCsvSource", new FileReader(nytCsvPath));
            } catch (IOException e) { 
                e.printStackTrace(); 
                return;
            }

            guardianContentApi.download();
            nyTimesCSV.download();

            // unisco gli articoli delle sorgenti
            List<Article> allArticles = new ArrayList<>();
            allArticles.addAll(Arrays.asList(guardianContentApi.getArticles()));
            allArticles.addAll(Arrays.asList(nyTimesCSV.getArticles()));

            // serializzo gli articoli nei file xml
            try {
                serializer.serialize(allArticles);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        // deserializzo gli articoli partendo dai file xml
        if (cmd.hasOption("e") || cmd.hasOption("de")) {
            List<Article> deserializedArticles = new ArrayList<>();
            try {
                deserializedArticles = serializer.deserialize();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            // controllo se la desrializzazione è andata a buon fine
            if (deserializedArticles == null) {
                System.err.println("ERROR - You need to download before extract");
                return;
            }

            // setto la strategia di conteggio delle parole ed effettuo il conteggio
            WordCounter counter = new WordCounter(new FrequencyPerArticleStrategy());
            List<Map.Entry<String, Integer>> result = counter.count(deserializedArticles);

            // stampa le prime 50 parole
            FileWriter writer = null;
            try {
                writer = new FileWriter(resultsOutputPath);
                for (int i = 0; i < 50; i++)
                    writer.write(result.get(i).getKey() + " " + result.get(i).getValue() + '\n');
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
