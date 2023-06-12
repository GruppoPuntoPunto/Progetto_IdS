package it.unipd.dei.eis;

import junit.framework.TestCase;
import java.io.File;

public class GuardianJSONSourceTest extends TestCase {

    private final String apiKey = "d882b87f-6009-434f-9076-af23bd12b56f";
    private final String outputPath = "output/TestOutput/Json";

    public void testGetDirectory() {
        GuardianJSONSource prova = new GuardianJSONSource(apiKey, outputPath);
        assertEquals(new File(outputPath), prova.getDirectory());
    }

    public void testSetDirectory() {
        GuardianJSONSource prova = new GuardianJSONSource(apiKey, outputPath);
        prova.setDirectory(new File(outputPath));
        assertEquals(new File(outputPath), prova.getDirectory());
    }

    public void testGetArticles() {
        GuardianJSONSource prova = new GuardianJSONSource(apiKey, outputPath);
        assertNotNull(prova.getArticles());
    }

    public void testSetArticles() {
        GuardianJSONSource prova = new GuardianJSONSource(apiKey, outputPath);
        Article[] list = { new ArticleXml("Titolo1", "Corpo1"), new ArticleXml("Titolo2", "Corpo2"), new ArticleXml("Titolo3", "Corpo3")};
        prova.setArticles(list);
        assertEquals(list, prova.getArticles());
    }

    /*public void testCorrectDownload() {
        GuardianJSONSource prova = new GuardianJSONSource(<validApiKey>,"TestOutput/Json");
        prova.download();
        assertFalse(prova.getArticles().length == 0);
    }*/

    public void testCorrectDownload() {
        GuardianJSONSource prova = new GuardianJSONSource(apiKey, outputPath);
        prova.download();
        assertFalse(prova.getArticles().length == 0);
    }

    public void tearDown() throws Exception { deleteFiles(outputPath); }

    private static void deleteFiles(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteFiles(file.getAbsolutePath());
                    } else {
                        file.delete();
                    }
                }
            }
        }
    }
}