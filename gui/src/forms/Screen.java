package forms;

import game.Game;
import gui.GuiControl;

import javax.swing.*;

public abstract class Screen {
    private final JFrame frame = new JFrame("Tetris - 2k20");
    private final GuiControl gui;
    private final Game game;

    /**
     * @param gui the current gui controller
     * @param game the current game
     */
    public Screen(GuiControl gui, Game game) {
        this.gui = gui;
        this.game = game;

        // Set standard frame values
        this.frame.setIconImage(new ImageIcon("res/logo-icon.png").getImage());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public abstract void addKeyListeners();

    /**
     * Opens a message popup
     *
     * @param message the message to be shown in popup
     * @param status the type of message it should be
     */
    public final void showMessage(String message, String status) {
        String title;
        int messageType;

        // Check which status is selected
        switch (status) {
            case "warning":
                title = "Warning message";
                messageType = JOptionPane.WARNING_MESSAGE;
            case "success":
                title = "Success message";
                messageType = JOptionPane.INFORMATION_MESSAGE;
            case "error":
                title = "Error message";
                messageType = JOptionPane.ERROR_MESSAGE;
            case "normal":
            default:
                title = "Message";
                messageType = JOptionPane.PLAIN_MESSAGE;
                break;
        }
        // Open the actual popup
        JOptionPane.showMessageDialog(this.frame, message, title, messageType);
    }

    public final JFrame getFrame() {
        return this.frame;
    }

    public final GuiControl getGui() {
        return this.gui;
    }

    public final Game getGame() {
        return this.game;
    }
}