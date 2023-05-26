package it.unipd.dei.eis;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class CSVContent {
    private FileReader CSVInput;
    private ArticleCSV[] results;

    public CSVContent(FileReader CSVInput) throws IOException { 
        this.CSVInput = CSVInput; 
        this.results = new ArticleCSV[1];

        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(CSVInput);
        ArrayList<ArticleCSV> res = new ArrayList<>();
        for (CSVRecord record : records)
            res.add(new ArticleCSV(record.get("Title"), record.get("Body")));
        results = res.toArray(results);
    }

    public void setCSVInput(FileReader input) { this.CSVInput = CSVInput; }
    
    public ArticleCSV[] getArticles() { return results; }
}
