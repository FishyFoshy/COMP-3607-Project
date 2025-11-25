package group20.Exceptions;

/**Thrown when a {@link group20.GameActionCommands.Command} fails to be created by {@link group20.GameActionCommands.CommandCreator} */
public class CommandCreationException extends Exception {
    public CommandCreationException(String message) { super(message); }
    public CommandCreationException(String message, Throwable cause) { super(message, cause); }
}
