package group20.GameLogic;

import java.util.List;

import group20.GameActionCommands.*;
import group20.ReportGenerationStrategy.*;

/**Controls game flow by instantiating the necessary {@link Command}s and using a {@link CommandInvoker} to execute them */
public class Game {
    private int id;
    /**{@link GameState} holds all the information regarding the current state of the game */
    private GameState gameState;
    /**{@link CommandInvoker} is used to execute commands */
    private CommandInvoker invoker;
    /**{@link CLIController} is used to get user inputs */
    private CLIController input;

    static private int idCounter = 0;

    public Game() {
        this.id = idCounter++;
        this.gameState = new GameState();
        this.invoker = new CommandInvoker();
        this.input = new CLIController();
    }

    public void startGame() {
        Command start = new StartGameCommand(gameState);

        try {
            invoker.executeCommand(start);
        } catch (InvalidCommandException e) {
            System.err.println("Failed to start game: " + e.getMessage());
        }

        String filePath = "C:\\Users\\Dominic\\Desktop\\OOP2 Proj\\sample_game_CSV.csv";
        Command load = new LoadFileCommand(gameState, filePath);
        try {
            invoker.executeCommand(load);
        } catch (InvalidCommandException e) {
            e.printStackTrace();
        }

        // Select player count
        try {
            int count = input.askForPlayerCount();
            Command countSelector = new SelectPlayerCountCommand(gameState, count);
            invoker.executeCommand(countSelector);
        } catch (InvalidCommandException e) {
            e.printStackTrace();
        }
            
        // Enter player names
        for (int index = 0; index < gameState.getPlayerCount(); index++) {
            String name = input.askForPlayerName();
            Command enterName = new EnterPlayerNameCommand(gameState, name);
            try {
                invoker.executeCommand(enterName);
            } catch (InvalidCommandException e) {
                e.printStackTrace();
            }
        }

        playGame();
    }

    public void playGame() {
        int index = 0;
        List<Player> players = gameState.getPlayers();
        
        while (!gameState.getIsOver()) {
            Player currentPlayer = players.get(index % gameState.getPlayerCount());
            index++;
            
            gameState.startNewTurn(currentPlayer);
            
            System.out.println("Turn " + index + " Current Player: " + currentPlayer.getName() + "\n");
            
            // Ask for category
            String category = input.askForCategory(gameState.getCategories());
            Command selectCategory = new SelectCategoryCommand(gameState, category);
            try {
                invoker.executeCommand(selectCategory);
            } catch (InvalidCommandException e) {
                e.printStackTrace();
                break;
            }
            
            Category c = gameState.getCurrentTurn().getTurnCategory();
            
            // Ask for question
            int question = input.askForQuestion(c);
            Command selectQuestion = new SelectQuestionCommand(gameState, question);
            try {
                invoker.executeCommand(selectQuestion);
            } catch (InvalidCommandException e) {
                e.printStackTrace();
                break;
            }
            
            Question q = gameState.getCurrentTurn().getTurnQuestion();
            
            // Ask for answer
            char answer = input.askForAnswer(q);
            Command answerQuestion = new AnswerQuestionCommand(gameState, answer);
            try {
                invoker.executeCommand(answerQuestion);
            } catch (InvalidCommandException e) {
                e.printStackTrace();
                break;
            }
            
            // Check if the player wants to quit after each turn
            String exit = input.askForEnding();
            if (exit.equalsIgnoreCase("quit")){
                endGame();
            }
            
            // Checks if the game has finished after each turn
            if(gameState.isFinished()) {
                endGame();
            }
        }
    }
    
    public void endGame(){
        Command exitCommand = new ExitGameCommand(gameState);

        String format = input.askForFileFormat();

        if (format.equalsIgnoreCase(".docx")){
            ReportGenerator report = new DOCXReportGenerator();
            Command reportCommand = new GenerateReportCommand(gameState, this.id, report);
            try {
                invoker.executeCommand(reportCommand);
            } catch (InvalidCommandException e) {
                e.printStackTrace();
            }
        }

        if (format.equalsIgnoreCase(".pdf")){
            ReportGenerator report = new PDFReportGenerator();
            Command reportCommand = new GenerateReportCommand(gameState, this.id, report);
            try {
                invoker.executeCommand(reportCommand);
            } catch (InvalidCommandException e) {
                e.printStackTrace();
            }
        }

        if (format.equalsIgnoreCase(".txt")){
            ReportGenerator report = new TXTReportGenerator();
            Command reportCommand = new GenerateReportCommand(gameState, this.id, report);
            try {
                invoker.executeCommand(reportCommand);
            } catch (InvalidCommandException e) {
                e.printStackTrace();
            }
        }

        try {
            Command eventLogCommand = new GenerateEventLogCommand(invoker, this.id);
            invoker.executeCommand(eventLogCommand);
            invoker.executeCommand(exitCommand);
        } catch (InvalidCommandException e) {
            e.printStackTrace();
        }
    }
}
