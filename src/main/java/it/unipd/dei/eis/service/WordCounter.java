package it.unipd.dei.eis.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 *  Class representing a counter of <code>String</code> words with a specified {@link WordCountStrategy}.
 *  <p> An instance of this class can do counting operation on all the words in a {@link Article} <code>List</code>, storing the data in a {@code List<Map.Entry<String, Integer>>}. </p>
 *
 * @author unascribed
 * @since  0.1
 */
public class WordCounter {
    /**
     *  Internal {@link WordCountStrategy} instance.
     */
    private WordCountStrategy strategy;

    /**
     * Creates a new <code>WordCounter</code> instance with given param.
     *
     * @param strategy {@link WordCountStrategy} instance
     *
     * @since  0.1
     */
    public WordCounter(WordCountStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Sets value of the strategy element with given param.
     *
     * @param strategy The new {@link WordCountStrategy} instance
     *
     * @since  0.1
     */
    public void setCountStrategy(WordCountStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Method that performs all the counting operations of words of an {@link Article} <code>List</code> storing them in a {@code List<Map.Entry<String, Integer>>}.
     *
     * @param articles The {@link Article} <code>List</code> to be processed
     *
     * @return A {@code List<Map.Entry<String, Integer>>} of counted words
     *
     * @see WordCountStrategy#execute(List)
     *
     * @since  0.1
     */
    public  List<Map.Entry<String, Integer>> count(List<Article> articles) {
        if (strategy == null) return new ArrayList<Map.Entry<String, Integer>>() {};
        return strategy.execute(articles);
    }
}
