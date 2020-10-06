package main;

import data.provider.interfaces.DataProvider;
import data.provite.Config;
import data.provite.DataMySQL;
import game.Game;
import gui.GuiControl;

public class Start {
    private final Config config = new Config();
    private final Game game;
    private final DataProvider provider = new DataMySQL(config);

    public Start() {
        // Initiate game by starting the gui controller
        game = new Game(provider);
        new GuiControl(game);
    }
}
