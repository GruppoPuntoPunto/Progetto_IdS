package it.unipd.dei.eis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author unascribed
 * @since  0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    private String status;
    private int total;
    private int pages;
    private ArticleJsonGuardian[] results;

    public Response() { }
    public Response(
                String status, 
                String userTier, 
                int total, int startIndex, 
                int pageSize, 
                int currentPage, 
                int pages, 
                String orderBy, 
                ArticleJsonGuardian[] results) {
        this.status = status;
        this.total = total;
        this.pages = pages;
        this.results = results;
    }

    public String getStatus() { return status; }

    public int getTotal() { return total; }

    public int getPages() { return pages; }

    public ArticleJsonGuardian[] getResults() { return results; }
}
