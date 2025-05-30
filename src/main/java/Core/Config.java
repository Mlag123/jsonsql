package Core;

import Utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;


public class Config {

    private final String config_path_folder = "./config";
    private final String config_path_file = "./config/configSQL.json";
    private Configures defConfig = new Configures(Constants.DB_PATH_DEF);
    private File dir;
    private File file;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Configures configs;

    public Configures getConfigs() {
        return configs;
    }

    public Config() {
        initFolders();
        configs = loadConfig();
    }

    //создает папки и файл конфигурации.
    private void initFolders() {
        dir = new File(config_path_folder);
        file = new File(config_path_file);

        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
                writeConfig();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //записывает в файл дефолтный конфиг.
    private void writeConfig() {
        if (!file.exists()) {
            throw new RuntimeException("Config folder is not exists");
        }


        String json = gson.toJson(defConfig);

        File fileConfig = new File(config_path_file);
        try {
            FileWriter fw = new FileWriter(fileConfig);
            fw.write(json);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //загружает из файла конфиг.
    private Configures loadConfig() {
        try (Reader reader = new FileReader(config_path_file)) {
            return gson.fromJson(reader, Configures.class);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }



}



