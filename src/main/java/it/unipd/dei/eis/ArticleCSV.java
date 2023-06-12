package it.unipd.dei.eis;

/**
 *  Implementation of {@link Article} representing a csv file object.
 *
 * @author unascribed
 * @since  0.1
 */
public class ArticleCSV implements Article {
    /**
     * Title element of the csv file.
     */
    private String title;
    /**
     * Body element of the csv file.
     */
    private String body;

    /**
     * Creates a new empty <code>ArticleCSV</code> instance.
     *
     * @since 0.1
     */
    public ArticleCSV() {}

    /**
     * Creates a new <code>ArticleCSV</code> instance with given params.
     *
     * @param title Title element string
     * @param body Body element string
     *
     * @since 0.1
     */
    public ArticleCSV(String title, String body) { 
        this.body = body;
        this.title = title;
    }

    /**
     *  Returns the string value of element title.
     *
     * @return The title element
     *
     * @since 0.1
     */
    public String getTitle() { return title; }

    /**
     * Returns the string value of element body.
     *
     * @return The body element
     *
     * @since 0.1
     */
    public String getBody() { return body; }

    /**
     * Sets value of the title element with given param.
     *
     * @param title Title element string to set
     *
     * @since 0.1
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * Sets value of the body element with given param.
     *
     * @param body Body element string to set
     *
     * @since 0.1
     */
    public void setBody(String body) { this.body= body; }
}

