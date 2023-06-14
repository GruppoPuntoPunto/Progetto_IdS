package it.unipd.dei.eis;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Implementation of {@link Source} that fits with the 'New York Times' newspaper .csv files.
 * <p> An instance of this class can parse .csv files and shapes that into {@link Article} objects. </p>
 *
 * @author unascribed
 * @since  0.1
 */
public class NewYorkTimesCsvSource implements Source {
    /**
     *  Internal {@link FileReader} instance for reading .csv input files
     */
    private FileReader CSVInput;

    /**
     *  Array containing all the downloaded {@link Article} files.
     */
    private Article[] results;

    /**
     *  Creates a new <code>NewYorkTimesCsvSource</code> instance with given param.
     *
     * @param CSVInput The file pathname <code>String</code> to process
     *
     *  @since  0.1
     */
    public NewYorkTimesCsvSource(FileReader CSVInput) {
        this.CSVInput = CSVInput; 
        this.results = new ArticleCsvNYTimes[0];
    }

    /**
     *  Returns the CSVInput {@link FileReader} object.
     *
     * @return The CSVInput element
     *
     * @since 0.1
     */
    public FileReader getCSVInput() { return CSVInput; }

    /**
     * Sets value of the CSVInput element with given param.
     *
     * @param CSVInput The new CSVInput {@link FileReader} element
     *
     * @since 0.1
     */
    public void setCSVInput(FileReader CSVInput) { this.CSVInput = CSVInput; }

    /**
     * Returns all the downloaded {@link Article} files.
     *
     * @return The array results element
     *
     * @since 0.1
     */
    @Override
    public Article[] getArticles() { return results; }

    /**
     * Sets value of the results element with given param.
     *
     * @param results Array of {@link Article} to set
     *
     * @since 0.1
     */
    public void setArticles(Article[] results) { this.results = results; }

    /**
     *  Downloads and stores the response files.
     *
     * @see CSVRecord
     * @see CSVFormat
     *
     * @since 0.1
     */
    @Override
    public void download() {
        Iterable<CSVRecord> records = null;
        try { 
            records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(CSVInput); 
        } catch(IOException e) { 
            e.printStackTrace(); 
        }

        ArrayList<Article> res = new ArrayList<>();
        for (CSVRecord record : records)
            res.add(new ArticleCsvNYTimes(record.get("Title"), record.get("Body")));
        results = res.toArray(results);
    }
}
