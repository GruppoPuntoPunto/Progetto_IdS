package it.unipd.dei.eis;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class GuardianContentApi implements Source {
    private static final String TARGET_URL = "https://content.guardianapis.com/search?show-fields=all";
    private final String apiKey;
    private final String OS; // per decidere quale script lanciare

    public GuardianContentApi(final String apiKey) {
        this.apiKey = apiKey;
        OS = System.getProperty("os.name").toLowerCase();
    }

    public Response getContent() { return getContent(null); }

    public Response getContent(final String query) {
        String command = ""; // comando da eseguire
        boolean osIsWindows = OS.equals("windows 10") || OS.equals("windows 11");

        if (osIsWindows)
            command += "Invoke-WebRequest -OutFile \"data.json\" -Method GET -URI ";
        else command += "curl -o data.json ";

        command += "\"" + TARGET_URL;

        if (query != null && !query.isEmpty())
            command += "&q=" + query;

        command += "&api-key=" + apiKey + "\" "; 

        try {
            // creo il processo
            ProcessBuilder builder = null;
            if (osIsWindows)
                builder = new ProcessBuilder("cmd.exe", "/c", command);
            else builder = new ProcessBuilder("bash", "-c", command);

            // lancio il processo
            Process process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        ResponseWrapper response = new ResponseWrapper();
        try { response = mapper.readValue(new File("data.json"), ResponseWrapper.class); }
        catch (IOException e) { e.printStackTrace(); }
        return response.getResponse();
    }

    public Article[] getArticles() {
        // in questo modo non possiamo sfruttare le query
        return getContent().getResults();
    }
}
