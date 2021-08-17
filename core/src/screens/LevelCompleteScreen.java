package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.crazy_writer_game.CrazyWriterGame;
import components.DataLevelsGame;
import screens.game_screens.GameScreen;
import utilities.Resource;

public class LevelCompleteScreen extends Screens{

    Texture bg;
    Texture message;
    int level;

    public LevelCompleteScreen(CrazyWriterGame game, int level, int stars) {
        super(game);
        bg = new Texture(Resource.BG_GAME_SCREEN);
        message = new Texture(Resource.getLevelComplete(stars));
        this.level = level;

        DataLevelsGame.listGameLevel.get(level-1).stars = stars;
        DataLevelsGame.listGameLevel.get(level-1).lock = false;

        if(level!=10)
            DataLevelsGame.listGameLevel.get(level).lock = false;

        DataLevelsGame.save();
    }

    @Override
    public void draw(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            if(level==10)
                this.game.setScreen(new MainMenuScreen(this.game));
            else if(level!=2)
                this.game.setScreen(new GameScreen(game,
                        DataLevelsGame.listGameLevel.get(level).level,
                        DataLevelsGame.listGameLevel.get(level).spawn_rate_words,
                        DataLevelsGame.listGameLevel.get(level).gravity,
                        DataLevelsGame.listGameLevel.get(level).lost_words_to_lose,
                        DataLevelsGame.listGameLevel.get(level).write_words_to_win,
                        DataLevelsGame.listGameLevel.get(level).maximum_letters_per_word));
            else
                this.game.setScreen(new GameOverScreen(this.game, level));
        }

        game.batch.begin();

        game.batch.draw(bg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        game.batch.draw(message, (SCREEN_WIDTH/2) - (message.getWidth()/2), (SCREEN_HEIGHT/2) - (message.getHeight()/2), message.getWidth(), message.getHeight());
        game.batch.end();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {
        bg.dispose();
        message.dispose();
    }

}
