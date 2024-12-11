package edu.augustana.Application.UI;

import edu.augustana.Application.UIHelper.MorseCodeHandlerManager;
import edu.augustana.Application.UIHelper.MorseCodePlayer;
import edu.augustana.Application.UIHelper.MorseCodeTranslator;
import edu.augustana.RadioModel.HamRadioSimulator;
import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import edu.augustana.RadioModel.Practice.*;
import edu.augustana.RadioModel.Practice.BotCollections.Bot;
import edu.augustana.RadioModel.Practice.BotCollections.GeminiBirdBot;
import edu.augustana.RadioModel.Practice.PracticeScenario;
import edu.augustana.RadioModel.Practice.SceneBuilderFactory.RoomBuilder;
import edu.augustana.RadioModel.Practice.TaskCollection.TaskForPractice;
import edu.augustana.RadioModel.Practice.TaskCollection.TransmittingTask;
import edu.augustana.RadioModel.User;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

public class HamPracticeUIController extends HamUIController {
    HamRadioSimulatorInterface radio;
    PracticeScenario room;
    private String userOutput = "";
    private String cleanMorse = "";
    private boolean isStartClicked = false;
    boolean isStartClickedTwice = false;
    private String statusConnect = " Not Connected";
    private static final double DEFAULT_MIN_FREQ = 7000;
    private static final double DEFAULT_MAX_FREQ = 7067;
    private static final double DEFAULT_TUNE = 1.0;
    public static final int TONE = 600;
    MorseCodeHandlerManager morseCodeHandlerManager;
    MorseCodePlayer player;
    User user= new User("Hello world");
    FileManager fileManager = App.getFileManager();
    boolean isRespondActive = false;
    boolean alreadyGivingTask = false;
    List<Bot> botList;

    @FXML
    private TextArea statusTextArea;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider receiveFreqSlider;
    @FXML
    private VBox chatLogVBox;
    @FXML
    private ScrollPane chatLogScrollPane;
    @FXML
    private TextArea inputTextArea;
    @FXML
    private ListView<Bot> botListView;
    @FXML
    private ComboBox wpmComboBox;
    @FXML
    private Slider bandWitdhSlider;

    private String primaryUserName;
    private String serverAddress;
    private int wpm;
    private UserPreferences userPreferences;


    @Override
    @FXML
    public void initialize() throws IOException {
        System.out.println("\ninitialize is running.....");
        userPreferences = App.getUserPrefs();
        this.room = App.getCurrentPracticeScenerio();
        room.setNewMessageEventListener(msg -> handleNewMessage(msg));

        primaryUserName = userPreferences.getPrimaryUserName();
        serverAddress = userPreferences.getServerAddress();
        wpm = userPreferences.getWPM();
        this.radio = new HamRadioSimulator(0,0,0,0
                ,3.0,0,1.0,wpm);
        morseCodeHandlerManager = new MorseCodeHandlerManager(inputTextArea, radio);
        radio.setVolume(volumeSlider.getValue());
        radio.setReceiveFrequency(receiveFreqSlider.getValue());
        for (ChatMessage message : room.getChatLogMessageList()) {
            addMessageToChatLogUI(message);
        }
        System.out.println("Radio WPM in Controller Practice Innitialize: " + radio.getWPM());
        wpmComboBox.getItems().addAll(5,10,15,20,25,30);
        System.out.println("In Initialize controller: User name is...." + App.getUserPrefs().getPrimaryUserName());
        System.out.println("In Initialize controller: WPM is..." + wpm);
        player = new MorseCodePlayer(wpm, radio);
        System.out.println("Print botList from innitialize----------------" + room.getBotList());
        System.out.println("\n" + userPreferences);
        botList = room.getBotList();

//        //Debug AI Bot
//        Bot aiBot = new GeminiBirdBot("TestBot", Color.BLACK, room, "You can only say what's up back to me");
//        room.getBotList().add(aiBot);
//        aiBot.requestMessage();

    }

    @FXML
    private void startButton() throws IOException {

        statusConnect = " Connected";
        if(isStartClicked){
            room.getBotList().clear();
        }
        botListView.getItems().addAll(room.getBotList());
//        System.out.println("In startButton in Controller:....." +
//                "\n botListView is....... " + room.getBotList() +
//                "\n is WhiteNoise on......." + App.getUserPrefs().getWhiteNoise());
        if(!isStartClicked){
            clearChatAction();
            for (ChatMessage message : room.getChatLogMessageList()) {
                addMessageToChatLogUI(message);
            }
        }
        statusTextArea.setText(displayTextString());
        morseCodeHandlerManager.setBandSelected(true);
        isStartClicked = true;
        if(App.getUserPrefs().getWhiteNoise() && !isStartClickedTwice){
            generateWhiteNoise();
            isStartClickedTwice = true;
        }
        App.getKeyBindManager().registerKeybind(KeyCode.SHIFT, this::onPress, this::onRelease);

        int BOT_SPEED_DELAY = 2; // speed 1 means 10 sec delay, speed 10 means 1 sec delay
        PauseTransition pause = new PauseTransition(Duration.seconds(BOT_SPEED_DELAY));
        pause.setOnFinished( e -> {
            // STOP running if the scene switched roots, and thus this chatLogVBox is longer visible
            if (chatLogVBox.getScene()!=null) {
                sendMessageFromRandomBot();
                pause.playFromStart(); // make it loop

            }
        });
        pause.play(); // start playing a 10-sec "do nothing" animation, & aferwards it triggers the 'onFinished' event
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

    private void handleNewMessage(ChatMessage msg) {
        System.out.println("I'm handling new message----------------");
        Platform.runLater(()->addMessageToChatLogUI(msg));
    }

    private void addMessageToChatLogUI(String sender, String message){
        ChatMessage newMessage = new ChatMessage(message, sender, Color.GREEN, true);
        addMessageToChatLogUI(newMessage);
    }

    private void addMessageToChatLogUI(TaskForPractice task){
        String message = task.getDescription();
        addMessageToChatLogUI(task.getSender().getIDCode(),message);
    }

    private void addMessageToChatLogUI(ChatMessage radioMessage) {
        Label label = new Label( radioMessage.getSender() + ": "+ radioMessage.getText());
        label.setTextFill(Color.RED);
        label.setWrapText(true);
        FontWeight fontWeight = FontWeight.BOLD;
        label.setFont(Font.font("System",fontWeight, 11));
        chatLogVBox.getChildren().add(label);

        Platform.runLater(() -> chatLogScrollPane.setVvalue(1.0)); // scroll the scrollpane to the bottom
    }

    @FXML
    private void sendMessageAction() {
        if(isStartClicked){
            String msgText = inputTextArea.getText();
            if (!msgText.isBlank()) {
                ChatMessage newMessageFromUser = new ChatMessage(msgText,
                        App.getUserPrefs().getPrimaryUserName(),
                        Color.DARKGREEN, true);
                App.getCurrentPracticeScenerio().addChatMessage(newMessageFromUser);
                inputTextArea.clear();
            }
        } else {
            showAlert();
        }

    }

    @FXML
    private void removeMessageAction(){
        morseCodeHandlerManager.clearMorse();
        inputTextArea.clear();
    }

    @FXML
    private void clearChatAction(){
        ChatMessage firstSystemPost;
        List<ChatMessage> messages = App.getCurrentPracticeScenerio().getChatLogMessageList();
        if(!messages.isEmpty()){
            firstSystemPost = messages.get(0);
            messages.clear();
            chatLogVBox.getChildren().clear();
            messages.add(firstSystemPost);
        } else {
            System.out.println("The chatMessage is null in clearChatAction");
        }

    }

    @Override
    @FXML public void volumeSliderAction(){ //volumeController
        double customizedVolume = volumeSlider.getValue();
        radio.setVolume(customizedVolume);
        statusTextArea.setText(displayTextString());
    }

    @FXML
    private void changeReceivedFrequency(){
        radio.setReceiveFrequency(receiveFreqSlider.getValue());
        statusTextArea.setText(displayTextString());
        if (isStartClicked && !alreadyGivingTask) {
            givingTask();
        } else if (alreadyGivingTask) {
            filterBotList();
        }
    }

    //method to give task
    private void givingTask() {
        alreadyGivingTask = true;
        //setting up
        System.out.println("-----------------");
        System.out.println("Start Giving Task.");
        double radioFreq = radio.getReceiveFrequency();
        double radioBandwidth = radio.getBandWidth();
        double receivableMin = radioFreq - radioBandwidth/2;
        double receivableMax = radioFreq + radioBandwidth/2;

        //traverse the bot list
        for (Bot bot: botList){
            //check if bot is in the frequency
            System.out.println(bot.getBotType());
            if(bot.getBotFrequency() < receivableMax && bot.getBotFrequency() > receivableMin){
                bot.setDiscovered();
                if(bot.isDiscovered()){
                    room.addBotToIdentifiedList(bot);
                }
                if (!bot.didAskForHelp()){
                    bot.setDidAskForHelp();
                }
                //player1.playMorse(botTaskTranslated);
                addMessageToChatLogUI(bot.getTask());

                bot.play(radio);
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
                addMessageToChatLogUI(bot.getTask());
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

    private void filterBotList() {
        for (Bot bot: botList) {
            if (bot.getBotFrequency() > radio.getReceiveFrequency() + (radio.getBandWidth()/2) ||
                    bot.getBotFrequency() < radio.getReceiveFrequency() - (radio.getBandWidth()/2)) {
                bot.stopPlay();
            }
        }
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
        if (isStartClicked && !alreadyGivingTask) {
            givingTask();
            System.out.println("Not yet");
        } else if (alreadyGivingTask) {
            filterBotList();
            System.out.println("Already given tasks");
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
        if (!isRespondActive) {
            isRespondActive = true;
            morseCodeHandlerManager.morseToTextAction();
        }
    }

    @FXML
    public void textToMorseAction() { //morse code controller
        userOutput = morseCodeHandlerManager.textToMorseAction();
    }

    @FXML
    public void switchToCustomizeScenario() throws IOException{
        setWhiteNoiseOn(false);
        room.getBotList().clear();
        room.clearChatLogMessageList();
        clearChatAction();
        App.setRoot("ScenarioSetScreen");
    }

    @FXML
    public void rulesButtonAction() {
        PopUpManager pop = new PopUpManager();
        pop.popUpWindow("GameRules", 400, 500, "Game Rules");
    }


    @FXML
    private void menuActionOpenUserData(ActionEvent event) throws IOException {
        fileManager.menuActionOpenUserData(event);
        cleanUpForNewPrefs();

    }

    private void cleanUpForNewPrefs() throws IOException{
        room.getBotList().clear();
        setWhiteNoiseOn(false);
        statusTextArea.setText("");
        initialize();
    }

    @FXML
    private void switchToWelcomeScreen() throws IOException {
        setWhiteNoiseOn(false);
        App.setRoot("WelcomeScreen");
    }

    @FXML
    private void menuActionSaveUserData(ActionEvent event) throws IOException {
        fileManager.menuActionSaveUserData(event);
        initialize();
    }

    @FXML
    private void menuActionSaveUserDataAs(ActionEvent event) {
        fileManager.menuActionSaveUserDataAs(event);
    }

    private void saveUserDataToFile(File chosenFile) {
        fileManager.saveUserDataToFile(chosenFile);
    }

    private void sendMessageFromRandomBot() {
        ChatMessage lastMsg = App.getCurrentPracticeScenerio().getChatLogMessageList().getLast();
        String lastSender = lastMsg.getSender();
        List<Bot> bots =  new ArrayList<>(App.getCurrentPracticeScenerio().getBotList());
        // choose a random bot that is NOT the most recent sender (so it doesn't reply to itself)
        Collections.shuffle(bots);
        Bot randomBot = null;
        for (Bot bot : bots) {
            if (!bot.getIDCode().equals(lastSender)) {
                randomBot = bot;
                break;
            }
        }
        if (randomBot != null) {
            randomBot.requestMessage();
        }
    }

}