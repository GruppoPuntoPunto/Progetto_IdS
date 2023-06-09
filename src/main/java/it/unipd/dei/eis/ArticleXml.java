package it.unipd.dei.eis;

import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Root;

@Root
public class ArticleXml implements  Article{
    @Element(name = "title", required = false)
    private String title;
    @Element(name = "body", required = false)
    private String body;


    public ArticleXml() {}

    public ArticleXml(String title, String body) {
        this.title = title;
        this.body = body;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "\nArticleXml{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                "}";
    }
}
