package screens.game_screens.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import screens.Screens;

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
    public static Sprite desk;
    public static Sprite typeWriter;
    public static Sprite writerNormal;
    public static Sprite corner;
    public static Animation<TextureRegion> writingCharacter;
    public static Animation<TextureRegion> error;
    public static Animation<TextureRegion> loserPapers;
    public static Animation<TextureRegion> winnerPapers;

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

        writingCharacter = new Animation<TextureRegion>( 0.1f, atlas.findRegions("escribiendo"), Animation.PlayMode.LOOP);
        desk = atlas.createSprite("escritorio");
        typeWriter = atlas.createSprite("maquina_escribir");
        writerNormal = atlas.createSprite("personaje_normal");
        corner = atlas.createSprite("esquina_puntuacion");

        error = new Animation<TextureRegion>(0.05f, atlas.findRegions("error"), Animation.PlayMode.NORMAL);
        loserPapers = new Animation<TextureRegion>(1, atlas.findRegions("pila_papeles_desordenada"), Animation.PlayMode.NORMAL);
        winnerPapers = new Animation<TextureRegion>(1, atlas.findRegions("pila_papeles_ordenada"), Animation.PlayMode.NORMAL);
    }

    public static void dispose() {
        atlas.dispose();
    }
}
