package screens.game_screens.game_objects;

import com.badlogic.gdx.physics.box2d.*;
import screens.game_screens.GameScreen;

public class FloorObject {
    public FloorObject(GameScreen screen, float px, float py) {
        BodyDef bd = new BodyDef();
        bd.position.set(px, py);
        bd.type = BodyDef.BodyType.StaticBody;

        EdgeShape shape = new EdgeShape();
        shape.set(0, 0, screen.WORD_WIDTH, 0);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;

        Body body = screen.getWorld().createBody(bd);
        body.createFixture(fixDef);
        body.setUserData(this);
        shape.dispose();
    }
}
