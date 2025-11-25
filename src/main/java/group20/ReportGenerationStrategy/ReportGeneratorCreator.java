package group20.ReportGenerationStrategy;

import group20.Exceptions.InvalidFileFormatException;

/** Instantiates the correct {@link ReportGenerator} given a format. 
* Throws a {@link InvalidFileFormatException} for invalid file formats */
public class ReportGeneratorCreator {
    public ReportGenerator getReportGenerator(String format) throws InvalidFileFormatException {
        if (format.contains("pdf")) return new PDFReportGenerator();
        if (format.contains("docx")) return new DOCXReportGenerator();
        if (format.contains("txt")) return new TXTReportGenerator();
        throw new InvalidFileFormatException("Unsupported report format");
    }
}
