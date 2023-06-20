package it.unipd.dei.eis.service;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.File;

public class FrequencyPerArticleStrategyTest extends TestCase {

    private final String inputPath = "src/main/resources/inputArticlesTest";

    public void testExecute() {
        List<Article> list = new ArrayList<>();

        File directory = new File(inputPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                    String title = reader.readLine(); // titolo inserito sulla prima riga del file
                    String body = reader.readLine(); // corpo inserito tutto sulla seconda riga del file
                    list.add(new ArticleXml(title, body));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        FrequencyPerArticleStrategy strategy = new FrequencyPerArticleStrategy();
        List<Map.Entry<String, Integer>> entryList = strategy.execute(list);
        String[] ranking = {"in", "of", "the", "and", "to"};

        for (int i = 0; i < ranking.length; i++)
            assertEquals(ranking[i], entryList.get(i).getKey());
    }
}