package it.unipd.dei.eis.service;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WordCounterTest extends TestCase {

    private final String inputPath = "src/main/resources/inputArticlesTest";

    public void testSetCountStrategy() {
        WordCounter prova = new WordCounter(new FrequencyPerArticleStrategy());
        FrequencyPerArticleStrategy strategy = new FrequencyPerArticleStrategy();
        prova.setCountStrategy(strategy);
        assertNotNull(prova);
    }

    public void testCount() {
        List<Article> list = new ArrayList<>();

        File directory = new File(inputPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                    String title = reader.readLine(); // titolo inserito alla prima riga del file
                    String body = reader.readLine(); // corpo inserito tutto alla seconda riga del file
                    list.add(new ArticleXml(title, body));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        WordCounter counter = new WordCounter(new FrequencyPerArticleStrategy());
        List<Map.Entry<String, Integer>> entryList = counter.count(list);

        assertFalse(entryList.size() == 0);
    }
}