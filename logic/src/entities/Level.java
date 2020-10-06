package entities;

public class Level {
    private final int id;
    private final String name;
    private final int rows;
    private int speed;

    /**
     * Set level variables
     * @param id    level id
     * @param name  level name
     * @param rows  level rows
     * @param speed level speed
     */
    public Level(int id, String name, int rows, int speed) {
        this.id = id;
        this.name = name;
        this.rows = rows;
        this.speed = speed;
    }

    public final int getId() {
        return this.id;
    }

    public final int getRows() {
        return this.rows;
    }

    public final String getName() {
        return this.name;
    }

    public final int getSpeed() {
        return this.speed;
    }

    public final void setSpeed(int speed) {
        this.speed = speed;
    }
}
