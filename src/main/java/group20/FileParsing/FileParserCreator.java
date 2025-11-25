package group20.FileParsing;

import group20.Exceptions.InvalidFileFormatException;

/** Instantiates the correct {@link AbstractQuestionParser} given a file path. 
* Throws a {@link InvalidFileFormatException} for invalid file formats */
public class FileParserCreator {
        public AbstractQuestionParser getFileLoader(String filePath) throws InvalidFileFormatException {
        if (filePath.endsWith(".csv")) return new CSVParser();
        if (filePath.endsWith(".json")) return new JSONParser();
        if (filePath.endsWith(".xml")) return new XMLParser();
        throw new InvalidFileFormatException("Unsupported file format");
    }
}
