package it.unipd.dei.eis;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author unascribed
 * @since  0.1
 */
public class WordCounter {
    /**
     *  Internal {@link WordCountStrategy} instance.
     */
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
