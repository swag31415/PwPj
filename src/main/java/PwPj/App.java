package PwPj;

import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public String getInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        scanner.close();
        return input;
    }
}
