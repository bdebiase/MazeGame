package com.maze.game.maze;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.maze.game.Main;
import com.maze.game.entities.Entity;
import com.maze.game.maze.GameMap;

import java.util.Random;

public class TiledGameMap extends GameMap {
    //INSTANCE VARAIBLES
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;

    //CONSTRUCTOR
    public TiledGameMap (int mapNum) {
        tiledMap = new TmxMapLoader().load(Main.maps[mapNum]);
        tiledMap.getLayers().get(1).setVisible(false);

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    //render every frame
    @Override
    public void render (OrthographicCamera camera, SpriteBatch batch) {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        super.render(camera, batch);
        batch.end();
    }

    //update every frame
    @Override
    public void update (float delta) {
        super.update(delta);
    }

    @Override
    public void dispose () {
        tiledMap.dispose();
    }

    //get tile by coords
    @Override
    public TileType getTileTypeByCoordinate (int layer, int col, int row) {
        Cell cell = ((TiledMapTileLayer) tiledMap.getLayers().get(layer)).getCell(col, row);

        if (cell != null) {
            TiledMapTile tile = cell.getTile();

            if (tile != null) {
                int id = tile.getId();
                return TileType.getTileTypeById(id);
            }
        }
        return null;
    }

    //GETTERS
    @Override
    public int getWidth () {
        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getWidth();
    }

    @Override
    public int getHeight () {
        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getHeight();
    }

    @Override
    public int getLayers() {
        return tiledMap.getLayers().getCount();
    }

}