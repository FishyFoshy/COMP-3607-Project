package group20.GameActionCommands;

import java.time.Instant;
import group20.GameLogic.GameState;

public abstract class Command {
    protected Instant timestamp;
    protected GameState state;

    public Command(){}
    
    public Command(GameState state){
        this.state = state;
    };

    public void storeTimestamp(){
        this.timestamp = Instant.now();
    }

    public abstract void execute() throws InvalidCommandException;

    public String toString(){
        return this.getClass().getSimpleName();
    }
}
