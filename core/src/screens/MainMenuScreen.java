package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.crazy_writer_game.CrazyWriterGame;
import components.DynamicButton;
import screens.game_screens.GameScreen;
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


       //--------------
        /*game.font.draw(game.batch, "X: "+ Gdx.input.getX() + " Y: " + Gdx.input.getY()
                + " -- " + (btnPlay.getX()*1.1) + "," + (btnPlay.getY()*1.1), 5, 15);

        game.font.draw(game.batch, "X: "+ Gdx.input.getX() + "  dx:" +  (Gdx.graphics.getHeight() - Gdx.input.getY()), 300, 15);
        // ---------------
           game.font.draw(game.batch, "->" + (Gdx.input.getX()) + ", "
                   + (Gdx.graphics.getHeight() - Gdx.input.getY()) , 300, 500);
        game.font.draw(game.batch, "DELTA: " + TimeUtils.nanoTime() , 500, 500);*/
        //if(btnCredits.isPressed()){
        //   game.font.draw(game.batch, "PRESSED", 300, 500);
        //}

        game.batch.end();

        if(btnPlay.isPressed())
            game.setScreen(new GameScreen(game, 3f, -0.1f));

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