package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.crazy_writer_game.CrazyWriterGame;

public class GameScreen extends Screens{

    Box2DDebugRenderer renderer;
    World world;
    Vector2 gravity = new Vector2();

    public GameScreen(CrazyWriterGame game) {
        super(game);

        Vector2 gravity = new Vector2(0, -9.8f);
        world = new World(gravity, true);
        renderer = new Box2DDebugRenderer();
        createWord();
    }

    private void createWord() {
        BodyDef bd = new BodyDef();
        bd.position.set(3.48f, 6.32f);
        bd.type = BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(.30f, .1f);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;

        Body body = world.createBody(bd);
        body.createFixture(fixDef);
    }

    @Override
    public void draw(float delta) {
        game.batch.begin();
        game.font.draw(game.batch, "Fps: "+ Gdx.graphics.getFramesPerSecond(), 5, 15);
        game.batch.end();

        game.batch.begin();
        cameraBox2D.update();
        renderer.render(world, cameraBox2D.combined);
        game.batch.end();
    }

    @Override
    public void update(float delta) {
        world.step(delta, 8, 6);
    }
}