package group20.FileParsing;

import java.io.File;
import java.util.List;
import java.util.Map;
import group20.GameLogic.Category;

public abstract class AbstractQuestionParser {
    protected File file;
    // Template method
    public final Map<String, Category> run(String filePath) throws Exception {
        if (!determineFileType(filePath)) {
            System.out.println("Cannot parse file: " + filePath);
            return Map.of();
        }

        if (!openFile(filePath)) {
            System.out.println("File not found: " + filePath);
            return Map.of();
        }
        List<String> raw = readFile();
        Map<String, Category> categories = parseFile(raw);
        closeFile();
        
        return categories;
    }

    // Check if this parser can handle the file type
    protected abstract boolean determineFileType(String filePath);

    protected boolean openFile(String filePath) {
        file = new File(filePath);
        return file.exists();
    }
    
    protected abstract List<String> readFile() throws Exception;

    // Parses the raw data
    protected abstract Map<String, Category> parseFile(List<String> raw) throws Exception;

    protected void closeFile() {
        file = null;
    }
}