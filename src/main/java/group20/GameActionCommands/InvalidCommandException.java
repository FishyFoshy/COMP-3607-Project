package group20.GameActionCommands;
/**Thrown when a {@link Command} fails */
public class InvalidCommandException extends Exception {
    public InvalidCommandException(String message){
        super(message);
    }

    public InvalidCommandException(String message, Throwable cause){
        super(message, cause);
    }
}
