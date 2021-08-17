package screens.game_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.crazy_writer_game.CrazyWriterGame;
import screens.Screens;
import screens.game_screens.Utils.GameAssets;
import screens.game_screens.Utils.GameUtils;
import screens.game_screens.game_objects.*;

public class GameScreen extends Screens {

    private final float SPAWN_WORD_RATE_IN_SECONDS;
    private boolean CHECK_MATCH_WORD_INPUT = false;
    public static boolean GENERATION_WORDS_ACTIVE = true;

    private final Box2DDebugRenderer renderer;
    private final World world;
    public final Vector2 gravity;
    public final Array<Body> arrBodies;
    public final Array<WordObject> arrWordObjects;
    private long score=0;
    private long lastDropTime;
    public short lostWords=0;
    public short lostWordsScore=0;
    private short writeWords=0;
    public int powersQueue = 0;
    UserStringInput userStringInput;
    Array<String> listWords;
    PowerEvents powerEvents;

    public static short BOX_GENERATION_ERROR_WALL = 1;
    public static short BOX_GENERATION_SUCCESS = 0;
    private short generationWordState = BOX_GENERATION_SUCCESS;

    //Constructor
    public GameScreen(CrazyWriterGame game, int Level, float spawn_rate_seconds, float gravity, int lost_words_to_lose, int write_words_to_win, int maximum_letters_per_word) {
        super(game);
        SPAWN_WORD_RATE_IN_SECONDS = spawn_rate_seconds;

        GameAssets.load();

        listWords = GameUtils.getWords(GameUtils.WORDS_OF_4_LETTERS_OR_LESS);

        this.gravity = new Vector2(0, gravity);
        world = new World(this.gravity, true);
        renderer = new Box2DDebugRenderer();

        arrBodies = new Array<>();
        arrWordObjects = new Array<>();

        FloorObject floor = new FloorObject(this, 0f, WORD_HEIGHT-5.5f);
        WallObject leftWall = new WallObject(this, 1.02f);
        WallObject rightWall = new WallObject(this, 5.93f);
        WallObject lastWall = new WallObject(this, WORD_WIDTH);
        FloorObject bottomShelf = new FloorObject(this, 5.93f, WORD_HEIGHT-3.6f);
        FloorObject topShelf = new FloorObject(this, 5.93f, WORD_HEIGHT-2.54f);

        world.setContactListener(new CollisionListener(this));

        userStringInput = new UserStringInput(WORD_WIDTH/2, world, game);
        powerEvents = new PowerEvents(world, this);
    }

    //Draw in every FPS
    @Override
    public void draw(float delta) {

        //Draw CameraUI
        game.batch.begin();
        GameAssets.background.draw(game.batch);

        game.font.draw(game.batch, "Fps: "+ Gdx.graphics.getFramesPerSecond(), 4, 20);
        game.font.draw(game.batch, "Lost Words: "+ lostWords, 4, 35);
        game.font.draw(game.batch, "Write Words: "+ writeWords, 4, 50);
        game.font.draw(game.batch, "Score: "+ score, 4, 65);

        world.getBodies(arrBodies);
        for(Body body:arrBodies) {
            if(body.getUserData() instanceof WordObject) {
                WordObject obj = (WordObject) body.getUserData();
                obj.draw(game);
            }
        }
        userStringInput.draw();
        game.batch.end( );

        //Draw CameraBox2D
        game.batch.begin();
        cameraBox2D.update();
        //renderer.render(world, cameraBox2D.combined);
        game.batch.end();
    }


    //Calculate game physics (position, velocity, etc.) in every FPS
    @Override
    public void update(float delta) {
        if(Gdx.input.isTouched())  {
            createWord();
        }

        world.step(delta, 8, 6);
        world.getBodies(arrBodies);

        //Spawn a new Word
        if(GENERATION_WORDS_ACTIVE && TimeUtils.nanoTime() - lastDropTime > (SPAWN_WORD_RATE_IN_SECONDS * Math.pow(10, 9)))
            createWord();

        //After press Enter or Space, this function check if the userInput is equals to any word
        if(CHECK_MATCH_WORD_INPUT) checkWordExist();

        //CheckPowerEvents
        if(PowerEvents.FIRE_POWER_ACTIVATED) powerEvents.firePowerActivate();
        else if(PowerEvents.ICE_POWER_ACTIVATED) powerEvents.icePowerActivate(delta);
        else if(PowerEvents.WIND_POWER_ACTIVATED) powerEvents.windPowerActivate();
        else if(PowerEvents.SLOW_POWER_ACTIVATED) powerEvents.slowPowerActivate(delta);

        //Check if a Word has touched a Wall or the Floor
        for(Body body: arrBodies) {
            if (world.isLocked()) continue;
            if(body.getUserData() instanceof WordObject){
                WordObject obj = (WordObject) body.getUserData();

                if(obj.getState() == WordObject.STATE_HIT_FLOOR || obj.getState() == WordObject.STATE_HIT_WALL) {
                    arrWordObjects.removeValue(obj, true);
                    world.destroyBody(body);
                    if(obj.getState() == WordObject.STATE_HIT_FLOOR) {
                        score -= 100;
                        lostWords++;
                        lostWordsScore++;
                    }
                }
            }
        }

        if(generationWordState == BOX_GENERATION_ERROR_WALL)
            createWord();

        for(Body body:arrBodies) {
            if(body.getUserData() instanceof WordObject) {
                WordObject obj = (WordObject) body.getUserData();
                obj.update();
            }
        }

        //Check if a letter should be added or removed in the input
        if(Gdx.input.isKeyPressed(Input.Keys.DEL)) {
            userStringInput.removeLetter(delta);
        }
        userStringInput.update();
    }


    //Function that creates a Word Object and adds it to Word each time it is called
    public void createWord() {

        short wordType = MathUtils.randomBoolean(0.5f) ? WordObject.POWER_TYPE : WordObject.NORMAL_TYPE;
        String text = listWords.get(MathUtils.random(0, listWords.size-1));

        WordObject word = new WordObject(MathUtils.random(1.02f, 5.93f),WORD_HEIGHT+0.15f, world, text, game, wordType);
        arrWordObjects.add(word);
        this.generationWordState = BOX_GENERATION_SUCCESS;
        lastDropTime = TimeUtils.nanoTime();
    }


    public void checkWordExist() {
        CHECK_MATCH_WORD_INPUT = false;
        boolean coincidence = false;

        for(Body body: arrBodies) {
            if(body.getUserData() instanceof WordObject){

                WordObject word = (WordObject) body.getUserData();
                if(word.getText().compareTo(userStringInput.getUserInput())==0)
                {
                    arrWordObjects.removeValue(word, true);
                    world.destroyBody(body);
                    coincidence = true;
                    score +=200;
                    writeWords++;

                    if(word.getType() == WordObject.POWER_TYPE && powersQueue < 4){
                        WordObject wordPower;
                        powersQueue++;

                        if(word.getPowerType() == WordObject.FIRE_POWER)
                            wordPower = new WordObject(world, "FIRE", game, WordObject.LIBRARY_POWER_TYPE);

                        if(word.getPowerType() == WordObject.ICE_POWER)
                            wordPower = new WordObject(world, "ICE", game, WordObject.LIBRARY_POWER_TYPE);

                        if(word.getPowerType() == WordObject.SLOW_POWER)
                            wordPower = new WordObject(world, "SLOW", game, WordObject.LIBRARY_POWER_TYPE);

                        if(word.getPowerType() == WordObject.WIND_POWER)
                            wordPower = new WordObject(world, "WIND", game, WordObject.LIBRARY_POWER_TYPE);
                    }
                    break;
                }
            }
        }

        //Check if the word is a Power
        if(coincidence) {
            if (userStringInput.getUserInput().equals("FIRE"))
                PowerEvents.FIRE_POWER_ACTIVATED = true;

            if (userStringInput.getUserInput().equals("ICE"))
                PowerEvents.ICE_POWER_ACTIVATED = true;

            if (userStringInput.getUserInput().equals("SLOW"))
                PowerEvents.SLOW_POWER_ACTIVATED = true;

            if (userStringInput.getUserInput().equals("WIND"))
                PowerEvents.WIND_POWER_ACTIVATED = true;
        }

        //User wrote wrong word
        if(!coincidence) {
            Array<Body> wrongWords = new Array<>();
            int maxMatchLetters = 0;
            int iterationLetter;
            String auxString;
            char[] userLetters = userStringInput.getUserInput().toCharArray();
            score -= 50;

            for (Body body: arrBodies) {
                if(body.getUserData() instanceof WordObject) {
                    WordObject word = (WordObject) body.getUserData();
                    iterationLetter=0;
                    auxString = "";
                    do {
                        auxString += userLetters[iterationLetter];
                        if(word.getText().startsWith(auxString) && iterationLetter >= maxMatchLetters) {
                            if(iterationLetter > maxMatchLetters) {
                                maxMatchLetters = iterationLetter;
                                wrongWords.clear();
                            }
                            wrongWords.add(body);
                        }
                        iterationLetter++;
                    }while (iterationLetter<userLetters.length);
                }
            }

            for (Body wrongWord: wrongWords) {
                wrongWord.applyLinearImpulse(new Vector2(0f, -0.25f), wrongWord.getWorldCenter(), true);
            }
        }
        userStringInput.removeAllLetters();
    }


    public void wordGenerationError() { this.generationWordState = BOX_GENERATION_ERROR_WALL; }


    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.DEL)
            userStringInput.removeLetter();

        if(keycode == Input.Keys.ENTER && userStringInput.getUserInput().length() > 0)
            CHECK_MATCH_WORD_INPUT = true;

        if(keycode == Input.Keys.SPACE && userStringInput.getUserInput().length() > 0)
            CHECK_MATCH_WORD_INPUT = true;

        return super.keyDown(keycode);
    }


    @Override
    public boolean keyTyped(char character) {
        int key = Input.Keys.valueOf(Character.toString(character).toUpperCase());

        if(key >=Input.Keys.A && key <= Input.Keys.Z)
            userStringInput.addLetter(character);

        return super.keyTyped(character);
    }


    public World getWorld() {
        return world;
    }
}