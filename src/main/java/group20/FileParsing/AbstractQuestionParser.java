package group20.FileParsing;

import java.io.File;
import java.util.List;
import java.util.Map;
import group20.GameLogic.Category;

/** Template class for parsing question files into {@link Category} objects. */
public abstract class AbstractQuestionParser {
    /** The file being parsed. */
    protected File file;

    /**
     * Executes the full parsing workflow for the specified file.
     *
     * @param filePath - path to the file being parsed
     * @return map of category names to their corresponding {@link Category} objects
     * @throws Exception - if reading or parsing fails
     */
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

    /**
     * Determines whether the file path corresponds to a supported format.
     *
     * @param filePath - file path to validate
     * @return true - if this parser can handle the file type
     */
    protected abstract boolean determineFileType(String filePath);

    /**
     * Opens the file for reading.
     *
     * @param filePath - file path to open
     * @return true - if the file exists and is ready to read
     */
    protected boolean openFile(String filePath) {
        file = new File(filePath);
        return file.exists();
    }
    
    /**
     * Reads raw file contents for parsing.
     *
     * @return list of raw file lines or content segments
     * @throws Exception - if reading fails
     */
    protected abstract List<String> readFile() throws Exception;

    /**
     * Parses the raw content and builds {@link Category} objects.
     *
     * @param raw - raw file contents
     * @return parsed category map
     * @throws Exception - if parsing fails
     */
    protected abstract Map<String, Category> parseFile(List<String> raw) throws Exception;

    /** Closes the file resource. */
    protected void closeFile() {
        file = null;
    }
}