import Core.Identifiable;

public class User implements Identifiable {

    private String id, password, login;

    public User(String id, String password, String login) {
        this.id = id;
        this.password = password;
        this.login = login;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getLogin() {
        return login;
    }
}
