package PwPj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Random static methods I couldn't place elsewhere
 * 
 * @author swag31415
 */
public class Utils {

    /**
     * Prints an object @param print to file @param fileName
     */
    public static void printToLocalFile(String fileName, Serializable print) {
        ObjectOutputStream oStream;
        try {
            oStream = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
            oStream.writeObject(print);
            oStream.flush();
            oStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads an object from the file @param fileName (perferably one created through
     * the method Utils.printToLocalFile(String, Serializable))
     */
    public static Object getFromLocalFile(String fileName)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream oStream;
        Object out = null;

        oStream = new ObjectInputStream(new FileInputStream(new File(fileName)));
        out = oStream.readObject();
        oStream.close();

        return out;
    }

    /**
     * Gets input using a Scanner and System.in
     */
    public static String getInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        scanner.close();
        return input;
    }

    /**
     * Does the sha256 hash on @param input and @return it
     */
    public static byte[] getSHA(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) { // This isn't going to happen
            e.printStackTrace();
            md = null;
        }
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Turns the @param hash into a string and @return
     */
    public static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    /**
     * Hashes @param text and @return as string
     */
    public static String hash(String text) {
        return toHexString(getSHA(text));
    }

    /**
     * Hashes @param text with salt @param salt and @return as String
     */
    public static String hash(String text, String salt) {
        return hash(text + salt);
    }
}