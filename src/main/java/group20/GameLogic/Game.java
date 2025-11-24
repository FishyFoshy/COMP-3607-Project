package group20.GameLogic;

import group20.FileParsing.AbstractQuestionParser;
import group20.FileParsing.CSVParser;
import group20.FileParsing.JSONParser;
import group20.FileParsing.XMLParser;
import group20.GameActionCommands.Command;
import group20.GameActionCommands.CommandInvoker;
<<<<<<< HEAD
import group20.GameActionCommands.EnterPlayerNameCommand;
import group20.GameActionCommands.InvalidCommandException;
import group20.GameActionCommands.LoadFileCommand;
import group20.GameActionCommands.SelectPlayerCountCommand;

=======
/**Controls game flow by instantiating the necessary {@link Command}s and using a {@link CommandInvoker} to execute them */
>>>>>>> event-logging
public class Game {
    private int id;
    /**{@link GameState} holds all the information regarding the current state of the game */
    private GameState gameState;
<<<<<<< HEAD
=======
    private Map<String, Command> commands;
    /**{@link CommandInvoker} is used to execute commands */
>>>>>>> event-logging
    private CommandInvoker invoker;
    private CLIController input;

    static private int idCounter = 0;

    public Game() {
        this.id = idCounter++;
        this.gameState = new GameState();
        this.input = new CLIController();
    }

    public void startGame() {
        /* String filePath = input.askForFilePath();

        if (filePath.toLowerCase().endsWith(".csv")) {
            AbstractQuestionParser parser = new CSVParser();
            Command load = new LoadFileCommand(parser, filePath);
            try {
                invoker.executeCommand(load);
            } catch (InvalidCommandException e) {
                // TODO: handle exception
            }
        } if (filePath.toLowerCase().endsWith(".json")) {
            AbstractQuestionParser parser = new JSONParser();
            Command load = new LoadFileCommand(parser, filePath);
            try {
                invoker.executeCommand(load);
            } catch (InvalidCommandException e) {
                // TODO: handle exception
            }
        } if (filePath.toLowerCase().endsWith(".xml")) {
            AbstractQuestionParser parser = new XMLParser();
            Command load = new LoadFileCommand(parser, filePath);
            try {
                invoker.executeCommand(load);
            } catch (InvalidCommandException e) {
                // TODO: handle exception
            }
        } else{
            System.out.println("Cannot parse file: " + filePath);
            System.out.println("File must be of type .csv, .json or .xml!");
        } */

            String filePath = "C:\\Users\\Dominic\\Desktop\\OOP2 Proj\sample_game_CSV.csv";
            AbstractQuestionParser parser = new CSVParser();
            Command load = new LoadFileCommand(parser, filePath);
            try {
                invoker.executeCommand(load);
            } catch (InvalidCommandException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            int count = input.askForPlayerCount();
            Command countSelector = new SelectPlayerCountCommand(gameState, count);
            try {
                invoker.executeCommand(countSelector);
            } catch (InvalidCommandException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            for (int index = 0; index < gameState.getPlayerCount(); index++) {
                Command enterName = new EnterPlayerNameCommand(gameState, input.askForPlayerName());
                try {
                    invoker.executeCommand(enterName);
                } catch (InvalidCommandException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            }
        
    }

    public void playTurn() {
        // Implementation for playing a turn
    }
    
    public void endGame() {
        // Implementation for ending the game
    }

    public int getId(){ return this.id; }
    public GameState getGameState(){ return this.gameState; }
    public Map<String, Command> commands(){ return this.commands; }
    public CommandInvoker getCommandInvoker(){ return this.invoker; }
}
