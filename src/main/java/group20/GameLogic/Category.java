package group20.GameLogic;

import java.util.Map;
import java.util.TreeMap;
/**Category to which {@link Question}s may belong. */
public class Category {
    private int id;
    /** Name of the category */
    private String name; 
    /** The currently unanswered questions left for the category */
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

    /**Displays the point values of the unanswered {@link Question}s left in the category. */
    public void displayQuestions(){ 
        System.out.println("Category: " + name);

        for (Integer key : unansweredQuestions.keySet()) {
            System.out.print(key + " ");
        }
        System.out.println();
    }
}
