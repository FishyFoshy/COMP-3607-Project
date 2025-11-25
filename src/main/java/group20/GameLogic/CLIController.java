package group20.GameLogic;

import java.util.List;
import java.util.Scanner;
/**Handles UI interaction through the console */
public class CLIController {
    private final Scanner scanner = new Scanner(System.in);

    public String askForFilePath(){
        System.out.println("Enter filepath to Start Game (eg. C:/Users/John/Desktop/sample_game_CSV.csv): ");
        return this.scanner.nextLine();
    }

    public int askForPlayerCount(){
        System.out.println("Enter number of players (1-4): ");
        return Integer.parseInt(this.scanner.nextLine());
    }

    public String askForPlayerName(int index){
        System.out.println("Enter Player " + index + " name: ");
        return this.scanner.nextLine();
    }

    public String askForFileFormat(){
        System.out.println("Enter file format to generate report as (eg. .docx, .pdf, .txt): ");
        String input = this.scanner.nextLine();
        return input.toLowerCase();
    }

    public void displayMessage(String message){
        System.out.println(message);
    }

    public void displayCategories(List<Category> categories){
        System.out.println("Select a category by typing its name (eg. Functions, Arrays): ");
        for(Category category : categories){
            category.displayQuestions();
            System.out.println();
        }
    }

    /**Requests a {@link Category} from the player, by specifying its name */
    public String askForCategory(){
        return this.scanner.nextLine().toLowerCase();
    }

    public void displayCategoryQuestions(Category category){
        System.out.println("Select a question by typing its point value (eg. 300, 500): ");
        category.displayQuestions();
    }

        
    /**Requests a {@link Question} from the player, by specifying its point value */
    public int askForQuestion(){
        return Integer.parseInt(this.scanner.nextLine());
    }

    public void displayQuestionOptions(Question question){
        System.out.println("Enter your answer option (eg. A, C): ");
        question.displayOptions();
    }

    /**Requests the answer for an {@link Question} from the player, by specifying the option letter */
    public char askForAnswer(){
        return Character.toUpperCase(this.scanner.nextLine().charAt(0));
    }

    public void displayFailedStart(String message){
        System.out.println("Failed to start game: " + message);
    }

    public void displayFailedEnd(String message){
        System.out.println("Failed to end game: " + message);
    }

    public void displayTurnMessage(int turnIndex, String playerName){
        System.out.println("[Turn " + turnIndex + "] Current Player: " + playerName);
        System.out.println("Type 'quit' to exit the game.\n");
    }

    public void displayEarlyEndGame(){
        System.out.println("Player chose to quit the game");
    }

    public void displayEventLogGenerated(String filename){
        System.out.println("Event log generated as: " + filename);
    }
}
