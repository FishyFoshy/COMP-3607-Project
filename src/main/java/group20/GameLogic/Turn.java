package group20.GameLogic;

public class Turn {
    private int turnNumber;
    private Player turnPlayer;
    private Category turnCategory;
    private Question turnQuestion;
    private char answerGiven;
    private boolean correct;
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
        this.correct = turnQuestion.isCorrect(answerGiven);
        turnPlayer.updateScore(turnQuestion.getPoints(), correct);
        this.newPlayerScore = turnPlayer.getScore();
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public Player getTurnPlayer() {
        return turnPlayer;
    }

    public Category getTurnCategory() {
        return turnCategory;
    }

    public Question getTurnQuestion() {
        return turnQuestion;
    }

    public char getAnswerGiven() {
        return answerGiven;
    }

    public boolean isCorrect() {
        return correct;
    }

    public int getTurnFinalScore() {
        return newPlayerScore;
    }
}
