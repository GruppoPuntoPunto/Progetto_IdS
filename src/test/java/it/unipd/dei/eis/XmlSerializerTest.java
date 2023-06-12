package it.unipd.dei.eis;

import junit.framework.TestCase;
import org.simpleframework.xml.stream.CamelCaseStyle;
import org.simpleframework.xml.stream.Format;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlSerializerTest extends TestCase {

    private final String outputPath = "output/TestOutput/Xml/";

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

    public void testDeserializeWithDirectory() throws Exception {
        XmlSerializer serializer = new XmlSerializer( outputPath + "7");
        List<Article> list = new ArrayList<>();
        list.add(new ArticleXml("Titolo", "Corpo"));
        list.add(new ArticleXml("Titolo", "Corpo"));
        list.add(new ArticleXml("Titolo", "Corpo"));
        serializer.serialize(list);
        assertEquals(list.toString(), serializer.deserialize(outputPath + "7").toString());
    }

    public void testDeserializeWithDirectoryAsNull1() throws Exception {
        XmlSerializer serializer = new XmlSerializer(outputPath + "8");
        assertNull(serializer.deserialize(outputPath + "8"));
    }

    public void testDeserializeWithDirectoryAsNull2() throws Exception {
        XmlSerializer serializer = new XmlSerializer(outputPath + "9");
        assertNull(serializer.deserialize(outputPath + "900"));
    }

    public void testDeserializeWithDirectoryAsNull3() throws Exception {
        XmlSerializer serializer = new XmlSerializer(outputPath + "10");
        assertNull(serializer.deserialize(""));
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