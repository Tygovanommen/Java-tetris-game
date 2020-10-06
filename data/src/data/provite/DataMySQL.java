package data.provite;

import data.provider.interfaces.DataProvider;
import entities.Level;
import entities.Score;
import entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataMySQL implements DataProvider {
    private final Connection conn;

    /**
     * Establish connection to mysql database.
     *
     * @param config database configuration values
     */
    public DataMySQL(Config config) {
        Connection connection = null;
        try {
            // Set database Driver
            Class.forName("com.mysql.jdbc.Driver");

            // Create connection URL
            String url = "jdbc:mysql://" + config.getValue("dbserver") + "/" + config.getValue("dbname") + "?serverTimezone=UTC";

            // Establish the connection based on given variables
            connection = DriverManager.getConnection(url, config.getValue("dbusername"), config.getValue("dbpassword"));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        this.conn = connection;
    }

    /**
     * Executing regular insert query
     *
     * @param query String containing insert query
     * @return ResultSet result of query
     */
    private int saveData(String query) {
        int insertId = 0;
        if (this.conn != null) {
            try {
                // Execute query
                String[] returnId = {"BATCHID"};
                PreparedStatement statement = this.conn.prepareStatement(query, returnId);
                statement.executeUpdate();
                ResultSet rs = statement.getGeneratedKeys();

                // Set return ID
                if (rs.next()) {
                    insertId = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return insertId;
    }


    /**
     * Executing regular select query
     *
     * @param query String containing select query
     * @return List result of query
     */
    private List<Map<String, String>> getData(String query) {
        List<Map<String, String>> data = new ArrayList<>();
        if (this.conn != null) {
            try {
                // Execute query
                Statement statement = this.conn.createStatement();
                ResultSet rs = statement.executeQuery(query);

                // Check if there are any results
                if (rs != null && rs.isBeforeFirst()) {
                    // Get query meta data and create query column
                    ResultSetMetaData rsmd = rs.getMetaData();
                    List<String> columns = new ArrayList<>(rsmd.getColumnCount());
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        columns.add(rsmd.getColumnLabel(i));
                    }

                    // Fill list with query result
                    while (rs.next()) {
                        Map<String, String> row = new HashMap<>(columns.size());
                        for (String col : columns) {
                            row.put(col, rs.getString(col));
                        }
                        data.add(row);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * Gets all levels (name, rows, speed) from 'levels' table.
     *
     * @return Level[] which contains a list of level parameters
     */
    @Override
    public final Level[] getAllLevels() {
        Level[] levels = null;
        // Create query
        String query = "SELECT * FROM levels";

        // Select all levels
        List<Map<String, String>> data = this.getData(query);

        // Loop through all levels and add to array
        if (!data.isEmpty()) {
            int i = 0;
            levels = new Level[data.size()];
            for (Map<String, String> item : data) {
                levels[i] = new Level(Integer.parseInt(item.get("id")), item.get("name"), Integer.parseInt(item.get("rows")), Integer.parseInt(item.get("speed")));
                i++;
            }
        }

        return levels;
    }

    /**
     * Get Level variables based on given level name
     *
     * @param name String that contains level name
     * @return Level which contains level variables
     */
    @Override
    public final Level getLevel(String name) {
        Level level;

        // Create query
        String query = "SELECT * FROM levels WHERE name = '" + name + "'";

        // Select level where level name is given value
        Map<String, String> data = this.getData(query).get(0);
        if (!data.isEmpty()) {
            level = new Level(Integer.parseInt(data.get("id")), data.get("name"), Integer.parseInt(data.get("rows")), Integer.parseInt(data.get("speed")));
        } else {
            level = new Level(0, "Undefined", 0, 1000);
        }
        return level;
    }

    /**
     * Gets a list of high scores based on given amount
     *
     * @param amount int that contains the amount of high scores that should be grabbed
     * @return List<Map<String, String>> that contains a list of high scores
     */
    @Override
    public final List<Map<String, String>> getHighScores(int amount) {
        // Create query
        String query = "SELECT * FROM vHighscores ORDER BY score DESC LIMIT " + amount + "";

        // Select all high scores join on user_id and level_id
        return this.getData(query);
    }

    /**
     * @param user  String that contains name of the user that played
     * @param score int that contains achieved score
     * @param level int that contains played level
     */
    @Override
    public final void addHighScore(User user, Score score, Level level) {
        // Add new high score record to database
        this.saveData("INSERT INTO scores (user_id, score, level_id) VALUES (" + user.getId() + ", " + score.getAmount() + ", " + level.getId() + ") ");
    }

    /**
     * Get Level variables based on given username
     *
     * @param name String that contains name of current user
     * @return User which contains user variables
     */
    @Override
    public final User getUser(String name) {
        User user;

        // Create query
        String query = "SELECT * FROM users WHERE name = '" + name + "' LIMIT 1";

        List<Map<String, String>> data = this.getData(query);

        // Determines if a user should be created or selected
        // And sets User variables
        if (data.isEmpty()) {
            user = this.addUser(name);
        } else {
            user = new User(Integer.parseInt(data.get(0).get("id")), data.get(0).get("name"));
        }
        return user;
    }

    /**
     * Creates new user based on variable name
     *
     * @param name containing user class
     * @return User that contains user variables
     */
    @Override
    public final User addUser(String name) {
        User user;

        // Create new user record in database
        String query = "INSERT INTO users (name) VALUES ('" + name + "') ";
        int userId = this.saveData(query);

        // Get created result so it can be returned
        query = "SELECT * FROM users WHERE id = '" + userId + "' LIMIT 1";
        Map<String, String> data = this.getData(query).get(0);

        if (!data.isEmpty()) {
            user = new User(Integer.parseInt(data.get("id")), data.get("name"));
        } else {
            user = new User(0, "undefined");
        }
        return user;
    }
}
