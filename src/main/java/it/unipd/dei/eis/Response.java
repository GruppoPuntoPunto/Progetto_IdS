package it.unipd.dei.eis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *  Class representing a web request response with particular specifics.
 *
 * @author unascribed
 * @since  0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    /**
     * Status of the response.
     */
    private String status;

    /**
     * Total number of requested responses.
     */
    private int total;

    /**
     * Pages number of the response.
     */
    private int pages;

    /**
     * Internal array of {@link ArticleJsonGuardian}
     */
    private ArticleJsonGuardian[] results;

    /**
     * Creates a new empty <code>Response</code> instance.
     *
     * @since 0.1
     */
    public Response() { }

    /**
     * Creates a new <code>Response</code> instance with given params.
     *
     * @param status The status <code>String</code> element
     * @param total The total<code>int</code> element
     * @param pages The pages <code>int</code> element
     * @param results The results {@link ArticleJsonGuardian} array element
     *
     * @since 0.1
     */
    public Response(String status, int total, int pages, ArticleJsonGuardian[] results) {
        this.status = status;
        this.total = total;
        this.pages = pages;
        this.results = results;
    }

    /**
     * Returns the <code>String</code> value of element status.
     *
     * @return The status element
     *
     * @since 0.1
     */
    public String getStatus() { return status; }

    /**
     * Returns the <code>int</code> value of element total.
     *
     * @return The total element
     *
     * @since 0.1
     */
    public int getTotal() { return total; }

    /**
     * Returns the <code>int</code> value of element pages.
     *
     * @return The pages element
     *
     * @since 0.1
     */
    public int getPages() { return pages; }

    /**
     * Returns the {@link ArticleJsonGuardian} array value of element results.
     *
     * @return The results element
     *
     * @since 0.1
     */
    public ArticleJsonGuardian[] getResults() { return results; }
}
