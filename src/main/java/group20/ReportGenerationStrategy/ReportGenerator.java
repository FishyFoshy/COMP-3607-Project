package group20.ReportGenerationStrategy;

import group20.GameLogic.GameState;

/**
 * Strategy interface for generating game reports in different formats.
 */
public interface ReportGenerator {
    /**
     * Generates a report for the specified game.
     *
     * @param gameState - the completed game state
     * @param gameId - unique identifier for the game
     */
    public void generateReport(GameState gameState, int gameId);
}
