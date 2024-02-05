package com.mygdx.game;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Libgdx Game Engine");

		// Get the primary monitor's current display mode (resolution, refresh rate, etc.)
		Graphics.DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();

		// Set the game to windowed mode with the screen's resolution
		config.setWindowedMode(displayMode.width, displayMode.height);

		// Set the window position to the top-left corner of the screen
		config.setWindowPosition(0, 0);

		// Optionally, remove window decorations for a cleaner look
		config.setDecorated(true);

		// Start the game
		new Lwjgl3Application(new MainGame(), config);
	}
}
