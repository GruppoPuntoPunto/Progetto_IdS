package it.unipd.dei.eis.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 *  Implementation of {@link Article} representing a The Guardian json file object.
 *
 * @author unascribed
 * @since  0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleJsonGuardian implements Article {
    /**
     * WebTitle element of the json file.
     */
    private String webTitle;
    /**
     * BodyText element of the json file.
     */
    private String bodyText;

    /**
     * Creates a new empty <code>ArticleJsonGuardian</code> instance.
     *
     * @since 0.1
     */
    public ArticleJsonGuardian() { }

    /**
     * Returns the <code>String</code> value of element bodyText.
     *
     * @return The bodyText element
     *
     * @since 0.1
     */
    public String getBodyText() { return bodyText; }

    /**
     * Returns the <code>String</code> value of element bodyText.
     *
     * @return The bodyText element
     *
     * @since 0.1
     */
    @Override
    public String getBody() { return getBodyText(); }

    /**
     *  Returns the <code>String</code> value of element webTitle.
     *
     * @return The webTitle element
     *
     * @since 0.1
     */
    public String getWebTitle() { return webTitle; }

    /**
     *  Returns the <code>String</code> value of element webTitle.
     *
     * @return The webTitle element
     *
     * @since 0.1
     */
    @Override
    public String getTitle() { return getWebTitle(); }

    /**
     * Sets value of the webTitle element with given param.
     *
     * @param title The new webTitle <code>String</code> element
     *
     * @since 0.1
     */
    @Override
    public void setTitle(String title) { this.webTitle = title; }

    /**
     * Sets value of the bodyText element with given param.
     *
     * @param body The new bodyText <code>String</code> element
     *
     * @since 0.1
     */
    @Override
    public void setBody(String body) { this.bodyText = body; }

    /**
     * Sets value of the bodyText element with given param instead of managing it as a {@code Map<String, Object>} object.
     *
     * @param fields The new {@code Map<String, Object>} element
     *
     * @since 0.1
     */
    @JsonProperty("fields")
    private void unpackNasted(Map<String, Object> fields) {
        this.bodyText = (String) fields.get("bodyText");
    }
}
