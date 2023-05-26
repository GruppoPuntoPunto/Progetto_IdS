package it.unipd.dei.eis;

public class ArticleCSV implements Article {
    private String title;
    private String body;

    public ArticleCSV() { }
    public ArticleCSV(String title, String body) { 
        this.body = body;
        this.title = title;
    }

    public String getTitle() { return title; }

    public String getBody() { return body; }
}

