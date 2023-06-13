package it.unipd.dei.eis;

import java.io.FileReader;

public class SourceFactory {
    private static SourceFactory instance;
    
    private SourceFactory() { }

    public Source createSource(String sourceType, Object... args) {
        if (sourceType.equals("GuardianJsonSource") && args[0] instanceof String && args[1] instanceof String)
            return new GuardianJsonSource((String) args[0], (String) args[1]);
        else if (sourceType.equals("NewYorkTimesCsvSource") && args[0] instanceof FileReader)
            return new NewYorkTimesCsvSource((FileReader) args[0]);
        return null;
    }

    public static SourceFactory getInstance() {
        if (instance == null)
            instance = new SourceFactory();
        return instance;
    }
}
