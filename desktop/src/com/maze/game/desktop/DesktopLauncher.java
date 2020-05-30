package com.maze.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.maze.game.Main;
import com.maze.game.maze.TileType;
import com.maze.game.maze.TiledGameMap;

public class DesktopLauncher {
	public static int MapSize = 10;

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = TileType.TILE_SIZE * MapSize;
		config.height = TileType.TILE_SIZE * MapSize;
		config.resizable = false;
		new LwjglApplication(new Main(), config);
	}
}
