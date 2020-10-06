package game;

import data.provider.interfaces.DataProvider;
import entities.Level;
import entities.Score;
import entities.User;

import java.util.List;
import java.util.Map;

public class Game {

    private final Music music = new Music();
    private final Score score = new Score();
    private Level level;
    private User user;
    private final DataProvider provider;
    private Board board;

    public Game(DataProvider provider) {
        this.provider = provider;
    }

    /* data providers */
    public final String[] getLevelNames() {
        Level[] levels = this.provider.getAllLevels();
        if (levels != null) {
            // Get all names to show in high score list
            int i = 0;
            String[] levelNames = new String[levels.length];
            for (Level level : levels) {
                levelNames[i] = level.getName();
                i++;
            }

            return levelNames;
        } else {
            return null;
        }
    }

    public final List<Map<String, String>> getHighScores(int amount) {
        return this.provider.getHighScores(amount);
    }

    public final Level getLevelByName(String name) {
        return this.provider.getLevel(name);
    }

    public final User getUserByName(String name) {
        return this.provider.getUser(name);
    }

    public final void addHighScore(User user, Score score, Level level) {
        this.provider.addHighScore(user, score, level);
    }

    /* normal getters and setters */
    public final Board getBoard() {
        return this.board;
    }

    public final Music getMusic() {
        return this.music;
    }

    public final User getUser() {
        return this.user;
    }

    public final Level getLevel() {
        return this.level;
    }

    public final Score getScore() {
        return this.score;
    }

    public final void setLevel(Level newLevel) {
        this.level = newLevel;
        this.board = new Board(this);
    }

    public final void setUser(User newUser) {
        this.user = newUser;
    }
}