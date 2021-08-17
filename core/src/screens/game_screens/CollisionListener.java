package screens.game_screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.*;
import screens.game_screens.game_objects.FloorObject;
import screens.game_screens.game_objects.WordObject;

public class CollisionListener implements ContactListener {

    GameScreen gameScreen;
    boolean flag = true;

    public CollisionListener(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void beginContact(Contact contact) {
        flag = false;
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        //Check if the Word is not a Library Power Word
        if(bodyA.getUserData() instanceof WordObject)
            if(((WordObject)bodyA.getUserData()).getType() != WordObject.LIBRARY_POWER_TYPE) flag = true;

        if(bodyB.getUserData() instanceof WordObject)
            if(((WordObject)bodyB.getUserData()).getType() != WordObject.LIBRARY_POWER_TYPE) flag = true;

        //Change State to the Word that will be eliminated in next update cycle

        //Means that a word has touched the Floor
        if(flag) {
            if(bodyA.getUserData() instanceof FloorObject || bodyB.getUserData() instanceof FloorObject) {
                if(bodyA.getUserData() instanceof FloorObject) ((WordObject)bodyB.getUserData()).hitFloor();
                else ((WordObject)bodyA.getUserData()).hitFloor();
            }
            //Means that a word has touched a Wall
            else if(bodyA.getUserData()==null || bodyB.getUserData()==null){
                gameScreen.wordGenerationError();
                if(bodyA.getUserData()==null) ((WordObject)bodyB.getUserData()).hitWall();
                else ((WordObject)bodyA.getUserData()).hitWall();
            }
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if(bodyA.getUserData() instanceof WordObject && bodyB.getUserData() instanceof WordObject) {
            //Means that two words are touching each other
            if(((WordObject)bodyA.getUserData()).getType() != WordObject.LIBRARY_POWER_TYPE)
                contact.setEnabled(false);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
