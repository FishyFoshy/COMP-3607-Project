package group20.FileParsing;

import java.io.IOException;
import java.util.*;
import javax.xml.parsers.*;
import java.io.FileNotFoundException;
import org.w3c.dom.*;

import group20.Exceptions.InvalidFileFormatException;
import group20.GameLogic.Category;
import group20.GameLogic.Question;

/**
 * Parser for XML-based Jeopardy question files.
 */
public class XMLParser extends AbstractQuestionParser {
    private static final Set<Integer> validScores = Set.of(100, 200, 300, 400, 500);
    private static final Set<Character> validAnswers = Set.of('A', 'B', 'C', 'D');

    @Override
    protected boolean determineFileType(String filePath) {
        return filePath.toLowerCase().endsWith(".xml");
    }

    @Override
    protected List<String> readFile() {
        return List.of(file.getAbsolutePath());
    }

    @Override
    protected Map<String, Category> parseFile(List<String> raw) throws Exception {
        Map<String, Category> categories = new TreeMap<>();
        String filePath = raw.get(0);

        try {
            DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = fac.newDocumentBuilder();
            Document doc = builder.parse(filePath);

            NodeList list = doc.getElementsByTagName("QuestionItem");

            if (list.getLength() == 0) {
                throw new InvalidFileFormatException("XML contains no <QuestionItem> elements.");
            }

            for (int i = 0; i < list.getLength(); i++) {
                Element e = (Element) list.item(i);

                try {
                    String categoryName = e.getElementsByTagName("Category")
                                           .item(0).getTextContent();

                    // Validate Score
                    int score = Integer.parseInt(
                        e.getElementsByTagName("Value")
                         .item(0).getTextContent()
                    );

                    if (!validScores.contains(score)) {
                        throw new InvalidFileFormatException(
                            "Invalid score at QuestionItem[" + i + "]: " + score +
                            ". Expected 100, 200, 300, 400, or 500."
                        );
                    }

                    String questionText = e.getElementsByTagName("QuestionText")
                                           .item(0).getTextContent();

                    Element opts = (Element) e.getElementsByTagName("Options").item(0);

                    Map<Character, String> options = new HashMap<>();
                    options.put('A', opts.getElementsByTagName("OptionA").item(0).getTextContent());
                    options.put('B', opts.getElementsByTagName("OptionB").item(0).getTextContent());
                    options.put('C', opts.getElementsByTagName("OptionC").item(0).getTextContent());
                    options.put('D', opts.getElementsByTagName("OptionD").item(0).getTextContent());

                    // Validate Correct Answer
                    char answer = e.getElementsByTagName("CorrectAnswer")
                                   .item(0).getTextContent().charAt(0);

                    if (!validAnswers.contains(answer)) {
                        throw new InvalidFileFormatException(
                            "Invalid correct answer at QuestionItem[" + i + "]: '" + answer +
                            "'. Expected A, B, C, or D."
                        );
                    }

                    Question q = new Question(questionText, score, answer, options);

                    categories.computeIfAbsent(categoryName, Category::new)
                              .addQuestion(q);

                } catch (InvalidFileFormatException valEx) {
                    throw valEx; // rethrow validation errors directly
                } catch (Exception inner) {
                    throw new InvalidFileFormatException(
                        "Malformed <QuestionItem> at index " + i, inner
                    );
                }
            }

        } catch (FileNotFoundException fnf) {
            throw new FileNotFoundException("XML file not found: " + filePath);
        } catch (IOException ioe) {
            throw new IOException("Error reading XML: " + filePath, ioe);
        } catch (InvalidFileFormatException iffe) {
            throw iffe;
        } catch (Exception ex) {
            throw new InvalidFileFormatException("Invalid XML structure.", ex);
        }

        return categories;
    }
}
