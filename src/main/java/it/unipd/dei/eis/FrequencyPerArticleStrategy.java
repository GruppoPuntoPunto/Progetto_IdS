package it.unipd.dei.eis;

import java.util.*;

public class FrequencyPerArticleStrategy implements WordCountStrategy {

    public FrequencyPerArticleStrategy() {}

    public List<Map.Entry<String, Integer>> execute(List<Article> articles) {
        Map<String, Integer> map = new HashMap<>();
        for (Article a : articles) {

            // pulizia punteggiatura dal corpo dell'articolo
            List<String> fullText = new ArrayList<>();
            fullText.addAll(Arrays.asList(a.getBody().toLowerCase().replaceAll("[^a-z0-9\\s]", "").split(" ")));
            fullText.addAll(Arrays.asList(a.getTitle().toLowerCase().replaceAll("[^a-z0-9\\s]", "").split(" ")));

            // elimino i doppioni inserendoli in un set
            Set<String> set = new HashSet<>(fullText);

            // inserisco le parole nella mappa
            for (String token : set) {
                int v = map.getOrDefault(token, 0);
                map.put(token, v + 1);
            }
        }

        // creo la lista di parole a partire dalla mappa
        List<Map.Entry<String, Integer>> lst = new ArrayList<>(map.entrySet());

        // riordino la lista: dalla parola più frequente alla meno
        lst.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return lst;
    }
}