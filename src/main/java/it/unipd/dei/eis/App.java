package it.unipd.dei.eis;

import java.io.FileNotFoundException;
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
        OptionGroup grp1 = new OptionGroup(); // gruppo per per la chiave api
        grp1.addOption(new Option("ak", "api-key", true, "Set the guardian API"));

        OptionGroup grp2= new OptionGroup(); // gruppo per download e download+estrazione
        grp2.addOption(new Option("d", "download", false, "Dowload all articles form all the resources"));
        grp2.addOption(new Option("de", "download-extract", false, "Download and extract terms"));

        grp2.setRequired(true);
        grp2.setRequired(true);
        opt.addOptionGroup(grp1);
        opt.addOptionGroup(grp2);

        // possible options
        opt.addOption(new Option("h", "help", false, "Print this help message"));
        opt.addOption(new Option("csv", "csv-input", true, "Set new york times .csv file input path"));
        opt.addOption(new Option("xml", "xml-output", true, "Set xml files output path"));

        // gestione help a parte, in questo modo separo le opzioni
        Options helpOpt = new Options();
        helpOpt.addOption(new Option("h", "help", false, "Print this help message"));

        // parse
        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        CommandLine help;
        try { 
            // controllo se sono state passate le opzioni obbligatorie
            cmd = parser.parse(opt, args); 
        }  catch (org.apache.commons.cli.ParseException e) {
            try {
                // controllo se è presente l'opzione help
                help = parser.parse(helpOpt, args);
                formatter.printHelp("App -{ak} -{d,de} [OPTION]...", opt);
            }  catch (org.apache.commons.cli.ParseException ex) {
                System.err.println("ERROR - parsing command line:");
                System.err.println(e.getMessage());
                formatter.printHelp("App -{ak} -{d,de} [OPTION]...", opt);
            }
            return;
        }

        // controllo se tra i comandi passati è presente help
        if (cmd.hasOption("h")) {
            formatter.printHelp("App -{ak} -{d,de} [OPTION]...", opt);
            return;
        }

        // prelevo l'api
        String apiKey = cmd.getOptionValue("ak");

        // controllo passaggio file path csv nytimes
        String nytCsvPath = getOptionValueOrDefault(cmd, "csv", "src/main/resources/nytimes_articles_v2.csv");

        // controllo passaggio path files xml
        String xmlOutputPath = getOptionValueOrDefault(cmd, "xml", "output/outputXml/");

        // creo le sorgenti del The Guardian e del New York Times
        SourceFactory factory = SourceFactory.getInstance();
        Source guardianContentApi = factory.createSource("GuardianJSONSource", apiKey, "output/outJsonTheGuardian");
        Source nyTimesCSV = null;
        try { 
            nyTimesCSV = factory.createSource("NewYorkTimesCSVSource", new FileReader(nytCsvPath)); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        }

        // effettuo il download dalle sorgenti
        guardianContentApi.download();
        nyTimesCSV.download();

        // creo una lista contenente tutti gli articoli delle sorgenti
        List<Article> allArticles = new ArrayList<>();
        allArticles.addAll(Arrays.asList(guardianContentApi.getArticles()));
        allArticles.addAll(Arrays.asList(nyTimesCSV.getArticles()));

        // serializzo gli articoli nei file xml
        XmlSerializer serializer = new XmlSerializer(xmlOutputPath);
        try {
            serializer.serialize(allArticles);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // fine fase di download
        if (cmd.hasOption("d")) return;

        // deserializzo gli articoli partendo dai file xml
        List<Article> deserializedArticles = new ArrayList<>();
        try {
            deserializedArticles.addAll(serializer.deserialize());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // setto la strategia di conteggio delle parole ed effettuo il conteggio
        WordCounter counter = new WordCounter(new FrequencyPerArticleStrategy());
        List<Map.Entry<String, Integer>> result = counter.count(deserializedArticles);
        // stampa le prime 50 parolo più frequenti
        for (int i = 0; i < 50; i++)
            System.out.println(result.get(i));
    }
}
