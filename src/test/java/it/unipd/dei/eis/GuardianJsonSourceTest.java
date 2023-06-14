package it.unipd.dei.eis;

import junit.framework.TestCase;
import java.io.File;

public class GuardianJsonSourceTest extends TestCase {

    private final String apiKey = "d882b87f-6009-434f-9076-af23bd12b56f";
    private final String outputPath = "output/testOutput/Json";

    public void testGetDirectory() {
        GuardianJsonSource prova = new GuardianJsonSource(apiKey, outputPath);
        assertEquals(new File(outputPath), prova.getDirectory());
    }

    public void testSetDirectory() {
        GuardianJsonSource prova = new GuardianJsonSource(apiKey, outputPath);
        prova.setDirectory(new File(outputPath));
        assertEquals(new File(outputPath), prova.getDirectory());
    }

    public void testGetArticles() {
        GuardianJsonSource prova = new GuardianJsonSource(apiKey, outputPath);
        assertNotNull(prova.getArticles());
    }

    public void testSetArticles() {
        GuardianJsonSource prova = new GuardianJsonSource(apiKey, outputPath);
        Article[] list = { new ArticleXml("Titolo1", "Corpo1"), new ArticleXml("Titolo2", "Corpo2"), new ArticleXml("Titolo3", "Corpo3")};
        prova.setArticles(list);
        assertEquals(list, prova.getArticles());
    }

    public void testCorrectDownload() {
        GuardianJsonSource prova = new GuardianJsonSource(apiKey, outputPath);
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