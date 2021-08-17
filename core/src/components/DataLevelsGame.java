package components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.SerializationException;

import java.io.FileNotFoundException;

public class DataLevelsGame {
    public static Array<GameLevel> listGameLevel;

    public static void load() {
        try{
            Json json = new Json();
            FileHandle from;
            from = Gdx.files.internal("data.json");

            if(Gdx.files.local("data_new.json").exists())
                from = Gdx.files.internal("data_new.json");

            listGameLevel = json.fromJson(Array.class, GameLevel.class, from);
            from.copyTo(Gdx.files.local("data_new.json"));
        }catch (NullPointerException e){
            System.out.println("FALLO Y PASO AL CATCH");
            Json js = new Json();
            listGameLevel = js.fromJson(Array.class, GameLevel.class, Gdx.files.internal("data.json"));
            Gdx.files.local("data_new.json").copyTo( Gdx.files.local("data_new.json") );
        }
    }

    public static void save(){
       Json json = new Json();
       json.setOutputType(JsonWriter.OutputType.json);
       String data = json.prettyPrint(listGameLevel);
       FileHandle file = Gdx.files.local("data_new.json");
       file.writeString(data, false);
    }
}
