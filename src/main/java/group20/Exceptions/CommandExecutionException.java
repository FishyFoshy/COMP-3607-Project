package group20.Exceptions;

/**Thrown when a {@link group20.GameActionCommands.Command} fails */
public class CommandExecutionException extends Exception {
    public CommandExecutionException(String message) { super(message); }
    public CommandExecutionException(String message, Throwable cause) { super(message, cause); }
}
