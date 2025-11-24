package group20.GameActionCommands;

import java.util.Map;

import group20.EventLogging.EventLogEntry;
import group20.FileParsing.*;
import group20.GameLogic.Category;
import group20.GameLogic.GameState;
/** Loads question data into {@link GameState} */
public class LoadFileCommand extends Command {
    private final String filePath;
    private boolean success;

    
    public LoadFileCommand(GameState state, String filePath){
        this.state = state;
        this.filePath = filePath;
        this.success = false;
    }
    
    /**Returns a concrete subclass of {@link AbstractQuestionParser} for the detected file format in {@link #filePath} 
     * Throws {@link InvalidCommandException} if {@link #filePath} is empty or the file format is unsupported.
    */
    private AbstractQuestionParser getParser() throws InvalidCommandException{
        if(this.filePath == null || this.filePath.isEmpty()){
            throw new InvalidCommandException("File path empty");
        }

        if (filePath.endsWith(".csv")) {
            return new CSVParser();
        } else if (filePath.endsWith(".json")) {
            return new JSONParser();
        } else if (filePath.endsWith(".xml")) {
            return new XMLParser();
        } else {
            throw new InvalidCommandException("Unsupported file format");
        }
    }

    /** Loads the given file data into {@link GameState} using an {@link AbstractQuestionParser} returned by {@link #getParser} */
    public void execute() throws InvalidCommandException {
        try {
            AbstractQuestionParser parser = getParser();
            Map<String, Category> categoriesMap = parser.run(this.filePath);
            for(Category category : categoriesMap.values()){
                this.state.addCategory(category);
            }
            this.success = true;
        } catch (InvalidCommandException e){
            throw e;
        } catch (Exception e) {
            throw new InvalidCommandException("Error parsing file: " + e.getMessage(), e);
        } finally {
            createEventLogEntry();
        }
    };

    protected void createEventLogEntry(){
        EventLogEntry entry = new EventLogEntry();
        entry.setPlayerID("System");
        entry.setActivity("Load File");
        entry.setTimestamp(this.timestamp);
        if(this.success == true) {
            entry.setResult("Success");
        } else {
            entry.setResult("Failed");
        }
        this.entry = entry;
    }
}
