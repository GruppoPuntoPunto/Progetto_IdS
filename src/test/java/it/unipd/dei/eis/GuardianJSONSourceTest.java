package it.unipd.dei.eis;

import junit.framework.TestCase;
import java.io.File;

public class GuardianJSONSourceTest extends TestCase {

    public void testGetTargetUrl() {
        GuardianJSONSource prova = new GuardianJSONSource("aaa","TestOutput/Json");
        assertEquals("https://content.guardianapis.com/search?show-fields=all", prova.getTargetUrl());
    }

    public void testGetDirectory() {
        GuardianJSONSource prova = new GuardianJSONSource("aaa","TestOutput/Json");
        assertEquals(new File("TestOutput/Json"), prova.getDirectory());
    }

    public void testSetDirectory() {
        GuardianJSONSource prova = new GuardianJSONSource("aaa","TestOutput");
        prova.setDirectory(new File("TestOutput/Json"));
        assertEquals(new File("TestOutput/Json"), prova.getDirectory());
    }

    public void testGetOS() {
        GuardianJSONSource prova = new GuardianJSONSource("aaa","TestOutput/Json");
        assertEquals(System.getProperty("os.name").toLowerCase(), prova.getOS());
    }

    public void testGetApiKey() {
        GuardianJSONSource prova = new GuardianJSONSource("aaa","TestOutput/Json");
        assertEquals("aaa", prova.getApiKey());
    }

    public void testGetArticles() {
        GuardianJSONSource prova = new GuardianJSONSource("aaa","TestOutput/Json");
        assertNotNull(prova.getArticles());
    }

    public void testSetArticles() {
        GuardianJSONSource prova = new GuardianJSONSource("aaa","TestOutput/Json");
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
        GuardianJSONSource prova = new GuardianJSONSource("d882b87f-6009-434f-9076-af23bd12b56f","TestOutput/Json");
        prova.download();
        assertFalse(prova.getArticles().length == 0);
    }

    public void tearDown() throws Exception { deleteFiles("TestOutput/Json"); }

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