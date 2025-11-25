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
            input.displayMessage(e.getMessage());
        }

        loadGameFile();

        // Select player count
        while (true) {
            try {
                getPlayerCount();
                break;
            } catch (InvalidCommandException e) {
                input.displayMessage(e.getMessage());
            }
        }
            
        // Enter player names
        for (int index = 0; index < gameState.getPlayerCount(); index++) {
            try {
                getPlayerName();
            } catch (InvalidCommandException e) {
                e.printStackTrace();
            }
        }

        playGame();
    }

    public void playGame() {
        int index = 0;
        List<Player> players = gameState.getPlayers();
        Category c;
        Question q;
        
        while (!gameState.getIsOver()) {
            Player currentPlayer = players.get(index % gameState.getPlayerCount());
            index++;
            
            gameState.startNewTurn(currentPlayer);
            
            System.out.println("Turn " + index + " Current Player: " + currentPlayer.getName() + "\n");
            
            // Ask for category
            while (true) {
                try {
                    c = selectCategory();
                    break;
                } catch (Exception e) {
                    input.displayMessage(e.getMessage());
                }
            }
            
            // Ask for question
            while (true) {
                try {
                    q = selectQuestion(c);
                    break;
                } catch (InvalidCommandException e) {
                    input.displayMessage(e.getMessage());
                }
            }
            
            // Ask for answer
            while (true) {
                try {
                    selectAnswer(q);
                    break;
                } catch (InvalidCommandException e) {
                    input.displayMessage(e.getMessage());
                }
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
        
        Command reportCommand = reportFormatter(format);
        try {
            invoker.executeCommand(reportCommand);
        } catch (Exception e) {
            input.displayMessage(e.getMessage());
        }
        
        Command eventLogCommand = new GenerateEventLogCommand(invoker, this.id);
        invoker.setDelayedCommand(eventLogCommand);
        
        try {            
            invoker.executeCommand(exitCommand);
        } catch (Exception e) {
            input.displayMessage(e.getMessage());
        }
        
        try {
            invoker.executeDelayedCommand();
        } catch (InvalidCommandException e) {
            input.displayMessage(e.getMessage());
        }
    }
    
    public void loadGameFile(){
        String filePath = "C:\\Users\\Dominic\\Desktop\\OOP2 Proj\\sample_game_CSV.csv";
        Command load = new LoadFileCommand(gameState, filePath);
        try {
            invoker.executeCommand(load);
        } catch (InvalidCommandException e) {
            e.printStackTrace();
        }
    }
    
    public void getPlayerCount() throws InvalidCommandException{
        int count = input.askForPlayerCount();
        Command countSelector = new SelectPlayerCountCommand(gameState, count);
        invoker.executeCommand(countSelector);
    }
    
    public void getPlayerName() throws InvalidCommandException{
        String name = input.askForPlayerName();
        Command enterName = new EnterPlayerNameCommand(gameState, name);
        invoker.executeCommand(enterName);
    }
    
    public Category selectCategory() throws InvalidCommandException{
        String category = input.askForCategory(gameState.getCategories());
        Command selectCategory = new SelectCategoryCommand(gameState, category);
        invoker.executeCommand(selectCategory);
        return gameState.getCurrentTurn().getTurnCategory();
    }
    
    public Question selectQuestion(Category category) throws InvalidCommandException{
        int question = input.askForQuestion(category);
        Command selectQuestion = new SelectQuestionCommand(gameState, question);
        invoker.executeCommand(selectQuestion);
        return gameState.getCurrentTurn().getTurnQuestion();
    }

    public void selectAnswer(Question question) throws InvalidCommandException{
        char answer = input.askForAnswer(question);
        Command answerQuestion = new AnswerQuestionCommand(gameState, answer);
        invoker.executeCommand(answerQuestion);
    }

    public Command reportFormatter(String format){
        if (format.equalsIgnoreCase(".docx") || format.equalsIgnoreCase("docx")){
            ReportGenerator report = new DOCXReportGenerator();
            Command reportCommand = new GenerateReportCommand(gameState, this.id, report);
            return reportCommand;
        } else if (format.equalsIgnoreCase(".pdf") || format.equalsIgnoreCase("pdf")){
            ReportGenerator report = new PDFReportGenerator();
            Command reportCommand = new GenerateReportCommand(gameState, this.id, report);
            return reportCommand;
        } else if (format.equalsIgnoreCase(".txt") || format.equalsIgnoreCase("txt")){
            ReportGenerator report = new TXTReportGenerator();
            Command reportCommand = new GenerateReportCommand(gameState, this.id, report);
            return reportCommand;
        } else {
            return null;
        }
    }
}