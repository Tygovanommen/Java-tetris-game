package gui;

import data.provite.DataProviderMock;
import entities.Level;
import game.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class GuiControlTest {

    @Parameterized.Parameters(name = "{index}: {0} = {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Home", false, true},
                {"End", false, true},
                {"Game", true, true},
                {"New", false, false},
                {"", false, false},
        });
    }

    private final String screen;
    private final boolean expected;
    private final boolean needLevel;
    private GuiControl gui;
    private Game game;

    public GuiControlTest(String screen, boolean needLevel, boolean expected) {
        this.screen = screen;
        this.expected = expected;
        this.needLevel = needLevel;
    }

    /**
     * Make sure there is a data provider before testing newScreen method
     */
    @Before
    public void setup() {
        DataProviderMock dataMock = new DataProviderMock();
        this.game = new Game(dataMock);
        this.gui = new GuiControl(this.game);
    }

    /**
     * Test if different screens can be opened
     */
    @Test
    public void testNewScreen() {
        // Arrange
        if (this.needLevel) {
            // Set level if screen need level variable
            Level gameLevel = this.game.getLevelByName("Super Hard");
            this.game.setLevel(gameLevel);
        }

        // Act
        this.gui.newScreen(this.screen);

        // Assert
        assertEquals(this.gui.getScreen().getFrame().isVisible(), this.expected);
    }
}