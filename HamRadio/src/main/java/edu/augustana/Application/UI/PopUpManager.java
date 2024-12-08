package edu.augustana.Application.UI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PopUpManager {
    public PopUpManager(){}

    public void popUpWindow(String fxmlFile, int windowWidth, int windowHeight, String title){
        try {
            // Load the FXML file for GameRules
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/augustana/Application/UI/" + fxmlFile + ".fxml"));
            Parent root = fxmlLoader.load();

            // Create a new scene and stage for the window
            Scene scene = new Scene(root, windowWidth, windowHeight);
            Stage gameRulesStage = new Stage();
            gameRulesStage.setScene(scene);

            // Set the title for the new window
            gameRulesStage.setTitle(title);

            // Show the new window
            gameRulesStage.show();
        } catch (IOException ex) {
            System.err.println("Can't find FXML file GameRules.fxml");
            ex.printStackTrace();
        }
    }
}
