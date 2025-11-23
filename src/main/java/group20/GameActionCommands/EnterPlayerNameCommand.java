package group20.GameActionCommands;

import group20.GameLogic.GameState;
import group20.GameLogic.Player;

public class EnterPlayerNameCommand extends Command {
    private final String name;

    public EnterPlayerNameCommand(GameState state, String name){
        super(state);
        this.name = name;
    };

    public void execute(){
        Player player = new Player(name);
        state.addPlayer(player);
    };
}
