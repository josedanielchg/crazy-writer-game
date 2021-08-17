package screens.game_screens.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import screens.Screens;
import screens.game_screens.game_objects.WordObject;

public class GameAssets {

    public static Sprite normal_word;
    public static Sprite fire_word;
    public static Sprite ice_word;
    public static Sprite slow_word;
    public static Sprite wind_word;
    public static Sprite background;
    public static Sprite cursorRight;
    public static Sprite cursorLeft;
    public static Sprite keyBg;

    static TextureAtlas atlas;

    public static void load() {
        atlas = new TextureAtlas(Gdx.files.internal("pack.atlas"));

        normal_word = atlas.createSprite("palabra_normal_grande");
        fire_word = atlas.createSprite("palabra_fuego_extra_grande");
        ice_word = atlas.createSprite("palabra_hielo_grande");
        slow_word = atlas.createSprite("palabra_lento_grande");
        wind_word = atlas.createSprite("palabra_viento_grande");

        background = atlas.createSprite("mapa");
        background.setPosition(0, 0);
        background.setSize(Screens.SCREEN_WIDTH, Screens.SCREEN_HEIGHT);

        cursorRight = atlas.createSprite("cursor_derecha");
        cursorLeft = atlas.createSprite("cursor_izquierda");
        keyBg = atlas.createSprite("tecla");
    }

    public static void dispose() {
        atlas.dispose();
    }
}
