package group20.GameActionCommands;

import group20.GameLogic.GameState;
import group20.ReportGenerationStrategy.ReportGenerator;

public class GenerateReportCommand extends Command {
    private ReportGenerator reportGenerator;
    private int gameID;

    public GenerateReportCommand(GameState state, int gameID, ReportGenerator reportGenerator){
        super(state);
        this.gameID = gameID;
    };

    public void execute() throws InvalidCommandException{
        this.reportGenerator.generateReport(state, gameID);
    };
}
