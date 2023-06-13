package it.unipd.dei.eis;

/**
 * Instances of classes that implement this interface are used to be web sources from where downloading an array of <code>Article</code>.
 *
 * @author unascribed
 * @since  0.1
 */
public interface Source {
    /**
     *  Downloads, from a chosen location, and formats, the requested files into <code>Article</code> elements.
     *
     * @since 0.1
     */
    void download();

    /**
     * Returns all the downloaded <code>Article</code> files.
     *
     * @return Array of {@link Article}
     *
     * @since 0.1
     */
    Article[] getArticles();
}
