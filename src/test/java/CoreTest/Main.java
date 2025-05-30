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

     //   jsonSQL.addData(a);

        String json = jsonSQL.getObjectByID("c9de5464-91fd-4d40-b27f-6042e464de86","pizdec");
        User b = gson.fromJson(json, User.class);
        try {
            System.out.println(b.getName()+"\n"+Crypto.decrypt(b.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
