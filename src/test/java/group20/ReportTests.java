package group20;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import group20.Exceptions.CommandExecutionException;
import group20.FileParsing.FileParserCreator;
import group20.GameActionCommands.*;
import group20.GameLogic.*;
import group20.ReportGenerationStrategy.*;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

public class ReportTests {
    static GameState gameState;
    static CommandInvoker invoker;
    static FileParserCreator fileParserCreator;
    static CommandCreator commandCreator;

    @TempDir
    Path tempDir;

    @BeforeAll
    public static void globalSetup() {
        fileParserCreator = new FileParserCreator();
        commandCreator = new CommandCreator(fileParserCreator);
        gameState = new GameState();
        invoker = new CommandInvoker();

        gameState.setPlayerCount(1);
        Player player = new Player("Alice");
        gameState.addPlayer(player);

        Category category = new Category("Functions");
        Question question = new Question("Which keyword is used to define a function returning no value?",100 , 'B',
            Map.of('A', "return", 'B', "void", 'C', "int", 'D', "empty"));
        category.addQuestion(question);
        gameState.addCategory(category);

        gameState.startNewTurn(player);
        gameState.getCurrentTurn().setTurnCategory(category);
        gameState.getCurrentTurn().setTurnQuestion(question);
        gameState.getCurrentTurn().setAnswerGiven('B');
        gameState.getCurrentTurn().evaluateAnswer();
        gameState.endTurn();
        gameState.setIsOver(true);
    }

    @Test
    public void testReportedGeneratedDOCX(){
        try {
            String reportFilename = tempDir.resolve("game_report.docx").toString();
            ReportGenerator reportGenerator = new DOCXReportGenerator(reportFilename);

            Command generateEventLogCommand = commandCreator.createGenerateReportCommand(gameState, 1, reportGenerator);
            invoker.executeCommand(generateEventLogCommand);

            assertTrue(Files.exists(Path.of(reportFilename)));
            
            try (FileInputStream fis = new FileInputStream(reportFilename); XWPFDocument doc = new XWPFDocument(fis); XWPFWordExtractor extractor = new XWPFWordExtractor(doc)) {

                String text = extractor.getText();
                List<String> lines = text.lines().toList();

                assertEquals("JEOPARDY PROGRAMMING GAME REPORT", lines.get(0));
                assertEquals("===================================", lines.get(1));
                assertEquals("Case ID: GAME001", lines.get(2));
                assertEquals("Players: Alice", lines.get(3));
                assertEquals("Gameplay Summary:", lines.get(4));
                assertEquals("----------------------------", lines.get(5));
                assertEquals("Turn 1: Alice selected Functions for 100 pts", lines.get(6));
                assertEquals("Question: Which keyword is used to define a function returning no value?", lines.get(7));
                assertEquals("Answer: void - Correct (+100 pts)", lines.get(8));
                assertEquals("Score after turn: Alice = 100", lines.get(9));
                assertEquals("Final Scores:", lines.get(11));
                assertEquals("Alice: 100", lines.get(12));
            }

        } catch (CommandExecutionException | IOException e) {
        }
    }

    @Test
    public void testReportedGeneratedPDF(){
        try {
            String reportFilename = tempDir.resolve("game_report.pdf").toString();
            ReportGenerator reportGenerator = new PDFReportGenerator(reportFilename);

            Command generateEventLogCommand = commandCreator.createGenerateReportCommand(gameState, 1, reportGenerator);
            invoker.executeCommand(generateEventLogCommand);

            assertTrue(Files.exists(Path.of(reportFilename)));

            String text;
            try (PDDocument document = PDDocument.load(new File(reportFilename))) {
                PDFTextStripper stripper = new PDFTextStripper();
                text = stripper.getText(document);
            }

            assertEquals("JEOPARDY PROGRAMMING GAME REPORT\r\n" + //
                                "====================================\r\n" + //
                                "Case ID: GAME001\r\n" + //
                                "Players: Alice\r\n" + //
                                "Gameplay Summary:\r\n" + //
                                "----------------------------\r\n" + //
                                "Turn 1: Alice selected Functions for 100 pts\r\n" + //
                                "Question: Which keyword is used to define a function returning no value?\r\n" + //
                                "Answer: void - Correct (+100 pts)\r\n" + //
                                "Score after turn: Alice = 100\r\n" + //
                                "Final Scores:\r\n" + //
                                "Alice: 100\r\n" + //
                                "", text);
        } catch (CommandExecutionException | IOException e) {
        }
    }

    @Test
    public void testReportedGeneratedTXT(){
        try {
            String reportFilename = tempDir.resolve("game_report.txt").toString();
            ReportGenerator reportGenerator = new TXTReportGenerator(reportFilename);

            Command generateEventLogCommand = commandCreator.createGenerateReportCommand(gameState, 1, reportGenerator);
            invoker.executeCommand(generateEventLogCommand);

            assertTrue(Files.exists(Path.of(reportFilename)));
            
            List<String> lines = Files.readAllLines(Path.of(reportFilename));

            assertEquals("JEOPARDY PROGRAMMING GAME REPORT", lines.get(0));
            assertEquals("================================", lines.get(1));
            assertEquals("Case ID: GAME001", lines.get(3));
            assertEquals("Players: Alice", lines.get(5));
            assertEquals("Gameplay Summary:", lines.get(7));
            assertEquals("-----------------", lines.get(8));
            assertEquals("Turn 1: Alice selected Functions for 100 pts", lines.get(10));
            assertEquals("Question: Which keyword is used to define a function returning no value?", lines.get(11));
            assertEquals("Answer: void - Correct (+100 pts)", lines.get(12));
            assertEquals("Score after turn: Alice = 100", lines.get(13));
            assertEquals("Final Scores:", lines.get(15));
            assertEquals("Alice: 100", lines.get(16));
        } catch (CommandExecutionException | IOException e) {
        }
    }
}
