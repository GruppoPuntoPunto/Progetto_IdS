package it.unipd.dei.eis;

import it.unipd.dei.eis.service.Source;
import it.unipd.dei.eis.service.Article;
import it.unipd.dei.eis.service.SourceFactory;
import it.unipd.dei.eis.service.WordCounter;
import it.unipd.dei.eis.service.FrequencyPerArticleStrategy;
import it.unipd.dei.eis.service.XmlSerializer;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.HelpFormatter;

public class App {
    // torna l'argomento dell'opzione se presente, altrimenti torna default
    private static String getOptionValueOrDefault(CommandLine cmd, String opt, String def) {
        if (cmd.hasOption(opt))
            return cmd.getOptionValue(opt);
        return def;
    }

    public static void main(String[] args) {
        // getione argomenti linea di comando
        Options opt = new Options();

        // definisco il gruppo di opzioni
        OptionGroup grp = new OptionGroup();
        grp.addOption(new Option("d", "download", false, "Dowload articles form all the sources"));
        grp.addOption(new Option("e", "extract", false, "Extract terms from all the downloaded files"));
        grp.addOption(new Option("de", "download-extract", false, "Download and extract terms"));
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

        // stampa help message
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

        // ### FASE DI DOWLOAD ###
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
            Source nyTimesCSV;
            try {
                nyTimesCSV = factory.createSource("NewYorkTimesCsvSource", new FileReader(nytCsvPath));
            } catch (IOException e) { 
                e.printStackTrace(); 
                return;
            }

            // dowload degli articoli dalle sorgenti
            System.out.println("INFO - Dowloading articles");
            guardianContentApi.download();
            nyTimesCSV.download();

            // unisco gli articoli delle sorgenti
            List<Article> allArticles = new ArrayList<>();
            allArticles.addAll(Arrays.asList(guardianContentApi.getArticles()));
            allArticles.addAll(Arrays.asList(nyTimesCSV.getArticles()));

            // serializzo gli articoli nei file xml
            System.out.println("INFO - Serializing articles");
            try {
                serializer.serialize(allArticles);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        // ### FASE DI ESTRAZIONE ###
        // deserializzo gli articoli partendo dai file xml
        if (cmd.hasOption("e") || cmd.hasOption("de")) {
            System.out.println("INFO - Deserializing articles");
            List<Article> deserializedArticles;
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
            List<Map.Entry<String, Integer>> results = counter.count(deserializedArticles);

            // stampa le prime 50 parole
            System.out.println("INFO - Extracting terms");
            FileWriter writer;
            try {
                writer = new FileWriter(resultsOutputPath);
                for (int i = 0; i < 50; i++)
                    writer.write(results.get(i).getKey() + " " + results.get(i).getValue() + '\n');
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            System.out.println("INFO - You can find the results in '" + resultsOutputPath + "'");
        }
    }
}
