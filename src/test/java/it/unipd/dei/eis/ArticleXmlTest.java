package it.unipd.dei.eis;

import junit.framework.TestCase;

public class ArticleXmlTest extends TestCase {
    public void testGetTitle() {
        ArticleXml prova = new ArticleXml("Titolo di prova", "Corpo di prova");
        assertEquals("Titolo di prova", prova.getTitle());
    }

    public void testSetTitle() {
        ArticleXml prova = new ArticleXml("Titolo di prova", "Corpo di prova");
        prova.setTitle("Nuovo titolo di prova");
        assertEquals("Nuovo titolo di prova", prova.getTitle());
    }

    public void testGetBody() {
        ArticleXml prova = new ArticleXml("Titolo di prova", "Corpo di prova");
        assertEquals("Corpo di prova", prova.getBody());
    }

    public void testSetBody() {
        ArticleXml prova = new ArticleXml("Titolo di prova", "Corpo di prova");
        prova.setBody("Nuovo corpo di prova");
        assertEquals("Nuovo corpo di prova", prova.getBody());
    }

    public void testTestToString() {
        ArticleXml prova = new ArticleXml("Titolo di prova", "Corpo di prova");
        assertEquals("\nArticleXml{title='Titolo di prova', body='Corpo di prova'}", prova.toString());
    }

    public void testInitializedArticle() {
        ArticleXml prova = new ArticleXml();
        assertEquals("", prova.initializedArticle().getTitle());
        assertEquals("", prova.initializedArticle().getBody());
    }
}