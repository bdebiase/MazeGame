package com.maze.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.maze.game.maze.GameMap;
import com.maze.game.maze.TileType;
import com.maze.game.maze.TiledGameMap;

public abstract class Entity {
    //INSTANCE VARIABLES
    protected Vector2 _position;
    protected Vector2 _velocity;
    protected Texture _texture;
    protected GameMap _gameMap;

    //CONSTRUCTOR
    public Entity(Vector2 position, Texture texture, GameMap gameMap) {
        _position = position;
        _texture = texture;
        _gameMap = gameMap;
        _velocity = new Vector2(0, 0);
    }

    //GETTERS & SETTERS
    public Vector2 getPosition() { return _position; }

    public void setPosition(Vector2 position) { _position = position; }

    public Vector2 getVelocity() { return _velocity; }

    public Vector2 getCoordinates() {
        return new Vector2((int)Math.floor(_position.x) / TileType.TILE_SIZE, (int)Math.floor(_position.y) / TileType.TILE_SIZE);
    }

    public Texture getTexture() {
        return _texture;
    }

    public void setVelocity(Vector2 val) { _velocity = val; }

    //ABSTRACTS
    public abstract void render(SpriteBatch batch);

    public abstract void update(float deltaTime);
}