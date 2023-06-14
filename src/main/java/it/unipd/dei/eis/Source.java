package it.unipd.dei.eis;

/**
 * Instances of classes that implement this interface are used to be web sources from where downloading an array of {@link Article}.
 *
 * @author unascribed
 * @since  0.1
 */
public interface Source {
    /**
     *  Downloads, from a chosen location, and formats, the requested files into {@link Article} elements.
     *
     * @since 0.1
     */
    void download();

    /**
     * Returns all the downloaded {@link Article} files.
     *
     * @return Array of <code>Article</code>
     *
     * @since 0.1
     */
    Article[] getArticles();
}
