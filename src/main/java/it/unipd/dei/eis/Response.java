package it.unipd.dei.eis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    private String status;
    private String userTier;
    private int total;
    private int startIndex;
    private int pageSize;
    private int currentPage;
    private int pages;
    private String orderBy;
    private ArticleJSON[] results;

    public Response() { }
    public Response(
                String status, 
                String userTier, 
                int total, int startIndex, 
                int pageSize, 
                int currentPage, 
                int pages, 
                String orderBy, 
                ArticleJSON[] results) {
        this.status = status;
        this.userTier = userTier;
        this.total = total;
        this.startIndex = startIndex;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.pages = pages;
        this.orderBy = orderBy;
        this.results = results;
    }

    public String getStatus() { return status; }

    public int getTotal() { return total; }

    public int getPages() { return pages; }

    public ArticleJSON[] getResults() { return results; }
}
