package it.unipd.dei.eis.service;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *  Implementation of {@link Article} representing a xml file object.
 *
 * @author unascribed
 * @since  0.1
 */
@Root
public class ArticleXml implements  Article{
    /**
     * Title element of the xml file.
     */
    @Element(name = "title", required = false)
    private String title;

    /**
     * Body element of the xml file.
     */
    @Element(name = "body", required = false)
    private String body;

    /* -- Constructors -- */

    /**
     * Creates a new empty <code>ArticleXml</code> instance.
     *
     * @since 0.1
     */
    public ArticleXml() {}

    /**
     * Creates a new <code>ArticleXml</code> instance with given params.
     *
     * @param title Title <code>String</code> element
     * @param body Body <code>String</code> element
     *
     * @since 0.1
     */
    public ArticleXml(String title, String body) {
        this.title = title;
        this.body = body;
    }

    /* -- Attribute accessors -- */

    /**
     *  Returns the <code>String</code> value of element title.
     *
     * @return The title element
     *
     * @since 0.1
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Sets value of the title element with given param.
     *
     * @param title The new title element <code>String</code>
     *
     * @since 0.1
     */
    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the <code>String</code> value of element body.
     *
     * @return The body element
     *
     * @since 0.1
     */
    @Override
    public String getBody() {
        return body;
    }

    /**
     * Sets value of the body element with given param.
     *
     * @param body The new body <code>String</code> element
     *
     * @since 0.1
     */
    @Override
    public void setBody(String body) {
        this.body = body;
    }

    /* -- Other methods -- */

    /**
     * Returns a <code>String</code> variable with all the object attributes.
     *
     * @return A string representation of the object
     *
     * @since 0.1
     */
    @Override
    public String toString() {
        return "\nArticleXml{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                "}";
    }
}
