package edu.augustana.Application.UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.augustana.Application.UIHelper.*;

import edu.augustana.RadioModel.CWMessage;
import edu.augustana.RadioModel.HamRadioSimulator;
import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import edu.augustana.RadioModel.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HamGUIController {
    private HamRadioSimulatorInterface radio;
    private String userOutput = "";
    private String cleanMorse = "";
    private FrequencyManager frequencyManager;
    private VolumeManager volumeManager;
    private WPMManager wpmManager;
    private boolean isStartClicked;
    private long timeOfLastPress;
    private long timeOfLastRelease;
    User user;
    private boolean isPTT = false;
    private String morseMessage = "";
    private boolean isFirstPress = true;
    private List<CWMessage> chatLogMessageList;


    @FXML
    private TextArea statusTextArea;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider transmitFreqSlider;

    @FXML
    private Slider receiveFreqSlider;

    @FXML
    private Slider bandWitdhSlider;

    @FXML
    private ComboBox wpmComboBox;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button sendButton;

    @FXML
    private TextArea inputTextArea;

    @FXML
    private ScrollPane chatLogScrollPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private VBox chatLogVBox;

    @FXML
    public void initialize() throws IOException {
        System.out.println("\ninitialization started");
        this.radio = new HamRadioSimulator(0,0,0,0,
                3.0,0,1.0,10);
        radio.setVolume(volumeSlider.getValue());
        radio.setReceiveFrequency(receiveFreqSlider.getValue());
        radio.setTransmitFrequency(receiveFreqSlider.getValue());
        radio.setOnChatMessage(this::handleIncomingChatMessage);
        frequencyManager = new FrequencyManager(this);
        wpmManager = new WPMManager(this);
        volumeManager = new VolumeManager(this);
        wpmComboBox.getItems().addAll(5,10,15,20,25,30);
        user = new User("Hello world");
        System.out.println(radio.getReceiveFrequency());
        System.out.println(radio.getTransmitFrequency());
        timeOfLastRelease = System.currentTimeMillis();
        inputTextArea.clear();
        chatLogMessageList = new ArrayList<>();
        chatLogMessageList.add(new CWMessage("Welcome to HAM Radio!", "System", receiveFreqSlider.getValue()));
        for (CWMessage cwMessage: chatLogMessageList) {
            addMessageToChatLogUI(cwMessage);
        }

    }

    //FXML Controller Actions
    @FXML
    private void startButton() throws Exception {
        isStartClicked = true;
        statusTextArea.setText(displayTextString());
        //start connecting to server

        radio.startRadio(usernameTextField.getText());
        setName(usernameTextField.getText());
    }

    @FXML
    public void volumeSliderAction() { //volumeController
        volumeManager.volumeSliderAction();
    }

    @FXML
    private void changeReceivedFrequency() {
        frequencyManager.changeTransmittedFrequencyController();
        frequencyManager.changeReceivedFrequencyController();
    }


    @FXML
    private void selectWPMAction() {
        wpmManager.selectWPMAction();
    }

    @FXML
    private void bandWidthAction() {
        frequencyManager.bandWidthAction();
    }

    @FXML
    private void switchToWelcomeScreen() throws IOException {
        App.setRoot("WelcomeScreen");
    }

    @FXML
    private void sendAction() throws Exception {
        CWMessage message = new CWMessage(morseMessage, usernameTextField.getText(),radio.getReceiveFrequency());
        if (message != null) {
            chatLogMessageList.add(message);
            System.out.println(chatLogMessageList);
            addMessageToChatLogUI(message);
        }
        radio.broadcastCWSignal(message);
        morseMessage = "";
        isFirstPress = true;
        inputTextArea.clear();
    }

    //API
    public void setVolume(double volume) {
        volumeSlider.setValue(volume);
    }
    public double getVolume() {
        return volumeSlider.getValue();
    }
    public void setReceivedFrequencyControl(double freq) {
        receiveFreqSlider.setValue(freq);
    }
    public double getReceivedFrequencyControl() {
        return receiveFreqSlider.getValue();
    }
    public void setBandwidthControl(double bandwidth) {
        bandWitdhSlider.setValue(bandwidth);
    }
    public double getBandwidthControl() {
        return bandWitdhSlider.getValue();
    }
    public void setStatusTextControl(String text) {
        statusTextArea.setText(text);
    }
    public String getStatusTextControl() {
        return statusTextArea.getText();
    }
    public int getWPMControl() {
        return (int) wpmComboBox.getSelectionModel().getSelectedItem();
    }
    public void setVolumeControl(double vol) {
        volumeSlider.setValue(vol);
    }
    public double getVolumeControl() {
        return volumeSlider.getValue();
    }
    public void setName(String input) { user.setUsername(input);}

    //Getter and setter
    public HamRadioSimulatorInterface getRadio() {
        return radio;
    }
    public void setRadio(HamRadioSimulatorInterface radio) {
        this.radio = radio;
    }
    public void setIsStartClicked(boolean isStartClicked) {
        this.isStartClicked = isStartClicked;
    }
    public boolean setIsStartClicked() {
        return this.isStartClicked;
    }

    //helper methods
    private void onPress() {
        radio.setIsKeyReleased(false);
        System.out.println("onPress");
        long currentTime = System.currentTimeMillis();
        long timeSinceLastRelease = currentTime - timeOfLastRelease;
        if (isFirstPress) {
            isFirstPress = false;
            timeSinceLastRelease = 0;
        }

        if (timeSinceLastRelease <= 8 * HelperClass.unitOfTime(radio.getWPM())) {
            morseMessage += "";
        } else if (timeSinceLastRelease <= 23 * HelperClass.unitOfTime(radio.getWPM())) {
            morseMessage += " ";
        } else {
            morseMessage += "/";
        }
        timeOfLastPress = currentTime;
        radio.playTone(600);  // Starts playing tone in a separate thread

    }

    private void onRelease() {
        radio.setIsKeyReleased(true);// Signals playTone loop to end
        long timeSinceLastPress = System.currentTimeMillis() - timeOfLastPress;
        timeOfLastRelease = System.currentTimeMillis(); //Get the time of Releasing the key
        if (timeSinceLastPress <= 3 * HelperClass.unitOfTime(radio.getWPM())) {
            morseMessage += ".";
        } else {
            morseMessage += "-";
        }

        inputTextArea.setText(morseMessage);


    }

    @FXML
    public void pushToTalkButton(ActionEvent actionEvent) {
        if (!isPTT) {
            App.getKeyBindManager().registerKeybind(KeyCode.SHIFT, this::onPress, this::onRelease);
            isPTT = true;
        }


    }

    private void handleIncomingChatMessage(CWMessage chatMessage) {
        System.out.println("CW Message is handled: " + chatMessage.getText());
        chatLogMessageList.add(chatMessage);
        Platform.runLater(() -> addMessageToChatLogUI(chatMessage));
    }

    private void addMessageToChatLogUI(CWMessage message) {
        System.out.println(message.getText());
        Label label = new Label(message.getUser() + ": " + message.getText());
        label.setWrapText(true);
        label.setTextFill(Color.RED);
        label.setFont(Font.font("System", FontWeight.NORMAL, 15));
        chatLogVBox.getChildren().add(label);

        Platform.runLater(() -> chatLogScrollPane.setVvalue(1.0));
    }

    public String displayTextString() { //TextFieldController
        String radioStatus = "Your received frequency: " + radio.getReceiveFrequency() + "MHz \n" +
                "Your transmit frequency: " + radio.getTransmitFrequency() + "MHz \n"+
                "Radio Status: Connected. You can start transmitting right now." +
                "\n" + "Radio Volume: " + radio.getVolume() +
                "\n" + "Radio Playback Speed: " + radio.getPlaybackSpeed() +
                "\n" + "Frequency Bandwidth: " + radio.getBandWidth();

        return radioStatus;
    }


}

//implementation of User, ChatMessage, broadcastCWSignal(), processSignalFromServer() -> Networking for client side
//implementation of SignalMixer, SignalFilter, SoundPlayer.playSound() -> Done of signal processing client side
//Rework the UI Component for HamGUI
//implementation of userListManager/chatLogManager: updateState(chatMessage)
//implementation of handleIncomingChatMessage(): String/ChatMessage -> userListManager.updateState(ChatMessage) -> chatLogTextField.setText();
//-> Done client code (For basic features)

//Server code
//fast api

//test and debug: Test scenario: 3 computers, 2 transmit signal at 2 different frequency, 1 listen at each of the frequency, and both frequencies.