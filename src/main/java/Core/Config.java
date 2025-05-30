package Core;

import Utils.Constants;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Config {
    private String config_path_folder = "./config";
    private String config_path = "./config/config.json";
    private configs defConfig = new configs(Constants.DB_FILE);

    public Config(){
      File file_to_folder = new File(config_path_folder);
      File fileConfig = new File(config_path);

      if (!file_to_folder.mkdirs()){
          file_to_folder.mkdirs();
      }
      if (!fileConfig.isFile()){
          try {
              fileConfig.createNewFile();
          } catch (IOException e) {
              throw new RuntimeException(e);
          }
      }
      writeConfig();
    }

    private Gson gson = new Gson();

    private void writeConfig(){
        String json = gson.toJson(defConfig);
        if (new File(config_path).isFile()){
            File fileConfig = new File(config_path);
            try {
                FileWriter fw = new FileWriter(fileConfig);
                fw.write(json);
                fw.flush();
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        new Config();
    }


}


class configs {
    private String path;

    public String getPath() {
        return path;
    }

    public configs(String path) {
        this.path = path;
    }
}
