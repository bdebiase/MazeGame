package com.maze.game.maze;

import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public enum TileType {
    //TILE TYPES
    AIR(0, false, "Air", new Vector2(3, 1)),
    BRICK_WALL(1, true, "Wall", new Vector2(0, 0)),
    MOSS_WALL(2, true, "Moss Wall", new Vector2(1, 0)),
    BRICK_FLOOR(3, false, "Floor", new Vector2(2, 0)),
    MOSS_FLOOR(4, false, "Moss Floor", new Vector2(3, 0)),
    START(5, false, "Start", new Vector2(0, 1)),
    END(6, false, "End", new Vector2(1, 1)),
    SWORD_SPAWNER(7, false, "Sword Spawner", new Vector2(2, 1)),
    MINOTAUR_SPAWNER(8, false, "Minotaur Spawner", new Vector2(3, 1)),
    RAT_SPAWNER(9, false, "Rat Spawner", new Vector2(0, 2));
    //TRAP(4, false, "Trap", 1);

    //INSTANCE VARIABLES
    public static final int TILE_SIZE = 64;

    private int id;
    private boolean collidable;
    private String name;
    private Vector2 texPos;
    private float damage;

    //CONSTRUCTORS
    private TileType (int id, boolean collidable, String name, Vector2 texPos) {
        this(id, collidable, name, texPos,0);
    }

    private TileType (int id, boolean collidable, String name, Vector2 texPos, float damage) {
        this.id = id;
        this.collidable = collidable;
        this.name = name;
        this.texPos = texPos;
        this.damage = damage;
    }

    //GETTERS
    public int getId() {
        return id;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public String getName() {
        return name;
    }

    public Vector2 getTexPos() { return texPos; }

    public float getDamage() {
        return damage;
    }

    private static HashMap<Integer, TileType> tileMap;

    static {
        tileMap = new HashMap<Integer, TileType>();
        for (TileType tileType : TileType.values()) {
            tileMap.put(tileType.getId(), tileType);
        }
    }

    public static TileType getTileTypeById (int id) {
        return tileMap.get(id);
    }
}