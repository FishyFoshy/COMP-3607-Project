package group20.FileParsing;

import java.io.*;
import java.util.*;

import org.json.*;

import group20.Exceptions.InvalidFileFormatException;
import group20.GameLogic.Category;
import group20.GameLogic.Question;

/**
 * Parser for JSON-based Jeopardy question files.
 */
public class JSONParser extends AbstractQuestionParser {
    private static final Set<Integer> validScores = Set.of(100, 200, 300, 400, 500);

    @Override
    protected boolean determineFileType(String filePath) {
        return filePath.toLowerCase().endsWith(".json");
    }

    @Override
    protected List<String> readFile() throws IOException {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }

        } catch (FileNotFoundException fnf) {
            throw new FileNotFoundException("JSON file not found: " + file.getAbsolutePath());
        } catch (IOException ioe) {
            throw new IOException("Error reading JSON file: " + file.getAbsolutePath(), ioe);
        }

        return List.of(sb.toString());
    }

    @Override
    protected Map<String, Category> parseFile(List<String> raw) throws Exception {
        Map<String, Category> categories = new TreeMap<>();

        try {
            JSONArray arr = new JSONArray(raw.get(0));

            if (arr.length() == 0) {
                throw new InvalidFileFormatException("JSON contains no question objects.");
            }

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);

                if (!obj.has("Category") || !obj.has("Value") ||
                    !obj.has("Question") || !obj.has("CorrectAnswer") ||
                    !obj.has("Options")) {

                    throw new InvalidFileFormatException(
                        "JSON object missing fields at index " + i + ": " + obj
                    );
                }

                String categoryName = obj.getString("Category");

                int points = obj.getInt("Value");
                if (!validScores.contains(points)) {
                    throw new InvalidFileFormatException(
                            "Invalid score " + points + " at index " + i
                    );
                }

                String questionText = obj.getString("Question");

                char answer = obj.getString("CorrectAnswer").charAt(0);
                if ("ABCD".indexOf(answer) == -1) {
                    throw new InvalidFileFormatException(
                            "Invalid CorrectAnswer '" + answer + "' at index " + i
                    );
                }

                JSONObject opts = obj.getJSONObject("Options");
                Map<Character, String> options = new HashMap<>();

                for (char letter : new char[]{'A', 'B', 'C', 'D'}) {
                    if (!opts.has(String.valueOf(letter))) {
                        throw new InvalidFileFormatException(
                                "Missing option '" + letter + "' in JSON item at index " + i
                        );
                    }
                    options.put(letter, opts.getString(String.valueOf(letter)));
                }

                Question q = new Question(questionText, points, answer, options);

                categories.computeIfAbsent(categoryName, Category::new)
                          .addQuestion(q);
            }

        } catch (JSONException je) {
            throw new InvalidFileFormatException("Malformed JSON: " + je.getMessage(), je);
        }

        return categories;
    }
}
