package group20.GameActionCommands;

import java.time.Instant;

import group20.EventLogging.EventLogEntry;
import group20.GameLogic.GameState;
/**Abstract class for the Command design pattern implementation. */
public abstract class Command {
    /** Each command stores its timestamp of execution. 
     * {@link CommandInvoker} calls {@link #storeTimestamp} to do so 
    */
    protected Instant timestamp; 

    /** Commands modify {@link GameState} */
    protected GameState state; 
    
    /** Each command knows how to create, and stores, an {@link EventLogEntry} for process mining */
    protected EventLogEntry entry; 

    public Command(){}
    
    public Command(GameState state){
        this.state = state;
    };

    public void storeTimestamp(){
        this.timestamp = Instant.now();
    }

    public abstract void execute() throws InvalidCommandException;

    /**
     * Creates an {@link EventLogEntry} object and stores it in the {@link #entry} field. Called at the end of every {@link #execute}.
     */
    public abstract void createEventLogEntry();

    public EventLogEntry getEntry() { return this.entry; }
    
    public String toString(){
        return this.getClass().getSimpleName();
    }
}
