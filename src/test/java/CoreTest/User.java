package CoreTest;

import Core.Identifiable;
import Core.SerializatedObject;

public class User extends SerializatedObject {


    //testCODE!!!!
    String name,surname,id,login,password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public User(String name, String surname, String login, String password, String id) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String getId() {
        return id;
    }
}
