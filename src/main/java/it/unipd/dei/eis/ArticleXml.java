package it.unipd.dei.eis;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class ArticleXml implements  Article{
    @Element(required = false)
    private String Title;
    @Element(required = false)
    private String Body;
    @Element(required = false)
    private String WebTitle;
    @Element(required = false)
    private String BodyText;


    public ArticleXml() {}

    public ArticleXml(String title, String body, String webTitle, String bodyText) {
        Title = title;
        Body = body;
        WebTitle = webTitle;
        BodyText = bodyText;
    }

    public ArticleXml(String webTitle, String bodyText) {
        WebTitle = webTitle;
        BodyText = bodyText;
    }

    public String getWebTitle() {
        return WebTitle;
    }

    public void setWebTitle(String webTitle) {
        WebTitle = webTitle;
    }

    public String getBodyText() {
        return BodyText;
    }

    public void setBodyText(String bodyText) {
        BodyText = bodyText;
    }

    @Override
    public String getTitle() {
        return Title;
    }

    @Override
    public String getBody() {
        return Body;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setBody(String body) {
        Body = body;
    }

    @Override
    public String toString() {
        return "\nArticleXml{" +
                "Title='" + Title + '\'' +
                ", Body='" + Body + '\'' +
                ", WebTitle='" + WebTitle + '\'' +
                ", BodyText='" + BodyText + '\'' +
                '}';
    }
}
