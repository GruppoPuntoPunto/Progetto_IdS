package it.unipd.dei.eis;

import junit.framework.TestCase;

public class ArticleJSONTest extends TestCase {
    public void testTitleRelatedMethods() {
        ArticleJSON prova = new ArticleJSON();
        prova.setTitle("Titolo di prova");
        assertEquals("Titolo di prova", prova.getTitle());
    }

    public void testBodyRelatedMethods() {
        ArticleJSON prova = new ArticleJSON();
        prova.setBody("Corpo di prova");
        assertEquals("Corpo di prova", prova.getBody());
    }
}