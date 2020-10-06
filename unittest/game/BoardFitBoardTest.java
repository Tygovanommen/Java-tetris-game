package game;

import data.provider.interfaces.DataProvider;
import data.provite.DataProviderMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import shapes.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Parameterized.class)
public class BoardFitBoardTest {

    @Parameterized.Parameters(name = "{index}: {1} {2} = {3}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new Corner(), "left-right", 1, true},
                {new J(), "left-right", -1, false},
                {new L(), "down", 1, true},
                {new Line(), "down", 20, false},
                {new S(), "turn", 3, true},
                {new Square(), "turn", 7, false},
                {new T(), "left-right", 50, false},
                {new Z(), "down", -1, false},
                {new Z(), "Vliegen", -1, true},
        });
    }

    private final Shape shape;
    private final String direction;
    private final int nextMovement;
    private final boolean expected;

    public BoardFitBoardTest(Shape shape, String direction, int next, boolean expected) {
        this.shape = shape;
        this.direction = direction;
        this.nextMovement = next;
        this.expected = expected;
    }

    /**
     * Test that checks if pieces can fit on the screen in different ways.
     */
    @Test
    public void testFitBoard() {
        // Arrange
        DataProvider provider = new DataProviderMock();
        Game game = new Game(provider);
        Board board = new Board(game);

        // Act
        boolean fitBoard;
        try {
            fitBoard = board.fitBoard(this.shape, this.direction, this.nextMovement);
        } catch (ArrayIndexOutOfBoundsException exception) {
            fitBoard = false;
        }

        // Assert
        assertEquals(fitBoard, this.expected);
    }
}