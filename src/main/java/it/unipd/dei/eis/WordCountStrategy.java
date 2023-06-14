package it.unipd.dei.eis;

import java.util.List;
import java.util.Map;

/**
 *   Instances of classes that implement this interface are used to be counters of <code>String</code> words.
 *   <p>This strategy collects all the methods that must be implemented in a real word counter.</p>
 *
 * @author unascribed
 * @since  0.1
 */
public interface WordCountStrategy {
    /**
     * Method that performs all the counting operations of words of an <code>Article</code> array in a {@code Map.Entry<String, Integer>} typed list.
     *
     * @param articles Material to be processed in {@code List<Article>}
     *
     * @return A {@code List<Map.Entry<String, Integer>>} of counted words
     *
     * @since  0.1
     */
    List<Map.Entry<String, Integer>> execute(List<Article> articles);
}
