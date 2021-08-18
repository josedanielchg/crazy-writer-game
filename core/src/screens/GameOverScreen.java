package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.crazy_writer_game.CrazyWriterGame;
import components.DataLevelsGame;
import components.DynamicButton;
import screens.game_screens.GameScreen;
import utilities.Resource;

public class GameOverScreen extends Screens{
    Texture bg;
    Texture message;
    DynamicButton btnBack;
    int level;

    public GameOverScreen(CrazyWriterGame game, int level) {
        super(game);
        bg = new Texture(Resource.BG_GAME_SCREEN);
        message = new Texture(Resource.GAME_OVER);
        btnBack = new DynamicButton(Resource.BTN_BACK, 25, 25);
        this.level = level;
    }

    public void draw(float delta) {
        if(btnBack.isPressed())
            game.setScreen(new MainMenuScreen(game));

        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            this.game.setScreen(new GameScreen(game,
                    DataLevelsGame.listGameLevel.get(level - 1).level,
                    DataLevelsGame.listGameLevel.get(level - 1).spawn_rate_words,
                    DataLevelsGame.listGameLevel.get(level - 1).gravity,
                    DataLevelsGame.listGameLevel.get(level - 1).lost_words_to_lose,
                    DataLevelsGame.listGameLevel.get(level - 1).write_words_to_win,
                    DataLevelsGame.listGameLevel.get(level - 1).maximum_letters_per_word));
        }

        game.batch.begin();

        game.batch.draw(bg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        game.batch.draw(message, (SCREEN_WIDTH/2) - (message.getWidth()/2), (SCREEN_HEIGHT/2) - (message.getHeight()/2), message.getWidth(), message.getHeight());
        game.batch.draw(btnBack.getButton(), btnBack.getX(), btnBack.getY(), btnBack.getWidth(), btnBack.getHeight());
        game.batch.end();
    }

    @Override
    public void update(float delta) {

    }
}
