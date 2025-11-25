package group20.GameLogic;
/**Holds relevant player information */
public class Player {
    private int id;
    private String name;
    private int score;

    static private int idCounter = 0;

    public Player(String name) {
        this.id = idCounter++;
        this.name = name;
        this.score = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    /**@param isAnswerCorrect Increments the player's {@link #score} by @param points if true, otherwise decrements it.  */
    public void updateScore(int points, boolean isAnswerCorrect) {
        if (isAnswerCorrect) {
            this.score += points;
        } else {
            this.score -= points;
        }
    }
}