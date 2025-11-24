package group20.GameLogic;

import java.util.List;
import java.util.Scanner;

public class CLIController {
    private final Scanner scanner = new Scanner(System.in);

    public String askForFilePath(){
        System.out.println("Enter filepath to Start Game (eg. sample_game.csv, sample_game.json): ");
        return this.scanner.nextLine();
    }

    public int askForPlayerCount(){
        System.out.println("Enter number of players (1-4): ");
        return this.scanner.nextInt();
    }

    public String askForPlayerName(){
        System.out.println("Enter Name: ");
        return this.scanner.nextLine();
    }

    public String askForCategory(List<Category> categories){
        System.out.println("Select a category by typing its name (eg. Functions, Arrays): ");
        for(Category category : categories){
            category.displayQuestions();
        }
        return this.scanner.nextLine();
    }

    public int askForQuestion(Category category){
        System.out.println("Select a question by typing its point value (eg. 300, 500): ");
        category.displayQuestions();
        return this.scanner.nextInt();
    }

    public char askForAnswer(){
        System.out.println("Enter your answer option (eg. A, C): ");
        return this.scanner.nextLine().charAt(0);
    }
}
