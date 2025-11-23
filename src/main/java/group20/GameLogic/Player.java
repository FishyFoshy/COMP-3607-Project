package group20.GameLogic;

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

    public int getScore() {
        return score;
    }

    public void updateScore(int points, boolean correct) {
        if (correct) {
            this.score += points;
        } else {
            this.score -= points;
        }
    }
}