package group20.ReportGenerationStrategy;

import group20.GameLogic.GameState;
import group20.GameLogic.Player;
import group20.GameLogic.Turn;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.File;
import java.io.IOException;

/**
 * Generates game reports in .pdf format using PDFBox.
 */
public class PDFReportGenerator implements ReportGenerator {
    private String fileName;

    public PDFReportGenerator() {
        this.fileName = "game_report.pdf";
    }

    public PDFReportGenerator(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Writes a text summary of the completed game.
     *
     * @param state - the game state containing all players and turns
     * @param gameId - the formatted game identifier
     */
    @Override
    public void generateReport(GameState state, int gameId) {
        try (PDDocument document = new PDDocument()) {
            
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);
            float yPosition = 750;

            // Title
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 12);
            content.newLineAtOffset(50, yPosition);
            content.showText("JEOPARDY PROGRAMMING GAME REPORT");
            content.endText();

            yPosition -= 20;
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 12);
            content.newLineAtOffset(50, yPosition);
            content.showText("====================================");
            content.endText();

            yPosition -= 30;
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 12);
            content.newLineAtOffset(50, yPosition);
            // Formatted to get the Game ID's format to include 3 significant figures
            content.showText("Case ID: GAME" + String.format("%03d", gameId));
            content.endText();

            // Players
            yPosition -= 30;
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 12);
            content.newLineAtOffset(50, yPosition);
            StringBuilder playerLine = new StringBuilder("Players: ");
            for (int i = 0; i < state.getPlayers().size(); i++) {
                playerLine.append(state.getPlayers().get(i).getName());
                if (i < state.getPlayers().size() - 1) playerLine.append(", ");
            }
            content.showText(playerLine.toString());
            content.endText();

            // Gameplay Summary
            yPosition -= 30;
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 12);
            content.newLineAtOffset(50, yPosition);
            content.showText("Gameplay Summary:");
            content.endText();

            yPosition -= 20;
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 12);
            content.newLineAtOffset(50, yPosition);
            content.showText("----------------------------");
            content.endText();

            // Iterate turns
            for (Turn t : state.getTurns().values()) {
                // Each turn might take up to 4 lines + 1 spacer
                int linesNeeded = 5;
                if (yPosition - (linesNeeded * 20) < 50) {
                    // Close current content stream and start new page
                    content.close();
                    page = new PDPage();
                    document.addPage(page);
                    content = new PDPageContentStream(document, page);
                    yPosition = 750; // reset top of page
                }

                // Turn number + player + category + points
                yPosition -= 20;
                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 12);
                content.newLineAtOffset(50, yPosition);
                content.showText("Turn " + t.getTurnNumber() + ": " +
                                t.getTurnPlayer().getName() + " selected " +
                                t.getTurnCategory().getName() + " for " +
                                t.getTurnQuestion().getPoints() + " pts");
                content.endText();
                yPosition -= 20;

                // Question
                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 12);
                content.newLineAtOffset(50, yPosition);
                content.showText("Question: " + t.getTurnQuestion().getText());
                content.endText();
                yPosition -= 20;
    
                // Answer
                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 12);
                content.newLineAtOffset(50, yPosition);
                String resultText;
                if (t.isCorrect()) {
                    resultText = "Correct (+" + t.getTurnQuestion().getPoints() + " pts)";
                } else {
                    resultText = "Incorrect (-" + t.getTurnQuestion().getPoints() + " pts)";
                }
                content.showText("Answer: " + t.getTurnQuestion().getOptionText(t.getAnswerGiven()) + " - " + resultText);
                content.endText();
                yPosition -= 20;

                // Score after turn
                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 12);
                content.newLineAtOffset(50, yPosition);
                content.showText("Score after turn: " + t.getTurnPlayer().getName()
                + " = " + t.getTurnFinalScore());
                content.endText();
                yPosition -= 20;

                // Blank line to separate turns
                yPosition -= 20;
            }


            // Final scores
            yPosition -= 30;
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 12);
            content.newLineAtOffset(50, yPosition);
            content.showText("Final Scores:");
            content.endText();

            yPosition -= 20;
            for (Player p : state.getPlayers()) {
                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 12);
                content.newLineAtOffset(50, yPosition);
                content.showText(p.getName() + ": " + p.getScore());
                content.endText();
                yPosition -= 20;
            }

            content.close();
            document.save(new File(fileName));
            System.out.println("PDF Report generated: " + fileName);

        } catch (IOException e) {
            System.out.println("Error generating PDF report: " + e.getMessage());
        }
    }
}