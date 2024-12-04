package edu.augustana.Application.UI;

import java.io.IOException;

import edu.augustana.Application.UIHelper.*;

import edu.augustana.RadioModel.ChatMessage;
import edu.augustana.RadioModel.HamRadioSimulator;
import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import edu.augustana.RadioModel.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

public class HamGUIController {
    private HamRadioSimulatorInterface radio;
    private String userOutput = "";
    private String cleanMorse = "";
    private FrequencyManager frequencyManager;
    private VolumeManager volumeManager;
    private WPMManager wpmManager;
    private boolean isStartClicked;
    private long timeOfLastPress;
    User user = new User();

    @FXML
    private TextArea statusTextArea;

    @FXML
    private TextArea displayTextArea;

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
    public void initialize() throws IOException {
        System.out.println("\ninitialization started");
        this.radio = new HamRadioSimulator(0,0,0,0,
                3.0,0,1.0,10);
        radio.setVolume(volumeSlider.getValue());
        radio.setReceiveFrequency(receiveFreqSlider.getValue());
        radio.setTransmitFrequency(transmitFreqSlider.getValue());
        radio.setOnChatMessage(this::handleIncomingChatMessage);
        frequencyManager = new FrequencyManager(this);
        wpmManager = new WPMManager(this);
        volumeManager = new VolumeManager(this);
        wpmComboBox.getItems().addAll(5,10,15,20,25,30);
    }

    //FXML Controller Actions
    @FXML
    private void startButton() throws Exception {
        isStartClicked = true;
        statusTextArea.setText(displayTextString());
        //start connecting to server
        radio.startRadio();
    }

    @FXML
    public void volumeSliderAction(){ //volumeController
        volumeManager.volumeSliderAction();
    }

    @FXML
    private void changeTransmittedFrequency(){
        frequencyManager.changeTransmittedFrequencyController();
    }

    @FXML
    private void changeReceivedFrequency(){
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

    //API
    public void setDisplayTextControl(String text) {
        displayTextArea.setText(text);
    }
    public String getDisplayTextControl() {
        return displayTextArea.getText();
    }
    public void setVolume(double volume) {
        volumeSlider.setValue(volume);
    }
    public double getVolume() {
        return volumeSlider.getValue();
    }
    public void setTransmitFrequencyControl(double freq) {
        transmitFreqSlider.setValue(freq);
    }
    public double getTransmitFrequencyControl() {
        return transmitFreqSlider.getValue();
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
        timeOfLastPress = System.currentTimeMillis();
        radio.playTone(600);  // Starts playing tone in a separate thread
    }

    private void onRelease() {
        long timeSinceLastPress = System.currentTimeMillis() - timeOfLastPress;
        if (timeSinceLastPress <= 3 * HelperClass.unitOfTime(radio.getWPM())) {
            radio.broadcastCWSignal(new ChatMessage(".", user));
        } else {
            radio.broadcastCWSignal(new ChatMessage("-", user));
        }
        radio.setIsKeyReleased(true);// Signals playTone loop to end


    }

    public void pushToTalkButton(ActionEvent actionEvent) {
        App.getKeyBindManager().registerKeybind(KeyCode.SHIFT, this::onPress, this::onRelease);
    }

    private void handleIncomingChatMessage(ChatMessage chatMessage) {

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