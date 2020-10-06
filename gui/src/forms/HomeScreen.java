package forms;

import game.Game;
import gui.GuiControl;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class HomeScreen extends Screen {
    // GUI variables
    private JLabel logo;
    private JButton startButton;
    private JButton muteButton;
    private JButton exitButton;
    private JPanel mainPane;
    private JLabel hsScore;
    private JLabel hsName;
    private JLabel hsLevel;
    private JComboBox<String> selectLevel;

    public HomeScreen(GuiControl gui, Game game) {
        super(gui, game);

        // Set main screen panel
        super.getFrame().add(mainPane);
        logo.setText("");

        // Fill levels dropdown
        String[] levelNames = game.getLevelNames();
        if (levelNames != null) {
            selectLevel.setModel(new DefaultComboBoxModel<>(levelNames));
            selectLevel.setSelectedIndex(0);
        } else {
            selectLevel.setEnabled(false);
        }

        // Loop through high scores
        List<Map<String, String>> data = game.getHighScores(5);
        if (!data.isEmpty()) {
            StringBuilder names;
            StringBuilder scores;
            StringBuilder level;
            names = new StringBuilder(scores = new StringBuilder(level = new StringBuilder()));
            for (Map<String, String> item : data) {
                names.append(item.get("username")).append("<br>");
                scores.append(item.get("score")).append("<br>");
                level.append(item.get("levelname")).append("<br>");
            }

            // Show high scores
            hsScore.setText("<html>" + scores + "</html>");
            hsName.setText("<html>" + names + "</html>");
            hsLevel.setText("<html>" + level + "</html>");
        }

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
        // Mute/toggle music
        muteButton.addActionListener(e -> super.getGame().getMusic().toggleMusic());

        // Exit game
        exitButton.addActionListener(e -> System.exit(0));

        // Start game
        startButton.addActionListener(e -> {
            // Set game level and open new screen
            if (selectLevel.getModel().getSelectedItem() != null) {
                super.getGame().setLevel(super.getGame().getLevelByName(selectLevel.getModel().getSelectedItem().toString()));
            }
            // Open the new screen
            super.getGui().newScreen("Game");
        });
    }

    /**
     * Change label to image
     */
    private void createUIComponents() {
        logo = new JLabel(new ImageIcon("res/logo.png"));
    }
}
