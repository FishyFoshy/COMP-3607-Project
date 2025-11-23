package group20.FileParsing;

import java.io.*;
import java.util.*;
import group20.GameLogic.Category;
import group20.GameLogic.Question;

public class JSONParser extends AbstractQuestionParser {
    private String content;

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
        content = sb.toString();
        return List.of(content);
    }

    @Override
    protected Map<String, Category> parseFile(List<String> raw) throws Exception {
        String json = raw.get(0);
        json = json.substring(1, json.length() - 1); // remove [ ]

        Map<String, Category> categories = new TreeMap<>();
        String[] objects = json.split("\\},\\s*\\{");

        for (String obj : objects) {
            obj = obj.replace("{", "").replace("}", "");

            String categoryName = extract(obj, "\"Category\"");
            int score = Integer.parseInt(extract(obj, "\"Value\""));
            String questionText = extract(obj, "\"Question\"");
            char answer = extract(obj, "\"CorrectAnswer\"").charAt(0);

            Map<Character, String> options = new HashMap<>();
            options.put('A', extract(obj, "\"A\""));
            options.put('B', extract(obj, "\"B\""));
            options.put('C', extract(obj, "\"C\""));
            options.put('D', extract(obj, "\"D\""));

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

    //Extracts the value for a key in the simple JSON object.
    //Expects format: "Key":"Value" or "Key":Value
    private String extract(String src, String key) {
        String search = "\"" + key + "\"";
        int start = src.indexOf(search);
        if (start == -1) return "";

        start = src.indexOf(":", start) + 1;
        int end = src.indexOf(",", start);
        if (end == -1) end = src.indexOf("}", start);
        if (end == -1) end = src.length();

        String value = src.substring(start, end).trim();

        // Remove quotes
        if (value.startsWith("\"") && value.endsWith("\"")) {
            value = value.substring(1, value.length() - 1);
        }
        return value;
    }
}