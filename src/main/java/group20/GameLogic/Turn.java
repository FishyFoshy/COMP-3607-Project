package group20.GameLogic;
/**Holds turn data */
public class Turn {
    private int turnNumber;
    /**The {@link Player} for whom the turn belongs to */
    private Player turnPlayer;

    /**The {@link Category} the {@link #turnPlayer} selected*/
    private Category turnCategory;

    /**The {@link Question} the {@link #turnPlayer} selected*/
    private Question turnQuestion;

    /**The answer(the option letter) the {@link #turnPlayer} gave*/
    private char answerGiven;

    /**Whether the {@link #turnPlayer}'s answer was correct or not */
    private boolean correct;

    /**The score of the {@link #turnPlayer} after giving their {@link #answerGiven} */
    private int newPlayerScore;

    public Turn(Player player, int turnNumber) {
        this.turnNumber = turnNumber;
        this.turnPlayer = player;
    }

    public void setTurnCategory(Category category) {
        this.turnCategory = category;
    }

    public void setTurnQuestion(Question question) {
        this.turnQuestion = question;
    }

    public void setAnswerGiven(char answer) {
        if (Character.isLowerCase(answer)) {
            answer = Character.toUpperCase(answer);
        }
        this.answerGiven = answer;
    }

    /**Checks if {@link #answerGiven} is correct for the selected {@link #turnQuestion}, and updates the {@link #turnPlayer}'s accordingly through
     * {@link Player#updateScore}
     */
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
