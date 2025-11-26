package group20;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group20.Exceptions.CommandCreationException;
import group20.Exceptions.CommandExecutionException;
import group20.FileParsing.FileParserCreator;
import group20.GameActionCommands.*;
import group20.GameLogic.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

public class GameplayTests {
    GameState gameState;
    CommandInvoker invoker;
    static FileParserCreator fileParserCreator;
    static CommandCreator commandCreator;

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
    public void testSelectingPlayerCount(){
        try {
            invoker.executeCommand(commandCreator.createSelectPlayerCountCommand(gameState, 2));
        } catch (CommandCreationException | CommandExecutionException e) {
        }
        assertEquals(2, gameState.getPlayerCount());
    }

    @Test
    public void testSelectingInvalidPlayerCount(){
        try {
            invoker.executeCommand(commandCreator.createSelectPlayerCountCommand(gameState, 5));
        } catch (CommandCreationException | CommandExecutionException e) {
            assertEquals("Player count must be between 1-4", e.getMessage());
        }
    }

    @Test
    public void testAddingPlayers(){
        gameState.setPlayerCount(1);
        
        try {
            invoker.executeCommand(commandCreator.createEnterPlayerNameCommand(gameState, "Alice"));
            assertEquals("Alice", gameState.getPlayers().get(0).getName());
        } catch (CommandExecutionException e) {
        }
    }

    @Test
    public void testAddingDuplicatePlayerNames(){
        gameState.setPlayerCount(2);
        
        try {
            invoker.executeCommand(commandCreator.createEnterPlayerNameCommand(gameState, "Alice"));
            invoker.executeCommand(commandCreator.createEnterPlayerNameCommand(gameState, "alice"));
        } catch (CommandExecutionException e) {
            assertEquals("Player name already taken: alice", e.getMessage());
        }
    }

    @Test
    public void testSelectingCategory(){
        Category category = new Category("Functions");
        gameState.addCategory(category);
        gameState.addPlayer(new Player("Alice"));
        gameState.startNewTurn(gameState.getPlayers().get(0));

        try {
            invoker.executeCommand(commandCreator.createSelectCategoryCommand(gameState, "Functions"));
            assertEquals(category, gameState.getCurrentTurn().getTurnCategory());
        } catch (CommandExecutionException e) {
        }
    }

    @Test
    public void testSelectingInvalidCategory(){
        Category category = new Category("Functions");
        gameState.addCategory(category);
        gameState.addPlayer(new Player("Alice"));
        gameState.startNewTurn(gameState.getPlayers().get(0));

        try {
            invoker.executeCommand(commandCreator.createSelectCategoryCommand(gameState, "Arrays"));
        } catch (CommandExecutionException e) {
            assertEquals("Invalid category:Arrays. Please try again.", e.getMessage());
        }
    }

    @Test
    public void testSelectingQuestion(){
        Category category = new Category("Functions");
        Question question = new Question("Which keyword is used to define a function returning no value?",100 , 'B',
            Map.of('A', "return", 'B', "void", 'C', "int", 'D', "empty"));
        category.addQuestion(question);
        gameState.addCategory(category);
        gameState.addPlayer(new Player("Alice"));
        gameState.startNewTurn(gameState.getPlayers().get(0));
        gameState.getCurrentTurn().setTurnCategory(category);

        try {
            invoker.executeCommand(commandCreator.createSelectQuestionCommand(gameState, 100));
            assertEquals(question, gameState.getCurrentTurn().getTurnQuestion());
        } catch (CommandExecutionException e) {
        }
    }

    @Test
    public void testSelectingInvalidQuestion(){
        Category category = new Category("Functions");
        Question question = new Question("Which keyword is used to define a function returning no value?",100 , 'B',
            Map.of('A', "return", 'B', "void", 'C', "int", 'D', "empty"));
        category.addQuestion(question);
        gameState.addCategory(category);
        gameState.addPlayer(new Player("Alice"));
        gameState.startNewTurn(gameState.getPlayers().get(0));
        gameState.getCurrentTurn().setTurnCategory(category);

        try {
            invoker.executeCommand(commandCreator.createSelectQuestionCommand(gameState, 200));
        } catch (CommandExecutionException e) {
            assertEquals("Invalid question:200. Please try again.", e.getMessage());
        }
    }

    @Test
    public void testAnsweringQuestionCorrectly(){
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

        try {
            invoker.executeCommand(commandCreator.createAnswerQuestionCommand(gameState, 'b'));
            assertEquals('B', gameState.getCurrentTurn().getAnswerGiven());
            assertEquals(100, player.getScore());
        } catch (CommandCreationException | CommandExecutionException e) {
        }
    }

    @Test
    public void testAnsweringQuestionIncorrectly(){
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

        try {
            invoker.executeCommand(commandCreator.createAnswerQuestionCommand(gameState, 'A'));
            assertEquals(-100, player.getScore());
        } catch (CommandCreationException | CommandExecutionException e) {
        }
    }

    @Test
    public void testAnsweringQuestionWithInvalidOption(){
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

        try {
            invoker.executeCommand(commandCreator.createAnswerQuestionCommand(gameState, 'f'));
        } catch (CommandCreationException | CommandExecutionException e) {
            assertEquals("Invalid answer option. Answer option must be between A and D. Please try again.", e.getMessage());
        }
    }
}
