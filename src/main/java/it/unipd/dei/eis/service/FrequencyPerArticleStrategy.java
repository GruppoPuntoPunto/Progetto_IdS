package it.unipd.dei.eis.service;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 *  Implementation of {@link WordCountStrategy} that defines the counting operations of a {@link WordCounter}.
 *  <p> An instance of this class can count the frequency of the words from an {@link Article} <code>List</code> and store them in an ordered {@code List<Map.Entry<String, Integer>>},
 *  and it's meant to used by way of a <code>WordCounter</code> instance. </p>
 *
 * @author unascribed
 * @since  0.1
 */
public class FrequencyPerArticleStrategy implements WordCountStrategy {
    /**
     * Creates a new empty <code>FrequencyPerArticleStrategy</code> instance.
     *
     * @since 0.1
     */
    public FrequencyPerArticleStrategy() {}

    /**
     * Method that performs all the counting operations of words of an {@link Article} <code>List</code> in an ordered {@code List<Map.Entry<String, Integer>>}.
     *
     * @param articles {@link Article} <code>List</code> to be processed
     *
     * @return An ordered {@code List<Map.Entry<String, Integer>>} of counted words
     *
     * @since 0.1
     */
    @Override
    public List<Map.Entry<String, Integer>> execute(List<Article> articles) {
        if (articles == null) return new ArrayList<Map.Entry<String, Integer>>() {};

        Map<String, Integer> map = new HashMap<>();
        for (Article a : articles) {

            // pulizia punteggiatura dal corpo dell'articolo
            List<String> fullText = new ArrayList<>();
            fullText.addAll(Arrays.asList(a.getTitle().toLowerCase().replaceAll("[^a-z0-9\\s]", "").split(" ")));
            fullText.addAll(Arrays.asList(a.getBody().toLowerCase().replaceAll("[^a-z0-9\\s]", "").split(" ")));

            // elimino i doppioni inserendoli in un set
            Set<String> set = new HashSet<>(fullText);

            // inserisco le parole nella mappa
            for (String token : set) {
                if (token.length() == 0) continue; // le stringhe non riconosciute vengono scartate
                int v = map.getOrDefault(token, 0);
                map.put(token, v + 1);
            }
        }

        // creo la lista di parole a partire dalla mappa
        List<Map.Entry<String, Integer>> lst = new ArrayList<>(map.entrySet());

        // riordino la lista dalla parola più frequente alla meno
        mergeSort(lst);
        return lst;
    }

    /**
     * Sorts the given {@code List<Map.Entry<String, Integer>>} using the Merge Sort algorithm.
     *
     * @param lst {@code List<Map.Entry<String, Integer>>} to be sorted
     *
     * @since 0.1
     */
    private void mergeSort(List<Map.Entry<String, Integer>> lst) {
        if (lst.size() < 2) return;

        int middle = lst.size() / 2;
        List<Map.Entry<String, Integer>> left = new ArrayList<>(lst.subList(0, middle));
        List<Map.Entry<String, Integer>> right = new ArrayList<>(lst.subList(middle, lst.size()));

        mergeSort(left);
        mergeSort(right);

        merge(lst, left, right);
    }

    /**
     * Merges two sorted {@code List<Map.Entry<String, Integer>>} into one sorted {@code List<Map.Entry<String, Integer>>}.
     *
     * @param lst The {@code List<Map.Entry<String, Integer>>} to store the merged result
     * @param left The left sorted {@code List<Map.Entry<String, Integer>>}
     * @param right The right sorted {@code List<Map.Entry<String, Integer>>}
     *
     * @since 0.1
     */
    private void merge(List<Map.Entry<String, Integer>> lst,
                             List<Map.Entry<String, Integer>> left,
                             List<Map.Entry<String, Integer>> right) {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            // ordino in base alla frequenza associata alla parola
            if (left.get(i).getValue() > right.get(j).getValue())
                lst.set(k++, left.get(i++));
            else if (left.get(i).getValue() < right.get(j).getValue())
                lst.set(k++, right.get(j++));
            else {
                // se hanno stessa frequenza uso l'ordine alfabetico
                if (left.get(i).getKey().compareTo(right.get(j).getKey()) <= 0)
                    lst.set(k++, left.get(i++));
                else
                    lst.set(k++, right.get(j++));
            }
        }

        while (i < left.size())
            lst.set(k++, left.get(i++));
        while (j < right.size())
            lst.set(k++, right.get(j++));
    }
}
