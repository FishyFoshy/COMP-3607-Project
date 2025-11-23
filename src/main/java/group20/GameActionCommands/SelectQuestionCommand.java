package group20.GameActionCommands;

import java.util.Map;
import group20.GameLogic.GameState;
import group20.GameLogic.Question;

public class SelectQuestionCommand extends Command {
    private int selectedQuestionVal;

    public SelectQuestionCommand(GameState state, int selectedQuestionVal){
        super(state);
        this.selectedQuestionVal = selectedQuestionVal;
    }

    public void execute() throws InvalidCommandException {    
        Map<Integer, Question> unansweredQuestions = state.getCurrentTurn().getTurnCategory().getUnansweredQuestions();
        if(unansweredQuestions.containsKey(this.selectedQuestionVal)){
            Question question = unansweredQuestions.get(this.selectedQuestionVal);
            state.getCurrentTurn().setTurnQuestion(question);
        } else {
            throw new InvalidCommandException("Selecteduestion invalid/unavailable");
        } 

    };
}
