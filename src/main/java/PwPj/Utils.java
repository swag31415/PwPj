package PwPj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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
}