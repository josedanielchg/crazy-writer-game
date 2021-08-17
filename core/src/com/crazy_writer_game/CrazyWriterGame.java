package com.crazy_writer_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import screens.game_screens.GameScreen;
import screens.LevelMenuScreen;
import screens.MainMenuScreen;

public class CrazyWriterGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		//this.setScreen(new GameScreen(this, 3f, -0.1f));
		//this.setScreen(new MainMenuScreen(this));
		this.setScreen(new LevelMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
