package screens.game_screens.game_objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.crazy_writer_game.CrazyWriterGame;
import screens.game_screens.Utils.GameAssets;

public class PauseMenu {

    public static Sprite menuModal = GameAssets.menuModal;
    public static Vector2 menuModalPosition = new Vector2(207, 159);
    public static Vector2 menuModalSize = new Vector2(298, 314);

    public static Sprite continueButton = GameAssets.continueButton;
    public static Vector2 continueButtonPosition = new Vector2(258, 284);
    public static Vector2 continueButtonSize = new Vector2(196, 97);

    public static Sprite quitButton = GameAssets.quitButton;
    public static Vector2 quitButtonPosition = new Vector2(259, 201);
    public static Vector2 quitButtonSize = new Vector2(191, 91);

    public static boolean GAME_IN_PAUSE = false;

    public PauseMenu() {
        menuModal.setPosition(menuModalPosition.x, menuModalPosition.y);
        menuModal.setSize(menuModalSize.x, menuModalSize.y);

        continueButton.setPosition(continueButtonPosition.x, continueButtonPosition.y);
        continueButton.setSize(continueButtonSize.x, continueButtonSize.y);

        quitButton.setPosition(quitButtonPosition.x, quitButtonPosition.y);
        quitButton.setSize(quitButtonSize.x, quitButtonSize.y);
    }

    public static void draw(CrazyWriterGame game) {
        if(GAME_IN_PAUSE) {
            menuModal.draw(game.batch);
            continueButton.draw(game.batch);
            quitButton.draw(game.batch);
        }
    }
}
