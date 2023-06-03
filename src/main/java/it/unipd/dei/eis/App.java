package it.unipd.dei.eis;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Comparator;

public class App {
    public static void main(String[] args) {
        // la api-key viene passata da riga di comando
        if (args.length != 1) {
            System.out.println("Missing api-key");
            System.exit(1);
        }

        // creo le sorgenti del the guardian e new york times
        SourceFactory factory = SourceFactory.getInstance();
        Source guardianContentApi = factory.createSource("GuardianJSONSource", args[0]);
        Source nyTimesCSV = null;
        try { nyTimesCSV = factory.createSource("NewYorkTimesCSVSource", new FileReader("nytimes_articles_v2.csv")); }
        catch (IOException e) { e.printStackTrace(); }

        // creo una lista contenente tutti gli articoli delle sorgenti
        List<Article> allArticles = new ArrayList<>();
        allArticles.addAll(Arrays.asList(guardianContentApi.getArticles()));
        allArticles.addAll(Arrays.asList(nyTimesCSV.getArticles()));

        // conteggio frequenza e stampa resoconto
        WordCounter counter = new WordCounter(new FrequencyPerArticleStrategy());//creo l'oggetto counter e gli affido la strategia FrequencyPerArticle
        List<Map.Entry<String, Integer>> result = counter.count(allArticles);// salvo in una lista coppia stringa valore il resiultato dell'algoritmo precedentemente scelto

        // Cambio dinamicamente la strategia di conteggio
        //counter.setCountStrategy(nuova strategia);
        // Cambio dinamicamente la strategia di conteggio
        //counter.setCountStrategy(nuova strategia);


        for (Map.Entry<String, Integer> e : result)
            System.out.println(e.getKey() + " : " + e.getValue());

    }
}
