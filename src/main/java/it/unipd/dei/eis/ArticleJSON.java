package it.unipd.dei.eis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleJSON implements Article {
    private String webTitle;
    private String bodyText;

    public ArticleJSON() { }

    public String getBodyText() { return bodyText; }

    public String getBody() { return getBodyText(); }

    public String getWebTitle() { return webTitle; }

    public String getTitle() { return getWebTitle(); }
 
    public void setTitle(String title) { this.webTitle = title; }

    public void setBody(String body) { this.bodyText = body; }

    @JsonProperty("fields")
    private void unpackNasted(Map<String, Object> fields) {
        this.bodyText = (String) fields.get("bodyText");
    }

    @Override
    public String toString() { return bodyText; }
}
