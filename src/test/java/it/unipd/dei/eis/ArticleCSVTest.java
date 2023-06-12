package it.unipd.dei.eis;

import junit.framework.TestCase;

public class ArticleCSVTest extends TestCase {

    public void testGetTitle() {
        ArticleCSV prova = new ArticleCSV("Titolo di prova", "Corpo di prova");
        assertEquals("Titolo di prova", prova.getTitle());
    }

    public void testGetBody() {
        ArticleCSV prova = new ArticleCSV("Titolo di prova", "Corpo di prova");
        assertEquals("Corpo di prova", prova.getBody());
    }

    public void testSetTitleAndSetBody() {
        ArticleCSV prova = new ArticleCSV();
        prova.setTitle("Nuovo titolo di prova");
        prova.setBody("Nuovo corpo di prova");
        assertEquals("Nuovo titolo di prova", prova.getTitle());
        assertEquals("Nuovo corpo di prova", prova.getBody());
    }
}