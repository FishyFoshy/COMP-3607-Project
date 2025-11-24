package group20.EventLogging;

import java.time.Instant;

public class EventLogEntry {
    private String caseID;
    private String playerID;
    private String activity;
    private Instant timestamp;
    private String category;
    private int questionValue;
    private String answerGiven;
    private String result;
    private int scoreAfterPlay;

    public EventLogEntry() {}

    public String getCaseID() { return caseID; }
    public String getPlayerID() { return playerID; }
    public String getActivity() { return activity; }
    public Instant getTimestamp() { return timestamp; }
    public String getCategory() { return category; }
    public int getQuestionValue() { return questionValue; }
    public String getAnswerGiven() { return answerGiven; }
    public String getResult() { return result; }
    public int getScoreAfterPlay() { return scoreAfterPlay; }

    public void setCaseID(String caseID) { this.caseID = caseID; }
    public void setPlayerID(String playerID) { this.playerID = playerID; }
    public void setActivity(String activity) { this.activity = activity; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    public void setCategory(String category) { this.category = category; }
    public void setQuestionValue(int questionValue) { this.questionValue = questionValue; }
    public void setAnswerGiven(String answerGiven) { this.answerGiven = answerGiven; }
    public void setResult(String result) { this.result = result; }
    public void setScoreAfterPlay(int scoreAfterPlay) { this.scoreAfterPlay = scoreAfterPlay; }
}

