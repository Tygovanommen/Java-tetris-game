package forms;

import game.Game;
import gui.GuiControl;

import javax.swing.*;

public class EndScreen extends Screen {
    // GUI variables
    private JPanel mainPane;
    private JButton exitButton;
    private JButton restartButton;
    private JLabel endScore;
    private JButton submitButton;
    private JTextField inputName;

    public EndScreen(GuiControl gui, Game game) {
        super(gui, game);

        // Set achieved score
        endScore.setText(String.valueOf(super.getGame().getScore().getAmount()));

        // Set main screen panel
        super.getFrame().add(mainPane);

        // Set location and resizability of window
        super.getFrame().pack();
        super.getFrame().setLocationRelativeTo(null);
        super.getFrame().setResizable(false);

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
        submitButton.addActionListener(e -> {
            // Check if name is set
            if (!inputName.getText().isEmpty()) {
                if (super.getGame().getScore().getAmount() > 0) {
                    // set user
                    super.getGame().setUser(super.getGame().getUserByName(inputName.getText()));

                    // Submit score
                    super.getGame().addHighScore(super.getGame().getUser(), super.getGame().getScore(), super.getGame().getLevel());

                    // Disable submit button to prevent multiple submits
                    submitButton.setEnabled(false);

                    super.showMessage("Successfully submitted score", "success");
                } else {
                    super.showMessage("Your score should be more than 0 to be submitted", "error");
                }
            } else {
                super.showMessage("Name cannot be empty", "error");
            }
        });
        restartButton.addActionListener(e -> {
            // Reset score
            super.getGame().getScore().setAmount(0);
            // Reset level
            super.getGame().setLevel(super.getGame().getLevelByName(super.getGame().getLevel().getName()));
            // Open Game screen again
            super.getGui().newScreen("Game");
        });
        // Exit game
        exitButton.addActionListener(e -> System.exit(0));
    }
}
