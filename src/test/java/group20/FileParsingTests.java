package group20;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import group20.Exceptions.InvalidFileFormatException;
import group20.FileParsing.*;
import group20.GameLogic.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FileParsingTests {
    Map<String, Category> result = null;

    @TempDir
    Path tempDir;

    @Test
    public void testCVSParser(){
        String csvContent = "Category,Value,Question,OptionA,OptionB,OptionC,OptionD,CorrectAnswer\nFunctions,100,Which keyword is used to define a function returning no value?,return,void,int,empty,B";
        Path csvFile = tempDir.resolve("test.csv");
        try {
            Files.writeString(csvFile, csvContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AbstractQuestionParser parser = new CSVParser();
        try {
            result = parser.run(csvFile.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Question q = result.get("Functions").getUnansweredQuestions().get(100);

        assertEquals(1, result.size());
        assertEquals("Functions", result.get("Functions").getName());
        assertEquals(1, result.get("Functions").getUnansweredQuestions().size());
        assertEquals(100, q.getPoints());
        assertEquals("Which keyword is used to define a function returning no value?", q.getText());
        assertEquals("return", q.getOptionText('A'));
        assertEquals("void", q.getOptionText('B'));
        assertEquals("int", q.getOptionText('C'));
        assertEquals("empty", q.getOptionText('D'));
        assertEquals('B', q.getAnswer());
    }

    @Test
    public void testCVSParserFail(){
        String csvContent = "Category,Value,OptionA,OptionB,OptionC,OptionD,CorrectAnswer\nFunctions,100,return,void,int,empty,B";
        Path csvFile = tempDir.resolve("test.csv");
        try {
            Files.writeString(csvFile, csvContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AbstractQuestionParser parser = new CSVParser();
        try {
            result = parser.run(csvFile.toString());
        } catch (Exception e) {
            assertTrue(e instanceof InvalidFileFormatException);
        }
    }

    @Test
    public void testJSONParser(){
        String jsonContent = "[{ \"Category\": \"Functions\", \"Value\": 100, \"Question\": \"Which keyword is used to define a function returning no value?\", \"Options\": { \"A\": \"return\", \"B\": \"void\", \"C\": \"int\", \"D\": \"empty\" }, \"CorrectAnswer\": \"B\" }]";
        Path jsonFile = tempDir.resolve("test.json");
        try {
            Files.writeString(jsonFile, jsonContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AbstractQuestionParser parser = new JSONParser();
        try {
            result = parser.run(jsonFile.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Question q = result.get("Functions").getUnansweredQuestions().get(100);

        assertEquals(1, result.size());
        assertEquals("Functions", result.get("Functions").getName());
        assertEquals(1, result.get("Functions").getUnansweredQuestions().size());
        assertEquals(100, q.getPoints());
        assertEquals("Which keyword is used to define a function returning no value?", q.getText());
        assertEquals("return", q.getOptionText('A'));
        assertEquals("void", q.getOptionText('B'));
        assertEquals("int", q.getOptionText('C'));
        assertEquals("empty", q.getOptionText('D'));
        assertEquals('B', q.getAnswer());
    }

    @Test
    public void testJSONParserFail(){
        String jsonContent = "[{ \\\"Category\\\": \\\"Functions\\\", \\\"Value\\\": 100, \\\"Question\\\": \\\"Which keyword is used to define a function returning no value?\\\", \\\"Options\\\": { \\\"A\\\": \\\"return\\\", \\\"B\\\": \\\"void\\\", \\\"C\\\": \\\"int\\\" }, \\\"CorrectAnswer\\\": \\\"B\\\" }]";
        Path jsonFile = tempDir.resolve("test.csv");
        try {
            Files.writeString(jsonFile, jsonContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AbstractQuestionParser parser = new CSVParser();
        try {
            result = parser.run(jsonFile.toString());
        } catch (Exception e) {
            assertTrue(e instanceof InvalidFileFormatException);
        }
    }

    @Test
    public void testXMLParser(){
        String xmlContent = "<JeopardyQuestions>\n" +
            "<QuestionItem>\n" +
            "<Category>Functions</Category>\n" +
            "<Value>100</Value>\n" +
            "<QuestionText>Which keyword is used to define a function returning no value?</QuestionText>\n" +
            "<Options>\n" +
            "<OptionA>return</OptionA>\n" +
            "<OptionB>void</OptionB>\n" +
            "<OptionC>int</OptionC>\n" +
            "<OptionD>empty</OptionD>\n" +
            "</Options>\n" +
            "<CorrectAnswer>B</CorrectAnswer>\n" +
            "</QuestionItem>\n" +
            "</JeopardyQuestions>";
        Path xmlFile = tempDir.resolve("test.xml");
        try {
            Files.writeString(xmlFile, xmlContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AbstractQuestionParser parser = new XMLParser();
        try {
            result = parser.run(xmlFile.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Question q = result.get("Functions").getUnansweredQuestions().get(100);

        assertEquals(1, result.size());
        assertEquals("Functions", result.get("Functions").getName());
        assertEquals(1, result.get("Functions").getUnansweredQuestions().size());
        assertEquals(100, q.getPoints());
        assertEquals("Which keyword is used to define a function returning no value?", q.getText());
        assertEquals("return", q.getOptionText('A'));
        assertEquals("void", q.getOptionText('B'));
        assertEquals("int", q.getOptionText('C'));
        assertEquals("empty", q.getOptionText('D'));
        assertEquals('B', q.getAnswer());
    }

    @Test
    public void testXMLParserFail(){
        String xmlContent = "<JeopardyQuestions>\n" +
            "<QuestionItem>\n" +
            "<Category>Functions</Category>\n" +
            "<QuestionText>Which keyword is used to define a function returning no value?</QuestionText>\n" +
            "<Options>\n" +
            "<OptionA>return</OptionA>\n" +
            "<OptionB>void</OptionB>\n" +
            "<OptionC>int</OptionC>\n" +
            "<OptionD>empty</OptionD>\n" +
            "</Options>\n" +
            "<CorrectAnswer>B</CorrectAnswer>\n" +
            "</QuestionItem>\n" +
            "</JeopardyQuestions>";
        Path xmlFile = tempDir.resolve("test.xml");
        try {
            Files.writeString(xmlFile, xmlContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AbstractQuestionParser parser = new XMLParser();
        try {
            result = parser.run(xmlFile.toString());
        } catch (Exception e) {
            assertTrue(e instanceof InvalidFileFormatException);
        }
    }

    @Test
    public void testInvalidFileType(){
        Path invalidFile = tempDir.resolve("test.txt");
        FileParserCreator creator = new FileParserCreator();
        try{
            creator.getFileLoader(invalidFile.toString());
        } catch (InvalidFileFormatException e) {
            assertEquals("Unsupported file format", e.getMessage());
        }
    }

    @Test
    public void testNonExistentFile(){
        Path nonExistentFile = tempDir.resolve("nonexistent.csv");
        AbstractQuestionParser parser = new CSVParser();
        try {
            result = parser.run(nonExistentFile.toString());
        } catch (Exception e) {
            assertTrue(e instanceof FileNotFoundException);
        }
        assertNull(result);
    }
}
