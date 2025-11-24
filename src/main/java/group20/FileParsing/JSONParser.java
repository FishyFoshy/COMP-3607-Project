package group20.FileParsing;

import java.io.*;
import java.util.*;
import org.json.*;
import group20.GameLogic.Category;
import group20.GameLogic.Question;

public class JSONParser extends AbstractQuestionParser {

    @Override
    protected boolean determineFileType(String filePath) {
        return filePath.toLowerCase().endsWith(".json");
    }

    @Override
    protected List<String> readFile() throws Exception {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }
        }
        return List.of(sb.toString());
    }

    @Override
    protected Map<String, Category> parseFile(List<String> raw) throws Exception {
        String jsonText = raw.get(0);
        JSONArray array = new JSONArray(jsonText);

        Map<String, Category> categories = new TreeMap<>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);

            String categoryName = obj.getString("Category");
            int points = obj.getInt("Value");
            String questionText = obj.getString("Question");
            char correctAnswer = obj.getString("CorrectAnswer").charAt(0);

            // Parse nested Options object
            JSONObject optionsObj = obj.getJSONObject("Options");
            Map<Character, String> options = new HashMap<>();
            for (char opt : new char[]{'A','B','C','D'}) {
                if (optionsObj.has(String.valueOf(opt))) {
                    options.put(opt, optionsObj.getString(String.valueOf(opt)));
                }
            }

            Question q = new Question(questionText, points, correctAnswer, options);

            // Add question to category
            Category cat = categories.get(categoryName);
            if (cat == null) {
                cat = new Category(categoryName);
                categories.put(categoryName, cat);
            }
            cat.addQuestion(q);
        }

        return categories;
    }
}