package com.crazy_writer_game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.crazy_writer_game.CrazyWriterGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.title = "Crazy Writer Game";
		config.width = 696;
		config.height = 633;
		new LwjglApplication(new CrazyWriterGame(), config);
	}
}
