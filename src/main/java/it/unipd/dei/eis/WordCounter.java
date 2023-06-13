package it.unipd.dei.eis;

import java.util.List;
import java.util.Map;

public class WordCounter {
    private WordCountStrategy strategy; //Creazione strategia

    public WordCounter(WordCountStrategy strategy) {
        this.strategy = strategy;
    }

    public void setCountStrategy(WordCountStrategy strategy) {
        this.strategy = strategy;
    }

    public  List<Map.Entry<String, Integer>> count(List<Article> articles) {
        return strategy.execute(articles);
    }
}
