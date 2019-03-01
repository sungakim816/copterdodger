package com.pypert.paper.copterdodger.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pypert.paper.copterdodger.CopterDodger;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = CopterDodger.GAME_WORLD_WIDTH;
        config.height = CopterDodger.GAME_WORLD_HEIGHT;
        config.resizable = false;
        config.title = "Copter Dodger";
        Game myGame = new CopterDodger();
        new LwjglApplication(myGame, config);
    }
}