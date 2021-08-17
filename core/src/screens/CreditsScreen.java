package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.crazy_writer_game.CrazyWriterGame;
import components.DynamicButton;
import utilities.Resource;

public class CreditsScreen extends Screens{

    Texture bg;
    DynamicButton btnBack;

    public CreditsScreen(CrazyWriterGame game) {
        super(game);
        bg = new Texture(Resource.BG_CREDITS_SCREEN);
        btnBack = new DynamicButton(Resource.BTN_BACK, 25, 25);
    }

    @Override
    public void draw(float delta) {
        if(btnBack.isPressed())
            game.setScreen(new MainMenuScreen(game));

        game.batch.begin();

        game.batch.draw(bg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        game.batch.draw(btnBack.getButton(), btnBack.getX(), btnBack.getY(), btnBack.getWidth(), btnBack.getHeight());

        game.batch.end();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {
        bg.dispose();
        btnBack.dispose();
    }
}
