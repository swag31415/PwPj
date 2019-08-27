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

public class Utils {

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

    public static Object getFromLocalFile(String fileName)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream oStream;
        Object out = null;

        oStream = new ObjectInputStream(new FileInputStream(new File(fileName)));
        out = oStream.readObject();
        oStream.close();

        return out;
    }

    public static String getInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        scanner.close();
        return input;
    }

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) 
    { 
        // Convert byte array into signum representation  
        BigInteger number = new BigInteger(1, hash);  
  
        // Convert message digest into hex value  
        StringBuilder hexString = new StringBuilder(number.toString(16));  
  
        // Pad with leading zeros 
        while (hexString.length() < 32)  
        {  
            hexString.insert(0, '0');  
        }  
  
        return hexString.toString();  
    } 

    public static String hash(String text) {
        try {
            return toHexString(getSHA(text));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String hash(String text, String salt) {
        return hash(text + salt);
    }
}