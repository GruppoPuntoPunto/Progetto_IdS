package it.unipd.dei.eis;

/**
 *  Instances of classes that implement this interface are used to be newspaper articles. They are designed to have as attributes a <code>title</code> and a <code>body</code>.
 */
public interface Article {
    /**
     *  Returns the string value of element title.
     *
     * @return The title element
     *
     * @since 0.1
     */
    String getTitle();

    /**
     * Returns the string value of element body.
     *
     * @return The body element
     *
     * @since 0.1
     */
    String getBody();

    /**
     * Sets value of the body element with given param.
     *
     * @param body Body element string to set
     *
     * @since 0.1
     */
    void setBody(String body);

    /**
     * Sets value of the title element with given param.
     *
     * @param title Title element string to set
     *
     * @since 0.1
     */
    void setTitle(String title);
}
