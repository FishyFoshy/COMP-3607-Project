package group20.GameLogic;

import java.util.Map;

public class Question {
    private int id;
    private String text;
    private int points;
    private char answer;
    private Map<Character, String> options;

    static private int idCounter = 0;

    public Question(String text, int points, char answer, Map<Character, String> options) {
        this.id = idCounter++;
        this.text = text;
        this.points = points;
        this.answer = answer;
        this.options = options;
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

    public int getPoints() {
        return points;
    }

    public boolean isCorrect(char response) {
        return this.answer == response;
    }

    public String getOption(char optionKey) {
        return options.get(optionKey);
    }
}