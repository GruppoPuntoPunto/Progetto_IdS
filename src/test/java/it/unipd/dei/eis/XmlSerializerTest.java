package it.unipd.dei.eis;

import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.simpleframework.xml.stream.CamelCaseStyle;
import org.simpleframework.xml.stream.Format;

import java.util.ArrayList;
import java.util.List;

public class XmlSerializerTest extends TestCase {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testSerializeWithArray() {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml1");
        Article[] list = { new ArticleXml("Titolo1", "Corpo1"), new ArticleXml("Titolo2", "Corpo2"), new ArticleXml("Titolo3", "Corpo3")};
        serializer.serialize(list);
    }

    @Test
    public void testSerializeWithArrayAndDirectory() {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml2");
        Article[] list = { new ArticleXml("Titolo1", "Corpo1"), new ArticleXml("Titolo2", "Corpo2"), new ArticleXml("Titolo3", "Corpo3")};
        serializer.serialize(list, "TestOutput/Xml2/bin");
    }

    @Test
    public void testSerializeWithList() {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml3");
        List<Article> list = new ArrayList<>();
        list.add(new ArticleXml("Titolo1", "Corpo1"));
        list.add(new ArticleXml("Titolo2", "Corpo2"));
        list.add(new ArticleXml("Titolo3", "Corpo3"));
        serializer.serialize(list);
    }

    @Test
    public void testSerializeWithListAndDirectory() {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml4");
        List<Article> list = new ArrayList<>();
        list.add(new ArticleXml("Titolo1", "Corpo1"));
        list.add(new ArticleXml("Titolo2", "Corpo2"));
        list.add(new ArticleXml("Titolo3", "Corpo3"));
        serializer.serialize(list, "TestOutput/Xml4/bin");
    }



    @Test
    public void testDeserialize() throws Exception {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml5", new Format(8, new CamelCaseStyle()));
        List<Article> list = new ArrayList<>();
        list.add(new ArticleXml("Titolo1", "Corpo1"));
        list.add(new ArticleXml("Titolo2", "Corpo2"));
        list.add(new ArticleXml("Titolo3", "Corpo3"));
        serializer.serialize(list);
        assertEquals(list.toString(), serializer.deserialize().toString());
    }

    @Test
    public void testDeserializeAsNull() throws Exception {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml6");
        assertNull(serializer.deserialize());
    }

    @Test
    public void testDeserializeWithDirectory() throws Exception {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml8");
        List<Article> list = new ArrayList<>();
        list.add(new ArticleXml("Titolo1", "Corpo1"));
        list.add(new ArticleXml("Titolo2", "Corpo2"));
        list.add(new ArticleXml("Titolo3", "Corpo3"));
        serializer.serialize(list);
        assertEquals(list.toString(), serializer.deserialize("TestOutput/Xml8").toString());
    }

    @Test
    public void testDeserializeWithDirectoryAsNull1() throws Exception {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml9");
        assertNull(serializer.deserialize("TestOutput/Xml9"));
    }

    @Test
    public void testDeserializeWithDirectoryAsNull2() throws Exception {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml10");
        assertNull(serializer.deserialize("TestOutput/Xml20000"));
    }

    @Test
    public void testDeserializeWithDirectoryAsNull3() throws Exception {
        XmlSerializer serializer = new XmlSerializer("TestOutput/Xml11");
        assertNull(serializer.deserialize(""));
    }
}