package it.unipd.dei.eis;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.File;
import java.io.IOException;

public class GuardianJSONSource implements Source {
    private static final String TARGET_URL = "https://content.guardianapis.com/search?show-fields=all";
    private File directory; // cartella in cui salvare le risposte json
    private static final String OS = System.getProperty("os.name").toLowerCase();; // per decidere quale script lanciare
    private final String apiKey;
    private Article[] results;

    public GuardianJSONSource(String apiKey, String directory) {
        this.directory = new File(directory);
        if (!this.directory.exists())
            this.directory.mkdirs(); // creo la cartella se non esiste
        this.apiKey = apiKey;
        this.results = new ArticleJSON[0];
    }

    public File getDirectory() { return directory; }

    public void setDirectory(File directory) { this.directory = directory;}

    public Article[] getArticles() { return results; }

    public void setArticles(Article[] results) { this.results = results;}

    private String getDirectoryPath() {
        String p = "";
        try { 
            p = directory.getCanonicalPath();
        }
        catch (IOException e) { 
            e.printStackTrace(); 
        }
        return p;
    }

    private void executeShellCommand(String command) {
        try {
            // creo il processo
            ProcessBuilder builder = null;
            if (OS.contains("windows"))
                builder = new ProcessBuilder("cmd.exe", "/c", command);
            else
                builder = new ProcessBuilder("bash", "-c", command);

            // lancio il processo
            Process process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void download() {
        String dirPath = getDirectoryPath();

        // parallelizzo l'esecuzione dei comandi shell
        Thread[] threads = new Thread[5];

        // creazione file json
        for (int i = 0; i < 5; i++) {
            // path file in cui salvare la risposta
            String filePath = dirPath + "/" + (i+1) + ".json";

            // comando da eseguire da shell
            StringBuilder buildCmd = new StringBuilder();
            buildCmd.append("echo > " + '\"' + filePath + '\"')
                    .append(" && curl -o " + '\"' + filePath + '\"' +" ")
                    .append('\"' + TARGET_URL)
                    .append("&page-size=" + 200)
                    .append("&page=" + i+1)
                    .append("&api-key=" + apiKey + '\"');
            String cmd = buildCmd.toString();

            threads[i] = new Thread(() -> executeShellCommand(cmd));
            threads[i].start();
//            // eseguo il comando
//            executeShellCommand(cmd);
        }

        // aspetto che tutti i thread terminino l'esecuzione del comando
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // deserializzo tutti gli articoli scaricati dalle risposte
        ObjectMapper mapper = new ObjectMapper();
        ResponseWrapper response = new ResponseWrapper();

        // lista in cui salvo tutti gli articoli
        List<Article> tmp = new ArrayList<>(); 

        File[] jsonFiles = directory.listFiles();
        if (jsonFiles != null) {
            for (File f : jsonFiles) {
                try { 
                    response = mapper.readValue(f, ResponseWrapper.class); 
                } catch (IOException e) { 
                    e.printStackTrace(); 
                }
                tmp.addAll(Arrays.asList(response.getResponse().getResults()));
            }
        }

        this.results = tmp.toArray(this.results);
    }
}
