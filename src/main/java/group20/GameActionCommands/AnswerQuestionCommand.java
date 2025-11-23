package group20.GameActionCommands;

import group20.GameLogic.GameState;
import group20.GameLogic.Question;
import group20.GameLogic.Turn;

public class AnswerQuestionCommand extends Command {
    private char selectedAnswer;

    public AnswerQuestionCommand(GameState state, char selectedAnswer){
        super(state);
        this.selectedAnswer = selectedAnswer;
    };

    public void execute(){
        Turn turn = this.state.getCurrentTurn();
        Question question = turn.getTurnQuestion();
        boolean result = question.isCorrect(selectedAnswer);
        turn.getTurnPlayer().updateScore(question.getPoints(), result);
    };
}
