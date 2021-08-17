package screens.game_screens;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import screens.game_screens.game_objects.WordObject;

public class PowerEvents {

    public static boolean FIRE_POWER_ACTIVATED = false;
    public static boolean ICE_POWER_ACTIVATED = false;
    public static boolean WIND_POWER_ACTIVATED = false;
    public static boolean SLOW_POWER_ACTIVATED = false;
    public float timeIceActivated = 0;
    public float timeSlowActivated = 0;

    private World world;
    private GameScreen game;

    public PowerEvents(World world, GameScreen game)
    {
        this.world = world;
        this.game = game;
    }

    public void firePowerActivate() {
        for(Body body: game.arrBodies) {
            if(body.getUserData() instanceof WordObject){
                WordObject obj = (WordObject) body.getUserData();
                if(obj.getType() != WordObject.LIBRARY_POWER_TYPE) {
                    game.arrWordObjects.removeValue(obj, true);
                    world.destroyBody(body);
                }
            }
        }
        game.powersQueue--;
        FIRE_POWER_ACTIVATED = false;
    }

    public void icePowerActivate(float delta) {

        if(ICE_POWER_ACTIVATED && timeIceActivated ==0)
            game.powersQueue--;

        if(ICE_POWER_ACTIVATED) {
            timeIceActivated += delta;
            world.setGravity(new Vector2(0,0));
            game.GENERATION_WORDS_ACTIVE = false;

            for(WordObject word : game.arrWordObjects)
                if(word.getType() != WordObject.LIBRARY_POWER_TYPE )
                    word.getBody().setLinearVelocity(0f, 0f);
        }

        if(timeIceActivated >= 5f && ICE_POWER_ACTIVATED) {
            world.setGravity(new Vector2(0,game.gravity.y));
            ICE_POWER_ACTIVATED = false;
            game.GENERATION_WORDS_ACTIVE = true;
            timeIceActivated=0;

            for(WordObject word : game.arrWordObjects)
                word.getBody().setLinearVelocity(0f, -0.01f);
        }
    }

    public void slowPowerActivate(float delta) {

        if(timeSlowActivated == 0 && SLOW_POWER_ACTIVATED)
            game.powersQueue--;

        if(SLOW_POWER_ACTIVATED) {
            timeSlowActivated += delta;

            world.setGravity(new Vector2(0,game.gravity.y/2));

            for(WordObject word : game.arrWordObjects)
                if(word.getType() != WordObject.LIBRARY_POWER_TYPE)
                    word.getBody().setLinearVelocity(0f, game.gravity.y/2);

            System.out.println(world.getGravity().y);
        }

        if(timeSlowActivated >= 5f && SLOW_POWER_ACTIVATED) {
            world.setGravity(new Vector2(0,game.gravity.y));
            SLOW_POWER_ACTIVATED = false;
            timeSlowActivated = 0;

            for(WordObject word : game.arrWordObjects)
                word.getBody().setLinearVelocity(0f, -0.1f);
        }
    }

    public void windPowerActivate() {
        game.lostWords = 0;
        game.powersQueue--;
        WIND_POWER_ACTIVATED = false;
    }
}
