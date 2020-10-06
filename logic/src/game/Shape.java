package game;

import java.awt.*;
import java.util.Random;

public abstract class Shape {
    private final int[] position = new int[]{4, 0};
    private final Color color;
    private int rotation = 0;

    /**
     * Create new shape
     */
    public Shape() {
        // Randomly select a color from Colors enum
        int RandomInt = new Random().nextInt(Colors.values().length);
        color = Colors.values()[RandomInt].getColor();
    }

    /**
     * Change the rotation of current shape
     */
    public final void turn(Board board) {
        // Grab next rotation
        int nextRotation = (((this.rotation - 1) < 0) ? 3 : this.rotation - 1);

        // Check if block can be turned
        if (board.fitBoard(this, "turn", nextRotation)) {
            this.rotation = nextRotation;
        }
    }

    /**
     * Move shape based on given direction
     *
     * @param board containing board where the shape should be moved in
     * @param direction String that contains direction of next shape movement
     */
    public final void move(Board board, String direction) {
        // Determine what direction shape should move
        if (direction.equals("down")) {
            // Check if the shape can move down
            if (board.fitBoard(this, "down", this.position[1] + 1)) {
                this.position[1] += 1;
            } else {
                // Place Shape inside board field
                for (Point block : getAxis(this.rotation).getPoints()) {
                    Color[][] newField = board.getField();
                    newField[(int) (this.position[0] + block.getX())][(int) (this.position[1] + block.getY())] = this.color;
                    board.setField(newField);
                }

                // Check on full rows
                board.checkRows();

                // Place new shape
                board.newShape();
            }
        } else {
            int pos = 0;
            // Determine what direction shape should move
            if (direction.equals("left")) {
                pos = -1;
            } else if (direction.equals("right")) {
                pos = +1;
            }
            // Check if shape can move (left or right)
            if (board.fitBoard(this, "left-right", this.position[0] + pos)) {
                this.position[0] += pos;
            }
        }
    }

    /**
     * What does axis variable contain?
     * ---------------------
     * 1: Each array has a row containing the axis which determines the direction of the shape's rotation
     * 2: Each row can be treated as a separate shape.
     * 3: Each column in a row makes a block shape. Each column equals: {x, y}
     * <p>
     * How are shapes formed?
     * ---------------------
     * Blocks from shapes are formed by the coordinates on a coordinate graph (https://en.wikipedia.org/wiki/Cartesian_coordinate_system)
     * Coordinates (points) of a block are determined by the x and y axis given in row columns
     * Merging different blocks (their coordinates) creates a shape.
     * <p>
     * Example shape:
     * 1: {{0, 0}, {1, 0}, {1, 1}, {2, 1}},
     * 2: {{1, 0}, {0, 1}, {1, 1}, {0, 2}},
     * 3: {{0, 0}, {1, 0}, {1, 1}, {2, 1}},
     * 4: {{1, 0}, {0, 1}, {1, 1}, {0, 2}}
     *
     * @param rotation int that contains the direction of the rotation
     * @return int[][] axis of selected rotation direction
     */
    public abstract Axis getAxis(int rotation);

    public final Axis getShape() {
        return getAxis(this.rotation);
    }

    public final int[] getShapePos() {
        return this.position;
    }

    public final Color getColor() {
        return this.color;
    }

    public final int getRotation() {
        return this.rotation;
    }
}