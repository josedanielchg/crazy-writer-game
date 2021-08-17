package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.crazy_writer_game.CrazyWriterGame;
import components.DataLevelsGame;
import components.DynamicButton;
import utilities.Resource;

public class MainMenuScreen extends Screens{
    Texture bg;
    DynamicButton btnPlay;
    DynamicButton btnQuit;
    DynamicButton btnCredits;
    float deltaTime;

    public MainMenuScreen(CrazyWriterGame game) {
        super(game);
        bg = new Texture(Resource.BG_MAIN_MENU);

        btnPlay = new DynamicButton(Resource.BTN_PLAY, -1, 160);
        btnQuit = new DynamicButton(Resource.BTN_QUIT, 25, 25);
        btnCredits = new DynamicButton(Resource.BTN_CREDITS, -1, 88);
        deltaTime = TimeUtils.nanoTime() + 1000000000;
    }

    @Override
    public void draw(float delta) {

        game.batch.begin();

        // - BackGround
        game.batch.draw(bg,0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // - Buttons
        game.batch.draw(btnPlay.getButton(), btnPlay.getX(), btnPlay.getY(), btnPlay.getWidth(), btnPlay.getHeight());
        game.batch.draw(btnQuit.getButton(), btnQuit.getX(), btnQuit.getY(), btnQuit.getWidth(), btnQuit.getHeight());
        game.batch.draw(btnCredits.getButton(), btnCredits.getX(), btnCredits.getY(), btnCredits.getWidth(), btnCredits.getHeight());

        game.batch.end();

        if(btnPlay.isPressed())
            game.setScreen(new LevelMenuScreen(game));

        if(btnCredits.isPressed())
            game.setScreen(new CreditsScreen(game));

        if(btnQuit.isPressed() && TimeUtils.nanoTime() > deltaTime)
            Gdx.app.exit();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {
        bg.dispose();
        btnPlay.dispose();
        btnQuit.dispose();
    }
}