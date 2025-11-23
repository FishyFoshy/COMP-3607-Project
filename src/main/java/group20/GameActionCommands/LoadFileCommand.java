package group20.GameActionCommands;

import group20.FileParsing.AbstractQuestionParser;
import group20.GameLogic.GameState;

public class LoadFileCommand extends Command {
    private AbstractQuestionParser parser;

    public LoadFileCommand(AbstractQuestionParser parser){
        this.parser = parser;
    }
    
    public void execute(GameState state){
        parser.run();
    };
}
