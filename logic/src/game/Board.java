package game;

import shapes.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {
    private final int fieldWidth = 11;
    private final int fieldHeight = 20;
    private Color[][] field = new Color[fieldWidth][fieldHeight];
    private final Color emptyBlock = new Color(51, 51, 51); // Dark gray
    private final Color levelBlock = new Color(34, 34, 34); // White
    private boolean prevFit = true;
    private boolean status = true;
    private boolean pause = false;

    private final Drawer drawer;
    private final Game game;
    private Shape nextShape;
    private Shape currentShape;

    /**
     * Draws the default field containing level blockages and start the drawing process
     */
    public Board(Game game) {
        this.game = game;
        // Loop through and set default field
        for (int i = 0; i < this.fieldWidth; i++) {
            for (int j = 0; j < fieldHeight; j++) {
                // Either set level blockages or empty block
                if (j > fieldHeight - (((game.getLevel() != null) ? game.getLevel().getRows() : 0) + 2)) {
                    field[i][j] = this.levelBlock;
                } else {
                    field[i][j] = this.emptyBlock;
                }
            }
        }

        // Start drawing process/cycle
        this.drawer = new Drawer(this, game);

        // Add first shape
        this.newShape();
    }

    /**
     * Check if given shape fits on board
     *
     * @param shape     Shape containing shape to be checked
     * @param direction int next shape movement
     * @param next      int additional value to check before moving the shape
     * @return boolean that determines if shape fits on board or not
     */
    public final boolean fitBoard(Shape shape, String direction, int next) {
        // Set movement variables
        int x = (direction.equals("left-right")) ? next : shape.getShapePos()[0];
        int y = (direction.equals("down")) ? next : shape.getShapePos()[1];
        int rotation = (direction.equals("turn")) ? next : shape.getRotation();

        boolean fits = true;
        // Simulate placing block
        for (Point block : shape.getAxis(rotation).getPoints()) {
            if ((block.getX() + x >= 0) && (block.getX() + x < field.length)) {
                if (!this.emptyBlock.equals(field[(int) (block.getX() + x)][(int) (block.getY() + y)])) {
                    fits = false;
                }
            } else {
                fits = false;
            }
        }

        // Check game status before returning if shape fits
        if (direction.equals("down")) {
            // If game is running and shape does not fit
            if (!fits && !this.prevFit) {
                this.status = false;
            }
            this.prevFit = fits;
        }

        return fits;
    }

    /**
     * Remove rows if full and give points based on amount of full rows
     */
    public final void checkRows() {
        int rows = 0;
        // Loop through rows
        for (int i = fieldHeight - 2; i > 0; i--) {
            boolean gap = false;
            for (int j = 0; j < 11; j++) {
                // Check if row has empty space or blockage
                if (this.emptyBlock.equals(field[j][i]) || this.levelBlock.equals(field[j][i])) {
                    gap = true;
                    break;
                }
            }
            // Delete filled rows if there is no gab (full row)
            if (!gap) {
                for (int y = i - 1; y > 0; y--) {
                    for (int x = 0; x < fieldWidth; x++) {
                        field[x][y + 1] = field[x][y];
                    }
                }
                i += 1;
                rows += 1;
                // Increase speed if speed is not at its fastest already
                if (this.game.getLevel().getSpeed() > 100) {
                    this.game.getLevel().setSpeed(this.game.getLevel().getSpeed() - 25);
                }
            }
        }

        // Determine the amount of points that should be added
        int addScore;
        if (rows == 1) {
            addScore = 100;
        } else {
            addScore = (rows * (rows - 1)) * 100;
        }
        // Add the points
        this.game.getScore().setAmount(this.game.getScore().getAmount() + addScore);
    }

    /**
     * Set next shape and update next new shape value
     */
    public final void newShape() {
        // Set nextShape if this is first shape
        if (nextShape == null) {
            nextShape = randomShape();
        }
        // Set current shape with next shape value
        currentShape = nextShape;

        // Set next shape value
        nextShape = randomShape();
    }

    /**
     * Selects a random Shape from Shape array
     *
     * @return Shape that is randomly selected
     */
    private Shape randomShape() {
        Shape block = null;
        // Define what shapes can be selected
        List<Class<? extends Shape>> list = Arrays.asList(J.class, L.class, Line.class, S.class, Square.class, T.class, Z.class, Corner.class);
        try {
            // Randomly selected shape from array
            block = list.get(new Random().nextInt(list.size())).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return block;
    }

    public final boolean getStatus() {
        return this.status;
    }

    public final Color[][] getField() {
        return field;
    }

    public final void setField(Color[][] newField) {
        this.field = newField;
    }

    public final int getFieldWidth() {
        return fieldWidth;
    }

    public final int getFieldHeight() {
        return fieldHeight;
    }

    public final Color getEmptyBlock() {
        return this.emptyBlock;
    }

    public final Drawer getDrawer() {
        return this.drawer;
    }

    public final boolean getPause() {
        return !this.pause;
    }

    public final void setPause(boolean status) {
        this.pause = status;
    }

    public final Shape getCurrentShape() {
        return currentShape;
    }

    public final Shape getNextShape() {
        return nextShape;
    }
}