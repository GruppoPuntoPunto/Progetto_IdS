package it.unipd.dei.eis;

import junit.framework.TestCase;

public class ArticleJsonTest extends TestCase {
    public void testTitleRelatedMethods() {
        ArticleJsonGuardian prova = new ArticleJsonGuardian();
        prova.setTitle("Titolo di prova");
        assertEquals("Titolo di prova", prova.getTitle());
    }

    public void testBodyRelatedMethods() {
        ArticleJsonGuardian prova = new ArticleJsonGuardian();
        prova.setBody("Corpo di prova");
        assertEquals("Corpo di prova", prova.getBody());
    }
}