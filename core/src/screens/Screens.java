package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.crazy_writer_game.CrazyWriterGame;

public abstract class Screens extends InputAdapter implements Screen {

    public static final float SCREEN_WIDTH = Gdx.graphics.getWidth();
    public static final float SCREEN_HEIGHT = Gdx.graphics.getHeight();

    public static final float WORD_WIDTH = Gdx.graphics.getWidth()/100f;
    public static final float WORD_HEIGHT = Gdx.graphics.getHeight()/100f;

    public CrazyWriterGame game;

    public OrthographicCamera cameraUI;
    public OrthographicCamera cameraBox2D;

    public Screens(CrazyWriterGame game) {
        this.game = game;
        cameraUI = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        cameraUI.position.set(SCREEN_WIDTH/2f, SCREEN_HEIGHT/2f, 0);

        cameraBox2D = new OrthographicCamera(WORD_WIDTH, WORD_HEIGHT);
        cameraBox2D.position.set(WORD_WIDTH/2f, WORD_HEIGHT/2f, 0);

        Gdx.input.setInputProcessor(this);
    }

    //Draw UI and Game Objects
    public abstract void draw(float delta);

    //Update game physics
    public abstract void update(float delta);

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        cameraUI.update();
        game.batch.setProjectionMatrix(cameraUI.combined);

        draw(delta);
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {

    }
}
