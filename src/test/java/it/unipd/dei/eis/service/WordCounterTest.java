package it.unipd.dei.eis.service;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WordCounterTest extends TestCase {

    public void testSetCountStrategy() {
        WordCounter prova = new WordCounter(new FrequencyPerArticleStrategy());
        FrequencyPerArticleStrategy strategy = new FrequencyPerArticleStrategy();
        prova.setCountStrategy(strategy);
        assertNotNull(prova);
    }

    public void testCount() {
        WordCounter prova = new WordCounter(new FrequencyPerArticleStrategy());
        List<Article> list = new ArrayList<>();
        list.add(new ArticleXml("Titolo1", "Corpo"));
        list.add(new ArticleXml("Titolo2", "Corpo2"));
        list.add(new ArticleXml("Titolo3", "Corpo"));

        List<Map.Entry<String, Integer>> entryList = prova.count(list);
        int valueEntry1 = entryList.get(0).getValue();
        int valueEntry2 = entryList.get(1).getValue();

        if(entryList.get(0).getKey().equals("Corpo2")) {
            int temp;
            temp = valueEntry1;
            valueEntry1 = valueEntry2;
            valueEntry2 = temp;
        }
        assertEquals(2, valueEntry1);
        assertEquals(1, valueEntry2);
    }
}