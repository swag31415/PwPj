package PwPj;

import javax.crypto.SecretKey;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Enc enc = new Enc();
        SecretKey key = enc.getKey("Big Ass Bacon");
        byte[] crypt = enc.encrypt("Potato", key);
        System.out.println(new String(crypt));
        System.out.println(enc.decrypt(crypt, key));
        System.exit(0);
	}
}
