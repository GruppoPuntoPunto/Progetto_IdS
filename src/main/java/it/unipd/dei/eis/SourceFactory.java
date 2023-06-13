package it.unipd.dei.eis;

import java.io.FileReader;

/**
 *  This class is designed to be a creator of {@link Source} objects.
 *  <p>An instance of this class is used to correctly create <code>Source</code> instances.</p>
 *
 * @author unascribed
 * @since  0.1
 */
public class SourceFactory {
    /**
     *  Internal <code>SourceFactory</code> instance.
     */
    private static SourceFactory instance;

    /**
     * Creates a new empty <code>SourceFactory</code> instance.
     *
     * @since 0.1
     */
    private SourceFactory() { }

    /**
     * Factory method for the creation of a <code>Source</code> instance.
     *
     * @param sourceType Type of source element string
     * @param args Source attributes object typed array
     *
     * @return An initialized {@link Source} instance or <code>null</code> if the params can not match a valid source type
     *
     * @since 0.1
     */
    public Source createSource(String sourceType, Object... args) {
        if (sourceType.equals("GuardianJsonSource") && args[0] instanceof String && args[1] instanceof String)
            return new GuardianJsonSource((String) args[0], (String) args[1]);
        else if (sourceType.equals("NewYorkTimesCsvSource") && args[0] instanceof FileReader)
            return new NewYorkTimesCsvSource((FileReader) args[0]);
        return null;
    }

    /**
     *  Returns an initialized instance of <code>SourceFactory</code>.
     *
     * @return Instance of <code>SourceFactory</code>
     *
     * @since 0.1
     */
    public static SourceFactory getInstance() {
        if (instance == null)
            instance = new SourceFactory();
        return instance;
    }
}
