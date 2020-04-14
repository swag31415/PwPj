package PwPj;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * A class used to encrypt and decrypt data using a SecretKey
 * 
 * @author swag31415
 */
public class Enc {

    private SecretKeyFactory skf;
    private Cipher cipher;
    SecretKey key;
    private String DESede = "DESede";

    public Enc() throws Exception {
        skf = SecretKeyFactory.getInstance(DESede);
        cipher = Cipher.getInstance(DESede);
    }

    /**
     * turns the String @param key into a SecretKey and @return the SecretKey
     */
    public SecretKey getKey(String key) {
        int len = key.length();
        if (len < 24) {
            for (int i = 0; i < (24 - len); i++) {
                key += "0";
            }
        }

        try {
            return skf.generateSecret(new DESedeKeySpec(key.getBytes(StandardCharsets.UTF_8)));
        } catch (InvalidKeyException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Encrypts the given @param unencryptedString with the key @param key
     * and @return a byte array
     */
    public byte[] encrypt(String unencryptedString, SecretKey key) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        try {
            return cipher.doFinal(unencryptedString.getBytes(StandardCharsets.UTF_8));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Decrypts the byte array @param encryptedText using the key @param key
     * and @return the decrypted String
     */
    public String decrypt(byte[] encryptedText, SecretKey key) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        try {
            return new String(cipher.doFinal(encryptedText));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}