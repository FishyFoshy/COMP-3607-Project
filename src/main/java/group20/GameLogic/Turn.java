package group20.GameLogic;

public class Turn {
    private int turnNumber;
    private Player turnPlayer;
    private Category turnCategory;
    private Question turnQuestion;
    private char answerGiven;
    private boolean isCorrect;
    private int newPlayerScore;

    static private int turnCounter = 0;

    public Turn(Player player){
        this.turnNumber = turnCounter++;
        this.turnPlayer = player;
    }

    public void setTurnCategory(Category category) {
        this.turnCategory = category;
    }

    public void setTurnQuestion(Question question) {
        this.turnQuestion = question;
    }

    public void setAnswerGiven(char answer) {
        this.answerGiven = answer;
    }

    public void evaluateAnswer() {
        this.isCorrect = turnQuestion.isCorrect(answerGiven);
        turnPlayer.updateScore(turnQuestion.getPoints(), isCorrect);
        this.newPlayerScore = turnPlayer.getScore();
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    @Override
    public String toString() {

    }
}
