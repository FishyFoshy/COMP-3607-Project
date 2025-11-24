package group20.FileParsing;

import java.io.*;
import java.util.*;
import com.opencsv.CSVReader;
import java.io.FileReader;
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
    protected Map<String, Category> parseFile(List<String> raw) throws Exception {
        Map<String, Category> categories = new TreeMap<>();
        
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] line;
            
            // Read header and ignore
            reader.readNext();
            
            while ((line = reader.readNext()) != null) {
                
                if (line.length < 8) continue;
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

                Category cat = categories.get(categoryName);
                if (cat == null) {
                    cat = new Category(categoryName);
                    categories.put(categoryName, cat);
                }
                cat.addQuestion(question);
            }
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