package it.unipd.dei.eis;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class GuardianJSONSource implements Source {
    private static final String TARGET_URL = "https://content.guardianapis.com/search?show-fields=all&page-size=200";
    private final String apiKey;
    private final String OS; // per decidere quale script lanciare
    private Article[] results;

    public GuardianJSONSource(String apiKey) {
        this.apiKey = apiKey;
        this.OS = System.getProperty("os.name").toLowerCase();
        this.results = new ArticleJSON[0];
    }

    //public Response getContent() { return getContent(null); }

    public void download() {
        String command = ""; // comando da eseguire
        boolean osIsWindows = OS.contains("windows");

        // costruisco il comando per scaricare la risposta json
        command += "curl -o data.json \"" + TARGET_URL;

        //if (query != null && !query.isEmpty())
        //    command += "&q=" + query;

        command += "&api-key=" + apiKey + "\" ";

        try {
            // creo il processo
            ProcessBuilder builder = null;
            if (osIsWindows)
                builder = new ProcessBuilder("cmd.exe", "/c", command);
            else  builder = new ProcessBuilder("bash", "-c", command);

            // lancio il processo
            Process process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // serializzo 
        ObjectMapper mapper = new ObjectMapper();
        ResponseWrapper response = new ResponseWrapper();
        try { response = mapper.readValue(new File("data.json"), ResponseWrapper.class); }
        catch (IOException e) { e.printStackTrace(); }

        // acquisisco gli articoli
        this.results = response.getResponse().getResults();
    }

    public Article[] getArticles() {
        return results;
    }
}
