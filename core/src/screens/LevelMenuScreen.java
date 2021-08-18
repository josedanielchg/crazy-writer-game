package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.TimeUtils;
import com.crazy_writer_game.CrazyWriterGame;
import components.BookLevel;
import components.DataLevelsGame;
import components.DynamicButton;
import screens.game_screens.GameScreen;
import utilities.Resource;
import utilities.Resource;

public class LevelMenuScreen extends Screens{
    final int NUM_LEVELS = 10;

    Texture bg;
    DynamicButton btnBack;
    BookLevel [] books;
    float deltaTime;

    public LevelMenuScreen(CrazyWriterGame game) {
        super(game);

        bg = new Texture(Resource.BG_LEVEL_MENU);
        btnBack = new DynamicButton(Resource.BTN_BACK, 25, 25);
        books = new BookLevel[NUM_LEVELS];
        deltaTime = TimeUtils.nanoTime() + 1000000000;

        String src = Resource.getBookStar(0);
        int dx=107 + 15, dy = BookLevel.POS_ROW_ONE, increaseX = 0;

        for(int i = 0; i < 10; i++) {

            if(DataLevelsGame.listGameLevel.get(i).lock && i>0)
                src = Resource.BOOK_LOCK;
            else if(DataLevelsGame.listGameLevel.get(i).lock == false)
                src = Resource.getBookStar(DataLevelsGame.listGameLevel.get(i).stars);

            if (i == 5) {
                increaseX = 0;
                dy = BookLevel.POS_ROW_TWO;
            }

            books[i] = new BookLevel(src, BookLevel.POS_X + (dx * increaseX), dy, i, DataLevelsGame.listGameLevel.get(i).lock);
            increaseX++;
        }
    }


    @Override
    public void draw(float delta) {
        if(btnBack.isPressed())
            game.setScreen(new MainMenuScreen(game));

        game.batch.begin();

        game.batch.draw(bg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        for(int i = 0; i < 10; i++){
            game.batch.draw(books[i].getButton(), books[i].getX(), books[i].getY(), books[i].getWidth(), books[i].getHeight());
            books[i].paintNumber(game.batch);

            // if Button isPressed and isUnLocked make a new Level
            if(TimeUtils.nanoTime() > deltaTime &&
                    ((books[i].isPressed() && books[i].isUnlocked()) || (i == 0 && books[i].isPressed()))
              ){
                this.game.setScreen(new GameScreen(game,
                        DataLevelsGame.listGameLevel.get(i).level,
                        DataLevelsGame.listGameLevel.get(i).spawn_rate_words,
                        DataLevelsGame.listGameLevel.get(i).gravity,
                        DataLevelsGame.listGameLevel.get(i).lost_words_to_lose,
                        DataLevelsGame.listGameLevel.get(i).write_words_to_win,
                        DataLevelsGame.listGameLevel.get(i).maximum_letters_per_word));
            }
        }

        game.batch.draw(btnBack.getButton(), btnBack.getX(), btnBack.getY(), btnBack.getWidth(), btnBack.getHeight());

        game.batch.end();
    }


    @Override
    public void update(float delta) {}

    @Override
    public void dispose() {
        bg.dispose();
        btnBack.dispose();
        books = null;
    }
}
