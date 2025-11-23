package group20.GameLogic;

import java.util.Map;

public class Category {
    private int id;
    private String name;
    private Map<Integer, Question> unansweredQuestions;

    static private int idCounter = 0;

    public Category(String name) {
        this.id = idCounter++;
        this.name = name;
        this.unansweredQuestions = new java.util.TreeMap<>();
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

    public void displayQuestions(){
        System.out.println("Category: " + name);

        for (Integer key : unansweredQuestions.keySet()) {
            System.out.println(key);
        }
    }
}
