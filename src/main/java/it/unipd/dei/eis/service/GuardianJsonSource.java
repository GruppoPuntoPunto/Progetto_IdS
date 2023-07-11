package it.unipd.dei.eis.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.File;
import java.io.IOException;

/**
 * Implementation of {@link Source} that fits with the 'The Guardian' newspaper .json response files.
 * <p> An instance of this class can send a complete api request to content.guardianapis.com, download articles .json files and shapes that into {@link Article} objects. </p>
 *
 * @author unascribed
 * @since  0.1
 */
public class GuardianJsonSource implements Source {
    /**
     * Specific Url <code>String</code> for the Api request.
     */
    private static final String TARGET_URL = "https://content.guardianapis.com/search?show-fields=all";

    /**
     * The directory {@link File} in which save the .json files response.
     */
    private File directory;

    /**
     * Automatically generated identifier <code>String</code> of the operating system launching the request.
     *
     * @see System#getProperty(String)
     */
    private static final String OS = System.getProperty("os.name").toLowerCase();

    /**
     * Api key <code>String</code> to launch the request with.
     */
    private final String apiKey;

    /**
     *  Array containing all the downloaded {@link Article} files.
     */
    private Article[] results;

    /**
     *  Creates a new <code>GuardianJsonSource</code> instance with given params.
     *  If the given directory doesn't exist, then it's created.
     *
     * @param apiKey The apiKey <code>String</code> element
     * @param directory The directory pathname <code>String</code>
     *
     *  @since  0.1
     */
    public GuardianJsonSource(String apiKey, String directory) {
        this.directory = new File(directory);
        if (!this.directory.exists())
            this.directory.mkdirs(); // creo la cartella se non esiste
        this.apiKey = apiKey;
        this.results = new ArticleJsonGuardian[0];
    }

    /**
     *  Returns the directory {@link Article} object.
     *
     * @return The directory element
     *
     * @since 0.1
     */
    public File getDirectory() { return directory; }

    /**
     * Sets value of the directory element with given param.
     *
     * @param directory The new directory {@link Article} element
     *
     * @since 0.1
     */
    public void setDirectory(File directory) { this.directory = directory;}

    /**
     * Returns all the downloaded {@link Article} files.
     *
     * @return The results element
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
    public void setArticles(Article[] results) { this.results = results;}

    /**
     *  Returns the directory element <code>String</code> path.
     *
     * @return The directory element pathname
     *
     * @since 0.1
     */
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

    /**
     * This method executes the given shell command as param.
     *
     * @param command The shell command code <code>String</code>
     *
     * @see ProcessBuilder
     * @see Process
     *
     * @since 0.1
     */
    private void executeShellCommand(String command) {
        try {
            // creo il processo
            ProcessBuilder builder;
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

    /**
     *  Downloads with the built request, and stores, the response files.
     *
     * @see StringBuilder
     * @see ObjectMapper
     * @see ResponseWrapper
     *
     * @since 0.1
     */
    @Override
    public void download() {
        if (directory == null) return;

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
                    .append("&page=" + (i+1))
                    .append("&api-key=" + apiKey + '\"');
            String cmd = buildCmd.toString();

            // eseguo il comando
            threads[i] = new Thread(() -> executeShellCommand(cmd));
            threads[i].start();
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

        // lista in cui salvo tutti gli articoli
        List<Article> tmp = new ArrayList<>(1000);

        File[] jsonFiles = directory.listFiles();
        if (jsonFiles != null) {
            for (File f : jsonFiles) {
                try { 
                    ResponseWrapper response = mapper.readValue(f, ResponseWrapper.class);
                    tmp.addAll(Arrays.asList(response.getResponse().getResults()));
                } catch (IOException e) {
                    e.printStackTrace(); 
                }
            }
        }

        this.results = tmp.toArray(this.results);
    }
}
