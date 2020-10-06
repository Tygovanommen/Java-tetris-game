package gui;

import forms.EndScreen;
import forms.GameScreen;
import forms.HomeScreen;
import forms.Screen;
import game.Game;

public class GuiControl {

    private Screen screen;
    private final Game game;

    public GuiControl(Game game) {
        this.game = game;
        this.newScreen("Home");
    }

    /**
     * Close old and open new selected screen
     *
     * @param screenName String that contains new selected screen
     */
    public final void newScreen(String screenName) {
        // Close old screen
        if (this.screen != null) {
            this.screen.getFrame().dispose();
        }

        // Determine screen that should be opened
        switch (screenName) {
            case "Home":
                this.screen = new HomeScreen(this, this.game);
                break;
            case "Game":
                this.screen = new GameScreen(this, this.game);
                break;
            case "End":
                this.screen = new EndScreen(this, this.game);
                break;
        }
    }

    public final Screen getScreen() {
        return this.screen;
    }
}
