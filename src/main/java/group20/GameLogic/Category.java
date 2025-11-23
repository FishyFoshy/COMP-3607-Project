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
        this.unansweredQuestions = new java.util.HashMap<>();
    }

    public void addQuestion(Question question) {
        unansweredQuestions.put(question.getId(), question);
    }

    public void removeQuestion(int questionId) {
        unansweredQuestions.remove(questionId);
    }

    public void displayQuestions(){
        
    }
}
