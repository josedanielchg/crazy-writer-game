package screens.game_screens.game_objects;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.crazy_writer_game.CrazyWriterGame;
import screens.game_screens.Utils.GameUtils;

public class UserLetter {
    //Needed to calculate the width of the string and build the word container
    float PADDING = 0.12f;
    static final float WIDTH = 0.132f;
    static final float HEIGHT = 0.137f;
    float PADDING_TOP;
    float PADDING_LEFT;
    GlyphLayout layout;

    World world;
    Body body;
    Vector2 position;
    Vector2 size;
    char letter;

    public UserLetter(float px, World world, char letter, CrazyWriterGame game){
        //Set word and get its width in pixels
        this.letter = Character.toUpperCase(letter);
        layout = new GlyphLayout(game.font, Character.toString(letter));
        PADDING_TOP = layout.width/200;
        PADDING_LEFT = layout.height/200;

        //Origin position
        position = new Vector2(px, 0.4f);
        size = new Vector2(WIDTH, HEIGHT);

        this.world = world;
        body = GameUtils.createBody(GameUtils.KINEMATIC_BODY, size, position, world, this);
    }

    public void setPosition(float x) {
        body.setTransform(x, position.y, body.getAngle());
        position.x = x;
    }

    public void draw(CrazyWriterGame game) {
        float x = (position.x-PADDING_TOP)*100;
        float y = (position.y+PADDING_LEFT)*100;
        game.font.draw(game.batch, Character.toString(letter), x, y);
    }

    public Body getBd() { return body; }

    public Vector2 getPosition() { return position; }

    public Vector2 getSize() { return size; }

}
