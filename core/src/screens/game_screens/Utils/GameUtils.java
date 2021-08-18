package screens.game_screens.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class GameUtils {

    public static short DYNAMIC_BODY = 0;
    public static short KINEMATIC_BODY = 1;
    public static short STATIC_BODY = 2;
    public static int WORDS_OF_4_LETTERS_OR_LESS = 0;
    public static int WORDS_OF_5_LETTERS_OR_LESS = 1;
    public static int WORDS_OF_6_LETTERS_OR_LESS = 3;
    public static int WORDS_OF_7_LETTERS_OR_LESS = 4;
    public static int WORDS_OF_15_LETTERS_OR_MORE = 5;

    public static Body createBody(short type, Vector2 size, Vector2 position, World world, Object obj) {

        BodyDef bd = new BodyDef();
        bd.position.set(position.x, position.y);

        if (type == DYNAMIC_BODY)
            bd.type = BodyDef.BodyType.DynamicBody;
        else if (type == STATIC_BODY)
            bd.type = BodyDef.BodyType.StaticBody;
        else if (type == KINEMATIC_BODY)
            bd.type = BodyDef.BodyType.KinematicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x, size.y);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;
        fixDef.density = 15;
        fixDef.restitution = .1f;

        Body body = world.createBody(bd);
        body.createFixture(fixDef);
        shape.dispose();
        body.setUserData(obj);
        return body;
    }

    public static Array<String> getWords(int nLetters) {

        JsonValue data = new JsonReader().parse(Gdx.files.internal("wordsList.json")).get("lessThanOrEqualsTo4");
        Array<String> listWords = new Array<>();

        if (nLetters >= WORDS_OF_4_LETTERS_OR_LESS) {
            for (int i = 0; i < data.size; i++)
                listWords.add(data.get(i).toString());

            if (nLetters >= WORDS_OF_5_LETTERS_OR_LESS) {
                for (int i = 0; i < data.size; i++)
                    listWords.add(data.get(i).toString());

                if (nLetters >= WORDS_OF_6_LETTERS_OR_LESS) {
                    for (int i = 0; i < data.size; i++)
                        listWords.add(data.get(i).toString());

                    if (nLetters >= WORDS_OF_7_LETTERS_OR_LESS) {
                        for (int i = 0; i < data.size; i++)
                            listWords.add(data.get(i).toString());

                        if (nLetters >= WORDS_OF_15_LETTERS_OR_MORE) {
                            for (int i = 0; i < data.size; i++)
                                listWords.add(data.get(i).toString());
                        }
                    }
                }
            }
        }
        return listWords;
    }
}