package edu.augustana.Application.UI;

import edu.augustana.Application.UIHelper.KeyBindManager;
import edu.augustana.RadioModel.Practice.PracticeScenerio;
import edu.augustana.RadioModel.Practice.UserPreferences;
import edu.augustana.RadioModel.SoundPlayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {
    private static App app;
    private static Scene scene;
    private static List<PracticeScenerio> practiceScenerioList = new ArrayList<>();
    private static int practiceIndex = -1;
    private static final KeyBindManager keyBindManager = new KeyBindManager();
    private UserPreferences userPrefs = UserPreferences.loadFromJSONFile(UserPreferences.DEFAULT_USER_PREFERENCES_FILE);
    private int hello = 1;

    private static App returnApp(){return app;}
    public static UserPreferences getUserPrefs() {
        return app.userPrefs;
    }

    @Override
    public void start(Stage stage) throws IOException {
        App.app = this;
        System.out.println("App: Debug in App for App.... " + this);
        System.out.println("App: Debug in App for App.... " + app);
        scene = new Scene(new BorderPane(), 900, 480);
        practiceScenerioList.add(new PracticeScenerio());
        practiceIndex = 0;
        stage.setScene(scene);
        System.out.println("UserPref: Debug in App for App.... " + userPrefs);
        System.out.println("UserPref: Debug in App for App.app...." + app.userPrefs);
        switchToMainView();

        // Set the key event handlers for both key presses and releases
        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyBindManager::handleKeyPress);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, keyBindManager::handleKeyRelease);

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

    public static KeyBindManager getKeyBindManager() {
        return keyBindManager;
    }

    public static void switchToMainView() {
        switchToView("WelcomeScreen.fxml");
    }

    public static void main(String[] args) {
        launch();
    }

}

// idea: set into onPress, which is the function that calls another function that handle its own thread contains a loop of infinitely many sound play of dot/dash
//onPress: first, compute the distance to the last string, then repeatitively add more dots/dash in at a multiple of a
//period of time call unitOfTime. This approach may increase the whole responsiveness of the sound playing module
//onRelease: cut the condition
// two keys

//UI wise: any action that results in a continuous infinite loops has to be put in its own thread.
//Handle server-client connection

//multiple scene...
