package Core;

import com.google.gson.Gson;

public class Config {

    private Gson gson = new Gson();


}


class config{
    private String path;

    public String getPath() {
        return path;
    }

    public config(String path) {
        this.path = path;
    }
}
