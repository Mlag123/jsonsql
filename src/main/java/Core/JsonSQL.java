package Core;

import com.google.gson.Gson;

/*
      Хранение должно быть такое.
      ключ:объект
      к примеру
      id:user
      2265:emil
 */
public class JsonSQL<T> {
    private T _class;
    private Gson gson = new Gson();

    public JsonSQL(T object) {


           String json = gson.toJson(object);// fixme важно проверить, что T - действительно является классом.


        System.out.println(json);

    }


}


class User{

    private String name;

    public String getName() {
        return name;
    }

    public User(String name) {
        this.name = name;
    }
}
