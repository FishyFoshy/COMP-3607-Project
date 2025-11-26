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
    private static final Set<Integer> validScores = Set.of(100, 200, 300, 400, 500);
    private static final Set<Character> validAnswers = Set.of('A', 'B', 'C', 'D');

    @Override
    protected boolean determineFileType(String filePath) {
        return filePath.toLowerCase().endsWith(".csv");
    }

    @Override
    protected List<String> readFile() throws IOException {
        return List.of();
    }

    @Override
    protected Map<String, Category> parseFile(List<String> raw) throws Exception {
        Map<String, Category> categories = new TreeMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(file))) {

            // Read and validate header
            String[] header = reader.readNext();
            if (header == null) {
                throw new InvalidFileFormatException("CSV file is empty.");
            }

            int rowNumber = 1;

            String[] line;
            while ((line = reader.readNext()) != null) {
                rowNumber++;

                // Validate row length
                if (line.length < 8) {
                    throw new InvalidFileFormatException(
                        "Malformed CSV row at line " + rowNumber +
                        ": expected 8 columns, got " + line.length
                    );
                }

                try {
                    String categoryName = line[0].trim();
                    String scoreStr = line[1].trim();
                    String questionText = line[2].trim();
                    String a = line[3].trim();
                    String b = line[4].trim();
                    String c = line[5].trim();
                    String d = line[6].trim();
                    String answerStr = line[7].trim();

                    // Validate score
                    int score = Integer.parseInt(scoreStr);
                    if (!validScores.contains(score)) {
                        throw new InvalidFileFormatException(
                            "Invalid score '" + score + "' at line " + rowNumber +
                            ". Score must be one of 100, 200, 300, 400, 500."
                        );
                    }

                    // Validate correct answer
                    if (answerStr.isEmpty()) {
                        throw new InvalidFileFormatException(
                            "Missing correct answer at line " + rowNumber
                        );
                    }

                    char answer = answerStr.charAt(0);
                    if (!validAnswers.contains(answer)) {
                        throw new InvalidFileFormatException(
                            "Invalid correct answer '" + answer + "' at line " + rowNumber +
                            ". Expected one of: A, B, C, or D."
                        );
                    }

                    // Prepare options map
                    Map<Character, String> options = new LinkedHashMap<>();
                    options.put('A', a);
                    options.put('B', b);
                    options.put('C', c);
                    options.put('D', d);

                    // Build question
                    Question question = new Question(questionText, score, answer, options);

                    // Add to category
                    categories.computeIfAbsent(categoryName, Category::new)
                              .addQuestion(question);

                } catch (NumberFormatException ex) {
                    throw new InvalidFileFormatException(
                        "Invalid numeric value in CSV row at line " + rowNumber + 
                        ": " + Arrays.toString(line), ex
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
