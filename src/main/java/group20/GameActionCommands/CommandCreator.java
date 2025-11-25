package group20.GameActionCommands;

import group20.Exceptions.CommandCreationException;
import group20.Exceptions.InvalidFileFormatException;
import group20.FileParsing.AbstractQuestionParser;
import group20.FileParsing.FileParserCreator;
import group20.GameLogic.GameState;
import group20.ReportGenerationStrategy.ReportGenerator;

/** Instantiates and returns concrete {@link Command}s with supplied paramaters. 
* Throws a {@link CommandCreationException} for invalid parameters */
public class CommandCreator {
    private FileParserCreator fileParserCreator;

    public CommandCreator(FileParserCreator fileParserCreator){
        this.fileParserCreator = fileParserCreator;
    }

    public Command createStartCommand(GameState state) throws CommandCreationException {
        return new StartGameCommand(state);
    }

    public Command createLoadFileCommand(GameState state, String filePath) throws CommandCreationException {
        try {
            AbstractQuestionParser parser = this.fileParserCreator.getFileLoader(filePath);
            return new LoadFileCommand(state, filePath, parser);
        } catch (InvalidFileFormatException e) {
            throw new CommandCreationException(e.getMessage(), e);
        }
    }

    public Command createSelectPlayerCountCommand(GameState state, int count) throws CommandCreationException {
        if(count < 1 || count > 4){
            throw new CommandCreationException("Player count must be between 1-4");
        }
        return new SelectPlayerCountCommand(state, count);
    }

    public Command createEnterPlayerNameCommand(GameState state, String name) {
        return new EnterPlayerNameCommand(state, name);
    }

    public Command createSelectCategoryCommand(GameState state, String category) {
        return new SelectCategoryCommand(state, category);
    }

    public Command createSelectQuestionCommand(GameState state, int questionIndex) {
        return new SelectQuestionCommand(state, questionIndex);
    }

    public Command createAnswerQuestionCommand(GameState state, char answer) throws CommandCreationException {
        if (answer < 'A' || answer > 'D') {
            throw new CommandCreationException("Invalid answer option. Answer option must be between A and D. Please try again.");
        }
        return new AnswerQuestionCommand(state, answer);
    }

    public Command createGenerateReportCommand(GameState state, int gameID, ReportGenerator reportGenerator){
        return new GenerateReportCommand(state, gameID, reportGenerator);
    }

    public Command createGenerateEventLogCommand(CommandInvoker invoker, int gameID, String filename){
        return new GenerateEventLogCommand(invoker, gameID, filename);
    }

    public Command createExitGameCommand(GameState state) {
        return new ExitGameCommand(state);
    }
}
