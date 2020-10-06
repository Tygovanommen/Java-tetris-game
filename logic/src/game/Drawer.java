package game;

import javax.swing.*;
import java.awt.*;

public class Drawer extends JPanel {

    private final Board board;
    private final Game game;
    private Timer checker;

    public Drawer(Board board, Game game) {
        this.board = board;
        this.game = game;
    }

    /**
     * Start draw cycle
     * Check if game is paused or ended
     */
    public final void startCycle() {
        // Define the cycle
        this.checker = new Timer((this.game.getLevel() != null) ? this.game.getLevel().getSpeed() : 0, e -> {
            if (this.board.getPause()) {
                // Pain new movement
                this.board.getCurrentShape().move(this.board, "down");
                this.repaint();

                // Check if speed can be increase
                this.checker.setInitialDelay(this.game.getLevel().getSpeed());
                this.checker.setDelay(this.game.getLevel().getSpeed());
                this.checker.restart();
            } else {
                //pause message
                JOptionPane.showMessageDialog(this, "Game paused", "Success message", JOptionPane.INFORMATION_MESSAGE);
                this.board.setPause(false);
            }
        });
        // Start the cycle
        this.checker.start();
    }

    /**
     * Stop the drawing cycle
     */
    public final void stopCycle() {
        this.checker.stop();
    }

    /**
     * Draw everything in GUI. (Shapes, field, scores etc)
     *
     * @param g Graphics of shapes to be drawn
     */
    @Override
    public final void paintComponent(Graphics g) {
        // Determine margin sizes and shape block size
        int boardMarginX = 12;
        int boardMarginY = 11;
        int shapeSize = 25;

        // Draw field by looping through field array
        g.fillRect(boardMarginX, boardMarginY, (shapeSize + 1) * this.board.getFieldWidth(), (shapeSize + 1) * (this.board.getFieldHeight() - 1));
        for (int i = 0; i < this.board.getFieldWidth(); i++) {
            for (int j = 0; j < this.board.getFieldHeight() - 1; j++) {
                g.setColor(this.board.getField()[i][j]);
                int x = (shapeSize + 1) * i + boardMarginX;
                int y = (shapeSize + 1) * j + boardMarginY;
                g.fillRect(x, y, shapeSize, shapeSize);
            }
        }

        // Set color and background of next shape container
        g.setColor(this.board.getEmptyBlock());
        g.fillRect(shapeSize * 12 + 7, 11, 207, 141);

        // Draw next shape inside next shape container by looping through next shape
        g.setColor(this.board.getNextShape().getColor());
        int[] shapePos = this.board.getNextShape().getShapePos();
        for (Point p : this.board.getNextShape().getShape().getPoints()) {
            int x = (int) ((p.getX() + shapePos[0]) * (shapeSize + 1) + 275);
            int y = (int) ((p.getY() + shapePos[1]) * (shapeSize + 1) + 50);
            g.fillRect(x, y, shapeSize, shapeSize);
        }

        // Draw currently falling block by looping through axis array
        g.setColor(this.board.getCurrentShape().getColor());
        shapePos = this.board.getCurrentShape().getShapePos();
        for (Point p : this.board.getCurrentShape().getShape().getPoints()) {
            int x = (int) ((p.getX() + shapePos[0]) * (shapeSize + 1) + boardMarginX);
            int y = (int) ((p.getY() + shapePos[1]) * (shapeSize + 1) + boardMarginY);
            g.fillRect(x, y, shapeSize, shapeSize);
        }

        g.setColor(new Color(238, 238, 238));
        g.fillRect(400, 220, 150, 50);

        // Set score font
        g.setColor(new Color(51, 51, 51));
        g.setFont(new Font("Default", Font.BOLD, 12));

        // Get reached score and speed
        String score = Integer.toString(this.game.getScore().getAmount());
        String speed = Integer.toString(this.game.getLevel().getSpeed());

        // Update score and speed label
        g.drawString(score, (int) ((this.getWidth() - 18) - g.getFontMetrics().getStringBounds(score, g).getWidth()), 241);
        g.drawString(speed, (int) ((this.getWidth() - 18) - g.getFontMetrics().getStringBounds(speed, g).getWidth()), 260);
    }

    public final boolean getCheckerStatus() {
        return this.checker.isRunning();
    }
}