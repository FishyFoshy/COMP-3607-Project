package group20;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import group20.FileParsing.AbstractQuestionParser;
import group20.FileParsing.CSVParser;
import group20.FileParsing.JSONParser;
import group20.FileParsing.XMLParser;
import group20.GameActionCommands.*;
import group20.GameLogic.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class GameplayTests {
    GameState gameState = new GameState();
    CommandInvoker invoker = new CommandInvoker();

    @Test
    public void testSelectingPlayerCount(){
        gameState.setPlayerCount(3);
        assertEquals(3, gameState.getPlayerCount());
    }
}
