package shapes;

import game.Axis;
import game.Shape;

import java.awt.*;

public final class T extends Shape {
    // Set axis of current shape
    private final Axis[] axis = {
            new Axis(new Point[]{new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1)}),
            new Axis(new Point[]{new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2)}),
            new Axis(new Point[]{new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2)}),
            new Axis(new Point[]{new Point(1, 0), new Point(1, 1), new Point(2, 1), new Point(1, 2)}),
    };

    @Override
    public final Axis getAxis(int rotation) {
        return axis[rotation];
    }
}