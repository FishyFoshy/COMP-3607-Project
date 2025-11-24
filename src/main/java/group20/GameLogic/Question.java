package group20.GameLogic;

import java.util.Map;
/**Holds question data */
public class Question {
    private int id;
    /**The question text */
    private String text;
    /**The point value of the question */
    private int points;
    /**The correct option answer to the question (eg. A, B, C) */
    private char answer;
    /**A map of option letter to option text. Eg. (A - What is 2+2?, B - What is 3-1?) */
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

    public String getText() {
        return text;
    }

    public int getPoints() {
        return points;
    }

    /**Checks if a given option letter matches the answer option letter */
    public boolean isCorrect(char response) {
        return answer == response;
    }

    public char getAnswer() {
        return answer;
    }

    /**Returns the option text for a given option letter */
    public String getOptionText(char optionKey) {
        return options.get(optionKey);
    }

    public Map<Character, String> getOptions() {
        return options;
    }

    /**Displays each option letter followed by their corresonding option text */
    public void displayOptions() {
        System.out.println(text);
        
        for (Map.Entry<Character, String> entry : options.entrySet()) {
            System.out.println("(" + entry.getKey() + ") " + entry.getValue());
        }
    }
}