package group20.GameLogic;

import java.util.Map;

public class Question {
    private int id;
    private String text;
    private int points;
    private char answer;
    private Map<Character, String> options;

    static private int idCounter = 0;

    public Question() {
        this.id = idCounter++;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setAnswer(char answer) {
        this.answer = answer;
    }

    public void setOptions(Map<Character, String> options) {
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getPoints() {
        return points;
    }

    public boolean isCorrect(char response) {
        return answer == response;
    }

    public String getOption(char optionKey) {
        return options.get(optionKey);
    }
}