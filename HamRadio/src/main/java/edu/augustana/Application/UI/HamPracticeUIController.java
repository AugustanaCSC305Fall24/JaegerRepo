package edu.augustana.Application.UI;

import edu.augustana.Application.UIHelper.MorseCodeHandlerManager;
import edu.augustana.Application.UIHelper.MorseCodePlayer;
import edu.augustana.Application.UIHelper.MorseCodeTranslator;
import edu.augustana.RadioModel.HamRadioSimulator;
import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import edu.augustana.RadioModel.Practice.Bot;
import edu.augustana.RadioModel.Practice.PracticeScenerio;
import edu.augustana.RadioModel.Practice.TaskForPractice;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class HamPracticeUIController extends HamUIController {
    Scene scene;
    Stage stage;
    HamRadioSimulatorInterface radio;
    PracticeScenerio room;
    private String userOutput = "";
    private String cleanMorse = "";
    private boolean isStartClicked = false;
    private String statusConnect = " Not Connected";
    private boolean isPushedToTalk = false;
    private boolean isEnglishOn = false;
    private static final double DEFAULT_MIN_FREQ = 7000;
    private static final double DEFAULT_MAX_FREQ = 7067;
    private static final double DEFAULT_TUNE = 1.0;
    public static final int TONE = 600;
    MorseCodeHandlerManager morseCodeHandlerManager;

    @FXML
    private TextArea statusTextArea;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider transmitFreqSlider;

    @FXML
    private Slider receiveFreqSlider;

    @FXML
    private Button pushToTalkButton;

    @FXML
    private Button englishOnButton;

    @FXML
    private VBox chatLogVBox;

    @FXML
    private ScrollPane chatLogScrollPane;

    @FXML
    private TextArea inputTextArea;

    @FXML
    private ListView<Bot> botListView;

    @FXML
    private Button rulesButton;

    @FXML
    private Button helpPeopleButton;

    @FXML
    private ComboBox wpmComboBox;

    @Override
    @FXML
    public void initialize() throws IOException {

        this.radio = new HamRadioSimulator(0,0,0,0
                ,0,0,1.0,10);
        this.room = App.getCurrentPracticeScenerio();
        morseCodeHandlerManager = new MorseCodeHandlerManager(inputTextArea, radio);
        radio.setVolume(volumeSlider.getValue());
        radio.setReceiveFrequency(receiveFreqSlider.getValue());
        radio.setTransmitFrequency(transmitFreqSlider.getValue());
        addMessageToChatLogUI("Radio: Hello, welcome to HAM Practice!");
        addMessageToChatLogUI("Radio: Please first read our game's rules by hitting \"Rules \"");
        System.out.println("Radio WPM in Controller Practice Innitialize: "+radio.getWPM());
        wpmComboBox.getItems().addAll(5.0,10.0,15.0,20.0);

        for (TaskForPractice task : room.getTaskList()) {
            addMessageToChatLogUI(task.getDescription());
        }

        for (int i = 0; i < Bot.nameList.length; i++){
            String name = Bot.nameList[i];
            int level = Bot.getRandomLevel();
            double frequency = Bot.getRandomFreq();
            Bot newBot = new Bot(level, name, frequency);
            room.getBotList().add(newBot);
            System.out.println("For testing in initialize() Practice UI: " + newBot + ", Freq: " + newBot.getBotFrequency());
        }

    }

    @FXML
    private void startButton() throws IOException {
        isStartClicked = true;
        statusConnect = " Connected";
        statusTextArea.setText(displayTextString());
        botListView.getItems().addAll(room.getBotList());
        morseCodeHandlerManager.setBandSelected(true);
        App.getKeyBindManager().registerKeybind(KeyCode.SHIFT, this::onPress, this::onRelease);
    }

    private void onPress() {
        radio.setIsKeyReleased(false);
        System.out.println("onPress");
        radio.playTone(600);  // Starts playing tone in a separate thread
    }

    private void onRelease() {
        radio.setIsKeyReleased(true);  // Signals playTone loop to end
    }

    private void addMessageToChatLogUI(String radioMessage) {
        Label label = new Label(radioMessage);
        label.setTextFill(Color.RED);
        label.setWrapText(true);
        FontWeight fontWeight = FontWeight.BOLD;
        label.setFont(Font.font("System",fontWeight, 11));
        chatLogVBox.getChildren().add(label);

        Platform.runLater(() -> chatLogScrollPane.setVvalue(1.0)); // scroll the scrollpane to the bottom
    }

    @Override
    @FXML public void volumeSliderAction(){ //volumeController
        double customizedVolume = volumeSlider.getValue();
        radio.setVolume(customizedVolume);
        statusTextArea.setText(displayTextString());
    }

    @FXML
    private void changeTransmittedFrequency(){
        radio.setTransmitFrequency(transmitFreqSlider.getValue());
        statusTextArea.setText(displayTextString());
    }

    @FXML
    private void changeReceivedFrequency(){
        radio.setReceiveFrequency(receiveFreqSlider.getValue());
        statusTextArea.setText(displayTextString());
        givingTask();
    }

    private void givingTask() {
        List<Bot> botList = room.getBotList();
        double radioFreq = radio.getReceiveFrequency();
        double radioBandwidth = radio.getBandWidth();
        double receivableMin = radioFreq - radioBandwidth/2;
        double receivableMax = radioFreq + radioBandwidth/2;
        for (Bot bot: botList){
            if(bot.getBotFrequency() < receivableMax && bot.getBotFrequency() > receivableMin){
                bot.setDiscovered();
                if(bot.isDiscovered()){
                    room.addBotToIdentifiedList(bot);
                }
                if (!bot.didAskForHelp()){
                    addMessageToChatLogUI(bot.getIDCode() + " needs help!");
                    bot.setDidAskForHelp();
                }
            }
        }
    }

    @FXML
    public void speedDownAction() { //speed controller
        radio.setPlaybackSpeed(radio.getPlaybackSpeed() - 0.1);
        statusTextArea.setText(displayTextString());
    }

    @FXML
    public void speedUpAction() { //speed controller
        radio.setPlaybackSpeed(radio.getPlaybackSpeed() + 0.1);
        statusTextArea.setText(displayTextString());
    }

    public String displayTextString() { //TextFieldController
        String radioStatus = "Receive: " + radio.getReceiveFrequency() + "MHz \n" +
                "Transmit: " + radio.getTransmitFrequency() + "MHz \n"+
                "Status: " + statusConnect +
                "\n" + "Volume: " + radio.getVolume() +
                "\n" + "Playback Speed: " + radio.getPlaybackSpeed() +
                "\n" + "Bandwidth: " + radio.getBandWidth() +
                "\n" + "\n" + "Your score: " +  room.getScore();
        return radioStatus;
    }

    @FXML
    public void playBackAction() { //playback controller
        if (!isStartClicked){
            String message = "Please hit Start and type in before Playback!";
            new Alert(Alert.AlertType.INFORMATION, message).show();
            return;
        }
        if (Math.abs(radio.getReceiveFrequency() - radio.getTransmitFrequency()) <= radio.getBandWidth() / 2) {
            MorseCodePlayer player = new MorseCodePlayer((int) radio.getWPM(), radio);
//            player.playMorseCode(userOutput);
            player.playMorse(userOutput);
            statusTextArea.setText("Start play back!\n" + "\nYou are transmitting: " + userOutput);
        }
    }

    @FXML
    public void bandwidthUpAction(){ //freq controller
        radio.setBandWidth(radio.getBandWidth() + this.DEFAULT_TUNE);
        statusTextArea.setText(displayTextString());
        givingTask();
    }

    @FXML
    public void bandwidthDownAction(){ //freq controller
        radio.setBandWidth(radio.getBandWidth() - this.DEFAULT_TUNE);
        statusTextArea.setText(displayTextString());
        givingTask();
    }

    @FXML
    public void selectWPMAction() {
        double wpm = (double) wpmComboBox.getSelectionModel().getSelectedItem();
        radio.setWPM(wpm);
        System.out.println("wpm: " + radio.getWPM());
    }

    @FXML
    public void morseToTextAction() { //morsecode controller
        morseCodeHandlerManager.morseToTextAction();
    }

    @FXML
    public void textToMorseAction() { //morse code controller
        userOutput = morseCodeHandlerManager.textToMorseAction();
    }

    public void pushToTalkButton(ActionEvent actionEvent) {
        isPushedToTalk = true;
    }


    public void englishOnButton(ActionEvent actionEvent) {
        isEnglishOn = true;
    }

    @FXML
    private void helpPeopleAction(){
        popUpWindow("HelpPeople", 400, 500, "Choose who to help!");
    }

    @FXML
    public void rulesButtonAction() {
        popUpWindow("GameRules", 400, 500, "Game Rules");
    }

    private void popUpWindow(String fxmlFile, int windowWidth, int windowHeight, String title){
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