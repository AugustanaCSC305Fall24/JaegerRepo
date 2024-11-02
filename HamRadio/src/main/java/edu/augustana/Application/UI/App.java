package edu.augustana.Application.UI;

import edu.augustana.RadioModel.Practice.PracticeScenerio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static List<PracticeScenerio> practiceScenerioList = new ArrayList<>();
    private static int practiceIndex = -1;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(new BorderPane(), 900, 480);
        practiceScenerioList.add(new PracticeScenerio());
        practiceIndex = 0;
        stage.setScene(scene);
        switchToMainView();
        stage.show();
    }

    public static List<PracticeScenerio> getPracticeScenerioList(){
        return practiceScenerioList;
    }

    public static PracticeScenerio getCurrentPracticeScenerio(){
        if (practiceIndex >= 0 && practiceIndex < practiceScenerioList.size()) {
            return practiceScenerioList.get(practiceIndex);
        } else {
            throw new IllegalStateException("No current/valid practice scenerio!");
        }
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
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/augustana/Application/UI/" + fxmlFileName));
            scene.setRoot(fxmlLoader.load());
        } catch (IOException ex) {
            System.err.println("Can't find FXML file " + fxmlFileName);
            ex.printStackTrace();
        }

    }

    public static void switchToMainView() {
        switchToView("WelcomeScreen.fxml");
    }


    public static void main(String[] args) {
        launch();
    }

}