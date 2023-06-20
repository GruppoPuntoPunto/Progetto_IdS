package it.unipd.dei.eis.service;


import junit.framework.TestCase;
import org.simpleframework.xml.stream.CamelCaseStyle;
import org.simpleframework.xml.stream.Format;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlSerializerTest extends TestCase {

    private final String outputPath = "output/testOutput/Xml/";

    public void testSerializeWithArray() {
        XmlSerializer serializer = new XmlSerializer(outputPath + "1");
        Article[] list = { new ArticleXml("Titolo1", "Corpo1"), new ArticleXml("Titolo2", "Corpo2"), new ArticleXml("Titolo3", "Corpo3")};
        serializer.serialize(list);

        File directory = new File(outputPath + "1");
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
        assertNotNull(files);
        assertEquals(3, files.length);
    }

    public void testSerializeWithArrayAndDirectory() {
        XmlSerializer serializer = new XmlSerializer(outputPath);
        Article[] list = { new ArticleXml("Titolo1", "Corpo1"), new ArticleXml("Titolo2", "Corpo2"), new ArticleXml("Titolo3", "Corpo3")};
        serializer.serialize(list, outputPath + "2");

        File directory = new File(outputPath + "2");
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
        assertNotNull(files);
        assertEquals(3, files.length);
    }

    public void testSerializeWithList() {
        XmlSerializer serializer = new XmlSerializer(outputPath + "3");
        List<Article> list = new ArrayList<>();
        list.add(new ArticleXml("Titolo1", "Corpo1"));
        list.add(new ArticleXml("Titolo2", "Corpo2"));
        list.add(new ArticleXml("Titolo3", "Corpo3"));
        serializer.serialize(list);

        File directory = new File(outputPath + "3");
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
        assertNotNull(files);
        assertEquals(3, files.length);
    }

    public void testSerializeWithListAndDirectory() {
        XmlSerializer serializer = new XmlSerializer(outputPath);
        List<Article> list = new ArrayList<>();
        list.add(new ArticleXml("Titolo1", "Corpo1"));
        list.add(new ArticleXml("Titolo2", "Corpo2"));
        list.add(new ArticleXml("Titolo3", "Corpo3"));
        serializer.serialize(list, outputPath + "4");

        File directory = new File(outputPath + "4");
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
        assertNotNull(files);
        assertEquals(3, files.length);
    }



    public void testDeserialize() throws Exception {
        XmlSerializer serializer = new XmlSerializer(outputPath + "5", new Format(8, new CamelCaseStyle()));
        List<Article> list = new ArrayList<>();
        list.add(new ArticleXml("Titolo", "Corpo"));
        list.add(new ArticleXml("Titolo", "Corpo"));
        list.add(new ArticleXml("Titolo", "Corpo"));
        serializer.serialize(list);
        assertEquals(list.toString(), serializer.deserialize().toString());
    }

    public void testDeserializeAsNull() throws Exception {
        XmlSerializer serializer = new XmlSerializer(outputPath + "6");
        assertNull(serializer.deserialize());
    }

    public void testDeserializeAsThrowedException() throws IOException {
        XmlSerializer serializer = new XmlSerializer(outputPath + "7");
        FileWriter writer = new FileWriter(outputPath + "7/" + "WrongXml.xml");
        writer.write("");
        writer.close();
        assertTrue(assertThrow(XMLStreamException.class, serializer::deserialize));
    }

    public void testDeserializeWithDirectory() throws Exception {
        XmlSerializer serializer = new XmlSerializer( outputPath + "8");
        List<Article> list = new ArrayList<>();
        list.add(new ArticleXml("Titolo", "Corpo"));
        list.add(new ArticleXml("Titolo", "Corpo"));
        list.add(new ArticleXml("Titolo", "Corpo"));
        serializer.serialize(list);
        assertEquals(list.toString(), serializer.deserialize(outputPath + "8").toString());
    }

    public void testDeserializeWithDirectoryAsNull1() throws Exception {
        XmlSerializer serializer = new XmlSerializer(outputPath + "9");
        assertNull(serializer.deserialize(outputPath + "9"));
    }

    public void testDeserializeWithDirectoryAsNull2() throws Exception {
        XmlSerializer serializer = new XmlSerializer(outputPath + "10");
        assertNull(serializer.deserialize(outputPath + "1000"));
    }

    public void testDeserializeWithDirectoryAsNull3() throws Exception {
        XmlSerializer serializer = new XmlSerializer(outputPath + "11");
        assertNull(serializer.deserialize(""));
    }

    public void testDeserializeWithDirectoryAsThrowedException1() throws IOException {
        XmlSerializer serializer = new XmlSerializer(outputPath + "12");
        FileWriter writer = new FileWriter(outputPath + "12/" + "WrongXml.xml");
        writer.write("");
        writer.close();
        assertTrue(assertThrow(XMLStreamException.class, () -> serializer.deserialize(outputPath + "12")));
    }

    public void testDeserializeWithDirectoryAsThrowedException2() {
        XmlSerializer serializer = new XmlSerializer(outputPath + "13");
        assertTrue(assertThrow(NullPointerException.class, () -> serializer.deserialize(null)));
    }

    public void testInitializedArticle() throws Exception {
        XmlSerializer serializer = new XmlSerializer( outputPath + "14");
        Article[] list = { new ArticleXml() };
        serializer.serialize(list);
        // initializedArticle is used inside deserialize()
        List<Article> prova = serializer.deserialize();
        assertNotNull(prova);
        assertEquals(1, prova.size());
        assertEquals("", prova.get(0).getTitle());
        assertEquals("", prova.get(0).getBody());
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
