package data.provite;

import data.provider.interfaces.DataProvider;
import entities.Level;
import entities.Score;
import entities.User;

import java.util.*;

public class DataProviderMock implements DataProvider {

    private final String[][] levels = {
            {"1", "Easy", "0", "1000"},
            {"2", "Medium", "1", "900"},
            {"3", "Hard", "2", "800"},
            {"4", "Super Hard", "3", "700"},
            {"5", "Easy2", "2", "1000"},
            {"6", "Easy3", "1", "1000"},
    };

    private final String[][] scores = {
            {"Tygo", "14000", "6"},
            {"Henk", "8000", "3"},
            {"Piet", "3000", "5"},
            {"Willem", "100", "4"},
            {"Sjef", "1000", "1"},
            {"Peter", "550", "2"},
            {"Jan", "50", "5"},
    };

    private String[][] users = {
            {"1", "Tygo"},
            {"2", "Henk"},
            {"3", "Piet"},
            {"4", "Willem"},
            {"5", "Peter"},
    };

    /**
     * @param item level item given
     * @return Level containing level variables
     */
    private Level setLevel(String[] item) {
        Map<String, String> map = new HashMap<>();
        if (item != null) {
            map.put("id", item[0]);
            map.put("name", item[1]);
            map.put("rows", item[2]);
            map.put("speed", item[3]);
        }
        return new Level(Integer.parseInt(map.get("id")), map.get("name"), Integer.parseInt(map.get("rows")), Integer.parseInt(map.get("speed")));
    }


    /**
     * @param item user info given
     * @return User containging user variables
     */
    private User setUser(String[] item) {
        Map<String, String> map = new HashMap<>();
        if (item != null) {
            map.put("id", item[0]);
            map.put("name", item[1]);
        }
        return new User(Integer.parseInt(map.get("id")), map.get("name"));
    }

    @Override
    public Level[] getAllLevels() {
        Level[] levels = null;

        if (this.levels != null) {
            int i = 0;
            levels = new Level[this.levels.length];
            for (String[] item : this.levels) {
                levels[i] = setLevel(item);
                i++;
            }
        }

        return levels;
    }

    @Override
    public Level getLevel(String name) {
        String[] item = null;
        for (String[] level : levels) {
            if (level[1].equals(name)) {
                item = level;
                break;
            }
        }
        return setLevel(item);
    }

    @Override
    public List<Map<String, String>> getHighScores(int amount) {
        List<Map<String, String>> data = new ArrayList<>();
        // Sort scores with highest score first
        String[][] sortedScores = Arrays.stream(this.scores).sorted(Comparator.comparing(x -> -Integer.parseInt(x[1]))).toArray(String[][]::new);
        int i = 0;
        for (String[] item : sortedScores) {
            Map<String, String> row = new HashMap<>();
            if (item != null) {
                row.put("username", item[0]);
                row.put("score", item[1]);
                row.put("levelname", item[2]);
            }
            // Add a limit to the amount added to result
            if (i >= amount) {
                break;
            }
            i++;
            data.add(row);
        }

        return data;
    }

    @Override
    public void addHighScore(User user, Score score, Level level) {
        // Nothing happens
    }

    @Override
    public User getUser(String name) {
        String[] item = null;
        for (String[] user : users) {
            if (user[1].equals(name)) {
                item = user;
                break;
            }
        }
        return setUser(item);
    }

    @Override
    public User addUser(String name) {
        int userId = users.length;

        String[] newUser = {String.valueOf(userId), name};

        users = Arrays.copyOf(users, users.length + 1);
        users[users.length - 1] = newUser;

        return getUser(name);
    }
}
