package Core;

import Core.Crypto.Crypto;
import Utils.Constants;
import Utils.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static Utils.Constants.DB_FILE;

/*
      Хранение должно быть такое.
      ключ:объект
      к примеру
      id:user
      2265:emil
 */
public class JsonSQL<T extends Identifiable> {
    private Gson gson = new Gson();
    private String path = "./database";
    private Class<T> type;
    private File dir = new File(path);
    private File file = new File(DB_FILE);


    public JsonSQL(Class<T> type) throws IOException {


        this.type = type;
        //создания папки и файла.
        if (!dir.exists()) {
            dir.mkdir();
        }


        if (!file.exists()) {
            file.createNewFile();
            saveData(new ArrayList<>());
        }


    }

    //выгружает данные из файла.

    public List<T> loadData() {

        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>(); // Возвращаем пустой список
        }


        try (Reader reader = new FileReader(DB_FILE)) {
            Type dataListType = TypeToken.getParameterized(ArrayList.class, type).getType();
            return gson.fromJson(reader, dataListType);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //сохраняет данные в файл.
    public void saveData(List<T> data) {
        try (Writer writer = new FileWriter(Constants.DB_FILE)) {
            gson.toJson(data, writer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //добавляет объект в файл.

    public void addData(T object) {
        if (object == null) throw new IllegalArgumentException("Object cannot be null");
        List<T> data = loadData();
        data.add(object);
        saveData(data);
    }

    //удаляет объект из файла по id.
    public boolean removeObjectById(String id) {
        List<T> data = loadData();
        boolean remove = data.removeIf(T -> T.getId().equals(id));
        saveData(data);
        return remove;
    }

    //обновляет объект находя его по айди.
    public boolean updateObject(String id, T newObject) {
        if (newObject == null) throw new IllegalArgumentException("Object cannot be null");

        List<T> data = loadData();

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(id)) {
                data.set(i, newObject);
                saveData(data);
                return true;
            }
        }
        return false;
    }

    //возвращает объект по айди.
    public String getObjectByID(String id) {
        List<T> data = loadData();
        return data.stream()
                .filter(obj -> obj.getId().equals(id))
                .findFirst()
                .map(gson::toJson)
                .orElseThrow();// Сериализуем объект в JSON-строку


    }

    //Опционально, возвращает объект по его логину и паролю
    public String getObjectByLoginAndPassowrd(String login, String password) {
        List<T> data = loadData();

        return data.stream().filter(obj -> obj.getLogin().equals(login) && obj.getPassword().equals(password))
                .findFirst()
                .map(gson::toJson)
                .orElseThrow();
    }

    //Опционально, возвращает объект по его логину
    public String getObjectByLogin(String login) {
        List<T> data = loadData();

        return data.stream().filter(obj -> obj.getLogin().equals(login))
                .findFirst()
                .map(gson::toJson)
                .orElseThrow();
    }


}



