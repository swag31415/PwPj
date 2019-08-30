package PwPj;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.crypto.SecretKey;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {

    @FXML
    private PasswordField PwBox;
    @FXML
    private AnchorPane splashPane;
    @FXML
    private TextField titleBox;
    @FXML
    private TextField entryBox;
    @FXML
    private Accordion bankPane;

    private static HashMap<byte[], byte[]> data;
    private static Enc encoder;
    private static byte[] encryptedPw;

    private static final String sequence = "c+=2a^L&z6&Yz6sBh@B5E#zn7BYKb7H-5-Z+cT4r@887hVATbWpnWbuh2VDH$Cx-";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        data = new HashMap<byte[], byte[]>();
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

    @FXML
    private void TestPassword(ActionEvent event) {
        String hash = null;
        try {
            hash = (String) Utils.getFromLocalFile("megapw.pwpj");
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Password file could not be loaded");
        }

        if (hash == null) {
            Utils.printToLocalFile("megapw.pwpj", Utils.hash(PwBox.getText()));
        } else if (Utils.hash(PwBox.getText()).equals(hash)) {
            encryptedPw = encoder.encrypt(sequence, encoder.getKey(PwBox.getText()));
            try {
                data = (HashMap<byte[], byte[]>) Utils.getFromLocalFile("data.pwpj");
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
            setView("Bank.fxml");

        } else {
            System.out.println("Try Again?");
        }
    }

    @FXML
    void addEntry(ActionEvent event) {
        String title = titleBox.getText();
        String text = entryBox.getText();

        if (title == "") {
            titleBox.requestFocus();
        } else {
            bankPane.getPanes().add(new TitledPane(title, new Label(text)));
            data.put(encoder.encrypt(title, encoder.getKey(new String(encryptedPw))), encoder.encrypt(text, encoder.getKey(new String(encryptedPw))));

            Utils.printToLocalFile("data.pwpj", data);

            titleBox.setText("");
            entryBox.setText("");
            titleBox.requestFocus();
        }
    }

    @FXML
    void addEntryTitle(ActionEvent event) {
        entryBox.requestFocus();
    }

    public void setView(String fxmlFile) {
        Parent blah = null;
        try {
            blah = FXMLLoader.load(getClass().getClassLoader().getResource(fxmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadPanes(blah);

        Scene scene = new Scene(blah);
        Stage appStage = (Stage) splashPane.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    private void loadPanes(Parent blah) {
        SecretKey key = encoder.getKey(new String(encryptedPw));
        for (Entry<byte[], byte[]> entry : data.entrySet()) {
            ((Accordion) blah).getPanes().add(new TitledPane(encoder.decrypt(entry.getKey(), key),
                    new Label(encoder.decrypt(entry.getValue(), key))));
        }
    }
}
