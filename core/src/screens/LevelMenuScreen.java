package screens;

import com.badlogic.gdx.graphics.Texture;
import com.crazy_writer_game.CrazyWriterGame;
import components.BookLevel;
import components.DynamicButton;
import utilities.Resource;

import java.awt.print.Book;

public class LevelMenuScreen extends Screens{
    final int NUM_LEVELS = 10;

    Texture bg;
    DynamicButton btnBack;
    BookLevel [] books;

    public LevelMenuScreen(CrazyWriterGame game) {
        super(game);

        bg = new Texture(Resource.BG_LEVEL_MENU);
        btnBack = new DynamicButton(Resource.BTN_BACK, 25, 25);
        books = new BookLevel[NUM_LEVELS];

        String configFromJson = "", src = "";
        int dx=107+15, dy = BookLevel.POS_ROW_ONE, increaseX = 0;

        // TODO: Comprobar que el primer libro no tenga el candado
        if(true)
            src = Resource.getBookStar(0);

        for(int i = 0; i < 10; i++) {

            if (i == 5) {
                increaseX = 0;
                dy = BookLevel.POS_ROW_TWO;
            }

            books[i] = new BookLevel(src, BookLevel.POS_X + (dx * increaseX), dy, i);
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
        }

        game.batch.draw(btnBack.getButton(), btnBack.getX(), btnBack.getY(), btnBack.getWidth(), btnBack.getHeight());

        game.batch.end();
    }

    @Override
    public void update(float delta) {

    }
}
