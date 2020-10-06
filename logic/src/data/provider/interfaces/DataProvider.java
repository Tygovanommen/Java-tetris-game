package data.provider.interfaces;

import entities.Level;
import entities.Score;
import entities.User;

import java.util.List;
import java.util.Map;

/**
 * Interface that makes it flexible to switch between data types
 * Also makes it possible to get data from data layer to gui layer
 */
public interface DataProvider {
    // Level
    Level[] getAllLevels();

    Level getLevel(String name);

    // Scores
    List<Map<String, String>> getHighScores(int amount);

    void addHighScore(User user, Score score, Level level);

    // User
    User getUser(String name);

    User addUser(String name);
}