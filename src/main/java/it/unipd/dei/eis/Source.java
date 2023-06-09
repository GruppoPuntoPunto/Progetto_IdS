package it.unipd.dei.eis;

public interface Source {
    void download();
    Article[] getArticles();
}
