package group20.FileParsing;

import java.io.*;
import java.util.*;

import com.opencsv.CSVReader;

import group20.Exceptions.InvalidFileFormatException;
import group20.GameLogic.Category;
import group20.GameLogic.Question;

/**
 * Parser for CSV-based Jeopardy question files.
 */
public class CSVParser extends AbstractQuestionParser {

    @Override
    protected boolean determineFileType(String filePath) {
        return filePath.toLowerCase().endsWith(".csv");
    }

    @Override
    protected List<String> readFile() throws IOException {
        // CSVReader handles reading; we only return placeholder
        return List.of(); 
    }

    @Override
    protected Map<String, Category> parseFile(List<String> raw) throws Exception {
        Map<String, Category> categories = new TreeMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(file))) {

            String[] line = reader.readNext(); // Skip header
            if (line == null) {
                throw new InvalidFileFormatException("CSV file is empty or missing header.");
            }

            while ((line = reader.readNext()) != null) {
                if (line.length < 8) {
                    throw new InvalidFileFormatException(
                        "Malformed CSV row: expected 8 columns, got " + line.length
                    );
                }

                try {
                    String categoryName = line[0].trim();
                    int score = Integer.parseInt(line[1].trim());
                    String questionText = line[2].trim();
                    char answer = line[7].trim().charAt(0);

                    Map<Character, String> options = new LinkedHashMap<>();
                    options.put('A', line[3].trim());
                    options.put('B', line[4].trim());
                    options.put('C', line[5].trim());
                    options.put('D', line[6].trim());

                    Question question = new Question(questionText, score, answer, options);

                    categories.computeIfAbsent(categoryName, Category::new)
                              .addQuestion(question);

                } catch (Exception ex) {
                    throw new InvalidFileFormatException(
                        "Error parsing CSV row: " + Arrays.toString(line), ex
                    );
                }
            }

        } catch (FileNotFoundException fnf) {
            throw new FileNotFoundException("CSV file not found: " + file.getAbsolutePath());
        } catch (IOException ioe) {
            throw new IOException("Error reading CSV file: " + file.getAbsolutePath(), ioe);
        }

        return categories;
    }
}
