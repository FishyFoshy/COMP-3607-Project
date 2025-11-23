package group20.GameActionCommands;

import group20.FileParsing.AbstractQuestionParser;

public class LoadFileCommand extends Command {
    private final AbstractQuestionParser parser;
    private final String filePath;

    public LoadFileCommand(AbstractQuestionParser parser, String filePath){
        this.parser = parser;
        this.filePath = filePath;
    }
    
    public void execute() throws InvalidCommandException {
        if(this.filePath == null || this.filePath.isEmpty()){
            throw new InvalidCommandException("File path not specified.");
        }

        try {
            parser.run(this.filePath);
        } catch (Exception e) {
            System.out.println("Caught " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    };
}
