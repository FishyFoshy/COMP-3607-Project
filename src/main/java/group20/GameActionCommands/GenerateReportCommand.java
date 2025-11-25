package group20.GameActionCommands;

import group20.EventLogging.EventLogEntry;
import group20.Exceptions.CommandExecutionException;
import group20.GameLogic.GameState;
import group20.ReportGenerationStrategy.ReportGenerator;
/** Generates the game report */
public class GenerateReportCommand extends Command {
    private ReportGenerator reportGenerator;
    private int gameID;

    /**@param reportGenerator a concrete subclass of {@link ReportGenerator} for the desired output format */
    public GenerateReportCommand(GameState state, int gameID, ReportGenerator reportGenerator){
        super(state);
        this.gameID = gameID;
        this.reportGenerator = reportGenerator;
    };

    public void execute() throws CommandExecutionException {
        try {
            this.reportGenerator.generateReport(state, gameID);
            createEventLogEntry();
        } catch (Exception e) {
            throw new CommandExecutionException("Failed to generate report: " + e.getMessage(), e);
        }  
    };

    protected void createEventLogEntry(){
        EventLogEntry entry = new EventLogEntry();
        entry.setPlayerID("System");
        entry.setActivity("Generate Report");
        entry.setTimestamp(this.timestamp);
        entry.setResult("N/A");
        this.entry = entry;
    }
}
