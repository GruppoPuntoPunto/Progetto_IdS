package it.unipd.dei.eis;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.File;

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
     * Creates a new empty <code>ArticleXml</code> instance
     *
     * @since 0.1
     */
    public ArticleXml() {}

    /**
     * Creates a new <code>ArticleXml</code> instance with given params.
     *
     * @param title Title element string
     * @param body Body element string
     *
     * @since 0.1
     */
    public ArticleXml(String title, String body) {
        this.title = title;
        this.body = body;
    }

    /* -- Attribute accessors -- */

    /**
     *  Returns the string value of element title
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
     * Sets value of the title element with given param
     *
     * @param title Title element string to set
     *
     * @since 0.1
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the string value of element body
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
     * Sets value of the body element with given param
     *
     * @param body Body element string to set
     *
     * @since 0.1
     */
    public void setBody(String body) {
        this.body = body;
    }

    /* -- Other methods -- */

    /**
     * Returns a string variable with all the object attributes
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

    /**
     * Checks all the element of the object, if an element is null, it's changed to an empty string, in order to get an object full initialized
     *
     * @return The object with all initialized elements
     *
     * @since 0.1
     */
    public Article initializedArticle() {
        if(this.getTitle() == null) this.setTitle("");
        if(this.getBody() == null) this.setBody("");
        return this;
    }
}
