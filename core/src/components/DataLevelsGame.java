package components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;

import java.io.FileNotFoundException;

public class DataLevelsGame {
    public static Array<GameLevel> listGameLevel;

    public static void load() {
        try{
            FileHandle file, from;
            from = Gdx.files.internal("data.json");


            file = Gdx.files.local("data_new.json");
            if(file.exists()){
                from = Gdx.files.internal("data_new.json");
            }

            Json json = new Json();
            listGameLevel = json.fromJson(Array.class, GameLevel.class, from);
            from.copyTo(Gdx.files.local("data_new.json"));
        }catch (NullPointerException e){
            Json js = new Json();
            listGameLevel = js.fromJson(Array.class, GameLevel.class, Gdx.files.internal("data.json"));
            Gdx.files.local("data_new.json").copyTo( Gdx.files.local("data_new.json"));
        }
    }

    public static void save(){
        Json json = new Json();
        json.toJson(listGameLevel, Array.class, GameLevel.class, Gdx.files.local("data_new.json"));
    }
}
