package it.unipd.dei.eis;

import junit.framework.TestCase;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.simpleframework.xml.stream.CamelCaseStyle;
import org.simpleframework.xml.stream.Format;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class XmlSerializerTest extends TestCase {
    public void testSerializeWithArray() {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml1");
        Article[] list = { new ArticleXml("Titolo1", "Corpo1"), new ArticleXml("Titolo2", "Corpo2"), new ArticleXml("Titolo3", "Corpo3")};
        serializer.serialize(list);
    }

    public void testSerializeWithArrayAndDirectory() {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml2");
        Article[] list = { new ArticleXml("Titolo1", "Corpo1"), new ArticleXml("Titolo2", "Corpo2"), new ArticleXml("Titolo3", "Corpo3")};
        serializer.serialize(list, "TestOutput/Xml2/bin");
    }

    public void testSerializeWithList() {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml3");
        List<Article> list = new ArrayList<>();
        list.add(new ArticleXml("Titolo1", "Corpo1"));
        list.add(new ArticleXml("Titolo2", "Corpo2"));
        list.add(new ArticleXml("Titolo3", "Corpo3"));
        serializer.serialize(list);
    }

    public void testSerializeWithListAndDirectory() {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml4");
        List<Article> list = new ArrayList<>();
        list.add(new ArticleXml("Titolo1", "Corpo1"));
        list.add(new ArticleXml("Titolo2", "Corpo2"));
        list.add(new ArticleXml("Titolo3", "Corpo3"));
        serializer.serialize(list, "TestOutput/Xml4/bin");
    }



    public void testDeserialize() throws Exception {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml5", new Format(8, new CamelCaseStyle()));
        List<Article> list = new ArrayList<>();
        list.add(new ArticleXml("Titolo1", "Corpo1"));
        list.add(new ArticleXml("Titolo2", "Corpo2"));
        list.add(new ArticleXml("Titolo3", "Corpo3"));
        serializer.serialize(list);
        assertEquals(list.toString(), serializer.deserialize().toString());
    }

    public void testDeserializeAsNull() throws Exception {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml6");
        assertNull(serializer.deserialize());
    }

    public void testDeserializeWithDirectory() throws Exception {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml7");
        List<Article> list = new ArrayList<>();
        list.add(new ArticleXml("Titolo1", "Corpo1"));
        list.add(new ArticleXml("Titolo2", "Corpo2"));
        list.add(new ArticleXml("Titolo3", "Corpo3"));
        serializer.serialize(list);
        assertEquals(list.toString(), serializer.deserialize("TestOutput/Xml7").toString());
    }

    public void testDeserializeWithDirectoryAsNull1() throws Exception {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml8");
        assertNull(serializer.deserialize("TestOutput/Xml8"));
    }

    public void testDeserializeWithDirectoryAsNull2() throws Exception {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml9");
        assertNull(serializer.deserialize("TestOutput/Xml20000"));
    }

    public void testDeserializeWithDirectoryAsNull3() throws Exception {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml10");
        assertNull(serializer.deserialize(""));
    }

    public void testZCleanResources() {
        deleteFiles("TestOutput");
    }

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