package it.unipd.dei.eis.service;


import junit.framework.TestCase;
import org.simpleframework.xml.stream.CamelCaseStyle;
import org.simpleframework.xml.stream.Format;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class XmlSerializerTest extends TestCase {

    private final String inputPath = "src/main/resources/inputArticlesTest";
    private final String outputPath = "output/testOutput/Xml/";

    public void testSerializeAndDeserializeWithList() throws Exception {
        List<Article> articles = new ArrayList<>();

        // creo gli articoli
        File inputDirectory = new File(inputPath);
        File[] inputFiles = inputDirectory.listFiles();
        if (inputFiles != null) {
            for (File f : inputFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                    String title = reader.readLine(); // titolo inserito alla prima riga del file
                    String body = reader.readLine(); // corpo inserito tutto alla seconda riga del file
                    articles.add(new ArticleXml(title, body));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // serializzo
        XmlSerializer serializer = new XmlSerializer(outputPath + "1");
        serializer.serialize(articles);

        // deserializzo
        List<Article> deserialized = serializer.deserialize();

        assertNotNull(deserialized);
        assertEquals(articles.size(), deserialized.size());

        // ricerca sequenziale necessaria: l'ordine di deserializzazione dipende dall'ordine dei file nella cartella
        for (Article x : deserialized) {
            boolean found = false;
            for (Article y : articles) {
                if ((x.getTitle().equals(y.getTitle())) && (x.getBody().equals(y.getBody()))) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }
    }

    public void testSerializeAndDeserializeWithArray() throws Exception {
        List<Article> articlesList = new ArrayList<>();

        // creo gli articoli
        File inputDirectory = new File(inputPath);
        File[] inputFiles = inputDirectory.listFiles();
        if (inputFiles != null) {
            for (File f : inputFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                    String title = reader.readLine(); // titolo inserito alla prima riga del file
                    String body = reader.readLine(); // corpo inserito tutto alla seconda riga del file
                    articlesList.add(new ArticleXml(title, body));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // converto la lista in array
        Article[] articlesArray = new Article[articlesList.size()];
        articlesList.toArray(articlesArray);

        // serializzo
        XmlSerializer serializer = new XmlSerializer(outputPath + "2");
        serializer.serialize(articlesArray);

        // deserializzo
        List<Article> deserialized = serializer.deserialize();

        assertNotNull(deserialized);
        assertEquals(articlesArray.length, deserialized.size());

        // ricerca sequenziale necessaria: l'ordine di deserializzazione dipende dall'ordine dei file nella cartella
        for (Article x : deserialized) {
            boolean found = false;
            for (Article y : articlesArray) {
                if ((x.getTitle().equals(y.getTitle())) && (x.getBody().equals(y.getBody()))) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }
    }

    public void testSerializeWithArrayAndDirectory() {
        List<Article> articlesList = new ArrayList<>();

        // creo gli articoli
        File inputDirectory = new File(inputPath);
        File[] inputFiles = inputDirectory.listFiles();
        if (inputFiles != null) {
            for (File f : inputFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                    String title = reader.readLine(); // titolo inserito alla prima riga del file
                    String body = reader.readLine(); // corpo inserito tutto alla seconda riga del file
                    articlesList.add(new ArticleXml(title, body));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Article[] articlesArray = new Article[articlesList.size()];
        articlesList.toArray(articlesArray);

        // serializzo
        XmlSerializer serializer = new XmlSerializer(outputPath);
        serializer.serialize(articlesArray, outputPath + "3");

        File outputDirectory = new File(outputPath + "3");
        File[] outputFiles = outputDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
        assertNotNull(outputFiles);
        assertEquals(inputFiles.length, outputFiles.length);
    }

    public void testSerializeWithListAndDirectory() {
        List<Article> articles = new ArrayList<>();

        // creo gli articoli
        File inputDirectory = new File(inputPath);
        File[] inputFiles = inputDirectory.listFiles();
        if (inputFiles != null) {
            for (File f : inputFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                    String title = reader.readLine(); // titolo inserito alla prima riga del file
                    String body = reader.readLine(); // corpo inserito tutto alla seconda riga del file
                    articles.add(new ArticleXml(title, body));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // serializzo
        XmlSerializer serializer = new XmlSerializer(outputPath);
        serializer.serialize(articles, outputPath + "4");

        File outputDirectory = new File(outputPath + "4");
        File[] outputFiles = outputDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
        assertNotNull(outputFiles);
        assertEquals(inputFiles.length, outputFiles.length);
    }

    public void testDeserializeAsNull() throws Exception {
        XmlSerializer serializer = new XmlSerializer(outputPath + "5");
        assertNull(serializer.deserialize());
    }

    public void testDeserializeAsThrowedException() throws IOException {
        XmlSerializer serializer = new XmlSerializer(outputPath + "6");
        FileWriter writer = new FileWriter(outputPath + "6/" + "WrongXml.xml");
        writer.write("");
        writer.close();
        assertTrue(assertThrow(XMLStreamException.class, serializer::deserialize));
    }

    public void testDeserializeWithDirectory() throws Exception {
        List<Article> articles = new ArrayList<>();

        // creo gli articoli
        File inputDirectory = new File(inputPath);
        File[] inputFiles = inputDirectory.listFiles();
        if (inputFiles != null) {
            for (File f : inputFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                    String title = reader.readLine(); // titolo inserito alla prima riga del file
                    String body = reader.readLine(); // corpo inserito tutto alla seconda riga del file
                    articles.add(new ArticleXml(title, body));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // serializzo
        XmlSerializer serializer = new XmlSerializer(outputPath + "7");
        serializer.serialize(articles);

        // deserializzo
        List<Article> deserialized = serializer.deserialize();

        // ricerca sequenziale necessaria: l'ordine di deserializzazione dipende dall'ordine dei file nella cartella
        for (Article x : deserialized) {
            boolean found = false;
            for (Article y : articles) {
                if ((x.getTitle().equals(y.getTitle())) && (x.getBody().equals(y.getBody()))) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }
    }

    public void testDeserializeWithDirectoryAsNull1() throws Exception {
        XmlSerializer serializer = new XmlSerializer(outputPath + "8");
        assertNull(serializer.deserialize(outputPath + "8"));
    }

    public void testDeserializeWithDirectoryAsNull2() throws Exception {
        XmlSerializer serializer = new XmlSerializer(outputPath + "9");
        assertNull(serializer.deserialize(outputPath + "9000"));
    }

    public void testDeserializeWithDirectoryAsNull3() throws Exception {
        XmlSerializer serializer = new XmlSerializer(outputPath + "10");
        assertNull(serializer.deserialize(""));
    }

    public void testDeserializeWithDirectoryAsThrowedException1() throws IOException {
        XmlSerializer serializer = new XmlSerializer(outputPath + "11");
        FileWriter writer = new FileWriter(outputPath + "11/" + "WrongXml.xml");
        writer.write("");
        writer.close();
        assertTrue(assertThrow(XMLStreamException.class, () -> serializer.deserialize(outputPath + "11")));
    }

    public void testDeserializeWithDirectoryAsThrowedException2() {
        XmlSerializer serializer = new XmlSerializer(outputPath + "12");
        assertTrue(assertThrow(NullPointerException.class, () -> serializer.deserialize(null)));
    }

    public void testInitializedArticle() throws Exception {
        XmlSerializer serializer = new XmlSerializer( outputPath + "13");
        Article[] list = { new ArticleXml() };
        serializer.serialize(list);
        // initializedArticle is used inside deserialize()
        List<Article> test = serializer.deserialize();
        assertNotNull(test);
        assertEquals(1, test.size());
        assertEquals("", test.get(0).getTitle());
        assertEquals("", test.get(0).getBody());
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

    /* Exception testing elements */

    @FunctionalInterface
    private interface FunctionTester {
        void instruction() throws Exception;
    }

    private boolean assertThrow(Class<? extends Exception> exception, FunctionTester functionTester) {
        try {
            functionTester.instruction();
        } catch (Exception e) {
            return exception == e.getClass();
        }
        return false;
    }
}