package game;

import data.provite.DataProviderMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Parameterized.class)
public class BoardCheckRowsTest {

    @Parameterized.Parameters(name = "{index}: Rows: {0} - Score: {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, 0, true},
                {1, 100, true},
                {2, 200, true},
                {3, 600, true},
                {4, 1200, true},
                {1, 0, false},
                {2, 0, false},
        });
    }

    public int rowCount;
    public int expectedSCore;
    public boolean completeRow;

    public Board board;
    public Game game;

    public BoardCheckRowsTest(int rowCount, int expectedScore, boolean completeRow) {
        this.rowCount = rowCount;
        this.expectedSCore = expectedScore;
        this.completeRow = completeRow;
    }

    /**
     * Create a row with blocks
     */
    @Before
    public void fieldSetup() {
        this.game = new Game(new DataProviderMock());
        game.setLevel(this.game.getLevelByName("Easy"));
        this.board = new Board(game);

        // Set filled lines
        Color[][] newField = board.getField();
        for (int i = 0; i < this.board.getFieldWidth(); i++) {
            for (int j = 0; j < this.board.getFieldHeight() - 1; j++) {

                // Make incomplete row when completeRow = false
                if (!this.completeRow && i == 1) {
                    continue;
                }

                if (j > this.board.getFieldHeight() - (this.rowCount + 2)) {
                    newField[i][j] = Colors.GREEN.getColor();
                }
            }
        }
        this.board.setField(newField);
    }

    /**
     * Check if full rows get removed
     */
    @Test
    public void testRowRemoved() {
        // Arrange
        boolean hasFullRows = false;

        // Act
        this.board.checkRows();

        for (int i = 0; i < this.board.getFieldWidth(); i++) {
            for (int j = 0; j < this.board.getFieldHeight(); j++) {
                if (this.board.getField()[i][j].equals(Colors.GREEN.getColor())) {
                    hasFullRows = true;
                    break;
                }
            }
        }

        // Assert
        assertEquals(!this.completeRow, hasFullRows);
    }

    /**
     * Check if scores are added when checking for full rows
     */
    @Test
    public void testScoreAdded() {
        // Arrange

        // Act
        this.board.checkRows();

        // Assert
        assertEquals(this.expectedSCore, this.game.getScore().getAmount());
    }
}
