package components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

public class DataLevelsGame {
    public static Array<GameLevel> listGameLevel;

    public static void load() {

        try{
            Json json = new Json();

            if(!Gdx.files.local("data_new.json").exists()){
                listGameLevel = json.fromJson(Array.class, GameLevel.class, Gdx.files.internal("data.json"));

                Gdx.files.internal("data.json").copyTo(Gdx.files.local("data_new.json"));
            }else{
                FileHandle file = Gdx.files.local("data_new.json");
                if(!file.readString().isEmpty())
                    listGameLevel = json.fromJson(Array.class, GameLevel.class, file);
                else{
                    listGameLevel = json.fromJson(Array.class, GameLevel.class, Gdx.files.internal("data.json"));
                    Gdx.files.internal("data.json").copyTo(Gdx.files.local("data_new.json"));
                }
            }

        }catch (NullPointerException e){
            System.out.println("ERROR LOAD FILE");
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
