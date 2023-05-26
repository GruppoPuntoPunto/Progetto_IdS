package it.unipd.dei.eis;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class GuardianContentApi {
    private static final String TARGET_URL = "https://content.guardianapis.com/search?show-fields=all";
    private final String apiKey;
    private final String OS; // per decidere quale script lanciare

    public GuardianContentApi(final String apiKey) {
        this.apiKey = apiKey;
        OS = System.getProperty("os.name").toLowerCase();
    }

    public Response getContent() {
        return getContent(null);
    }

    public Response getContent(final String query) {
        // download risposta
        String bashCmd = "curl " + "\"" + TARGET_URL;

        if (query != null && !query.isEmpty())
            bashCmd += "&q=" + query;

        bashCmd += "&api-key=" + apiKey + "\"" + " -o data.json";

        try {
            ProcessBuilder builder = new ProcessBuilder("bash", "-c", bashCmd);
            builder.redirectErrorStream(true);

            // lancio il processo
            Process process = builder.start();
            process.waitFor();

            // stampa l'output del comando bash
            //BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            //String line;
            //while ((line = reader.readLine()) != null)
            //    System.out.println(line);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // serializzo la risposta
        ObjectMapper mapper = new ObjectMapper();
        ResponseWrapper response = new ResponseWrapper();
        try { response = mapper.readValue(new File("data.json"), ResponseWrapper.class); }
        catch (IOException e) { System.out.println(e); }
        return response.getResponse();
    }
}
