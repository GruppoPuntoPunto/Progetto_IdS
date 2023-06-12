package it.unipd.dei.eis;

import junit.framework.TestCase;
import org.simpleframework.xml.stream.CamelCaseStyle;
import org.simpleframework.xml.stream.Format;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlSerializerTest extends TestCase {
    public void testSerializeWithArray() {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml/1");
        Article[] list = { new ArticleXml("Titolo1", "Corpo1"), new ArticleXml("Titolo2", "Corpo2"), new ArticleXml("Titolo3", "Corpo3")};
        serializer.serialize(list);

        File directory = new File("TestOutput/Xml/1");
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
        assertNotNull(files);
        assertEquals(3, files.length);
    }

    public void testSerializeWithArrayAndDirectory() {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml");
        Article[] list = { new ArticleXml("Titolo1", "Corpo1"), new ArticleXml("Titolo2", "Corpo2"), new ArticleXml("Titolo3", "Corpo3")};
        serializer.serialize(list, "TestOutput/Xml/2");

        File directory = new File("TestOutput/Xml/2");
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
        assertNotNull(files);
        assertEquals(3, files.length);
    }

    public void testSerializeWithList() {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml/3");
        List<Article> list = new ArrayList<>();
        list.add(new ArticleXml("Titolo1", "Corpo1"));
        list.add(new ArticleXml("Titolo2", "Corpo2"));
        list.add(new ArticleXml("Titolo3", "Corpo3"));
        serializer.serialize(list);

        File directory = new File("TestOutput/Xml/3");
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
        assertNotNull(files);
        assertEquals(3, files.length);
    }

    public void testSerializeWithListAndDirectory() {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml");
        List<Article> list = new ArrayList<>();
        list.add(new ArticleXml("Titolo1", "Corpo1"));
        list.add(new ArticleXml("Titolo2", "Corpo2"));
        list.add(new ArticleXml("Titolo3", "Corpo3"));
        serializer.serialize(list, "TestOutput/Xml/4");

        File directory = new File("TestOutput/Xml/4");
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
        assertNotNull(files);
        assertEquals(3, files.length);
    }



    public void testDeserialize() throws Exception {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml/5", new Format(8, new CamelCaseStyle()));
        List<Article> list = new ArrayList<>();
        list.add(new ArticleXml("Titolo1", "Corpo1"));
        list.add(new ArticleXml("Titolo2", "Corpo2"));
        list.add(new ArticleXml("Titolo3", "Corpo3"));
        serializer.serialize(list);
        assertEquals(list.toString(), serializer.deserialize().toString());
    }

    public void testDeserializeAsNull() throws Exception {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml/6");
        assertNull(serializer.deserialize());
    }

    public void testDeserializeWithDirectory() throws Exception {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml/7");
        List<Article> list = new ArrayList<>();
        list.add(new ArticleXml("Titolo1", "Corpo1"));
        list.add(new ArticleXml("Titolo2", "Corpo2"));
        list.add(new ArticleXml("Titolo3", "Corpo3"));
        serializer.serialize(list);
        assertEquals(list.toString(), serializer.deserialize("TestOutput/Xml/7").toString());
    }

    public void testDeserializeWithDirectoryAsNull1() throws Exception {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml/8");
        assertNull(serializer.deserialize("TestOutput/Xml/8"));
    }

    public void testDeserializeWithDirectoryAsNull2() throws Exception {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml/9");
        assertNull(serializer.deserialize("TestOutput/Xml/900"));
    }

    public void testDeserializeWithDirectoryAsNull3() throws Exception {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml/10");
        assertNull(serializer.deserialize(""));
    }

    public void tearDown() throws Exception { deleteFiles("TestOutput/Xml"); }

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