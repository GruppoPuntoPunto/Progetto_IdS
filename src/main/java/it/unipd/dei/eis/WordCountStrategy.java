package it.unipd.dei.eis;

import java.util.List;
import java.util.Map;

//Pattern Strategy per la scelta della tipologia di conteggio
public interface WordCountStrategy {
    List<Map.Entry<String, Integer>> execute(List<Article> articles);
}
