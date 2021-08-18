package screens.game_screens.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
    public static Animation<TextureRegion> happyWriter;
    public static Animation<TextureRegion> angryWriter;

    public static Sprite menuModal;
    public static Sprite continueButton;
    public static Sprite quitButton;

    public static Music music1, music3, music4, music5;

    public static Sound sound12, sound13, sound17, sound23, sound27, sound30, sound41, sound52,
            sound60, sound61, sound62, sound63, sound64, sound66, sound67, sound68, sound77;


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
        happyWriter = new Animation<TextureRegion>(0.25f, atlas.findRegions("personaje_festejando"), Animation.PlayMode.LOOP_RANDOM);
        angryWriter = new Animation<TextureRegion>(0.05f, atlas.findRegions("personaje_tirando_hojas"), Animation.PlayMode.LOOP_PINGPONG);

        menuModal = atlas.createSprite("menuPausa");
        continueButton = atlas.createSprite("boton_continue");
        quitButton = atlas.createSprite("boton_salir");

        loadSounds();
    }

    public static void loadSounds() {
        music1 = Gdx.audio.newMusic(Gdx.files.internal("./music&sound/Sound 1.mp3"));
        music3 = Gdx.audio.newMusic(Gdx.files.internal("./music&sound/Sound 3.mp3"));
        music4 = Gdx.audio.newMusic(Gdx.files.internal("./music&sound/Sound 4.mp3"));
        music5 = Gdx.audio.newMusic(Gdx.files.internal("./music&sound/Sound 5.mp3"));

        sound12 = Gdx.audio.newSound(Gdx.files.internal("./music&sound/Sound 12.mp3"));
        sound13 = Gdx.audio.newSound(Gdx.files.internal("./music&sound/Sound 13.mp3"));
        sound17 = Gdx.audio.newSound(Gdx.files.internal("./music&sound/Sound 17.mp3"));
        sound23 = Gdx.audio.newSound(Gdx.files.internal("./music&sound/Sound 23.mp3"));
        sound27 = Gdx.audio.newSound(Gdx.files.internal("./music&sound/Sound 27.mp3"));
        sound30 = Gdx.audio.newSound(Gdx.files.internal("./music&sound/Sound 30.mp3"));
        sound41 = Gdx.audio.newSound(Gdx.files.internal("./music&sound/Sound 41.mp3"));
        sound52 = Gdx.audio.newSound(Gdx.files.internal("./music&sound/Sound 52.mp3"));
        sound60 = Gdx.audio.newSound(Gdx.files.internal("./music&sound/Sound 60.mp3"));
        sound61 = Gdx.audio.newSound(Gdx.files.internal("./music&sound/Sound 61.mp3"));
        sound62 = Gdx.audio.newSound(Gdx.files.internal("./music&sound/Sound 62.mp3"));
        sound63 = Gdx.audio.newSound(Gdx.files.internal("./music&sound/Sound 63.mp3"));
        sound64 = Gdx.audio.newSound(Gdx.files.internal("./music&sound/Sound 64.mp3"));
        sound66 = Gdx.audio.newSound(Gdx.files.internal("./music&sound/Sound 66.mp3"));
        sound67 = Gdx.audio.newSound(Gdx.files.internal("./music&sound/Sound 67.mp3"));
        sound68 = Gdx.audio.newSound(Gdx.files.internal("./music&sound/Sound 68.mp3"));
        sound77 = Gdx.audio.newSound(Gdx.files.internal("./music&sound/Sound 77.mp3"));
    }

    public static void dispose() {
        atlas.dispose();
    }
}
