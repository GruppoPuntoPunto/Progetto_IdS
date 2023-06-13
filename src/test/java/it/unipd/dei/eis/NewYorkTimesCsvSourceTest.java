package it.unipd.dei.eis;

import junit.framework.TestCase;

import java.io.*;

public class NewYorkTimesCsvSourceTest extends TestCase {
    private final String outputPath = "output/testOutput/NewYorkTimesCsvSource/";

    public void setUp() throws IOException {
        File directory = new File(outputPath);
        if (!directory.exists())
            directory.mkdirs();
        FileWriter writer = new FileWriter(outputPath + "CSVexample.csv");
        writer.write("Identifier,URL,Title,Body,Date,Source Set,Source\n" +
                "b06d62b4-f99c-47eb-896d-b8d0a00f1c7b,http://query.nytimes.com/gst/fullpage.html?res=9D0CE6D7163FF934A35757C0A967958260,The World Held Its Breath,\"THE TRUTH ABOUT CHERNOBYL.\"\"\",1991-04-07T02:00:00.000+02:00,NYTIMES,arts books");
        FileWriter writer2 = new FileWriter(outputPath + "CSVexample2.csv");
        writer2.write("");
        writer.close();
        writer2.close();
    }

    public void testGetCSVInput() throws FileNotFoundException {
        FileReader b = new FileReader(outputPath + "CSVexample.csv");
        NewYorkTimesCsvSource prova = new NewYorkTimesCsvSource(b);
        assertEquals(b, prova.getCSVInput());
    }

    public void testSetCSVInput() throws FileNotFoundException {
        FileReader a = new FileReader(outputPath + "CSVexample.csv");
        NewYorkTimesCsvSource prova = new NewYorkTimesCsvSource(new FileReader(outputPath + "CSVexample.csv"));
        prova.setCSVInput(a);
        assertEquals(a, prova.getCSVInput());
    }

    public void testGetArticles() throws FileNotFoundException {
        NewYorkTimesCsvSource prova = new NewYorkTimesCsvSource(new FileReader(outputPath + "CSVexample.csv"));
        assertNotNull(prova.getArticles());
    }

    public void testSetArticles() throws FileNotFoundException {
        NewYorkTimesCsvSource prova = new NewYorkTimesCsvSource(new FileReader(outputPath + "CSVexample.csv"));
        Article[] list = { new ArticleXml("Titolo1", "Corpo1"), new ArticleXml("Titolo2", "Corpo2"), new ArticleXml("Titolo3", "Corpo3")};
        prova.setArticles(list);
        assertEquals(list, prova.getArticles());
    }

    public void testDownloadCorrectCSV() throws FileNotFoundException {
        NewYorkTimesCsvSource prova = new NewYorkTimesCsvSource(new FileReader(outputPath + "CSVexample.csv"));
        prova.download();
        assertNotNull(prova.getArticles());
        assertEquals(1, prova.getArticles().length);
        assertNotNull(prova.getArticles()[0].getBody());
        assertNotNull(prova.getArticles()[0].getTitle());
    }

    public void testDownloadWrongCSV() throws FileNotFoundException {
        NewYorkTimesCsvSource prova = new NewYorkTimesCsvSource(new FileReader(outputPath + "CSVexample2.csv"));
        prova.download();
        assertEquals(0, prova.getArticles().length);
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