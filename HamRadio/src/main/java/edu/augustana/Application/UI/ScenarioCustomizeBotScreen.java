package edu.augustana.Application.UI;
import edu.augustana.Application.UIHelper.ScenarioSetSceneBuilder;
import edu.augustana.RadioModel.Practice.BotCollections.Bot;
import edu.augustana.RadioModel.Practice.PracticeScenario;
import edu.augustana.RadioModel.Practice.UserPreferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ScenarioCustomizeBotScreen {
    @FXML
    private TextField botNameTextField;
    @FXML
    private ComboBox<String> botTypeBox;
    @FXML
    private ListView<Bot> botListView;
    PracticeScenario room = App.getCurrentPracticeScenerio();
    UserPreferences prefs = App.getUserPrefs();
    private String listener;

    @FXML
    void initialize(){
        System.out.println(room);
        if(ScenarioOptionController.sceneOptionListener.equalsIgnoreCase("scripted")){
            listener = ScenarioOptionController.sceneOptionListener;
            botNameTextField.setText("Cannot Add Bots...");
            botTypeBox.getItems().add("Cannot Add Bots...");
            botTypeBox.getSelectionModel().select(0);
        } else {
            listener = ScriptedScenarioOptionController.scriptedSceneListener;
            botTypeBox.getItems().add("AI Bots...");

        }
        ScenarioSetSceneBuilder scenarioSetSceneBuilder = new ScenarioSetSceneBuilder(botListView,room);
        scenarioSetSceneBuilder.buildBotCustomizeUI();
    }

    @FXML
    void addBotAction(ActionEvent event) {
    }
    @FXML
    void botListView(ActionEvent event) {
    }

    @FXML
    void kickSelectedBotAction(ActionEvent event) {

    }

    @FXML
    public void backAction() throws IOException {
        room.getBotList().clear();
        App.setRoot("ScenarioSetScreen");
    }

    @FXML
    public void nextAction() throws IOException {
        App.setRoot("HAMPracticeUI");
    }
}
