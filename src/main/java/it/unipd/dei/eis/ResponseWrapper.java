package it.unipd.dei.eis;

/**
 * @author unascribed
 * @since  0.1
 */
public class ResponseWrapper {
    private Response response;

    public ResponseWrapper() { }

    public ResponseWrapper(Response response) { this.response = response; }

    public Response getResponse() { return response; }

    public void setResponse(Response response) { this.response = response; }
}
