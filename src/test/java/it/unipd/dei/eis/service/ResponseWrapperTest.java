package it.unipd.dei.eis.service;

import junit.framework.TestCase;

public class ResponseWrapperTest extends TestCase {

    public void testGetResponse() {
        Response response = new Response("status", 1, 1, new ArticleJsonGuardian[0]);
        ResponseWrapper prova = new ResponseWrapper(response);
        assertEquals(response, prova.getResponse());
    }

    public void testSetResponse() {
        Response response = new Response("status", 1, 1, new ArticleJsonGuardian[0]);
        ResponseWrapper prova = new ResponseWrapper();
        prova.setResponse(response);
        assertEquals(response, prova.getResponse());
    }
}