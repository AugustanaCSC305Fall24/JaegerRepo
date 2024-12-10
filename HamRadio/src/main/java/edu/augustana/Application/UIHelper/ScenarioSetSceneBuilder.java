package edu.augustana.Application.UIHelper;

import edu.augustana.Application.UI.App;
import edu.augustana.RadioModel.Practice.PracticeScenario;
import edu.augustana.RadioModel.Practice.UserPreferences;
import edu.augustana.RadioModel.Practice.BotCollections.Bot;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.util.*;

public class ScenarioSetSceneBuilder {
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
    @FXML
    private ListView<Bot> botListView;

    private String listener;
    UserPreferences prefs;
    PracticeScenario room;

    public ScenarioSetSceneBuilder(TextField primaryUserNameTextField,
                                   ComboBox<Integer> wpmBox, CheckBox whiteNoiseBox,
                                   ComboBox<String> scenarioTypeBox,
                                   TextField scenarioNameTextField,
                                   String listener){
        this.primaryUserNameTextField = primaryUserNameTextField;
        this.wpmBox = wpmBox;
        this.whiteNoiseBox = whiteNoiseBox;
        this.scenarioTypeBox = scenarioTypeBox;
        this.scenarioNameTextField = scenarioNameTextField;
        this.listener = listener;
        prefs = App.getUserPrefs();
    }

    public ScenarioSetSceneBuilder(ListView<Bot> botListView, PracticeScenario room){
        this.room = room;
        this.botListView = botListView;
        prefs = App.getUserPrefs();
    }

    public void buildBotCustomizeUI(){
        botListView.getItems().addAll(room.getBotList());
        System.out.println(room.getBotList());
    }

    public void buildUI(){
        primaryUserNameTextField.setText(prefs.getPrimaryUserName());
        wpmBox.setValue(prefs.getWPM());
        whiteNoiseBox.setSelected(prefs.getWhiteNoise());
        if(listener.equalsIgnoreCase("detective")){
            scenarioTypeBox.getItems().addAll("Detective America");
            buildUIForDetective();
        } else if (listener.equals("forrest")){
            scenarioTypeBox.getItems().addAll("Forest Burning");
            buildUIForForrest();
        } else {
        scenarioTypeBox.getItems().addAll("Build Yours", "Open yours",
                "Detective America", "Forrest Burning");
        scenarioNameTextField.setText("Your Fancy HAM Radio");
        if(listener.equalsIgnoreCase("build")){
            buildUIForBuild();
        } else if (listener.equals("open")){
            buildUIForOpened();
        }

        }
        wpmBox.getItems().addAll(5, 10, 15, 20,25, 30);
    }

    private void buildUIForBuild() {
        scenarioTypeBox.getSelectionModel().select(0);
    }

    public void buildUIForDetective(){
        scenarioTypeBox.getSelectionModel().select(0);
        scenarioNameTextField.setText("Your Detective Is Here");
    }

    private void buildUIForForrest() {
        scenarioTypeBox.getSelectionModel().select(0);
        scenarioNameTextField.setText("Help save the forest");
    }

    private void buildUIForOpened(){
        scenarioTypeBox.getSelectionModel().select(1);

    }


}
