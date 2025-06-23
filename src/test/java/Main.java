import Core.JsonSQL;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            User a = new User("aaa","aasdasd","asdasd");
            JsonSQL<User> jsonSQL = new JsonSQL<>(User.class);
            Map<String,User> maps = new HashMap<>();
            maps.put("IDS",a);
            jsonSQL.addData(maps);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
