package it.unipd.dei.eis;

/**
 *  Implementation of {@link Article} representing a csv file object.
 *
 * @author unascribed
 * @since  0.1
 */
public class ArticleCsvNYTimes implements Article {
    /**
     * Title element of the csv file.
     */
    private String title;
    /**
     * Body element of the csv file.
     */
    private String body;

    /**
     * Creates a new empty <code>ArticleCsvNYTimes</code> instance.
     *
     * @since 0.1
     */
    public ArticleCsvNYTimes() {}

    /**
     * Creates a new <code>ArticleCsvNYTimes</code> instance with given params.
     *
     * @param title Title <code>String</code> element
     * @param body Body <code>String</code> element
     *
     * @since 0.1
     */
    public ArticleCsvNYTimes(String title, String body) {
        this.body = body;
        this.title = title;
    }

    /**
     *  Returns the <code>String</code> value of element title.
     *
     * @return The title element
     *
     * @since 0.1
     */
    @Override
    public String getTitle() { return title; }

    /**
     * Returns the <code>String</code> value of element body.
     *
     * @return The body element
     *
     * @since 0.1
     */
    @Override
    public String getBody() { return body; }

    /**
     * Sets value of the title element with given param.
     *
     * @param title The new title <code>String</code> element
     *
     * @since 0.1
     */
    @Override
    public void setTitle(String title) { this.title = title; }

    /**
     * Sets value of the body element with given param.
     *
     * @param body The new body <code>String</code> element
     *
     * @since 0.1
     */
    @Override
    public void setBody(String body) { this.body= body; }
}

