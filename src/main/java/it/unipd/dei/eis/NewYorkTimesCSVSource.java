package it.unipd.dei.eis;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class NewYorkTimesCSVSource implements Source {
    private FileReader CSVInput;
    private Article[] results;


    public NewYorkTimesCSVSource(FileReader CSVInput) { 
        this.CSVInput = CSVInput; 
        this.results = new ArticleCSV[0];
    }

    public FileReader getCSVInput() { return CSVInput; }
    public void setCSVInput(FileReader input) { this.CSVInput = CSVInput; }
    public Article[] getArticles() { return results; }
    public void setArticles(Article[] results) { this.results = results; }


    public void download() {
        Iterable<CSVRecord> records = null;
        try { 
            records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(CSVInput); 
        } catch(IOException e) { 
            e.printStackTrace(); 
        }

        ArrayList<Article> res = new ArrayList<>();
        for (CSVRecord record : records)
            res.add(new ArticleCSV(record.get("Title"), record.get("Body")));
        results = res.toArray(results);
    }


}
