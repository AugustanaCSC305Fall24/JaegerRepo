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

    @Override
    @FXML
    public void initialize() throws IOException {
        this.radio = new HamRadioSimulator(0,0,0,0
                ,0,0,1.0,29.0);
        this.room = App.getCurrentPracticeScenerio();
        morseCodeHandlerManager = new MorseCodeHandlerManager(inputTextArea, radio);
        radio.setVolume(volumeSlider.getValue());
        radio.setReceiveFrequency(receiveFreqSlider.getValue());
        radio.setTransmitFrequency(transmitFreqSlider.getValue());
        addMessageToChatLogUI("Radio: Hello, welcome to HAM Practice!");
        addMessageToChatLogUI("Radio: Please first read our game's rules by hitting \"Rules \"");
        System.out.println("Radio WPM in Controller Practice Innitialize: "+radio.getWPM());


        for (TaskForPractice task : room.getTaskList()) {
            addMessageToChatLogUI(task.getDescription());
        }

        for (int i =0; i < Bot.nameList.length; i++){
            String name = Bot.getRandomBotName();
            int level = Bot.getRandomLevel();
            double frequency = Bot.getRandomFreq();
            Bot newBot = new Bot(level, name, frequency);
            room.getBotList().add(newBot);
        }

    }

    @FXML
    private void startButton() throws IOException {
        isStartClicked = true;
        statusConnect = " Connected";
        statusTextArea.setText(displayTextString());
        botListView.getItems().addAll(room.getBotList());
        //App.getKeyBindManager().registerKeybind(KeyCode.ENTER, this::dotAction);
        //App.getKeyBindManager().registerKeybind(KeyCode.SHIFT, this::dashAction);
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
    }

    @FXML
    private void dashAction() {
        if (!isStartClicked) {
            showAlert();
            return;
        }

        radio.playTone(TONE, 300); //bug: frequency se luon la 1500 Hz regardless
        cleanMorse += "-";
        userOutput += "- ";
        inputTextArea.setText("You are transmitting: " + userOutput);
    }

    @FXML
    public void dotAction() {
        if (!isStartClicked) {
            showAlert();
            return;
        }
        radio.playTone(TONE, 100);
        cleanMorse += ".";
        userOutput += ". ";
        inputTextArea.setText("You are transmitting: " +userOutput);
    }

    @FXML
    public void spaceAction(ActionEvent event) {
        if (!isStartClicked) {
            super.showAlert();
            return;
        }
        userOutput += "  ";
        cleanMorse += " ";
        inputTextArea.setText("You are transmitting: " + userOutput);
    }

    @FXML
    public void splashAction(ActionEvent event) {
        if (!isStartClicked) {
            showAlert();
            return;
        }
        userOutput += " / ";
        cleanMorse += " / ";
        inputTextArea.setText("You are transmitting: " + userOutput);
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
                "\n" + "Bandwidth: " + radio.getBandWidth();

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
            player.playMorseCode(userOutput);
            statusTextArea.setText("Start play back!\n" + "\nYou are transmitting: " + userOutput);
        }
    }

    @FXML
    public void bandwidthUpAction(){ //freq controller
        radio.setBandWidth(radio.getBandWidth() + this.DEFAULT_TUNE);
        statusTextArea.setText(displayTextString());
    }

    @FXML
    public void bandwidthDownAction(){ //freq controller
        radio.setBandWidth(radio.getBandWidth() - this.DEFAULT_TUNE);
        statusTextArea.setText(displayTextString());
    }

    @FXML
    public void morseToTextAction() { //morsecode controller
        morseCodeHandlerManager.morseToTextAction();
    }

    @FXML
    public void textToMorseAction() { //morse code controller
        if (!isStartClicked){
            String message = "Please hit Start and type in before Playback!";
            new Alert(Alert.AlertType.INFORMATION, message).show();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type English to be translated: ");
        String textToBeTranslated = scanner.nextLine();
        String textToMorse = MorseCodeTranslator.textToMorse(textToBeTranslated);
        userOutput = textToMorse;
        inputTextArea.setText("You typed: " + textToBeTranslated + "\n"
                + "Translated as: " + textToMorse);
    }

    public void pushToTalkButton(ActionEvent actionEvent) {
    }


    public void englishOnButton(ActionEvent actionEvent) {
    }

    @FXML
    public void rulesButtonAction() {
        try {
            // Load the FXML file for GameRules
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/augustana/Application/UI/GameRules.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new scene and stage for the GameRules window
            Scene scene = new Scene(root, 400, 500);
            Stage gameRulesStage = new Stage();
            gameRulesStage.setScene(scene);

            // Set the title for the new window
            gameRulesStage.setTitle("Game Rules");

            // Show the new window
            gameRulesStage.show();
        } catch (IOException ex) {
            System.err.println("Can't find FXML file GameRules.fxml");
            ex.printStackTrace();
        }
    }

}