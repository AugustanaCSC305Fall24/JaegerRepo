package edu.augustana.Application.UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;


public class ScenarioOptionController {
    private PopUpManager pop = new PopUpManager();
    public static String sceneOptionListener;

    @FXML
    void backAction(ActionEvent event) throws IOException {
        App.setRoot("WelcomeScreen");
    }

    @FXML
    void buildSceneAction(ActionEvent event) throws IOException {
        sceneOptionListener = "build";
        App.setRoot("ScenarioSetScreen");

    }

    @FXML
    void helpAction(ActionEvent event) {
        pop.popUpWindow("SceneBuilderHelp", 400, 500, "Instruction");
    }

    @FXML
    void openScenarioAction(ActionEvent event) throws IOException{
        sceneOptionListener = "open";
        App.getFileManager().menuActionOpenUserData(event);
        System.out.println(App.getUserPrefs());
        App.setRoot("ScenarioSetScreen");
    }

    @FXML
    void playScriptedSceneAction(ActionEvent event) throws IOException {
        sceneOptionListener = "scripted";
        App.setRoot("ScriptedScenarioOption");

    }
}
