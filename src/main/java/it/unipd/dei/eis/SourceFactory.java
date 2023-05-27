package it.unipd.dei.eis;

import java.io.FileReader;

public class SourceFactory {
    private static SourceFactory instance;
    
    private SourceFactory() { }

    public Source createSource(String sourceType, Object... args) {
        if (sourceType.equals("GuardianContentApi") && args[0] instanceof String)
            return new GuardianContentApi((String) args[0]);
        else if (sourceType.equals("NewYorkTimesCSV") && args[0] instanceof FileReader)
            return new NewYorkTimesCSV((FileReader) args[0]);
        return null;
    }

    public static SourceFactory getInstance() {
        if (instance == null)
            instance = new SourceFactory();
        return instance;
    }
}
