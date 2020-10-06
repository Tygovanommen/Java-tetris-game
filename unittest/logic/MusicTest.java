package logic;

import game.Music;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MusicTest {


    @Parameterized.Parameters(name = "{index}: pause {0} | result is active {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {true, false},
                {false, true},
        });
    }

    private final boolean pause;
    private Music music;
    private final boolean result;

    public MusicTest(boolean pause, boolean result) {
        this.pause = pause;
        this.result = result;
    }

    @Before
    public void setup() throws InterruptedException {
        this.music = new Music();
        // Make sure music is loaded before toggling
        TimeUnit.MILLISECONDS.sleep(500);
    }

    @Test
    public void testToggleMusic() {
        // Arrange

        // Act
        if (this.pause) {
            music.toggleMusic();
        }

        boolean active = music.getMusic().isActive();

        // Assert
        assertEquals(active, this.result);
    }

    @After
    public void close() {
        this.music.getMusic().close();
    }
}