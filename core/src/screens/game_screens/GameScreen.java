package screens.game_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.crazy_writer_game.CrazyWriterGame;
import screens.GameOverScreen;
import screens.LevelCompleteScreen;
import screens.LevelMenuScreen;
import screens.Screens;
import screens.game_screens.Utils.GameAssets;
import screens.game_screens.Utils.GameUtils;
import screens.game_screens.game_objects.*;

public class GameScreen extends Screens {

    private final float SPAWN_WORD_RATE_IN_SECONDS;
    private final float LOST_WORDS_TO_LOSE;
    private final float WRITE_WORDS_TO_WIN;
    private boolean CHECK_MATCH_WORD_INPUT = false;
    public boolean GENERATION_WORDS_ACTIVE = true;
    public boolean GAME_PAUSE = false;
    private boolean GAME_WON = false;
    private boolean GAME_LOST = false;
    private int LEVEL;
    private int STARS;

    private Box2DDebugRenderer renderer;
    private World world;
    public final Vector2 gravity;
    public Array<Body> arrBodies;
    public Array<WordObject> arrWordObjects;
    private long score=0;
    private long lastDropTime;
    public short lostWords=0;
    public short lostWordsScore=0;
    private short writeWords=0;
    public int powersQueue = 0;
    UserStringInput userStringInput;
    Array<String> listWords;
    PowerEvents powerEvents;
    Writer writer;
    PauseMenu pauseMenu;
    Music backgroundMusic;
    public short BOX_GENERATION_ERROR_WALL = 1;
    public short BOX_GENERATION_SUCCESS = 0;
    private short generationWordState = BOX_GENERATION_SUCCESS;

    //Constructor
    public GameScreen(CrazyWriterGame game, int level, float spawn_rate_seconds, float gravity, int lost_words_to_lose, int write_words_to_win, int maximum_letters_per_word) {
        super(game);
        game.backgroundMusic.stop();
        LEVEL = level;
        SPAWN_WORD_RATE_IN_SECONDS = spawn_rate_seconds;
        WRITE_WORDS_TO_WIN = write_words_to_win;
        LOST_WORDS_TO_LOSE = lost_words_to_lose;
        this.gravity = new Vector2(0, gravity);
        listWords = GameUtils.getWords(maximum_letters_per_word);
    }

    @Override
    public void show() {
        super.show();

        Array<Music> posibleMusicTheme = new Array<>();
        posibleMusicTheme.add(GameAssets.music1);
        posibleMusicTheme.add(GameAssets.music3);
        posibleMusicTheme.add(GameAssets.music4);
        posibleMusicTheme.add(GameAssets.music5);

        int randomIndex = MathUtils.random(0, posibleMusicTheme.size-1);

        backgroundMusic = posibleMusicTheme.get(randomIndex);
        backgroundMusic.setVolume(0.2f);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

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

        writer = new Writer(game);
        pauseMenu = new PauseMenu();

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
        writer.draw();
        Score.draw(game, writeWords, score);

        game.font.draw(game.batch, "Fps: "+ Gdx.graphics.getFramesPerSecond(), 4, 20);
        Papers.draw(lostWords, writeWords, lostWords/LOST_WORDS_TO_LOSE, writeWords/WRITE_WORDS_TO_WIN, game);

        world.getBodies(arrBodies);
        for(Body body:arrBodies) {
            if(body.getUserData() instanceof WordObject) {
                WordObject obj = (WordObject) body.getUserData();
                obj.draw(game);
            }
        }
        userStringInput.draw();
        pauseMenu.draw(game);

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
        world.step(delta, 8, 6);
        world.getBodies(arrBodies);

        if(!GAME_PAUSE) {
            //Spawn a new Word
            if(GENERATION_WORDS_ACTIVE && TimeUtils.nanoTime() - lastDropTime > (SPAWN_WORD_RATE_IN_SECONDS * Math.pow(10, 9)) && !GAME_WON && !GAME_LOST)
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
                            if(score >=100)
                                score -= 100;
                            GameAssets.sound17.setVolume(GameAssets.sound17.play(), 1.5f);
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
            userStringInput.update(delta);

            writer.update(delta);
            checkVictory();
        }
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


                    if(word.getType() == WordObject.NORMAL_TYPE)
                        GameAssets.sound77.play();

                    if(word.getType() != WordObject.LIBRARY_POWER_TYPE)
                        writeWords++;

                    if(word.getType() == WordObject.POWER_TYPE)
                        GameAssets.sound30.play();

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
            GameAssets.sound41.setVolume(GameAssets.sound41.play(), 0.5f);
            userStringInput.wrongWord();
            Array<Body> wrongWords = new Array<>();
            int maxMatchLetters = 0;
            int iterationLetter;
            String auxString;
            char[] userLetters = userStringInput.getUserInput().toCharArray();

            if(score >= 50)
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
                wrongWord.applyLinearImpulse(new Vector2(0f, gravity.y*5), wrongWord.getWorldCenter(), true);
            }
        }
        userStringInput.removeAllLetters();
    }


    public void wordGenerationError() { this.generationWordState = BOX_GENERATION_ERROR_WALL; }


    public void checkVictory() {
        if(writeWords==WRITE_WORDS_TO_WIN && !GAME_WON) {
            backgroundMusic.stop();
            GameAssets.sound23.play();
            GENERATION_WORDS_ACTIVE = false;
            GAME_WON = true;
            PowerEvents.FIRE_POWER_ACTIVATED = true;
            writer.changeState(Writer.HAPPY_STATE);
        }

        if(lostWords == LOST_WORDS_TO_LOSE && !GAME_LOST) {
            backgroundMusic.stop();
            GameAssets.sound13.play();
            GENERATION_WORDS_ACTIVE = false;
            GAME_LOST = true;
            PowerEvents.FIRE_POWER_ACTIVATED = true;
            writer.changeState(Writer.ANGRY_STATE);
        }

        if(GAME_LOST && Writer.timeToFinish >=4) {
            this.backgroundMusic.stop();
            game.setScreen(new GameOverScreen(game, LEVEL));
        }

        if(GAME_WON && Writer.timeToFinish >=4) {
            int stars;
            if(lostWords/LOST_WORDS_TO_LOSE <= 0.2f)
                stars = 3;
            else if(lostWords/LOST_WORDS_TO_LOSE <= 0.6)
                stars = 2;
            else
                stars = 1;

            this.backgroundMusic.stop();
            game.backgroundMusic.play();
            game.setScreen(new LevelCompleteScreen(game, LEVEL, stars));
            this.dispose();
        }

    }

    //Pause menu
    public void showModal() {
        GAME_PAUSE = true;
        PauseMenu.GAME_IN_PAUSE = true;
        //Stop
        world.setGravity(new Vector2(0,0));
        GENERATION_WORDS_ACTIVE = false;

        for(WordObject word : arrWordObjects)
            if(word.getType() != WordObject.LIBRARY_POWER_TYPE )
                word.getBody().setLinearVelocity(0f, 0f);
    }

    //Hide menu
    public void hideMenu() {
        GAME_PAUSE = false;
        PauseMenu.GAME_IN_PAUSE = false;
        //Stop
        world.setGravity(new Vector2(0,gravity.y));
        GENERATION_WORDS_ACTIVE = true;

        for(WordObject word : arrWordObjects)
            if(word.getType() != WordObject.LIBRARY_POWER_TYPE )
                word.getBody().setLinearVelocity(0f, gravity.y*2);
    }


    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.DEL) {
            GameAssets.sound52.setVolume(GameAssets.sound52.play(), 2f);
            userStringInput.removeLetter();
        }


        if(keycode == Input.Keys.ENTER && userStringInput.getUserInput().length() > 0)
            CHECK_MATCH_WORD_INPUT = true;

        if(keycode == Input.Keys.SPACE && userStringInput.getUserInput().length() > 0)
            CHECK_MATCH_WORD_INPUT = true;

        if(keycode == Input.Keys.ESCAPE) {
            showModal();
            GameAssets.load();
        }

        return super.keyDown(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        int key = Input.Keys.valueOf(Character.toString(character).toUpperCase());

        if(key >=Input.Keys.A && key <= Input.Keys.Z) {
            GameAssets.sound12.setVolume(GameAssets.sound12.play(), 2f);
            userStringInput.addLetter(character);
        }

        return super.keyTyped(character);
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(button != Input.Buttons.LEFT || pointer > 0) return false;
        if(!GAME_PAUSE) return false;

        screenY = (int)SCREEN_HEIGHT - screenY;

        if((screenX >= PauseMenu.continueButtonPosition.x && screenX <= (PauseMenu.continueButtonPosition.x+PauseMenu.continueButtonSize.x))
            && (screenY >= PauseMenu.continueButtonPosition.y && screenY <= (PauseMenu.continueButtonPosition.y+PauseMenu.continueButtonSize.y))        )
        {
            GameAssets.sound12.setVolume(GameAssets.sound12.play(), 3f);
            hideMenu();
        }

        if((screenX >= PauseMenu.quitButtonPosition.x && screenX <= (PauseMenu.quitButtonPosition.x+PauseMenu.quitButtonSize.x))
            && (screenY >= PauseMenu.quitButtonPosition.y && screenY <= (PauseMenu.quitButtonPosition.y+PauseMenu.quitButtonSize.y)))
        {
            GameAssets.sound12.setVolume(GameAssets.sound12.play(), 3f);
            PauseMenu.GAME_IN_PAUSE = false;
            this.backgroundMusic.stop();
            game.backgroundMusic.play();
            game.setScreen(new LevelMenuScreen(game));
            this.dispose();
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {
        renderer.dispose();
        world.dispose();
        super.dispose();
    }
}