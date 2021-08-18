package com.crazy_writer_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import components.DataLevelsGame;
import screens.MainMenuScreen;
import screens.game_screens.Utils.GameAssets;

public class CrazyWriterGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public Music backgroundMusic;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.local("./music&sound/Sound 2.mp3"));
		backgroundMusic.setLooping(true);
		backgroundMusic.setVolume(0.2f);
		backgroundMusic.play();
		DataLevelsGame.load();
		this.setScreen(new MainMenuScreen(this));
		GameAssets.load();
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
