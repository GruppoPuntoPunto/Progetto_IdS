package it.unipd.dei.eis.service;

/**
 *  This class extends the behavior of a {@link Response} object.
 *  <p> Every instance of this class is a wrapper that covers an internal <code>Response</code> instance. </p>
 *
 * @author unascribed
 * @since  0.1
 */
public class ResponseWrapper {
    /**
     * Internal instance of {@link Response}
     */
    private Response response;

    /**
     * Creates a new empty <code>ResponseWrapper</code> instance.
     *
     * @since 0.1
     */
    public ResponseWrapper() { }

    /**
     * Creates a new <code>ResponseWrapper</code> instance with given params.
     *
     * @param response The response {@link Response} element
     *
     * @since  0.1
     */
    public ResponseWrapper(Response response) { this.response = response; }

    /**
     * Returns the {@link Response} value of element response.
     *
     * @return The response element
     *
     * @since  0.1
     */
    public Response getResponse() { return response; }

    /**
     * Sets value of the response element with given param.
     *
     * @param response The new {@link Response} element
     *
     * @since  0.1
     */
    public void setResponse(Response response) { this.response = response; }
}
