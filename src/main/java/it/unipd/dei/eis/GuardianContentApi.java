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
        String cmd = ""; // comando da eseguire

        if (OS.equals("windows 10") || OS.equals("windows 11"))
            cmd += "Invoke-WebRequest -Method GET -URI ";
        else cmd += "curl ";

        cmd += "\"" + TARGET_URL;

        if (query != null && !query.isEmpty())
            cmd += "&q=" + query;

        cmd += "&api-key=" + apiKey + "\" "; 

        if (OS.equals("windows 10") || OS.equals("windows 11"))
            cmd += "-OutFile \"data.json\"";
        else cmd += "-o data.json";

        try {
            // lancio un processo per eseguire il comando e aspetto che termini
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        ResponseWrapper response = new ResponseWrapper();
        try { response = mapper.readValue(new File("data.json"), ResponseWrapper.class); }
        catch (IOException e) { System.out.println(e); }
        return response.getResponse();
    }

    public Article[] getArticles() {
        // in questo modo non possiamo sfruttare le query
        return getContent().getResults();
    }
}
