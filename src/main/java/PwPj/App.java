package PwPj;

import java.io.IOException;
import java.net.URL;

import javax.sound.midi.ControllerEventListener;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage pStage;
    private static App instance;

    public enum fxmlFiles {
        Splash("SplashGUI.fxml");

        private String file;

        private fxmlFiles(String file) {
            this.file = file;
        }

        public String get() {
            return this.file;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        App.pStage = primaryStage;

        primaryStage.setResizable(false);

        App.loadScene(fxmlFiles.Splash, App.pStage);
        primaryStage.show();
    }

    @Override
    public void init() {
        App.instance = this;
    }

    public static <T extends ControllerEventListener> T loadScene(fxmlFiles file, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.getResource(file.get()));
        Pane pane = loader.<Pane>load();
        stage.setScene(new Scene(pane));
        return loader.getController();
    }

    public static <T extends Pane> T getFxml(fxmlFiles file) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.getResource(file.get()));
        return loader.<T>load();
    }

    public static URL getResource(String file) {
        return App.instance.getClass().getClassLoader().getResource(file);
    }
}