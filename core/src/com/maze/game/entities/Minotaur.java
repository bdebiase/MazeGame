package com.maze.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.maze.game.maze.GameMap;
import com.maze.game.maze.TileType;

import java.util.Random;

public class Minotaur extends Entity {
    //INSTANCE VARIABLES
    public static final int SPEED = 2;
    public static final int CHARGE_SPEED = 4;
    public static final int ACCEL = 25;

    protected Vector2 _startPos, _direction;
    protected boolean isCharging;
    protected boolean _isDead;
    private Vector2 dir = new Vector2(0, 0);

    //CONSTRUCTOR
    public Minotaur(Vector2 position, Texture texture, GameMap gameMap) {
        super(position, texture, gameMap);
        _startPos = _position;
        isCharging = false;
        dir = new Vector2(0, -1);
    }

    public boolean isCharging() { return isCharging; }
    public void charge(boolean charge) { isCharging = charge;}

    public boolean isDead() { return _isDead; }

    public void isDead(boolean val) { _isDead = val; }

    //render every frame
    @Override
    public void render(SpriteBatch batch) {
        if (!_isDead)
            batch.draw(_texture, _position.x, _position.y, _texture.getWidth(), _texture.getHeight());
    }

    //update every frame
    @Override
    public void update(float deltaTime) {
        if (!seesPlayer())
            patrol();
        else
            chasePlayer();

        _position.x += _velocity.x;
        _position.y += _velocity.y;
    }

    //GETTER
    public Vector2 getVelocity() { return _velocity; }

    //SETTER
    public void setVelocity(Vector2 velocity) { _velocity = velocity; }

    //makes minotaur patrol
    public void patrol() {
        _velocity.x = dir.x * SPEED;
        _velocity.y = dir.y * SPEED;

        if(_gameMap.doesRectCollideWithMap((_position.x  + (dir.x * (_texture.getWidth() / 2))) + _velocity.x, (_position.y + (dir.y * (_texture.getHeight() / 2))) + _velocity.y, _texture.getWidth(), _texture.getHeight())) {
            int rand = randomRange(-1, 1);
            _velocity.rotate90(rand);
            dir.rotate90(rand);
        }
    }

    //makes minotaur chase the player
    public void chasePlayer() {
        _velocity.x = dir.x * CHARGE_SPEED;
        _velocity.y = dir.y * CHARGE_SPEED;

        if(_gameMap.doesRectCollideWithMap((_position.x  + (dir.x * (_texture.getWidth() / 2))) + _velocity.x, (_position.y + (dir.y * (_texture.getHeight() / 2))) + _velocity.y, _texture.getWidth(), _texture.getHeight())) {
                int rand = randomRange(-1, 1);
                _velocity.rotate90(rand);
                dir.rotate90(rand);
        }
    }

    //gets a random number between min & max
    public static int randomRange(int min, int max) {
        if (min >= max)
            throw new IllegalArgumentException("max must be greater than min");

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    //if minotaur sees the palyer
    public boolean seesPlayer() {
        boolean result = false;
        for (int i = 0; i < 5; i++) {
            if (_gameMap.isEntityAtTile(_gameMap.player, (int)getCoordinates().x + i * (int)dir.x, (int)getCoordinates().y + i * (int)dir.y))
                result = true;

        }
        return result;
    }
}

