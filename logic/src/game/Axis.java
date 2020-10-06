package game;

import java.awt.*;

public class Axis {
    private final Point[] points;

    public Axis(Point[] points) {
        this.points = points;
    }

    public Point[] getPoints() {
        return this.points;
    }
}
