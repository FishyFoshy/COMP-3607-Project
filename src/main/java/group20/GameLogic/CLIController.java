package group20.GameLogic;

import java.util.List;
import java.util.Scanner;
/**Handles UI interaction through the console */
public class CLIController {
    private final Scanner scanner = new Scanner(System.in);

    /**Requests a {@link Category} from the player, by specifying its name */
    public String askForCategory(List<Category> categories){
        System.out.println("Select a category by typing its name (eg. Functions, Arrays): ");
        for(Category category : categories){
            category.displayQuestions();
        }
        return this.scanner.nextLine();
    }

    /**Requests a {@link Question} from the player, by specifying its point value */
    public int askForQuestion(){
        System.out.println("Select a question by typing its point value (eg. 300, 500): ");
        return this.scanner.nextInt();
    }

    /**Requests the answer for an {@link Question} from the player, by specifying the option letter */
    public char askForAnswer(){
        System.out.println("Enter your answer option (eg. A, C): ");
        return this.scanner.nextLine().charAt(0);
    }
}
