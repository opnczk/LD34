package com.quailshillstudio.ludumdare34.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.quailshillstudio.ludumdare34.LD34;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;//640
		config.height = 960; //480
		new LwjglApplication(new LD34(), config);
	}
}
