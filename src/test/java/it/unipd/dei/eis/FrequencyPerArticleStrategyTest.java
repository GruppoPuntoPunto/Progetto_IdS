package it.unipd.dei.eis;

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
        int valueEntry1 = entryList.get(0).getValue();
        int valueEntry2 = entryList.get(1).getValue();
        int valueEntry3 = entryList.get(2).getValue();
        int valueEntry4 = entryList.get(3).getValue();
        int valueEntry5 = entryList.get(4).getValue();

        assertEquals(5, valueEntry1);
        assertEquals(5, valueEntry2);
        assertEquals(5, valueEntry3);
        assertEquals(5, valueEntry4);
        assertEquals(5, valueEntry5);
    }
}