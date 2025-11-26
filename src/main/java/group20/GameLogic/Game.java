package group20.GameLogic;

import java.util.List;

import group20.Exceptions.CommandCreationException;
import group20.Exceptions.CommandExecutionException;
import group20.Exceptions.GameQuitException;
import group20.Exceptions.InvalidFileFormatException;
import group20.FileParsing.AbstractQuestionParser;
import group20.FileParsing.FileParserCreator;
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
    private CLIController cliController;

    /**{@link CommandCreator} is used to create the required commands */
    private CommandCreator commandCreator;

    /**{@link FileParserCreator} is used to create the required {@link AbstractQuestionParser} */
    private FileParserCreator fileParserCreator;

    /**{@link ReportGeneratorCreator} is used to create the required {@link ReportGenerator} */
    private ReportGeneratorCreator reportCreator;

    static private int idCounter = 0;

    public Game() {
        this.id = idCounter++;
        this.gameState = new GameState();
        this.invoker = new CommandInvoker();
        this.cliController = new CLIController();

        this.fileParserCreator = new FileParserCreator();
        this.reportCreator = new ReportGeneratorCreator();
        this.commandCreator = new CommandCreator(fileParserCreator);
    }

    private void loadFile() {
        while (true) {
            try {
                String filePath = cliController.askForFilePath();
                invoker.executeCommand(commandCreator.createLoadFileCommand(gameState, filePath));
                return;
            } catch (CommandCreationException | CommandExecutionException e) {
                cliController.displayMessage(e.getMessage());
            }
        }
    }

    private void selectPlayerCount() {
        while (true) {
            try {
                int count = cliController.askForPlayerCount();
                invoker.executeCommand(commandCreator.createSelectPlayerCountCommand(gameState, count));
                return;
            } catch (CommandCreationException | CommandExecutionException e) {
                cliController.displayMessage(e.getMessage());
            }
        }
    }

    private void enterPlayerNames() {
        for (int i = 1; i <= gameState.getPlayerCount(); i++) {
            while (true) {
                try {
                    String name = cliController.askForPlayerName(i);
                    invoker.executeCommand(commandCreator.createEnterPlayerNameCommand(gameState, name));
                    break;
                } catch (CommandExecutionException e) {
                    cliController.displayMessage(e.getMessage());
                }
            }
        }
    }

    private boolean askForCategory() throws GameQuitException {
        cliController.displayCategories(gameState.getCategories());
        while (true) {
            try {
                String category = cliController.askForCategory();
                if(category.equalsIgnoreCase("quit")) { throw new GameQuitException(); }

                Command selectCategory = commandCreator.createSelectCategoryCommand(gameState, category);
                invoker.executeCommand(selectCategory);
                return true;

            } catch (CommandExecutionException e) {
                cliController.displayMessage(e.getMessage());
            }
        }
    }

    private void askForQuestion() {
        cliController.displayCategoryQuestions(this.gameState.getCurrentTurn().getTurnCategory());
        while (true) {
            try {
                int question = cliController.askForQuestion();
                Command selectQuestion = commandCreator.createSelectQuestionCommand(gameState, question);
                invoker.executeCommand(selectQuestion);
                return;

            } catch (CommandExecutionException e) {
                cliController.displayMessage(e.getMessage());
            }
        }
    }

    private void askForAnswer() {
        cliController.displayQuestionOptions(this.gameState.getCurrentTurn().getTurnQuestion());
        while (true) {
            try {
                char answer = cliController.askForAnswer();
                Command answerQuestion = commandCreator.createAnswerQuestionCommand(gameState, answer);
                invoker.executeCommand(answerQuestion);
                return;
            } catch (CommandCreationException | CommandExecutionException e) {
                cliController.displayMessage(e.getMessage());
            }
        }
    }

    private void generateReport(){
        while (true) {
            try {
                String format = cliController.askForFileFormat();
                ReportGenerator reportGenerator = reportCreator.getReportGenerator(format);
                Command reportCommand = commandCreator.createGenerateReportCommand(gameState, this.id, reportGenerator);
                invoker.executeCommand(reportCommand);
                return;
            } catch (InvalidFileFormatException | CommandExecutionException e) {
                cliController.displayMessage(e.getMessage());
            }
        }
    }
    /** Starts the game by calling {@link #loadFile()} -> {@link #selectPlayerCount()} -> {@link #enterPlayerNames()} -> {@link #playGame()} */
    public void startGame() {
        try {
            invoker.executeCommand(commandCreator.createStartCommand(gameState));
            loadFile();
            selectPlayerCount();
            enterPlayerNames();
            playGame();
        } catch (CommandExecutionException | CommandCreationException e) {
            cliController.displayFailedStart(e.getMessage());
        }
    }

    /** Game loop that calls {@link #askForCategory} -> {@link #askForQuestion()} -> {@link #askForAnswer()} while checking for game end. */
    public void playGame() {
        int index = 0;
        List<Player> players = gameState.getPlayers();
        
        while (!gameState.getIsOver()) {
            Player currentPlayer = players.get(index % gameState.getPlayerCount());
            index++;
            
            gameState.startNewTurn(currentPlayer);
            cliController.displayTurnMessage(index, currentPlayer.getName());
            
            try {
                askForCategory();
            } catch (GameQuitException e) {
                cliController.displayEarlyEndGame();
                endGame();
                return;
            }

            askForQuestion();
            askForAnswer();   
            
            if(gameState.isFinished()) {
                endGame();
                return;
            }
            gameState.endTurn();
        }
    }
    
    /**Ends the game and generates the report + process mining log */
    public void endGame(){
        try {
            generateReport();

            String eventLogFilename = "game_event_log.csv"; //Assignmented specified this filename, so it wasn't made dynamic
            Command eventLogCommand = commandCreator.createGenerateEventLogCommand(invoker, this.id, eventLogFilename);
            invoker.setDelayedCommand(eventLogCommand);

            Command exitCommand = commandCreator.createExitGameCommand(gameState);
            invoker.executeCommand(exitCommand);
            invoker.executeDelayedCommand();
            cliController.displayEventLogGenerated(eventLogFilename);

        } catch (CommandExecutionException e) {
            cliController.displayFailedEnd(e.getMessage());
        }
    }
}