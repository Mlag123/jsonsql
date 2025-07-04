package Core;

import Utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static Utils.Constants.*;

public class JsonSQL<T extends Identifiable> {
    private Gson gson = new Gson();
    private String DB_PATH = DB_PATH_FROM_CONFIG;
    private String path = DB_PATH + "/database.json";
    private Class<T> type;
    private File dir = new File(DB_PATH);
    private File file;
    private List<T> cache;
    private boolean isCacheDirty = true;


    /// @param type     Тип объекта, который будет храниться в базе данных
    /// @param fileName кастомное название файла
    public JsonSQL(Class<T> type, String fileName) throws IOException {
        this.type = type;

        if (fileName.isEmpty() | fileName == null) {
            try {
                throw new IllegalArgumentException(fileName + " - is empty, or null, or incorrect");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        this.path = DB_PATH + "/" + fileName + Constants.JSON_TYPE;
        initDir();
    }

    /// @param type Тип объекта, который будет храниться в базе данных

    //use default nameFile;
    public JsonSQL(Class<T> type) throws IOException {
        this.type = type;
        initDir();


    }


    private void initDir() throws IOException {
        file = new File(path);
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

    public synchronized List<T> loadData() {
        List<T> data;

        if (!isCacheDirty && cache != null) {
            return cache;
        }
        if (!file.exists() || file.length() == 0) {
            data = new ArrayList<>();
        } else {
            try (Reader reader = new FileReader(path)) {
                Type dataListType = TypeToken.getParameterized(List.class, type).getType();
                data = gson.fromJson(reader, dataListType);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        cache = data;
        isCacheDirty = false;
        return data;

    }

    //сохраняет данные в файл.
    public synchronized void saveData(List<T> data) {
        try (Writer writer = new FileWriter(path)) {
            gson.toJson(data, writer);
            isCacheDirty = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //добавляет объект в файл.

    public synchronized void addData(T object) {
        requireNonNull(object, "object");
        List<T> data = loadData();
        data.add(object);
        saveData(data);
    }

    //удаляет объект из файла по id.
    public synchronized boolean removeObjectById(String id) {
        List<T> data = loadData();
        boolean remove = data.removeIf(T -> T.getId().equals(id));
        saveData(data);
        return remove;
    }

    //обновляет объект находя его по айди.
    public synchronized boolean updateObject(String id, T newObject) {
        requireNonNull(newObject, "newObject");
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
    public String getObjectByID(String id, String errorMsg) {
        return findAndSerialize(obj -> obj.getId().equals(id), errorMsg);// Сериализуем объект в JSON-строку


    }

    public static void requireNonNull(Object obj, String fieldName) {
        if (obj == null) {
            try {
                throw new IllegalArgumentException(fieldName + " cannot be null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Опционально, возвращает объект по его логину и паролю
    public String getObjectByLoginAndPassoword(String login, String password, String errorMsg) {
        requireNonNull(login, "Login");
        requireNonNull(password, "Password");

        return findAndSerialize(obj -> obj.getLogin().equals(login) && obj.getPassword().equals(password), errorMsg);
    }

    //Опционально, возвращает объект по его логину
    public String getObjectByLogin(String login, String errorMsg) {
        requireNonNull(login, "Login");
        return findAndSerialize(obj -> obj.getLogin().equals(login), errorMsg);
    }

    private String findAndSerialize(Predicate<T> condition, String errorMsg) {
        return loadData().stream()
                .filter(condition)
                .findFirst()
                .map(gson::toJson)
                .orElseThrow(() -> new RuntimeException(errorMsg + " " + condition));
    }


}



