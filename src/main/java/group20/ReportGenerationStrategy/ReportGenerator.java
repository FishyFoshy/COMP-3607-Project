package group20.ReportGenerationStrategy;

import group20.GameLogic.GameState;

public interface ReportGenerator {
    public void generateReport(GameState gameState, int gameId);
}
