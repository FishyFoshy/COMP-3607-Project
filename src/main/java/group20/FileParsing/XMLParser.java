package group20.FileParsing;

import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import group20.GameLogic.Category;
import group20.GameLogic.Question;

public class XMLParser extends AbstractQuestionParser {

    @Override
    protected boolean determineFileType(String filePath) {
        return filePath.toLowerCase().endsWith(".xml");
    }

    @Override
    protected List<String> readFile() throws Exception {
        return List.of(file.getAbsolutePath());
    }

    @Override
    protected Map<String, Category> parseFile(List<String> raw) throws Exception {
        String filePath = raw.get(0);
        Map<String, Category> categories = new TreeMap<>();

        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = fac.newDocumentBuilder();
        Document doc = builder.parse(filePath);

        NodeList nodes = doc.getElementsByTagName("QuestionItem");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);

            String categoryName = e.getElementsByTagName("Category").item(0).getTextContent();
            int score = Integer.parseInt(e.getElementsByTagName("Value").item(0).getTextContent());
            String questionText = e.getElementsByTagName("QuestionText").item(0).getTextContent();
            Element opts = (Element) e.getElementsByTagName("Options").item(0);

            Map<Character, String> options = new HashMap<>();
            options.put('A', opts.getElementsByTagName("OptionA").item(0).getTextContent());
            options.put('B', opts.getElementsByTagName("OptionB").item(0).getTextContent());
            options.put('C', opts.getElementsByTagName("OptionC").item(0).getTextContent());
            options.put('D', opts.getElementsByTagName("OptionD").item(0).getTextContent());

            char answer = e.getElementsByTagName("CorrectAnswer").item(0).getTextContent().charAt(0);

            Question question = new Question(questionText, score, answer, options);

            Category cat = categories.get(categoryName);
            if (cat == null) {
                cat = new Category(categoryName);
                categories.put(categoryName, cat);
            }
            cat.addQuestion(question);
        }
        return categories;
    }
}