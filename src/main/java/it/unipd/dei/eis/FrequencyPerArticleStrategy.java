package it.unipd.dei.eis;

import java.util.*;

//-->Rivedere problema del carattere fantasma "vai a capo"
public class FrequencyPerArticleStrategy implements WordCountStrategy {

    public List<Map.Entry<String, Integer>> execute(List<Article> articles) { //Ritorna una lista costituita da coppia stringa valore

        Map<String, Integer> map = new HashMap<>(); //Creazione di una mappa che userò come magazzino per capire quali sono le parole più frequenti
        for (Article a : articles) {
            // pulizia punteggiatura dal corpo dell'articolo
            List<String> body = Arrays.asList(a.getBody().toLowerCase().replaceAll("[^a-z0-9\\s]\\p{C}", "").split(" ")); //passo da array a lista, dove mi serve per invocare in seguito la creazione del set, chiamo a cascata vari metodi per flitrare e buttare in un array con split
            // elimino i doppioni inserendoli in un set, che è la sua unica utilità
            Set<String> set = new HashSet<>(body);
            for (String token : set) {
                int v = map.getOrDefault(token, 0); // prende dalla mappa
                map.put(token, v + 1);
            }
        }
        // riordino
        List<Map.Entry<String, Integer>> lst = new ArrayList<>(map.entrySet());
        lst.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        return lst;
    }

}
