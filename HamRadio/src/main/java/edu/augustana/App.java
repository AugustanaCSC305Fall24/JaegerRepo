package edu.augustana;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(new BorderPane(), 640, 480);
        stage.setScene(scene);
        switchToMainView();
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private static void switchToView(String fxmlFileName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFileName));
            scene.setRoot(fxmlLoader.load());
        } catch (IOException ex) {
            System.err.println("Can't find FXML file " + fxmlFileName);
            ex.printStackTrace();
        }

    }

    public static void switchToMainView() {
        switchToView("WelcomeScreen.fxml");
    }

    public static void switchToRegisterView() {
        switchToView("WelcomeScreen.fxml");
    }

    public static void switchToLogInView() {
        switchToView("WelcomeScreen.fxml");
    }

    public static void main(String[] args) {
        launch();
    }

}