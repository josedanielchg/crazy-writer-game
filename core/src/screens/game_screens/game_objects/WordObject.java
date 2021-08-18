package screens.game_screens.game_objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.crazy_writer_game.CrazyWriterGame;
import screens.game_screens.Utils.GameAssets;
import screens.game_screens.Utils.GameUtils;

public class WordObject {

    //Needed to calculate the width of the string and build the word container
    float HORIZONTAL_PADDING = 0.4f;
    float HEIGHT = 0.11f;
    float VERTICAL_PADDING = HEIGHT/2;
    float POWER_WIDTH = 0.42f;
    GlyphLayout layout;

    //Constants to Powers
    public static short NORMAL_TYPE = 0;
    public static short POWER_TYPE = 2;
    public static short LIBRARY_POWER_TYPE = 1;
    public static short FIRE_POWER = 0;
    public static short ICE_POWER = 1;
    public static short SLOW_POWER = 2;
    public static short WIND_POWER = 3;
    

    //Constants of Collision State
    public static final short STATE_NORMAL = 0;
    public static final short STATE_HIT_FLOOR = 1;
    public static final short STATE_HIT_WALL = 2;
    
    short state;
    short type;
    short powerType;
    World world;
    Body body;
    Vector2 position;
    Vector2 size;
    String text;
    float sizeX=0;


    public WordObject(float px, float py, World world, String text, CrazyWriterGame game, short type){
        state = STATE_NORMAL;

        this.type = type;
        this.text = text.toUpperCase();

        if(type == POWER_TYPE) {
            int power = MathUtils.random(0, 3);
            if(power == 0) this.powerType = FIRE_POWER;
            if(power == 1) this.powerType = ICE_POWER;
            if(power == 2) this.powerType = SLOW_POWER;
            if(power == 3) this.powerType = WIND_POWER;
        }

        layout = new GlyphLayout(game.font, this.text);
        sizeX = (layout.width/100)+ HORIZONTAL_PADDING;

        //Origin position
        position = new Vector2(px, py);
        size = new Vector2(sizeX/2, HEIGHT);

        this.world = world;
        body = GameUtils.createBody(GameUtils.DYNAMIC_BODY, size, position, world, this);
    }

    public WordObject(World world, String text, CrazyWriterGame game, short type){
        if(type == LIBRARY_POWER_TYPE) {
            this.type = type;
            this.text = text.toUpperCase();
            layout = new GlyphLayout(game.font, this.text);
            position = new Vector2(6.5f, 3.60f);
            size = new Vector2(POWER_WIDTH, HEIGHT);
        }

        this.world = world;
        body = GameUtils.createBody(GameUtils.DYNAMIC_BODY, size, position, world, this);
        body.applyLinearImpulse(new Vector2(0f, -4f), body.getWorldCenter(), true);
    }


    public void update() {
        position.x = body.getPosition().x;
        position.y = body.getPosition().y;
    }

    public void draw(CrazyWriterGame game) {
        selectSprite(game);
        float x = 0f, y = 0f;

        if(this.type != LIBRARY_POWER_TYPE) {
            x = (position.x-(size.x)+ HORIZONTAL_PADDING /2)*100;
            y = (position.y+(size.y)-VERTICAL_PADDING)*100;
        }
        else if(this.type == LIBRARY_POWER_TYPE) {
            x = (position.x*100)-layout.width/2;
            y = (position.y*100)+layout.height/2;
        }
        game.font.setColor(0, 0, 0, 1);
        game.font.draw(game.batch, text, x, y);
        game.font.setColor(0.80f, 0.80f, 0.80f, 1);
    }

    private void selectSprite(CrazyWriterGame game) {
        Sprite sprite = GameAssets.normal_word;
        float keyframeX = position.x*100-size.x*100;
        float keyframeY = position.y*100-size.y*100-VERTICAL_PADDING*100/2;
        float sizeX = layout.width+HORIZONTAL_PADDING*100;
        float sizeY = size.y*200;

        if(type == POWER_TYPE) {
            if(powerType == FIRE_POWER) sprite = GameAssets.fire_word;
            if(powerType == ICE_POWER) sprite = GameAssets.ice_word;
            if(powerType == SLOW_POWER) sprite = GameAssets.slow_word;
            if(powerType == WIND_POWER) sprite = GameAssets.wind_word;
        }

        if(type == LIBRARY_POWER_TYPE) {
            if(this.text.compareTo("FIRE")==0) sprite = GameAssets.fire_word;
            if(this.text.compareTo("ICE")==0) sprite = GameAssets.ice_word;
            if(this.text.compareTo("SLOW")==0) sprite = GameAssets.slow_word;
            if(this.text.compareTo("WIND")==0) sprite = GameAssets.wind_word;
            sizeX = POWER_WIDTH*200;
        }
        sprite.setPosition(keyframeX, keyframeY);
        sprite.setSize(sizeX, sizeY);
        sprite.draw(game.batch);
    }

    public Body getBody() { return body; }

    public short getState() { return state; }

    public short getType() { return type; }

    public short getPowerType() { return powerType; }

    public void hitWall() { this.state = STATE_HIT_WALL; }

    public void hitFloor() { this.state = STATE_HIT_FLOOR; }

    public String getText() {return this.text;}
}
