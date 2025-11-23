package group20.GameLogic;

import java.util.Map;
import java.util.TreeMap;

public class Category {
    private int id;
    private String name;
    private Map<Integer, Question> unansweredQuestions;

    static private int idCounter = 0;

    public Category(String name) {
        this.id = idCounter++;
        this.name = name;
        this.unansweredQuestions = new TreeMap<>();
    }

    public void addQuestion(Question question) {
        unansweredQuestions.put(question.getPoints(), question);
    }

    public void removeQuestion(int questionId) {
        unansweredQuestions.remove(questionId);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<Integer, Question> getUnansweredQuestions() {
        return unansweredQuestions;
    }

    public void displayQuestions(){
        System.out.println("Category: " + name);

        for (Integer key : unansweredQuestions.keySet()) {
            System.out.println(key);
        }
    }
}
