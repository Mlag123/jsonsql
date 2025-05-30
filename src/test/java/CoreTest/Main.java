package CoreTest;

import Core.Crypto.Crypto;
import Core.JsonSQL;
import Utils.Util;
import com.google.gson.Gson;

import java.io.IOException;
import java.rmi.server.UID;

public class Main {

    public static void main(String[] args) throws IOException {
        JsonSQL jsonSQL = new JsonSQL(User.class);
        Gson gson = new Gson();
        User a = new User("amama","mumumu","mlag", Crypto.encrypt("Pophop900"), Util.getRandID());

        jsonSQL.addData(a);

        String json = jsonSQL.getObjectByLogin("mlag");
        User b = gson.fromJson(json, User.class);
        try {
            System.out.println(b.getName()+"\n"+Crypto.decrypt(b.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
