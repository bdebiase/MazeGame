package com.maze.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.maze.game.entities.Entity;
import com.maze.game.entities.Player;
import com.maze.game.entities.Rat;
import com.maze.game.maze.GameMap;
import com.maze.game.maze.TileType;
import com.maze.game.maze.TiledGameMap;

public class Main extends ApplicationAdapter {
	//INSTANCE VARIABLES
	public static String[] maps = {"level1.tmx", "level2.tmx", "level3.tmx", "level4.tmx", "level5.tmx"};
	public static int currentMap;

	boolean gameStarted;
	SpriteBatch batch;
	OrthographicCamera camera;
	public static GameMap gameMap;

	private Stage stage;

	//runs on program start
	@Override
	public void create() {
		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();

		loadMap(getNextMap());

		if (!gameStarted) {
			stage = new Stage();
			Gdx.input.setInputProcessor(stage);

			Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

			Label.LabelStyle labelStyle = new Label.LabelStyle();
			BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
			labelStyle.font = font;
			labelStyle.fontColor = Color.WHITE;

			Label label = new Label("Controls: Arrow keys to move.\nObjective: Dodge the rats and kill the Minotaur to escape!\nA sword can be used to slay the Minotaur.", labelStyle);
			label.setAlignment(Align.center);
			label.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			label.setPosition(0, 100);

			TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
			textButtonStyle.up = skin.newDrawable("white", Color.GRAY);
			textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
			textButtonStyle.font = font;
			TextButton btn = new TextButton("Start Game", textButtonStyle);
			btn.setPosition(Gdx.graphics.getWidth() / 2 - btn.getWidth() / 2, 300);
			btn.addListener(new ClickListener() {
				@Override
				public void touchUp(InputEvent e, float x, float y, int point, int button) {
					gameStarted = true;
				}
			});

			stage.addActor(label);
			stage.addActor(btn);
		}
	}

	//render every frame
	@Override
	public void render() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1))
			loadMap(0);
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2))
			loadMap(1);
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3))
			loadMap(2);
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4))
			loadMap(3);
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5))
			loadMap(4);

		if (!gameStarted) {
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();
		} else {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			if(Math.random() < 0.1){
				gameMap.player.setVelocity(new Vector2(gameMap.player.velocity().x * 0.5f, gameMap.player.velocity().y * 0.5f));

			}

			camera.update();
			gameMap.update(Gdx.graphics.getDeltaTime());
			gameMap.render(camera, batch);
		}
	}

	//dispose for optimization
	@Override
	public void dispose () {
		batch.dispose();
		gameMap.dispose();
	}

	//loads map number
	public static void loadMap(int mapNum) {
		gameMap = new TiledGameMap(mapNum);
		gameMap.player.setPosition(gameMap.getStartTilePosition());
		gameMap.player.hasSword(false);
		gameMap.minotaur.setPosition(gameMap.getMinotaurTilePosition());
		gameMap.minotaur.isDead(false);
		gameMap.sword.setPosition(gameMap.getSwordTilePosition());
		gameMap.rat.setPosition(gameMap.getRatTilePosition());
	}

	//restarts map
	public static void restartMap() {
		gameMap.player.setPosition(gameMap.getStartTilePosition());
		gameMap.player.hasSword(false);
		gameMap.minotaur.setPosition(gameMap.getMinotaurTilePosition());
		gameMap.minotaur.isDead(false);
		gameMap.sword.setPosition(gameMap.getSwordTilePosition());
		gameMap.rat.setPosition(gameMap.getRatTilePosition());
	}

	//returns next map number
	public static int getNextMap() {
		currentMap++;
		if(currentMap >= maps.length) currentMap = 0;
		return currentMap;
	}
}