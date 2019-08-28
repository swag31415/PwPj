package PwPj;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.crypto.SecretKey;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {

    @FXML PasswordField PwBox;

    Enc encoder;
    String passwordHash;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        encoder = new Enc();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("Splash.fxml"));
        AnchorPane pane = loader.<AnchorPane>load();

        primaryStage.setScene(new Scene(pane));
        primaryStage.show();
    }
    
    @FXML private void TestPassword(ActionEvent event) {
        String hash = Utils.hash(PwBox.getText());

        try {
            passwordHash = (String) Utils.getFromLocalFile("megapw.pwpj");
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Password file could not be loaded");
        }
        
        if (passwordHash == null) {
            passwordHash = hash;
            Utils.printToLocalFile("megapw.pwpj", hash);
        } else if (passwordHash.equals(hash)) {
            System.exit(0);
        } else {
            System.out.println("Try Again?");
        }
    }
}
