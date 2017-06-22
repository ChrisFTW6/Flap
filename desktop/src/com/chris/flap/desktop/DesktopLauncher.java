package com.chris.flap.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.chris.flap.FlapGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Flap";
		config.width = 272;
		config.height = 408;
		new LwjglApplication(new FlapGame(), config);
	}
}

