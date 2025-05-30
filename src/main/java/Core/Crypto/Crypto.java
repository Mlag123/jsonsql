package Core.Crypto;

import Utils.Constants;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;

import static Utils.Constants.ITERATIONS;
import static Utils.Constants.KEY_LENGTH;

public class Crypto {
    private String key;
    private MessageDigest messageDigest;
    private static final byte[] SALT = "RandomSalt123".getBytes();
    private static final String ENCRYPTION_KEY = System.getenv("ENCRYPTION_KEY");

    private static void initKey() {
        if (ENCRYPTION_KEY == null) {
            throw new RuntimeException("The ENCRYPTION_KEY is not specified in the variables");
        }
    }

    //шифрует
    public static String encrypt(String data) {
        initKey();
        try {


            SecretKeySpec secrKey = generateKey(ENCRYPTION_KEY);
            Cipher cipher = Cipher.getInstance(Constants.crypto_method);
            cipher.init(Cipher.ENCRYPT_MODE, secrKey);
            byte[] encrByte = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrByte);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //расшифровывает
    public static String decrypt(String encryptedData) throws Exception {
        SecretKeySpec secretKey = generateKey(ENCRYPTION_KEY);
        Cipher cipher = Cipher.getInstance(Constants.crypto_method);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    private static SecretKeySpec generateKey(String password) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                SALT,
                ITERATIONS,
                KEY_LENGTH
        );
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), Constants.crypto_method);
    }


    @Deprecated
    private static byte[] getValidKey(String key) {
        byte[] keyBytes = key.getBytes();
        int keyLength = 16;

        if (keyBytes.length == keyLength) {
            return keyBytes;
        }

        byte[] validKey = new byte[keyLength];
        System.arraycopy(
                keyBytes, 0,
                validKey, 0,
                Math.min(keyBytes.length, keyLength)
        );
        return validKey;
    }


}
