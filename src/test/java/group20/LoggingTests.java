package group20;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import group20.Exceptions.CommandCreationException;
import group20.Exceptions.CommandExecutionException;
import group20.FileParsing.FileParserCreator;
import group20.GameActionCommands.*;
import group20.GameLogic.*;
import group20.GameLogic.Player;
import group20.ReportGenerationStrategy.DOCXReportGenerator;
import group20.ReportGenerationStrategy.PDFReportGenerator;
import group20.ReportGenerationStrategy.ReportGenerator;
import group20.ReportGenerationStrategy.TXTReportGenerator;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class LoggingTests {
    GameState gameState;
    CommandInvoker invoker;
    static FileParserCreator fileParserCreator;
    static CommandCreator commandCreator;

    @TempDir
    Path tempDir;

    @BeforeAll
    public static void globalSetup() {
        fileParserCreator = new FileParserCreator();
        commandCreator = new CommandCreator(fileParserCreator);
    }

    @BeforeEach
    public void setup(){
        gameState = new GameState();
        invoker = new CommandInvoker();
    }

    @Test
    public void testStartGameLogging(){
        try {
            Command startCommand = commandCreator.createStartCommand(gameState);
            invoker.executeCommand(startCommand);
            assertNotNull(startCommand.getEntry());
            assertEquals("System", startCommand.getEntry().getPlayerID());
            assertEquals("Start Game", startCommand.getEntry().getActivity());
        } catch (CommandCreationException | CommandExecutionException e) {
        }
    }

    @Test
    public void testLoadFileLoggingSuccess(){
        try {
            String csvContent = "Category,Value,Question,OptionA,OptionB,OptionC,OptionD,CorrectAnswer\nFunctions,100,Which keyword is used to define a function returning no value?,return,void,int,empty,B";
            Path csvFile = tempDir.resolve("test.csv");
            Files.writeString(csvFile, csvContent);

            Command loadFileCommand = commandCreator.createLoadFileCommand(gameState, csvFile.toString());
            invoker.executeCommand(loadFileCommand);
            assertNotNull(loadFileCommand.getEntry());
            assertEquals("System", loadFileCommand.getEntry().getPlayerID());
            assertEquals("Load File", loadFileCommand.getEntry().getActivity());
            assertEquals("Success", loadFileCommand.getEntry().getResult());
        } catch (CommandCreationException | CommandExecutionException | IOException e) {
        }
    }

    @Test
    public void testLoadFileLoggingFail(){
        try {
            String csvContent = "Category,Value,Question,OptionA,OptionB,OptionC,OptionD,CorrectAnswer\nFunctions,100,Which keyword is used to define a function returning no value?,return,void,int,empty,B";
            Path csvFile = tempDir.resolve("test.txt");
            Files.writeString(csvFile, csvContent);

            Command loadFileCommand = commandCreator.createLoadFileCommand(gameState, csvFile.toString());
            invoker.executeCommand(loadFileCommand);
            assertNotNull(loadFileCommand.getEntry());
            assertEquals("System", loadFileCommand.getEntry().getPlayerID());
            assertEquals("Load File", loadFileCommand.getEntry().getActivity());
            assertEquals("Failed", loadFileCommand.getEntry().getResult());
        } catch (CommandCreationException | CommandExecutionException | IOException e) {
        }
    }

    @Test
    public void testSelectPlayerCountLogging(){
        try {
            Command selectPlayerCountCommand = commandCreator.createSelectPlayerCountCommand(gameState, 2);
            invoker.executeCommand(selectPlayerCountCommand);
            assertNotNull(selectPlayerCountCommand.getEntry());
            assertEquals("System", selectPlayerCountCommand.getEntry().getPlayerID());
            assertEquals("Select Player Count", selectPlayerCountCommand.getEntry().getActivity());
            assertEquals("2", selectPlayerCountCommand.getEntry().getAnswerGiven());
            assertEquals("N/A", selectPlayerCountCommand.getEntry().getResult());
        } catch (CommandCreationException | CommandExecutionException e) {
        }
    }

    @Test
    public void testEnterPlayerNameLogging(){
        try {
            Command enterPlayerNameCommand = commandCreator.createEnterPlayerNameCommand(gameState, "Alice");
            invoker.executeCommand(enterPlayerNameCommand);
            assertNotNull(enterPlayerNameCommand.getEntry());
            assertEquals("Alice", enterPlayerNameCommand.getEntry().getPlayerID());
            assertEquals("Enter Player Name", enterPlayerNameCommand.getEntry().getActivity());
            assertEquals("Alice", enterPlayerNameCommand.getEntry().getAnswerGiven());
            assertEquals("N/A", enterPlayerNameCommand.getEntry().getResult());
        } catch (CommandExecutionException e) {
        }
    }

    @Test
    public void testSelectCategoryLogging(){
        try {
            Category category = new Category("Functions");
            gameState.addCategory(category);
            gameState.addPlayer(new Player("Alice"));
            gameState.startNewTurn(gameState.getPlayers().get(0));

            Command selectCategoryCommand = commandCreator.createSelectCategoryCommand(gameState, "Functions");
            invoker.executeCommand(selectCategoryCommand);

            assertNotNull(selectCategoryCommand.getEntry());
            assertEquals("Alice", selectCategoryCommand.getEntry().getPlayerID());
            assertEquals("Select Category", selectCategoryCommand.getEntry().getActivity());
            assertEquals("Functions", selectCategoryCommand.getEntry().getCategory());
            assertEquals("0", selectCategoryCommand.getEntry().getScoreAfterPlay());
        } catch (CommandExecutionException e) {
        }
    }

    @Test
    public void testSelectQuestionLogging(){
        try {
            Category category = new Category("Functions");
            Question question = new Question("Which keyword is used to define a function returning no value?",100 , 'B',
                Map.of('A', "return", 'B', "void", 'C', "int", 'D', "empty"));
            category.addQuestion(question);
            gameState.addCategory(category);
            gameState.addPlayer(new Player("Alice"));
            gameState.startNewTurn(gameState.getPlayers().get(0));
            gameState.getCurrentTurn().setTurnCategory(category);

            Command selectQuestionCommand = commandCreator.createSelectQuestionCommand(gameState, 100);
            invoker.executeCommand(selectQuestionCommand);

            assertNotNull(selectQuestionCommand.getEntry());
            assertEquals("Alice", selectQuestionCommand.getEntry().getPlayerID());
            assertEquals("Select Question", selectQuestionCommand.getEntry().getActivity());
            assertEquals("Functions", selectQuestionCommand.getEntry().getCategory());
            assertEquals(100, selectQuestionCommand.getEntry().getQuestionValue());
            assertEquals("0", selectQuestionCommand.getEntry().getScoreAfterPlay());
        } catch (CommandExecutionException e) {
        }
    }

    @Test
    public void testAnswerQuestionLoggingCorrect(){
        try {
            Category category = new Category("Functions");
            Question question = new Question("Which keyword is used to define a function returning no value?",100 , 'B',
                Map.of('A', "return", 'B', "void", 'C', "int", 'D', "empty"));
            category.addQuestion(question);
            gameState.addCategory(category);
            Player player = new Player("Alice");
            gameState.addPlayer(player);
            gameState.startNewTurn(player);
            gameState.getCurrentTurn().setTurnCategory(category);
            gameState.getCurrentTurn().setTurnQuestion(question);

            Command answerQuestionCommand = commandCreator.createAnswerQuestionCommand(gameState, 'B');
            invoker.executeCommand(answerQuestionCommand);

            assertNotNull(answerQuestionCommand.getEntry());
            assertEquals("Alice", answerQuestionCommand.getEntry().getPlayerID());
            assertEquals("Answer Question", answerQuestionCommand.getEntry().getActivity());
            assertEquals("Functions", answerQuestionCommand.getEntry().getCategory());
            assertEquals(100, answerQuestionCommand.getEntry().getQuestionValue());
            assertEquals("void", answerQuestionCommand.getEntry().getAnswerGiven());
            assertEquals("Correct", answerQuestionCommand.getEntry().getResult());
            assertEquals("100", answerQuestionCommand.getEntry().getScoreAfterPlay());
        } catch (CommandCreationException | CommandExecutionException e) {
        }
    }

    @Test
    public void testAnswerQuestionLoggingIncorrect(){
        try {
            Category category = new Category("Functions");
            Question question = new Question("Which keyword is used to define a function returning no value?",100 , 'B',
                Map.of('A', "return", 'B', "void", 'C', "int", 'D', "empty"));
            category.addQuestion(question);
            gameState.addCategory(category);
            Player player = new Player("Alice");
            gameState.addPlayer(player);
            gameState.startNewTurn(player);
            gameState.getCurrentTurn().setTurnCategory(category);
            gameState.getCurrentTurn().setTurnQuestion(question);

            Command answerQuestionCommand = commandCreator.createAnswerQuestionCommand(gameState, 'a');
            invoker.executeCommand(answerQuestionCommand);

            assertNotNull(answerQuestionCommand.getEntry());
            assertEquals("Alice", answerQuestionCommand.getEntry().getPlayerID());
            assertEquals("Answer Question", answerQuestionCommand.getEntry().getActivity());
            assertEquals("Functions", answerQuestionCommand.getEntry().getCategory());
            assertEquals(100, answerQuestionCommand.getEntry().getQuestionValue());
            assertEquals("return", answerQuestionCommand.getEntry().getAnswerGiven());
            assertEquals("Incorrect", answerQuestionCommand.getEntry().getResult());
            assertEquals("-100", answerQuestionCommand.getEntry().getScoreAfterPlay());
        } catch (CommandCreationException | CommandExecutionException e) {
        }
    }

    @Test
    public void testGenerateReportLogging(){
        try {
            String reportFilename = tempDir.resolve("game_report.pdf").toString();
            ReportGenerator reportGenerator = new PDFReportGenerator(reportFilename);
            Command generateReportCommand = commandCreator.createGenerateReportCommand(gameState, 1, reportGenerator);
            invoker.executeCommand(generateReportCommand);

            assertNotNull(generateReportCommand.getEntry());
            assertEquals("System", generateReportCommand.getEntry().getPlayerID());
            assertEquals("Generate Report", generateReportCommand.getEntry().getActivity());
            assertEquals("N/A", generateReportCommand.getEntry().getResult());
        } catch (CommandExecutionException e) {
        }
    }

    @Test
    public void testGenerateEventLogLogging(){
        try {
            String logFilename = tempDir.resolve("event_log.csv").toString();
            Command generateEventLogCommand = commandCreator.createGenerateEventLogCommand(invoker, 1, logFilename);
            invoker.executeCommand(generateEventLogCommand);

            assertNotNull(generateEventLogCommand.getEntry());
            assertEquals("System", generateEventLogCommand.getEntry().getPlayerID());
            assertEquals("Generate Event Log", generateEventLogCommand.getEntry().getActivity());
            assertEquals("N/A", generateEventLogCommand.getEntry().getResult());
        } catch (CommandExecutionException e) {
        }
    }

    @Test
    public void testExitGameLogging(){
        try {
            Command exitGameCommand = commandCreator.createExitGameCommand(gameState);
            invoker.executeCommand(exitGameCommand);

            assertNotNull(exitGameCommand.getEntry());
            assertEquals("System", exitGameCommand.getEntry().getPlayerID());
            assertEquals("Exit Game", exitGameCommand.getEntry().getActivity());
        } catch (CommandExecutionException e) {
        }
    }

    @Test
    public void testEventLogGenerated(){
        try {
            String logFilename = tempDir.resolve("event_log.csv").toString();

            Command generateEventLogCommand = commandCreator.createGenerateEventLogCommand(invoker, 1, logFilename);
            invoker.executeCommand(generateEventLogCommand);

            assertTrue(Files.exists(Path.of(logFilename)));

        } catch (CommandExecutionException e) {
        }
    }
}
