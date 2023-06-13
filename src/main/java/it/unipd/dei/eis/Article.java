package it.unipd.dei.eis;

/**
 *  Instances of classes that implement this interface are used to be newspaper articles.
 *  <p>These articles are designed to have, at least,a <code>title</code> and a <code>body</code> as attributes.</p>
 *
 *  @author unascribed
 *  @since  0.1
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
