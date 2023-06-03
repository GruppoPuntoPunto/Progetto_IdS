package it.unipd.dei.eis;

import java.util.List;
import java.util.Map;

public class WordCounter {
    private WordCountStrategy strategy; //Creazione strategia

    //Costruttore
    public WordCounter(WordCountStrategy strategy) {
        this.strategy = strategy;
    }
    //Metodo per il setting della strategia di conteggio
    public void setCountStrategy(WordCountStrategy strategy) {
        this.strategy = strategy;
    }
    //Metodo per eseguire il tipo di strategia selezionata
    public  List<Map.Entry<String, Integer>> count(List<Article> articles) {
        return strategy.execute( articles);
    }

}
