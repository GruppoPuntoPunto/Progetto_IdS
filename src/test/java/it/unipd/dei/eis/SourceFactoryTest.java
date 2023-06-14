package it.unipd.dei.eis;

import junit.framework.TestCase;

import java.io.*;

public class SourceFactoryTest extends TestCase {

    private final String outputPath = "output/testOutput/Source/";

    public void setUp() throws IOException {
        File directory = new File(outputPath);
        if (!directory.exists())
            directory.mkdirs();
        FileWriter writer = new FileWriter(outputPath + "CSVexample.csv");
        writer.write("Identifier,URL,Title,Body,Date,Source Set,Source\n" +
                "b06d62b4-f99c-47eb-896d-b8d0a00f1c7b,http://query.nytimes.com/gst/fullpage.html?res=9D0CE6D7163FF934A35757C0A967958260,The World Held Its Breath,\"THE TRUTH ABOUT CHERNOBYL.\"\"\",1991-04-07T02:00:00.000+02:00,NYTIMES,arts books");
        writer.close();
    }

    public void testGetInstance() {
        SourceFactory prova = SourceFactory.getInstance();
        assertNotNull(prova);
    }

    public void testCreateSourceGuardianJSONSource() {
        SourceFactory factory = SourceFactory.getInstance();
        String[] args = {"aaa", outputPath};
        Source prova = factory.createSource("GuardianJsonSource", args);
        assertNotNull(prova);
    }

    public void testCreateSourceNewYorkTimesCSVSource() throws FileNotFoundException {
        SourceFactory factory = SourceFactory.getInstance();
        Source prova = factory.createSource("NewYorkTimesCsvSource", new FileReader(outputPath + "CSVexample.csv"));
        assertNotNull(prova);
    }

    public void testCreateSourceNull() {
        SourceFactory factory = SourceFactory.getInstance();
        Source prova = factory.createSource("", "");
        assertNull(prova);
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