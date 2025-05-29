package Core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/*
      Хранение должно быть такое.
      ключ:объект
      к примеру
      id:user
      2265:emil
 */
public class JsonSQL<T> {
    private T _class;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String DB_FILE = "./database/database.json";
    private String path = "./database";

    public JsonSQL() throws IOException {

        File a = new File(path);
        if (!a.isDirectory()){
            a.mkdir();
        }
        File b = new File(DB_FILE);

        if(!b.isFile()){
            a.createNewFile();
        }


    }

    public void saveBase(List<T> base){
        try (Writer writer = new FileWriter(DB_FILE)){

            gson.toJson(base,writer);
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public List<T> loadBase(){
    
    }


}



