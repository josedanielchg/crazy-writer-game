package screens.game_screens.game_objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.crazy_writer_game.CrazyWriterGame;
import screens.game_screens.Utils.GameAssets;

public class Papers {

    public static Vector2 loserPapersPosition = new Vector2(228+15, 82);
    public static Vector2 loserPapersSize = new Vector2(241, 156);
    public static Animation<TextureRegion> loserPapers = GameAssets.loserPapers;

    public static Vector2 winnerPapersPosition = new Vector2(609, 80);
    public static Vector2 winnerPapersSize = new Vector2(86, 169);
    public static Animation<TextureRegion> winnerPapers = GameAssets.winnerPapers;

    public static void draw(int lostWords, int writtenWords, CrazyWriterGame game) {
        if(lostWords > 0)
            game.batch.draw(loserPapers.getKeyFrame(lostWords),
                    loserPapersPosition.x,
                    loserPapersPosition.y,
                    0.f,
                    0.0f,
                    loserPapersSize.x,
                    loserPapersSize.y,
                    1, 1, 0
            );

        if(writtenWords > 0)
            game.batch.draw(winnerPapers.getKeyFrame(writtenWords),
                    winnerPapersPosition.x,
                    winnerPapersPosition.y,
                    0.f,
                    0.0f,
                    winnerPapersSize.x,
                    winnerPapersSize.y,
                    1, 1, 0
            );
    }
}
