package Utils;

import Core.Config;

public class Constants {
    Config c = new Config();

    public final static String ver = "1.0.0_BETA";
    public final static String name = "JSON SQL";
    public final static String utf8 = "UTF-8";
    public final static  String crypto_method = "AES";
    public static final int KEY_LENGTH = 128; // 128, 192 или 256
    public static final int ITERATIONS = 65536;
    public static final String DB_FILE = "./database/database.json";
    public static final String DB_PATH = new Config().getConfigs().getPath();
    public static final String DB_PATH_DEF = "./database";

}
