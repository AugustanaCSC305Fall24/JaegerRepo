package edu.augustana.Application.UI;

import edu.augustana.Application.UIHelper.MorseCodeHandlerManager;
import edu.augustana.Application.UIHelper.MorseCodePlayer;
import edu.augustana.Application.UIHelper.MorseCodeTranslator;
import edu.augustana.RadioModel.HamRadioSimulator;
import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import edu.augustana.RadioModel.Practice.*;
import edu.augustana.RadioModel.Practice.Bot;
import edu.augustana.RadioModel.Practice.PracticeScenerio;
import edu.augustana.RadioModel.Practice.TaskForPractice;
import edu.augustana.RadioModel.Practice.TransmittingTask;
import edu.augustana.RadioModel.SoundPlayer;
import edu.augustana.RadioModel.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

public class HamPracticeUIController extends HamUIController {
    Scene scene;
    Stage stage;
    HamRadioSimulatorInterface radio;
    PracticeScenerio room;
    private String userOutput = "";
    private String cleanMorse = "";
    private boolean isStartClicked = false;
    boolean isStartClickedTwice = false;
    private String statusConnect = " Not Connected";
    private boolean isPushedToTalk = false;
    private boolean isEnglishOn = false;
    private static final double DEFAULT_MIN_FREQ = 7000;
    private static final double DEFAULT_MAX_FREQ = 7067;
    private static final double DEFAULT_TUNE = 1.0;
    public static final int TONE = 600;
    MorseCodeHandlerManager morseCodeHandlerManager;
    MorseCodePlayer player;
    User user= new User("Hello world");
    FileManager fileManager = App.getFileManager();

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

    @FXML
    private Slider bandWitdhSlider;

    private String primaryUserName;
    private String serverAddress;
    private int numBot;
    private int wpm;

    @Override
    @FXML
    public void initialize() throws IOException {
        System.out.println("\ninitialize is running.....");
        primaryUserName = App.getUserPrefs().getPrimaryUserName();
        String serverAddress = App.getUserPrefs().getServerAddress();
        int numBot = App.getUserPrefs().getNumBot();
        int wpm = App.getUserPrefs().getWPM();
        this.radio = new HamRadioSimulator(0,0,0,0
                ,3.0,0,1.0,wpm);
        this.room = App.getCurrentPracticeScenerio();
        morseCodeHandlerManager = new MorseCodeHandlerManager(inputTextArea, radio);
        radio.setVolume(volumeSlider.getValue());
        radio.setReceiveFrequency(receiveFreqSlider.getValue());
        radio.setTransmitFrequency(transmitFreqSlider.getValue());
        addMessageToChatLogUI("Radio: Hello, welcome to HAM Practice!");
        addMessageToChatLogUI("Radio: Please first read our game's rules by hitting \"Rules \"");
        System.out.println("Radio WPM in Controller Practice Innitialize: " + radio.getWPM());
        wpmComboBox.getItems().addAll(5,10,15,20,25,30);
        List<TaskForPractice> taskForPracticeList = new ArrayList<>();
        System.out.println("In Controller: Num bots is....." + numBot);
        System.out.println("In Controller: User name is...." + App.getUserPrefs().getPrimaryUserName());
        System.out.println("In controller: WPM is..." + wpm);
        for (int i = 0; i < numBot; i++){
            String name = Bot.nameList[i];
            int level = Bot.getRandomLevel();
            double frequency = Bot.getRandomFreq();
            Bot newBot = new Bot(level, name, frequency);
            TaskForPractice task = new TransmittingTask(newBot.getIDCode() + " " + TransmittingTask.desscriptionOptions[i], newBot);
            taskForPracticeList.add(task);
            newBot.setTask(task);
            room.getBotList().add(newBot);
            System.out.println("For testing in initialize() Practice UI: " + newBot + ", Freq: " + newBot.getBotFrequency());
        }
        player = new MorseCodePlayer(wpm, radio);
    }

    @FXML
    private void startButton() throws IOException {
        isStartClicked = true;
        statusConnect = " Connected";
        statusTextArea.setText(displayTextString());
        botListView.getItems().clear();
        botListView.getItems().addAll(room.getBotList());
        morseCodeHandlerManager.setBandSelected(true);

        if(App.getUserPrefs().getWhiteNoise() && !isStartClickedTwice){
            generateWhiteNoise();
            isStartClickedTwice = true;
        }
        App.getKeyBindManager().registerKeybind(KeyCode.SHIFT, this::onPress, this::onRelease);
    }

    private void generateWhiteNoise() {
        player.playWhiteNoise();
    }

    public void setWhiteNoiseOn(boolean isOn) {
        player.setIsWhiteNoiseOn(isOn);
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
        if(isStartClicked){
            givingTask();
        }
    }

    //method to give task
    private void givingTask() {
        //setting up
        System.out.println("-----------------");
        System.out.println("Start Giving Task.");
        List<Bot> botList = room.getBotList();
        double radioFreq = radio.getReceiveFrequency();
        double radioBandwidth = radio.getBandWidth();
        double receivableMin = radioFreq - radioBandwidth/2;
        double receivableMax = radioFreq + radioBandwidth/2;

        //traverse the bot list
        for (Bot bot: botList){
            //check if bot is in the frequency
            if(bot.getBotFrequency() < receivableMax && bot.getBotFrequency() > receivableMin){
                bot.setDiscovered();
                if(bot.isDiscovered()){
                    room.addBotToIdentifiedList(bot);
                }
                if (!bot.didAskForHelp()){
                    bot.setDidAskForHelp();
                }
                MorseCodePlayer player1 = new MorseCodePlayer(radio.getWPM(), radio);

                //play the morse code of that bot
                String botTaskTranslated = MorseCodeTranslator.textToMorse(bot.getTask().getDescription());
                player1.playMorseForBot(botTaskTranslated, bot);
                //player1.playMorse(botTaskTranslated);
                addMessageToChatLogUI(bot.getIDCode() + ": " + bot.getTask().getDescription());
            }
        }
        System.out.println("No Loop");
        System.out.println("End Giving Task.");
        System.out.println("-----------------------");

    }

    private void givingTaskBackUp() {
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
                    bot.setDidAskForHelp();
                }
                player = new MorseCodePlayer(radio.getWPM(), radio);
                player.playMorse(bot.getTask().getDescription());
                addMessageToChatLogUI(bot.getIDCode() + ": " + bot.getTask().getDescription());
                String botTaskTranslated = MorseCodeTranslator.textToMorse(bot.getTask().getDescription());
//                new Thread(() -> {
//                    MorseCodePlayer new_player = new MorseCodePlayer((int) radio.getWPM(), radio);
//                    new_player.playMorseForBot(botTaskTranslated, bot);
//                }).start();
                MorseCodePlayer new_player = new MorseCodePlayer((int) radio.getWPM(), radio);
                new_player.playMorseForBot(botTaskTranslated, bot);
            }
        }
        System.out.println("-----------------------");
        System.out.println("Done Giving Task.");
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
        if (Math.abs(radio.getReceiveFrequency() - radio.getTransmitFrequency()) <= radio.getBandWidth()/2) {
            MorseCodePlayer player2 = new MorseCodePlayer((int) radio.getWPM(), radio);
            player2.playMorse(userOutput);
            statusTextArea.setText("Start play back!\n" + "\nYou are transmitting: " + userOutput);
        }
    }

    public void playBackActionBackUp() {//playback controller
        new Thread(() -> {
            if (!isStartClicked){
                String message = "Please hit Start and type in before Playback!";
                new Alert(Alert.AlertType.INFORMATION, message).show();
                return;
            }
            if (Math.abs(radio.getReceiveFrequency() - radio.getTransmitFrequency()) <= radio.getBandWidth()/2) {
                MorseCodePlayer player = new MorseCodePlayer((int) radio.getWPM(), radio);
                player.playMorse(userOutput);
                statusTextArea.setText("Start play back!\n" + "\nYou are transmitting: " + userOutput);
            }
        }).start();
    }

    @FXML
    public void bandWidthAction(){
        radio.setBandWidth(radio.getBandWidth() + bandWitdhSlider.getValue());
        statusTextArea.setText(displayTextString());
        if (isStartClicked){
            givingTask();
        }

    }

    @FXML
    public void selectWPMAction(){
        int wpm = (int) wpmComboBox.getSelectionModel().getSelectedItem();
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
    public void switchToCustomizeScenario() throws IOException{
        App.setRoot("ScenarioSetScreen");
        setWhiteNoiseOn(false);
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

    @FXML
    private void menuActionOpenUserData(ActionEvent event) {
        fileManager.menuActionOpenUserData(event);
    }


    private void applyLoadedPreferences(UserPreferences prefs) throws IOException {
        // Example of applying preferences to the application
        initialize();
        fileManager.applyLoadedPreferences(prefs);
    }

    @FXML
    private void switchToWelcomeScreen() throws IOException {
        setWhiteNoiseOn(false);
        App.setRoot("WelcomeScreen");
    }

    @FXML
    private void menuActionSaveUserData(ActionEvent event) {
        fileManager.menuActionSaveUserData(event);
    }

    @FXML
    private void menuActionSaveUserDataAs(ActionEvent event) {
        fileManager.menuActionSaveUserDataAs(event);
    }

    private void saveUserDataToFile(File chosenFile) {
        fileManager.saveUserDataToFile(chosenFile);
    }

}