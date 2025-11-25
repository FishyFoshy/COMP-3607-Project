package group20.ReportGenerationStrategy;

import group20.GameLogic.GameState;
import org.apache.poi.xwpf.usermodel.*;
import java.io.FileOutputStream;
import group20.GameLogic.Player;
import group20.GameLogic.Turn;

/**
 * Generates game reports in .docx format using Apache POI.
 */
public class DOCXReportGenerator implements ReportGenerator {
    private String fileName;

    public DOCXReportGenerator() {
        this.fileName = "game_report.docx";
    }

    public DOCXReportGenerator(String fileName) {
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
        try (XWPFDocument doc = new XWPFDocument(); FileOutputStream out = new FileOutputStream(fileName)) {
            // Title
            XWPFParagraph title = doc.createParagraph();
            XWPFRun runTitle = title.createRun();
            runTitle.setText("JEOPARDY PROGRAMMING GAME REPORT");
            
            XWPFParagraph equalLinePara = doc.createParagraph();
            XWPFRun runLine = equalLinePara.createRun();
            runLine.setText("===================================");

            // Case ID
            XWPFParagraph caseId = doc.createParagraph();
            XWPFRun runCase = caseId.createRun();
            runCase.setFontSize(12);
            // Formatted to get the Game ID's format to include 3 significant figures
            runCase.setText("Case ID: GAME" + String.format("%03d", gameId));

            // Players
            StringBuilder playerLine = new StringBuilder("Players: ");
            for (int i = 0; i < state.getPlayers().size(); i++) {
                playerLine.append(state.getPlayers().get(i).getName());
                if (i < state.getPlayers().size() - 1) playerLine.append(", ");
            }
            XWPFParagraph playersPara = doc.createParagraph();
            XWPFRun runPlayers = playersPara.createRun();
            runPlayers.setText(playerLine.toString());

            // Gameplay Summary (header + line)
            XWPFParagraph summaryPara = doc.createParagraph();
            XWPFRun runSummary = summaryPara.createRun();
            runSummary.setText("Gameplay Summary:");
            
            XWPFParagraph summaryLinePara = doc.createParagraph();
            XWPFRun runSummaryLine = summaryLinePara.createRun();
            runSummaryLine.setText("----------------------------");
    
            // Iterate turns
            for (Turn t : state.getTurns().values()) {

                // Turn number + player + category + points
                XWPFParagraph turnPara = doc.createParagraph();
                XWPFRun runTurn = turnPara.createRun();
                runTurn.setText("Turn " + t.getTurnNumber() + ": " +
                                t.getTurnPlayer().getName() + " selected " +
                                t.getTurnCategory().getName() + " for " +
                                t.getTurnQuestion().getPoints() + " pts");

                // Question
                XWPFParagraph qPara = doc.createParagraph();
                XWPFRun runQ = qPara.createRun();
                runQ.setText("Question: " + t.getTurnQuestion().getText());

                // Result
                XWPFParagraph ansPara = doc.createParagraph();
                XWPFRun runAns = ansPara.createRun();
                String resultText;
                if (t.isCorrect()) {
                    resultText = "Correct (+" + t.getTurnQuestion().getPoints() + " pts)";
                } else {
                    resultText = "Incorrect (-" + t.getTurnQuestion().getPoints() + " pts)";
                }
                runAns.setText("Answer: " + t.getTurnQuestion().getOptionText(t.getAnswerGiven()) + " - " + resultText);

                // Score after turn
                XWPFParagraph scorePara = doc.createParagraph();
                XWPFRun runScore = scorePara.createRun();
                runScore.setText("Score after turn: " + t.getTurnPlayer().getName() +
                                 " = " + t.getTurnFinalScore());

                // Blank line to separate this turn from the next
                XWPFParagraph spacer = doc.createParagraph();
                spacer.createRun().setText(""); // empty paragraph
            }

            // Final Scores
            XWPFParagraph finalPara = doc.createParagraph();
            XWPFRun runFinal = finalPara.createRun();
            runFinal.setText("Final Scores:");

            for (Player p : state.getPlayers()) {
                XWPFParagraph pPara = doc.createParagraph();
                XWPFRun runP = pPara.createRun();
                runP.setText(p.getName() + ": " + p.getScore());
            }

            // Write to file
            doc.write(out);
            System.out.println("DOCX Report generated: " + fileName);

        } catch (Exception e) {
            System.out.println("Error generating DOCX report: " + e.getMessage());
        }
    }
}