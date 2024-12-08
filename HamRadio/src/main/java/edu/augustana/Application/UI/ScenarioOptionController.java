package edu.augustana.Application.UI;

import edu.augustana.RadioModel.Practice.UserPreferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;


public class ScenarioOptionController {
    private PopUpManager pop = new PopUpManager();

    @FXML
    void backAction(ActionEvent event) throws IOException {
        App.setRoot("WelcomeScreen");
    }

    @FXML
    void buildSceneAction(ActionEvent event) {

    }

    @FXML
    void helpAction(ActionEvent event) {
        pop.popUpWindow("SceneBuilderHelp", 400, 500, "Instruction");
    }

    @FXML
    void openScenarioAction(ActionEvent event) {
        App.getFileManager().menuActionOpenUserData(event);

    }

    @FXML
    void playScriptedSceneAction(ActionEvent event) {

    }
}
