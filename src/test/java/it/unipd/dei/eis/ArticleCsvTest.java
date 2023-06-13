package it.unipd.dei.eis;

import junit.framework.TestCase;

public class ArticleCsvTest extends TestCase {

    public void testGetTitle() {
        ArticleCsvNYTimes prova = new ArticleCsvNYTimes("Titolo di prova", "Corpo di prova");
        assertEquals("Titolo di prova", prova.getTitle());
    }

    public void testGetBody() {
        ArticleCsvNYTimes prova = new ArticleCsvNYTimes("Titolo di prova", "Corpo di prova");
        assertEquals("Corpo di prova", prova.getBody());
    }

    public void testSetTitleAndSetBody() {
        ArticleCsvNYTimes prova = new ArticleCsvNYTimes();
        prova.setTitle("Nuovo titolo di prova");
        prova.setBody("Nuovo corpo di prova");
        assertEquals("Nuovo titolo di prova", prova.getTitle());
        assertEquals("Nuovo corpo di prova", prova.getBody());
    }
}