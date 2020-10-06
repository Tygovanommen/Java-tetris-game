package logic;

import data.provite.DataProviderMock;
import entities.Level;
import entities.Score;
import entities.User;
import game.Game;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameTest {

    private Game game;

    /**
     * Make sure all tests have a data provider
     */
    @Before
    public void setup() {
        DataProviderMock provider = new DataProviderMock();
        this.game = new Game(provider);
    }

    @Test
    public void getLevelNames() {
        // Arrange

        // Act
        String[] strings = this.game.getLevelNames();

        // Assert
        assertNotNull(strings);
    }

    @Test
    public void getHighScores() {
        // Arrange

        // Act
        List<Map<String, String>> highScores = this.game.getHighScores(5);

        // Assert
        assertEquals(highScores.size(), 5);
    }

    @Test
    public void getLevelByName() {
        // Arrange

        // Act
        Level level = this.game.getLevelByName("Hard");

        // Assert
        assertEquals(level.getName(), "Hard");
    }

    @Test
    public void getUserByName() {
        // Arrange
        String expectedName = "Tygo";

        // Act
        User user = this.game.getUserByName("Tygo");

        // Assert
        assertEquals(user.getName(), expectedName);
    }

    @Test
    public void getUser() {
        // Arrange
        User user = new User(1, "Tygo");

        // Act
        this.game.setUser(user);

        // Assert
        assertNotNull(this.game.getUser());
    }

    @Test
    public void getLevel() {
        // Arrange
        Level level = new Level(1, "Tygo", 2, 800);

        // Act
        this.game.setLevel(level);

        // Assert
        assertNotNull(this.game.getLevel());
    }

    @Test
    public void getScore() {
        // Arrange
        Score score = new Score();
        int expectedScore = 1000;

        // Act
        score.setAmount(1000);

        // Assert
        assertEquals(score.getAmount(), expectedScore);
    }
}