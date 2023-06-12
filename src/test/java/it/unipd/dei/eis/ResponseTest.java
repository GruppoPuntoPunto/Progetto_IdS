package it.unipd.dei.eis;

import junit.framework.TestCase;

public class ResponseTest extends TestCase {
    public void testGetStatus() {
        Response prova = new Response("status", "userTier", 1,  1, 1,
        1, 1, "orderBy", new ArticleJSON[0]);
        assertEquals("status", prova.getStatus());
    }

    public void testGetTotal() {
        Response prova = new Response("status", "userTier", 10,  1, 1,
                1, 1, "orderBy", new ArticleJSON[0]);
        assertEquals(10, prova.getTotal());
    }

    public void testGetPages() {
        Response prova = new Response("status", "userTier", 1,  1, 1,
                1, 10, "orderBy", new ArticleJSON[0]);
        assertEquals(10, prova.getPages());
    }

    public void testGetResults() {
        Response prova = new Response("status", "userTier", 1,  1, 1,
                1, 1, "orderBy", new ArticleJSON[1]);
        Response prova2 = new Response();
        assertEquals(1, prova.getResults().length);
        assertNull(prova2.getResults());
    }
}