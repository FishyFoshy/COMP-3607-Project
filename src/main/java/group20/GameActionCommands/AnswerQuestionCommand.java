package group20.GameActionCommands;

import group20.EventLogging.EventLogEntry;
import group20.GameLogic.GameState;
import group20.GameLogic.Player;
import group20.GameLogic.Turn;
/**Evaluates a {@link Player}'s answer for the current {@link Turn}'s {@link group20.GameLogic.Question}, and updates the {@link Player}'s score */
public class AnswerQuestionCommand extends Command {
    private char selectedAnswer;

    public AnswerQuestionCommand(GameState state, char selectedAnswer){
        super(state);
        this.selectedAnswer = selectedAnswer;
    };

    public void execute(){
        Turn turn = this.state.getCurrentTurn();
        turn.setAnswerGiven(selectedAnswer);
        turn.evaluateAnswer();
        createEventLogEntry();
    };

    protected void createEventLogEntry(){
        EventLogEntry entry = new EventLogEntry();
        Turn turn = this.state.getCurrentTurn();
        Player player = turn.getTurnPlayer();
        
        entry.setPlayerID(player.getName());
        entry.setActivity("Answer Question");
        entry.setTimestamp(this.timestamp);
        entry.setCategory(turn.getTurnCategory().getName());
        entry.setQuestionValue(turn.getTurnQuestion().getPoints());
        entry.setAnswerGiven(turn.getTurnQuestion().getOptionText(this.selectedAnswer));
        if(turn.isCorrect() == true) {
            entry.setResult("Correct");
        } else {
            entry.setResult("Incorrect");
        }
        entry.setScoreAfterPlay(player.getScore());;
        this.entry = entry;
    }
}
