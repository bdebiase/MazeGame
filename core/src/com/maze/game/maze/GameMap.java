package com.maze.game.maze;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.maze.game.Main;
import com.maze.game.entities.*;

public abstract class GameMap {
    //INSTANCE VARIABLES
    protected ArrayList<Entity> entities;

    public Minotaur minotaur;
    public Sword sword;
    public Player player;
    public Rat rat;

    //CONSTRUCTOR
    public GameMap() {
        entities = new ArrayList<Entity>();

        minotaur = new Minotaur(new Vector2(0, 0), new Texture("big-cow-face.png"), this);
        sword = new Sword(new Vector2(0, 0), new Texture("sword.png"), this);
        rat = new Rat(new Vector2(0, 0), new Texture("bat-face.png"), this);
        player = new Player(new Vector2(0, 0), new Texture("player.png"), this);

        entities.add(minotaur);
        entities.add(sword);
        entities.add(rat);
        entities.add(player);
    }

    //render every frame
    public void render (OrthographicCamera camera, SpriteBatch batch) {
        for (Entity entity : entities) {
            entity.render(batch);
        }
    }

    //update very frame
    public void update (float delta) {
        for (Entity entity : entities) {
            entity.update(delta);
        }
        restartOnDeath();
    }

    public void dispose () {
    }

    //returns tile at layer, x, y
    public TileType getTileTypeByLocation(int layer, float x, float y) {
        return this.getTileTypeByCoordinate(layer, (int) (x / TileType.TILE_SIZE), (int) (y / TileType.TILE_SIZE));
    }

    public abstract TileType getTileTypeByCoordinate(int layer, int col, int row);

    //detects if rect is colliding with map
    public boolean doesRectCollideWithMap(float x, float y, int width, int height) {
        if (x < 0 || y < 0 || x + width > getPixelWidth() || y + height > getPixelHeight())
            return true;

        for (int row = (int) (y / TileType.TILE_SIZE); row < Math.ceil((y + height) / TileType.TILE_SIZE); row++) {
            for (int col = (int) (x / TileType.TILE_SIZE); col < Math.ceil((x + width) / TileType.TILE_SIZE); col++) {
                for (int layer = 0; layer < getLayers(); layer++) {
                    TileType type = getTileTypeByCoordinate(layer, col, row);
                    if (type != null && type.isCollidable())
                        return true;
                }
            }
        }

        return false;
    }

    //gets start tile
    public Vector2 getStartTilePosition() {
        for(int r = 0; r < 10; r++){
            for(int c = 0; c < 10; c++){
                if (getTileTypeByCoordinate(1, r, c) != null)
                    if(getTileTypeByCoordinate(1, r, c) == TileType.START) return new Vector2(r * TileType.TILE_SIZE + 16, c * TileType.TILE_SIZE + 16);
            }
        }
        return new Vector2(0, 0);
    }

    //gets minotaur postiion
    public Vector2 getMinotaurTilePosition() {
        for(int r = 0; r < 10; r++){
            for(int c = 0; c < 10; c++){
                if (getTileTypeByCoordinate(1, r, c) != null)
                    if(getTileTypeByCoordinate(1, r, c) == TileType.MINOTAUR_SPAWNER)
                        return new Vector2(r * TileType.TILE_SIZE + 16, c * TileType.TILE_SIZE + 16);
            }
        }
        return new Vector2(0, 0);
    }

    //gets rat position
    public Vector2 getRatTilePosition() {
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                if (getTileTypeByCoordinate(1, r, c) != null)
                    if (getTileTypeByCoordinate(1, r, c) == TileType.RAT_SPAWNER)
                        return new Vector2(r * TileType.TILE_SIZE + 16, c * TileType.TILE_SIZE + 16);
            }
        }

        return new Vector2(0, 0);
    }

    //gets sword position
    public Vector2 getSwordTilePosition() {
        for(int r = 0; r < 10; r++){
            for(int c = 0; c < 10; c++){
                if (getTileTypeByCoordinate(1, r, c) != null)
                    if(getTileTypeByCoordinate(1, r, c).getId() == TileType.SWORD_SPAWNER.getId())
                        return new Vector2(r * TileType.TILE_SIZE + 16, c * TileType.TILE_SIZE + 16);
            }
        }
        return new Vector2(0, 0);
    }

    //checks if entity is colliding with other entity
    public boolean isEntityCollidingWithOther(Entity a, Entity b) {
        return a.getPosition().x + a.getTexture().getWidth() / 2 > b.getPosition().x && a.getPosition().x - a.getTexture().getWidth() / 2 < b.getPosition().x
                && a.getPosition().y + a.getTexture().getHeight() / 2 > b.getPosition().y && a.getPosition().y - a.getTexture().getHeight() / 2 < b.getPosition().y;
    }

    //checks if entity is at tile
    public boolean isEntityAtTile(Entity ent, int x, int y) {
        return ent.getCoordinates().equals(new Vector2(x, y));
    }

    //restarts map on death
    public void restartOnDeath() {
        if (isEntityCollidingWithOther(player, minotaur) && !minotaur.isDead()) {
            if (!player.hasSword())
                Main.restartMap();
            else
                minotaur.isDead(true);
        }

        if (isEntityCollidingWithOther(player, rat))
            player.hasSword(false);
    }

    //ABSTRACTS
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getLayers();

    //GETTERS
    public int getPixelWidth() {
        return this.getWidth() * TileType.TILE_SIZE;
    }

    public int getPixelHeight() {
        return this.getHeight() * TileType.TILE_SIZE;
    }
}