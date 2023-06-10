package it.unipd.dei.eis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.List;
import java.util.ArrayList;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.CamelCaseStyle;
import org.simpleframework.xml.stream.Format;

/**
 *  Class used to manage conversion of several files with the {@link org.simpleframework.xml} package xml conversions.
 *
 *  <p> Simplifies the operations of serialization and deserialization of objects which implement {@link Article},
 *  designed to manage conversions of several amounts of xml objects stored in a specific directory.
 *
 *  <p> This class serializes types which implement <code>Article</code> into files xml structured as {@link ArticleXml} classes,
 *  in the same way, it deserializes xml files into <code>Article</code> type objects.
 *
 *  @author unascribed
 *  @since   0.1
 */
public class XmlSerializer {
    /**
     * Component from {@link org.simpleframework.xml.Serializer}
     */
    Serializer serializer;

    /**
     * The default directory where the object has to operate
     */
    private File directory;

    /**
     *  Unique number for identifying the xml files produced
     */
    private static int productionCount = 1;

    /* -- Constructors -- */

    /**
     *  Creates a new <code>XmlSerializer</code> instance by setting the default directory.
     *  If the given directory doesn't exist, then it's created.
     *
     *  @param directory The default directory pathname string
     *
     *  @see org.simpleframework.xml.core.Persister
     *  @see org.simpleframework.xml.stream.Format
     *  @since 0.1
     */
    public XmlSerializer(String directory) {
        this.serializer = new Persister(new Format(4, new CamelCaseStyle()));
        this.directory = new File(directory);
        if (!this.directory.exists())
            this.directory.mkdirs();
    }

    /**
     *  Creates a new <code>XmlSerializer</code> instance by setting the default directory and format.
     *  If the given directory doesn't exist, then it's created.
     *
     * @param directory The default directory pathname string
     * @param format Specifies the <code>Format</code> to build the <code>Serializer</code> with
     *
     *  @see org.simpleframework.xml.core.Persister
     *  @see org.simpleframework.xml.stream.Format
     *  @since 0.1
     */
    public XmlSerializer(String directory, Format format) {
        this.serializer = new Persister(format);
        this.directory = new File(directory);
        if (!this.directory.exists())
            this.directory.mkdirs();
    }

    /* -- Serialize operations -- */

    /**
     *  Serialize an array of <code>Article</code> into xml files in a safeguarded way.
     *
     * @param list Array of <code>Article</code> to serialize
     *
     * @see org.simpleframework.xml.core.Persister
     * @since 0.1
     */
    public void serialize(Article[] list) {
        try {
            for(Article article : list)
                // write(Object source, File out) - > throws Exception if the schema(source) for the object is not valid
                // new File(File parent, String child) -> throws NullPointerException if child is null
                serializer.write(new ArticleXml(article.getTitle(), article.getBody()), new File(this.directory, productionCount++ + ".xml"));
        } catch (Exception e) { // not supposed to be reached -> guaranteed safe operation before
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Serialize an array of <code>Article</code> into xml files, in a specified directory,
     *  in a safeguarded way. If the given directory doesn't exist, then it's created.
     *
     * @param list Array of <code>Article</code> to serialize
     * @param fileDirectory The directory pathname string to use
     *
     * @see org.simpleframework.xml.core.Persister
     * @since 0.1
     */
    public void serialize(Article[] list, String fileDirectory) {
        File directory = new File(fileDirectory);
        if (!directory.exists())
            directory.mkdirs();

        try {
            for(Article article : list)
                // write(Object source, File out) - > throws Exception if the schema(source) for the object is not valid
                // new File(File parent, String child) -> throws NullPointerException if child is null
                serializer.write(new ArticleXml(article.getTitle(), article.getBody()), new File(directory, productionCount++ + ".xml"));
        } catch (Exception e) { // not supposed to be reached -> guaranteed safe operation before
            System.out.println(e.getMessage());
        }
    }

    /**
     *  Serialize a list of <code>Article</code> into xml files in a safeguarded way.
     *
     * @param list List of <code>Article</code> to serialize
     *
     * @see org.simpleframework.xml.core.Persister
     * @since 0.1
     */
    public void serialize(List<? extends Article> list) {
        try {
            for(Article article : list)
                // write(Object source, File out) - > throws Exception if the schema(source) for the object is not valid
                // new File(File parent, String child) -> throws NullPointerException if child is null
                serializer.write(new ArticleXml(article.getTitle(), article.getBody()), new File(this.directory, productionCount++ + ".xml"));
        } catch (Exception e) { // not supposed to be reached -> guaranteed safe operation before
           System.out.println(e.getMessage());
        }
    }

    /**
     *  Serialize a list of <code>Article</code> into xml files, in a specified directory,
     *  in a safeguarded way. If the given directory doesn't exist, then it's created.
     *
     * @param list List of <code>Article</code> to serialize
     * @param fileDirectory The directory pathname string to use
     *
     * @see org.simpleframework.xml.core.Persister
     * @since 0.1
     */
    public void serialize(List<? extends Article> list, String fileDirectory) {
        File directory = new File(fileDirectory);
        if (!directory.exists())
            directory.mkdirs();

        try {
            for(Article article : list)
                // write(Object source, File out) - > throws Exception if the schema(source) for the object is not valid
                // new File(File parent, String child) -> throws NullPointerException if child is null
                serializer.write(new ArticleXml(article.getTitle(), article.getBody()), new File(directory, productionCount++ + ".xml"));
        } catch (Exception e) { // not supposed to be reached -> guaranteed safe operation before
            System.out.println(e.getMessage());
        }
    }

    /* -- Deserialize operations -- */

    /**
     *  Deserialize all the array files stored in the default directory into an <code>Article</code> typed list.
     *
     *  @return An <code>Article</code> typed list or null if no .xml files are found
     *
     * @see File#listFiles(FilenameFilter)
     * @see org.simpleframework.xml.core.Persister
     * @since 0.1
     */
    public List<Article> deserialize() throws Exception {
        // Lambda function: accept(File dir, String name) -> raccoglie tutti i file .xml
        File[] files = this.directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
        List<Article> allArticles = new ArrayList<>();

        if(files != null) {
            for (File file : files) {
                // read(Class<? extends T> type, File source) -> throws Exception if the object cannot be fully deserialized
                Article a = serializer.read(ArticleXml.class, file);
                // se il corpo è nullo
                if (a.getBody() == null) a.setBody("");
                allArticles.add(a);
            }
            return allArticles;
        }
        else { return null; }
    }

    /**
     *  Deserialize all the array files stored in the specified directory into an <code>Article</code> typed list.
     *
     * @param fileDirectory The directory pathname string to search in
     *
     * @return An <code>Article</code> typed list or null if no .xml files are found
     *
     * @see File#listFiles(FilenameFilter)
     * @see org.simpleframework.xml.core.Persister
     * @since 0.1
     */
    public List<Article> deserialize(String fileDirectory) throws Exception {
        File directory = new File(fileDirectory);

        // Lambda function: accept(File dir, String name) -> raccoglie tutti i file .xml
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));
        List<Article> allArticles = new ArrayList<>();

        if(files != null) {
            for (File file : files) {
                // read(Class<? extends T> type, File source) -> throws Exception if the object cannot be fully deserialized
                Article a = serializer.read(ArticleXml.class, file);
                // se il corpo è nullo
                if (a.getBody() == null) a.setBody("");
                allArticles.add(a);
            }
            return allArticles;
        }
        else { return null; }
    }
}
