package group20.ReportGenerationStrategy;

import group20.GameLogic.GameState;
import group20.GameLogic.Player;
import group20.GameLogic.Turn;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Generates game reports in .txt format.
 */
public class TXTReportGenerator implements ReportGenerator {

    /**
     * Writes a text summary of the completed game.
     *
     * @param state - the game state containing all players and turns
     * @param gameId - the formatted game identifier
     */
    @Override
    public void generateReport(GameState state, int gameId) {
        String fileName = "sample_game_report.txt";
        try (FileWriter writer = new FileWriter(fileName)) {

            writer.write("JEOPARDY PROGRAMMING GAME REPORT\n");
            writer.write("================================\n\n");
            // Formatted to get the Game ID's format to include 3 significant figures
            writer.write("Case ID: GAME" + String.format("%03d", gameId) + "\n\n");

            // Print players
            writer.write("Players: ");
            for (int i = 0; i < state.getPlayers().size(); i++) {
                writer.write(state.getPlayers().get(i).getName());
                if (i < state.getPlayers().size() - 1) {
                    writer.write(", ");
                }
            }

            writer.write("\n\nGameplay Summary:\n");
            writer.write("-----------------\n\n");

            // Print each turn
            for (Turn t : state.getTurns().values()) {

                int pts = t.getTurnQuestion().getPoints();

                writer.write("Turn " + t.getTurnNumber() + ": " +
                            t.getTurnPlayer().getName() +
                            " selected " + t.getTurnCategory().getName() +
                            " for " + pts + " pts\n");

                writer.write("Question: " + t.getTurnQuestion().getText() + "\n");

                // Get the answer text
                String optionText = t.getTurnQuestion().getOptionText(t.getAnswerGiven());

                // Result
                String resultText;
                if (t.isCorrect()) {
                    resultText = "Correct (+" + pts + " pts)";
                } else {
                    resultText = "Incorrect (-" + pts + " pts)";
                }

                writer.write("Answer: " + optionText + " - " + resultText + "\n");

                writer.write("Score after turn: "
                        + t.getTurnPlayer().getName()
                        + " = " + t.getTurnFinalScore() + "\n\n");
            }

            // Final scores
            writer.write("Final Scores:\n");
            for (Player p : state.getPlayers()) {
                writer.write(p.getName() + ": " + p.getScore() + "\n");
            }

            System.out.println("TXT Report generated: " + fileName);

        } catch (IOException e) {
            System.out.println("Error generating TXT report: " + e.getMessage());
        }
    }
}