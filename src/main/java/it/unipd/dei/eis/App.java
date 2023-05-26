package it.unipd.dei.eis;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import java.util.Comparator;
import java.util.HashSet;

public class App {
    public static List<Map.Entry<String, Integer>> countWordsFrequency(Article[] articles) {
        // conteggio
        HashMap<String, Integer> map = new HashMap<>();
        for (Article a : articles) {
            HashSet<String> set = new HashSet<>(Arrays.asList(a.getBody().toLowerCase().split(" ")));
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

    public static List<Map.Entry<String, Integer>> merge(List<Map.Entry<String, Integer>> list1, List<Map.Entry<String, Integer>> list2) {
        ArrayList<Map.Entry<String, Integer>> mergedList = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < list1.size() && j < list2.size()) {
            if (list1.get(i).getValue().compareTo(list2.get(j).getValue()) > 0) {
                mergedList.add(list1.get(i)); i++;
            }
            else {
                mergedList.add(list2.get(j)); j++;
            }
        }
        while (i < list1.size()) { mergedList.add(list1.get(i)); i++;}
        while (j < list2.size()) { mergedList.add(list2.get(i)); j++;}
        return mergedList;
    }

    public static void main(String[] args) {
        // JSON
        GuardianContentApi api = new GuardianContentApi("<api-key>");
        Response response = api.getContent();

        // CSV
        CSVContent csv = null;
        try { csv = new CSVContent(new FileReader("nytimes_articles_v2.csv")); }
        catch (IOException e) { System.out.println(e); }

        List<Map.Entry<String, Integer>> result = null;
        result = merge(countWordsFrequency(response.getResults()), countWordsFrequency(csv.getArticles()));
        for (Map.Entry<String, Integer> e : result)
            System.out.println(e.getKey() + " : " + e.getValue());

    }
}
