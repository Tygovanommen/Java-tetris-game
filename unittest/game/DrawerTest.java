package game;

import data.provite.DataProviderMock;
import entities.Level;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DrawerTest {

    private Drawer drawer;

    @Before
    public void setup() {
        DataProviderMock provider = new DataProviderMock();
        Game game = new Game(provider);

        Level gameLevel = game.getLevelByName("Easy2");
        game.setLevel(gameLevel);

        this.drawer = new Drawer(game.getBoard(), game);
    }

    /**
     * Check if drawer cycle can start
     */
    @Test
    public void testStartCycle() throws InterruptedException {
        // Arrange
        boolean expectedRunning = true;

        // Act
        this.drawer.startCycle();

        // Wait half a second to see if anything happens
        TimeUnit.MILLISECONDS.sleep(500);

        // Assert
        assertEquals(this.drawer.getCheckerStatus(), expectedRunning);
    }

    /**
     * Check if drawer cycle can be stopped after starting
     */
    @Test
    public void testStopCycle() throws InterruptedException {
        // Arrange
        boolean expectedRunning = false;

        // Act
        this.drawer.startCycle();

        // Wait half a second to make sure drawer started
        TimeUnit.MILLISECONDS.sleep(500);

        this.drawer.stopCycle();

        // Assert
        assertEquals(this.drawer.getCheckerStatus(), expectedRunning);
    }
}
