package it.unipd.dei.eis;

import java.util.List;
import java.util.Map;

public interface WordCountStrategy {
    List<Map.Entry<String, Integer>> execute(List<Article> articles);
}
