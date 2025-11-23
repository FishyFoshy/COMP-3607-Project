package group20.GameActionCommands;

import group20.GameLogic.GameState;

public class ExitGameCommand extends Command {
    public ExitGameCommand(GameState state){
        super(state);
    };

    public void execute(){
        this.state.setIsOver(true);
    };
}
