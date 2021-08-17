package screens.game_screens.game_objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import screens.game_screens.GameScreen;

public class WallObject {

    public WallObject(GameScreen screen, float px) {
        BodyDef bd = new BodyDef();
        bd.position.set(px, 0);
        bd.type = BodyDef.BodyType.StaticBody;

        EdgeShape shape = new EdgeShape();
        shape.set(0, 0, 0, screen.WORD_HEIGHT);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;

        Body body = screen.getWorld().createBody(bd);
        body.createFixture(fixDef);
        shape.dispose();
    }
}
