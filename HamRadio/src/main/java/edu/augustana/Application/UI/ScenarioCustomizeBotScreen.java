package edu.augustana.Application.UI;
import edu.augustana.Application.UIHelper.ScenarioSetSceneBuilder;
import edu.augustana.RadioModel.Practice.BotCollections.Bot;
import edu.augustana.RadioModel.Practice.BotCollections.GeminiBirdBot;
import edu.augustana.RadioModel.Practice.PracticeScenario;
import edu.augustana.RadioModel.Practice.UserPreferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

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
            botTypeBox.getItems().add("GeminiBirdWatcher");
            botTypeBox.getSelectionModel().select(0);
            botNameTextField.setText(Bot.getRandomBotNameFromList());

        }
        ScenarioSetSceneBuilder scenarioSetSceneBuilder = new ScenarioSetSceneBuilder(botListView,room);
        scenarioSetSceneBuilder.buildBotCustomizeUI();
    }


    @FXML
    private void randomNameAction() {botNameTextField.setText(Bot.getRandomBotNameFromList());
    }

    @FXML
    private void addBotAction(ActionEvent event) {
        String name = botNameTextField.getText();
        String botType = botTypeBox.getValue();
        Bot newBot;
        switch (botType) {
            case "GeminiBirdWatcher":
                final String systemPromptText = "You are an avid bird watcher in a chatroom.  " +
                        "Respond to other chat users' messages by making bird-related puns or jokes, " +
                        "or telling anecdotes about birds that you've seen." +
                        "LIMIT YOUR RESPONSE WITHIN 5 WORDS";
                newBot = new GeminiBirdBot(name, Color.RED, App.getCurrentPracticeScenerio(), systemPromptText);
                //room.getBotList().add(newBot);
                break;
            default:
                throw new IllegalStateException("Invalid personality type: " + botType);
        }
        App.getCurrentPracticeScenerio().getBotList().add(newBot);
        System.out.println("For testing in RoomBuilder Practice UI: " + newBot + ", Freq: " + newBot.getBotFrequency() + ", Task: " + newBot.getTask());
        System.out.println(room.getTaskList());
        botListView.getItems().add(newBot);
    }

    @FXML
    private void kickSelectedBotAction() {
        Bot botToDelete = botListView.getSelectionModel().getSelectedItem();
        if (botToDelete != null) {
            App.getCurrentPracticeScenerio().getBotList().remove(botToDelete);
            botListView.getItems().remove(botToDelete);
        } else {
            new Alert(Alert.AlertType.WARNING, "Select a bot to kick out first!").show();
        }
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
