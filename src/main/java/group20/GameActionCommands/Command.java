package group20.GameActionCommands;

import java.time.Instant;
import group20.GameLogic.GameState;

public abstract class Command {
    protected Instant timestamp;

    public Command(){};

    public Instant getTimestamp(){
        this.timestamp = Instant.now();
        return this.timestamp;
    }
    public abstract void execute(GameState state);

    public String toString(){
        return this.getClass().getSimpleName();
    }
}
