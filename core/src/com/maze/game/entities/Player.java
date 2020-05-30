package com.maze.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.maze.game.Main;
import com.maze.game.maze.GameMap;
import com.maze.game.maze.TileType;

public class Player extends Entity {
    //INSTANCE VARIABLES
    public static final int SPEED = 300;
    public static final int ACCEL = 15;

    protected static Vector2 _input, _dir;
    protected boolean _hasSword;

    private float deltaTime;
    private boolean isEffected;

    //CONSTRUCTOR
    public Player(Vector2 position, Texture texture, GameMap gameMap) {
        super(position, texture, gameMap);
        isEffected = false;
    }

    public boolean hasSword() { return _hasSword; }

    public void hasSword(boolean val) { _hasSword = val; }

    //render every frame
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(_texture, _position.x, _position.y, _texture.getWidth(), _texture.getHeight());
    }

    //update every frame
    @Override
    public void update(float deltaTime) {
        this.deltaTime = deltaTime;
        getInput();

        if (checkForSword()) {
            _hasSword = true;
        }

        getVelocity();

        if(!isEffected) isEffected = Math.random() < 0.001;
        if (_gameMap.getTileTypeByLocation(1, _position.x, _position.y) == TileType.END && _gameMap.minotaur.isDead())
            Main.loadMap(Main.getNextMap());
    }

    //gets player input
    public Vector2 getInput() {
        _input = new Vector2(
                boolToInt(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) - boolToInt(Gdx.input.isKeyPressed(Input.Keys.LEFT)),
                boolToInt(Gdx.input.isKeyPressed(Input.Keys.UP)) - boolToInt(Gdx.input.isKeyPressed(Input.Keys.DOWN)));
        _input.clamp(-1, 1);

        if (_input != Vector2.Zero)
            _dir = _input;

        return _input;
    }

    //gets player velocity
    public Vector2 getVelocity() {
        float temp = 1f;
        if(isEffected){
            isEffected = true;
            temp = 0.5f;
        }
        _velocity.x = MathUtils.lerp(_velocity.x, _input.x * SPEED * temp, ACCEL * deltaTime);
        _velocity.y = MathUtils.lerp(_velocity.y, _input.y * SPEED * temp, ACCEL * deltaTime);

        float newX = _position.x + _velocity.x * deltaTime;
        if (!_gameMap.doesRectCollideWithMap(newX, _position.y, _texture.getWidth(), _texture.getHeight()))
            _position.x = newX;

        float newY = _position.y + _velocity.y * deltaTime;
        if (!_gameMap.doesRectCollideWithMap(_position.x, newY, _texture.getWidth(), _texture.getHeight()))
            _position.y = newY;

        return _velocity;
    }

    //check if player has sword
    public boolean checkForSword() {
        return _gameMap.getTileTypeByLocation(1, _position.x, _position.y) == TileType.SWORD_SPAWNER;
    }

    //SETTER
    public void setVelocity(Vector2 velocity) { _velocity = velocity; }

    public Vector2 velocity() { return _velocity; }
    private int boolToInt(boolean bool) {
        return bool ? 1 : 0;
    }
}
