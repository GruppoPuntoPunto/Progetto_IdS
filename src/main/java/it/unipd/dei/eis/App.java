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
    // metodo che riceve in input una lista di articoli e restituisce una lista 
    // ordinata delle parole più frequenti per articolo (ordine decrescente)
    public static List<Map.Entry<String, Integer>> countWordsFrequency(List<Article> articles) {
        // conteggio
        Map<String, Integer> map = new HashMap<>();
        for (Article a : articles) {
            Set<String> set = new HashSet<>(Arrays.asList(a.getBody().toLowerCase().split(" ")));
            for (String token : set) {
                int k = map.getOrDefault(token.toLowerCase(), 0);
                map.put(token.toLowerCase(), k+1);
            }
        }

        // riordino
        List<Map.Entry<String, Integer>> lst = new ArrayList<>(map.entrySet());
        lst.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return lst;
    }

    public static void main(String[] args) {
        // la api-key viene passata da riga di comando
        if (args.length != 1) {
            System.out.println("Missing api-key");
            System.exit(1);
        }

        // creo le sorgenti sorgenti
        SourceFactory factory = SourceFactory.getInstance();
        Source guardianContentApi = factory.createSource("GuardianContentApi", args[0]);
        Source nyTimesCSV = null;
        try { nyTimesCSV = factory.createSource("NewYorkTimesCSV", new FileReader("nytimes_articles_v2.csv")); }
        catch (IOException e) { e.printStackTrace(); }

        // creo una lista contenente tutti gli articoli delle sorgenti
        List<Article> allArticles = new ArrayList<>();
        allArticles.addAll(Arrays.asList(guardianContentApi.getArticles()));
        allArticles.addAll(Arrays.asList(nyTimesCSV.getArticles()));

        // conteggio frequenza e stampa resoconto
        List<Map.Entry<String, Integer>> result = countWordsFrequency(allArticles);
        for (Map.Entry<String, Integer> e : result)
            System.out.println(e.getKey() + " : " + e.getValue());

    }
}
