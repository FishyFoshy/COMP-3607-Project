package group20.GameLogic;

import java.util.List;

import group20.GameActionCommands.AnswerQuestionCommand;
import group20.GameActionCommands.Command;
import group20.GameActionCommands.CommandInvoker;
import group20.GameActionCommands.EnterPlayerNameCommand;
import group20.GameActionCommands.InvalidCommandException;
import group20.GameActionCommands.LoadFileCommand;
import group20.GameActionCommands.SelectCategoryCommand;
import group20.GameActionCommands.SelectPlayerCountCommand;
import group20.GameActionCommands.SelectQuestionCommand;
import group20.GameActionCommands.ExitGameCommand;

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
        String filePath = "C:\\Users\\Dominic\\Desktop\\OOP2 Proj\\sample_game_CSV.csv";
        Command load = new LoadFileCommand(gameState, filePath);
        try {
            invoker.executeCommand(load);
        } catch (InvalidCommandException e) {
            e.printStackTrace();
        }

        int count = input.askForPlayerCount();
        Command countSelector = new SelectPlayerCountCommand(gameState, count);

        try {
            invoker.executeCommand(countSelector);
        } catch (InvalidCommandException e) {
            e.printStackTrace();
        }

        for (int index = 0; index < gameState.getPlayerCount(); index++) {
            String name = input.askForPlayerName();
            Command enterName = new EnterPlayerNameCommand(gameState, name);
            try {
                invoker.executeCommand(enterName);
            } catch (InvalidCommandException e) {
                e.printStackTrace();
            }
        }
    }

    public void playGame() {
        int index = 0;
        List<Player> players = gameState.getPlayers();
        Command exitCommand = new ExitGameCommand(gameState);
        while (!gameState.getIsOver()) {
            Player currentPlayer = players.get(index % gameState.getPlayerCount());
            index++;

            gameState.startNewTurn(currentPlayer);

            System.out.println("Turn " + index + " Current Player: " + currentPlayer.getName() + "\n");

            String category = input.askForCategory(gameState.getCategories());

            if (category.equalsIgnoreCase("quit") || category.equalsIgnoreCase("exit") || category.equalsIgnoreCase("end")) {
                try {
                    invoker.executeCommand(exitCommand);
                } catch (InvalidCommandException e) {
                    e.printStackTrace();
                }
                break;
            }

            Command selectCategory = new SelectCategoryCommand(gameState, category);
            try {
                invoker.executeCommand(selectCategory);
            } catch (InvalidCommandException e) {
                e.printStackTrace();
            }
            
            Category c = gameState.getCurrentTurn().getTurnCategory();

            if (category.equalsIgnoreCase("quit") || category.equalsIgnoreCase("exit") || category.equalsIgnoreCase("end")) {
                try {
                    invoker.executeCommand(exitCommand);
                } catch (InvalidCommandException e) {
                    e.printStackTrace();
                }
                break;
            }

            int question = input.askForQuestion(c);
            Command selectQuestion = new SelectQuestionCommand(gameState, question);
            try {
                invoker.executeCommand(selectQuestion);
            } catch (InvalidCommandException e) {
                e.printStackTrace();
            }

            Question q = gameState.getCurrentTurn().getTurnQuestion();

            char answer = input.askForAnswer(q);

            if (category.equalsIgnoreCase("quit") || category.equalsIgnoreCase("exit") || category.equalsIgnoreCase("end")) {
                try {
                    invoker.executeCommand(exitCommand);
                } catch (InvalidCommandException e) {
                    e.printStackTrace();
                }
                break;
            }

            Command answerQuestion = new AnswerQuestionCommand(gameState, answer);
            try {
                invoker.executeCommand(answerQuestion);
            } catch (InvalidCommandException e) {
                e.printStackTrace();
            }
            
            if(gameState.isFinished()) {
                gameState.setIsOver(true);
                try {
                    invoker.executeCommand(exitCommand);
                } catch (InvalidCommandException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
