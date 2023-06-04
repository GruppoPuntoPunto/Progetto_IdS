package it.unipd.dei.eis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.CamelCaseStyle;
import org.simpleframework.xml.stream.Format;


// Utilizza la libreria standard di Java -> JAXB (Java Architecture for XML Binding)
public class XmlSerializer {
    Serializer serializer;
    private File directory;


    public XmlSerializer(File directory, Format f) {
        this.serializer = new Persister(f);
        this.directory = directory;
        if (!this.directory.exists())
            this.directory.mkdirs(); // Crea la cartella se non esiste già
    }
    public XmlSerializer(File directory) {
        this.serializer = new Persister(new Format(4, new CamelCaseStyle()));
        this.directory = directory;
        if (!this.directory.exists())
            this.directory.mkdirs(); // Crea la cartella se non esiste già
    }

    public XmlSerializer(String directory, Format f) {
        this.serializer = new Persister(f);
        this.directory = new File(directory);
        if (!this.directory.exists())
            this.directory.mkdirs(); // Crea la cartella se non esiste già
    }

    public XmlSerializer(String directory) {
        this.serializer = new Persister(new Format(4, new CamelCaseStyle()));
        this.directory = new File(directory);
        if (!this.directory.exists())
            this.directory.mkdirs(); // Crea la cartella se non esiste già
    }


    public void serialize(List<? extends Article> list) throws Exception {
        int i = 1;
        for(Article article : list)
            serializer.write(article, new File(this.directory, i++ + ".xml"));
    }

    public void serialize(List<? extends Article> list, File directory) throws Exception {
        if (!directory.exists())
            directory.mkdirs(); // Crea la cartella se non esiste già

        int i = 1;
        for(Article article : list)
            serializer.write(article, new File(directory, i++ + ".xml"));
    }

    public void serialize(List<? extends Article> list, String fileDirectory) throws Exception {
        File directory = new File(fileDirectory);
        if (!directory.exists())
            directory.mkdirs(); // Crea la cartella se non esiste già

        int i = 1;
        for(Article article : list)
            serializer.write(article, new File(directory, i++ + "a.xml"));
    }


    public List<Article> deserialize() throws Exception {
        File[] files = this.directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml")); // raccoglie tutti i file .xml
        List<Article> allArticles = new ArrayList<>(); // Articoli deserializzati

        if(files != null) {
            for (File file : files)
                allArticles.add(serializer.read(ArticleXml.class, file));
            return allArticles;
        }
        else { throw new FileNotFoundException(); }
    }

    public List<Article> deserialize(File directory) throws Exception {
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml")); // raccoglie tutti i file .xml
        List<Article> allArticles = new ArrayList<>(); // Articoli deserializzati

        if(files != null) {
            for (File file : files)
                allArticles.add(serializer.read(ArticleXml.class, file));
            return allArticles;
        }
        else { throw new FileNotFoundException(); }
    }

    public List<Article> deserialize(String fileDirectory) throws Exception {
        File directory = new File(fileDirectory);

        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml")); // raccoglie tutti i file .xml
        List<Article> allArticles = new ArrayList<>(); // Articoli deserializzati

        if(files != null) {
            for (File file : files)
                allArticles.add(serializer.read(ArticleXml.class, file));
            return allArticles;
        }
        else { throw new FileNotFoundException(); }
    }
}
