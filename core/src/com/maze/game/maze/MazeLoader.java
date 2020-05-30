package com.maze.game.maze;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import com.maze.game.maze.MazeData;
import com.maze.game.maze.TileType;

public class MazeLoader {
    //INSTANCE VARIABLES
    private static Json json = new Json();
    private static final int SIZE = 100;

    //loads map with name
    public static MazeData loadMap (String id, String name) {
        Gdx.files.local("maps/").file().mkdirs();
        FileHandle file = Gdx.files.local("maps/" + id + ".map");
        if (file.exists()) {
            MazeData data = json.fromJson(MazeData.class, file.readString());
            return data;
        }  else {
            MazeData data = generateRandomMap(id, name);
            saveMap(data.id, data.name, data.map);
            return data;
        }
    }

    //saves map
    public static void saveMap (String id, String name, int[][][] map) {
        MazeData data = generateRandomMap("map", "Rand");
        data.id = id;
        data.name = name;
        data.map = map;

        Gdx.files.local("maps/").file().mkdirs();
        FileHandle file = Gdx.files.local("maps/" + id + ".map");
        file.writeString(json.prettyPrint(data), false);
    }

    //generates random map
    public static MazeData generateRandomMap (String id, String name) {
        MazeData mapData = new MazeData();
        mapData.id = id;
        mapData.name = name;
        mapData.map = new int[2][SIZE][SIZE];

        Random random = new Random();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                mapData.map[0][row][col] = TileType.BRICK_WALL.getId();
            }
        }

        return mapData;
    }
}