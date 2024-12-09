package edu.augustana.Application.UI;
import edu.augustana.Application.UIHelper.ScenarioSetSceneBuilder;
import edu.augustana.RadioModel.Practice.SceneBuilderFactory.HamRadioSceneTypeFactory;
import edu.augustana.RadioModel.Practice.SceneBuilderFactory.SceneBuilderFactory;
import edu.augustana.RadioModel.Practice.PracticeScenario;
import edu.augustana.RadioModel.Practice.UserPrefBuilder;
import javafx.fxml.FXML;
import java.io.IOException;
import java.util.List;

import edu.augustana.RadioModel.Practice.UserPreferences;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ScenarioSetScreenController {
    @FXML
    private TextField primaryUserNameTextField;
    @FXML
    private ComboBox<Integer> wpmBox;
    @FXML
    private CheckBox whiteNoiseBox;
    @FXML
    private ComboBox<String> scenarioTypeBox;
    @FXML
    private TextField scenarioNameTextField;

    private String listener;

    UserPreferences prefs = App.getUserPrefs();
    SceneBuilderFactory sceneFactory;

    @FXML
    private void initialize() throws IOException {
        if(!ScenarioOptionController.sceneOptionListener.equalsIgnoreCase("scripted")){
            listener = ScenarioOptionController.sceneOptionListener;
        } else {
            listener = ScriptedScenarioOptionController.scriptedSceneListener;
        }
        ScenarioSetSceneBuilder scenarioSetSceneBuilder =
                new ScenarioSetSceneBuilder(primaryUserNameTextField, wpmBox,
                        whiteNoiseBox, scenarioTypeBox, scenarioNameTextField, listener);
        scenarioSetSceneBuilder.buildUI();
        SceneBuilderFactory builderFactory = new HamRadioSceneTypeFactory(ScenarioOptionController.sceneOptionListener);
    }

    @FXML
    public void backAction() throws IOException {
        App.setRoot("ScenarioOption");
    }

    @FXML
    public void nextAction() throws IOException {
        prefs.setWhiteNoise(whiteNoiseBox.isSelected());
        prefs.setWPM(wpmBox.getValue());
        prefs.setSceneTypeString(scenarioTypeBox.getValue());
        prefs.setPrimaryUserName(primaryUserNameTextField.getText());
        if(listener.equalsIgnoreCase("forrest") | listener.equalsIgnoreCase("detective")){
            App.setRoot("HamPracticeUI");
        } else {
            App.setRoot("ScenarioCustomizeBotScreen");
        }

    }

}