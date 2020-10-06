package game;

import java.awt.*;

public enum Colors {
    YELLOW(250, 250, 1),
    RED(2, 228, 254),
    GREEN(248, 0, 2),
    ORANGE(105, 181, 37),
    PINK(255, 80, 187),
    PURPLE(105, 89, 166);

    private final int r;
    private final int g;
    private final int b;

    /**
     * Set the rgb values of the selected color
     *
     * @param r int red
     * @param g int green
     * @param b int blue
     */
    Colors(final int r, final int g, final int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public final Color getColor() {
        return new Color(r, g, b);
    }
}