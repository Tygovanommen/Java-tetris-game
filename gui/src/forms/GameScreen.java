package forms;

import game.Board;
import game.Game;
import gui.GuiControl;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameScreen extends Screen {
    private JButton exitButton;
    private JButton pauseButton;
    private JButton muteButton;
    private JSplitPane mainPane;

    private final Board board;
    private Timer statusCheck;

    public GameScreen(GuiControl gui, Game game) {
        super(gui, game);
        this.board = game.getBoard();

        // Set main screen panel
        super.getFrame().add(mainPane);

        // Set location and resizability of window
        super.getFrame().pack();
        super.getFrame().setLocationRelativeTo(null);
        super.getFrame().setResizable(false);

        // Start game cycle
        this.board.getDrawer().startCycle();

        // Check game status
        this.statusCheck = new Timer(1, e -> {
            if (!this.board.getStatus()) {
                // End game
                this.statusCheck.stop();
                this.board.getDrawer().stopCycle();
                this.getGui().newScreen("End");
            }
        });
        this.statusCheck.start();

        super.getFrame().add(this.board.getDrawer());

        // Open screen
        super.getFrame().setVisible(true);

        // Add key listeners
        addKeyListeners();
    }

    /**
     * Add end screen key listeners
     */
    @Override
    public final void addKeyListeners() {
        // Mute/toggle music
        muteButton.addActionListener(e -> super.getGame().getMusic().toggleMusic());

        // Exit game
        exitButton.addActionListener(e -> System.exit(0));

        // Pause game
        pauseButton.addActionListener(e -> this.board.setPause(this.board.getPause()));

        super.getFrame().addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {

            }

            // Listen when key is pressed
            public void keyPressed(KeyEvent e) {
                if (board.getPause()) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                        case KeyEvent.VK_W:
                            // Rotate shape
                            board.getCurrentShape().turn(board);
                            break;
                        case KeyEvent.VK_RIGHT:
                        case KeyEvent.VK_D:
                            // Move shape right
                            board.getCurrentShape().move(board, "right");
                            break;
                        case KeyEvent.VK_LEFT:
                        case KeyEvent.VK_A:
                            // Move shape left
                            board.getCurrentShape().move(board, "left");
                            break;
                        case KeyEvent.VK_DOWN:
                        case KeyEvent.VK_S:
                            // Move shape down
                            board.getCurrentShape().move(board, "down");
                            break;
                    }
                    board.getDrawer().repaint();
                }
            }

            public void keyReleased(KeyEvent e) {

            }
        });
    }
}

