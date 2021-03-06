package screens.game_screens.game_objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.crazy_writer_game.CrazyWriterGame;
import screens.game_screens.Utils.GameAssets;

public class Writer {
    private CrazyWriterGame game;
    public static Vector2 deskPosition = new Vector2(276, 106);
    public static Vector2 deskSize = new Vector2(144, 83);
    public static Sprite deskSprite = GameAssets.desk;

    public static Vector2 typeWriterPosition = new Vector2(311, 185);
    public static Vector2 typeWriterSize = new Vector2(74, 36);
    public static Sprite typeWriterSprite = GameAssets.typeWriter;

    public static Vector2 writingCharacterPosition = new Vector2(295, 209);
    public static Vector2 writingCharacterSize = new Vector2(108, 24);
    public static Animation<TextureRegion> writingCharacter = GameAssets.writingCharacter;

    public static Animation<TextureRegion> happyWriterCharacter = GameAssets.happyWriter;
    public static Animation<TextureRegion> angryWriterCharacter = GameAssets.angryWriter;

    public static Vector2 writerNormalPosition = new Vector2(228, 140);
    public static Vector2 writerNormalSize = new Vector2(238, 218);
    public static Sprite writerNormalSprite = GameAssets.writerNormal;

    public static short NORMAL_STATE = 0;
    public static short CRY_STATE = 1;
    public static short ANGRY_STATE = 2;
    public static short HAPPY_STATE = 3;

    float stateTime;
    float stateTimeWriter = 0;
    float state;
    public static float timeToFinish = 0;

    public Writer(CrazyWriterGame game) {
        this.game = game;
        state = NORMAL_STATE;

        deskSprite.setPosition(deskPosition.x, deskPosition.y);
        deskSprite.setSize(deskSize.x, deskSize.y);

        typeWriterSprite.setPosition(typeWriterPosition.x, typeWriterPosition.y);
        typeWriterSprite.setSize(typeWriterSize.x, typeWriterSize.y);
        typeWriterSprite.setScale(1.2f);

        writerNormalSprite.setPosition(writerNormalPosition.x, writerNormalPosition.y);
        writerNormalSprite.setSize(writerNormalSize.x, writerNormalSize.y);
    }

    public void draw() {
        deskSprite.draw(game.batch);

        if(state == NORMAL_STATE) {
            writerNormalSprite.draw(game.batch);
            game.batch.draw(writingCharacter.getKeyFrame(this.stateTime),
                    writingCharacterPosition.x,
                    writingCharacterPosition.y,
                    0.f,
                    0.0f,
                    writingCharacterSize.x,
                    writingCharacterSize.y,
                    1, 1, 0
            );
        }

        if(state == HAPPY_STATE) {
            game.batch.draw(happyWriterCharacter.getKeyFrame(this.stateTimeWriter),
                    writerNormalPosition.x,
                    writerNormalPosition.y,
                    0f,
                    0f,
                    writerNormalSize.x,
                    writerNormalSize.y,
                    1, 1, 0
            );
        }

        if(state == ANGRY_STATE) {
            game.batch.draw(angryWriterCharacter.getKeyFrame(this.stateTimeWriter),
                    writerNormalPosition.x,
                    writerNormalPosition.y,
                    0f,
                    0f,
                    writerNormalSize.x,
                    writerNormalSize.y,
                    1, 1, 0
            );
        }

        if(state == HAPPY_STATE || state == ANGRY_STATE) {
            game.batch.draw(writingCharacter.getKeyFrame(5.39f),
                    writingCharacterPosition.x,
                    writingCharacterPosition.y,
                    0.f,
                    0.0f,
                    writingCharacterSize.x,
                    writingCharacterSize.y,
                    1, 1, 0
            );
        }

        typeWriterSprite.draw(game.batch);
    }

    public void update(float delta) {
        stateTime +=delta;
        stateTimeWriter += delta;

        if(state == HAPPY_STATE || state == ANGRY_STATE)
            timeToFinish += delta;
    }

    public void changeState(short state){ this.state = state; stateTimeWriter =0;}
}
