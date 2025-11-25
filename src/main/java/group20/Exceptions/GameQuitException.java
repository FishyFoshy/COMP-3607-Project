package group20.Exceptions;

/**Thrown when a player quits the game early */
public class GameQuitException extends Exception {
    public GameQuitException() {
        super("Player chose to quit the game.");
    }
}