package it.unipd.dei.eis.service;

import java.util.List;
import java.util.Map;

/**
 *   Instances of classes that implement this interface are used to be definers of the behavior of a {@link WordCounter}.
 *   <p> This strategy is meant to specified the operating process of a real <code>WordCounter</code>.</p>
 *
 * @author unascribed
 * @since  0.1
 */
public interface WordCountStrategy {
    /**
     * Method that performs all the counting operations of words of an {@link Article} <code>List</code> storing them in a {@code List<Map.Entry<String, Integer>>}.
     *
     * @param articles The {@link Article} <code>List</code> to be processed
     *
     * @return A {@code List<Map.Entry<String, Integer>>} of counted words
     *
     * @since  0.1
     */
    List<Map.Entry<String, Integer>> execute(List<Article> articles);
}
