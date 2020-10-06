package entities;

import java.util.Map;

public class User {
    private final int id;
    private final String name;


    /**
     * Set user variables
     * @param id   current user id
     * @param name current user name
     */
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public final int getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }
}