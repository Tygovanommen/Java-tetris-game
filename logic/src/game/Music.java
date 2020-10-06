package game;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    private Clip music;

    /**
     * Initialize, set and play music
     */
    public Music() {
        try {
            // Get music file
            File file = new File("res/tetris-8bit.wav");
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            music = AudioSystem.getClip();
            music.open(stream);

            // Change volume of music (So it is not very loud)
            FloatControl gainControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-30.0f);

            // Start playing the music
            music.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mute or unmute (toggle) music
     */
    public final void toggleMusic() {
        // Toggle music based on music status
        if (music.isActive()) {
            music.stop();
        } else {
            music.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public final Clip getMusic() {
        return this.music;
    }
}