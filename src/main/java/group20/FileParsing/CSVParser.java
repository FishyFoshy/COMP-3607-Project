package group20.FileParsing;

import java.io.*;
import java.util.*;
import group20.GameLogic.Category;
import group20.GameLogic.Question;

public class CSVParser extends AbstractQuestionParser {
    private BufferedReader br;

    @Override
    protected boolean determineFileType(String filePath) {
        return filePath.toLowerCase().endsWith(".csv");
    }

    @Override
    protected boolean openFile(String filePath) {
        if (!super.openFile(filePath)) return false;
        try {
            br = new BufferedReader(new FileReader(file));
            br.readLine(); // Skip the header at the very top of the sample file
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    protected List<String> readFile() throws Exception {
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

    @Override
    protected Map<String, Category> parseFile(List<String> rows) {
        Map<String, Category> categories = new TreeMap<>();

        for (String row : rows) {
            String[] f = row.split(",");

            String categoryName = f[0].trim();
            int score = Integer.parseInt(f[1].trim());
            String questionText = f[2].trim();

            Map<Character, String> options = new HashMap<>();
            options.put('A', f[3].trim());
            options.put('B', f[4].trim());
            options.put('C', f[5].trim());
            options.put('D', f[6].trim());
            
            char answer = f[7].trim().charAt(0);
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

    @Override
    protected void closeFile() {
        try {
            if (br != null) br.close();
        } catch (IOException ignored) {}
        super.closeFile();
    }
}