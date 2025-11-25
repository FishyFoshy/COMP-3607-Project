package group20.GameActionCommands;

import java.util.Map;

import group20.EventLogging.EventLogEntry;
import group20.Exceptions.CommandExecutionException;
import group20.GameLogic.GameState;
import group20.GameLogic.Player;
import group20.GameLogic.Question;
/**Verifies a given {@link Question} exists in the current {@link GameState}, then updates the current
 * {@link group20.GameLogic.Turn} by calling {@link group20.GameLogic.Turn#setTurnQuestion}
 */
public class SelectQuestionCommand extends Command {
    private int selectedQuestionVal;

    public SelectQuestionCommand(GameState state, int selectedQuestionVal){
        super(state);
        this.selectedQuestionVal = selectedQuestionVal;
    }

    public void execute() throws CommandExecutionException {    
        Map<Integer, Question> unansweredQuestions = state.getCurrentTurn().getTurnCategory().getUnansweredQuestions();
        if(unansweredQuestions.containsKey(this.selectedQuestionVal)){
            Question question = unansweredQuestions.get(this.selectedQuestionVal);
            state.getCurrentTurn().setTurnQuestion(question);
            state.getCurrentTurn().getTurnCategory().removeQuestion(selectedQuestionVal);
        } else {
            throw new CommandExecutionException("Invalid question:" + selectedQuestionVal + ". Please try again.");
        } 
        createEventLogEntry();
    };

    protected void createEventLogEntry(){
        EventLogEntry entry = new EventLogEntry();
        Player player = this.state.getCurrentTurn().getTurnPlayer();
        entry.setPlayerID(player.getName());
        entry.setActivity("Select Question");
        entry.setTimestamp(this.timestamp);
        entry.setCategory(this.state.getCurrentTurn().getTurnCategory().getName());
        entry.setQuestionValue(this.selectedQuestionVal);
        entry.setScoreAfterPlay(String.valueOf(player.getScore()));;
        this.entry = entry;
    }
}
