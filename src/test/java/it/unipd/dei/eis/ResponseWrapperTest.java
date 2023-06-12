package it.unipd.dei.eis;

import junit.framework.TestCase;

public class ResponseWrapperTest extends TestCase {

    public void testGetResponse() {
        ResponseWrapper prova = new ResponseWrapper();
        assertNull(prova.getResponse());
    }
}