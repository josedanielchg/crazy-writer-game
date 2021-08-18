package screens.game_screens.game_objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.crazy_writer_game.CrazyWriterGame;
import screens.game_screens.Utils.GameAssets;

public class Score {
    public static Sprite cornerScore = GameAssets.corner;
    public static Vector2 positionCornerScore = new Vector2(580, 589);

    public static Sprite cornerWords = GameAssets.corner;
    public static Vector2 positionCornerWords = new Vector2(580, 551);

    public static Vector2 sizeCorner = new Vector2(205, 45);

    public static void draw(CrazyWriterGame game, short writeWords, long score) {
        cornerWords.setPosition(positionCornerWords.x, positionCornerWords.y);
        cornerWords.setSize(sizeCorner.x, sizeCorner.y);
        cornerWords.draw(game.batch);

        cornerScore.setPosition(positionCornerScore.x, positionCornerScore.y);
        cornerScore.setSize(sizeCorner.x, sizeCorner.y);
        cornerScore.draw(game.batch);

        game.font.setColor(0,0,0,1);
        game.font.draw(game.batch, "Words: "+ writeWords, positionCornerScore.x+10, positionCornerScore.y+35);
        game.font.draw(game.batch, "Score: "+ score, positionCornerWords.x+10, positionCornerWords.y+35);
        game.font.setColor(1,1,1,1);
    }
}
