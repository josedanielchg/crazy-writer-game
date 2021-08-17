package screens.game_screens.game_objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.crazy_writer_game.CrazyWriterGame;
import screens.game_screens.Utils.GameAssets;
import screens.game_screens.Utils.GameUtils;

public class UserStringInput {
    //Needed to calculate the width of the string and build the word container
    float PADDING_BETWEEN_WORDS = 0.00f;
    static final float WIDTH = 0.15f;
    static final float HEIGHT = 0.25f;
    float DISTANCE_FROM_CENTER;
    float SCREEN_CENTER;
    boolean LETTER_ADDED = false;
    boolean LETTER_REMOVED = false;
    char LETTER_TO_ADD;
    boolean DELETE_LOOKED = true;
    boolean REMOVE_ALL = false;

    float lastDelete=0f;
    String userInput;
    Array<UserLetter> letters;
    CrazyWriterGame game;
    World world;
    Body borderRight, borderLeft;
    Vector2 positionBorderRight, positionBorderLeft;
    float positionLeft, positionRight;
    Vector2 size;

    int position=0;
    float positionLetter =0f;

    public UserStringInput(float screenCenter, World world, CrazyWriterGame game){
        //Set word and get its width in pixels
        this.game = game;
        this.letters = new Array<>();
        SCREEN_CENTER = screenCenter;
        userInput = "";

        size = new Vector2(WIDTH, HEIGHT);
        //Origin position
        positionRight = SCREEN_CENTER + WIDTH + PADDING_BETWEEN_WORDS/2;
        positionLeft = SCREEN_CENTER - WIDTH - PADDING_BETWEEN_WORDS/2;
        positionBorderLeft = new Vector2(SCREEN_CENTER - WIDTH - PADDING_BETWEEN_WORDS/2, 0.4f);
        positionBorderRight = new Vector2(SCREEN_CENTER + WIDTH + PADDING_BETWEEN_WORDS/2, 0.4f);

        this.world = world;
        borderRight = GameUtils.createBody(GameUtils.KINEMATIC_BODY, size, positionBorderRight, world, this);
        borderLeft = GameUtils.createBody(GameUtils.KINEMATIC_BODY, size, positionBorderLeft, world, this);
    }

    public void update() {
        if (LETTER_ADDED) {
            userInput += Character.toString(LETTER_TO_ADD);
            UserLetter letterObj = new UserLetter(6f, world, LETTER_TO_ADD, game);
            letters.add(letterObj);
            LETTER_ADDED = false;
        }

        if (letters.size > 0) {
            DISTANCE_FROM_CENTER = (this.WIDTH) +
                    (PADDING_BETWEEN_WORDS) +
                    ((letters.size - 1) * PADDING_BETWEEN_WORDS /2) +
                    (UserLetter.WIDTH) +
                    ((letters.size - 1) * UserLetter.WIDTH);

            this.positionLeft = SCREEN_CENTER - DISTANCE_FROM_CENTER;
            this.positionRight = SCREEN_CENTER + DISTANCE_FROM_CENTER;
            borderLeft.setTransform(positionLeft, positionBorderLeft.y, borderLeft.getAngle());
            borderRight.setTransform(positionRight, positionBorderRight.y, borderLeft.getAngle());

            position = 0;
            for (UserLetter letter : letters) {
                positionLetter = positionLeft
                        + this.WIDTH
                        + ((position + 1) * PADDING_BETWEEN_WORDS)
                        + (2 * position * letter.WIDTH)
                        + letter.WIDTH;

                letter.setPosition(positionLetter);
                position++;
            }
        }

        if(LETTER_REMOVED) {
            Body body = letters.get(letters.size-1).getBd();
            world.destroyBody(body);
            letters.removeIndex(letters.size-1);
            userInput = userInput.substring(0, userInput.length()-1);
            LETTER_REMOVED = false;

            if(letters.size == 0) initialPosition();
        }

        if(REMOVE_ALL) {
            REMOVE_ALL = false;
            userInput = "";
            for (UserLetter letter: letters) {
                if(world.isLocked()) continue;
                Body body = letter.getBd();
                world.destroyBody(body);
            }
            letters.clear();
            initialPosition();
        }
    }

    public void initialPosition() {
        positionRight = SCREEN_CENTER + WIDTH + PADDING_BETWEEN_WORDS/2;
        positionLeft = SCREEN_CENTER - WIDTH - PADDING_BETWEEN_WORDS/2;

        borderLeft.setTransform(SCREEN_CENTER - WIDTH - PADDING_BETWEEN_WORDS/2, positionBorderLeft.y, borderLeft.getAngle());
        borderRight.setTransform(SCREEN_CENTER + WIDTH + PADDING_BETWEEN_WORDS/2, positionBorderRight.y, borderLeft.getAngle());
    }

    public void draw() {
        Sprite spriteCursorRight = GameAssets.cursorRight;
        spriteCursorRight.setPosition(positionRight*100-15, positionBorderRight.y*100/2);
        spriteCursorRight.setSize(30, 47);

        Sprite spriteCursorLeft = GameAssets.cursorLeft;
        spriteCursorLeft.setPosition(positionLeft*100-15, positionBorderLeft.y*100/2);
        spriteCursorLeft.setSize(30, 47);

        spriteCursorRight.draw(game.batch);
        spriteCursorLeft.draw(game.batch);

        for (UserLetter letter: letters) {
            letter.draw(game);
        }
    }

    public void addLetter(char letter) {
        if(letters.size<=15)
        {
            LETTER_ADDED = true;
            LETTER_TO_ADD = Character.toUpperCase(letter);
        }
    }

    public void removeLetter() {
        if(letters.size > 0)
            LETTER_REMOVED = true;
    }

    public void removeLetter(float delta) {
        if(DELETE_LOOKED) {
           lastDelete += delta;
           if(lastDelete >= 0.15f) DELETE_LOOKED = false;
        }

        if(letters.size > 0 && !DELETE_LOOKED) {
            DELETE_LOOKED = true;
            lastDelete = 0;
            LETTER_REMOVED = true;
        }
    }

    public void removeAllLetters() {
        REMOVE_ALL = true;
    }

    public Vector2 getSize() { return size; }

    public String getUserInput() { return  userInput;}
}

