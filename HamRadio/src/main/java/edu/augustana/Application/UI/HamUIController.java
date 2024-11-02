package edu.augustana.Application.UI;

import java.io.IOException;
import java.util.Scanner;

import edu.augustana.Application.UIHelper.MorseCodePlayer;
import edu.augustana.Application.UIHelper.MorseCodeTranslator;
import edu.augustana.RadioModel.HamRadioSimulator;
import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class HamUIController {

    private static final double DEFAULT_MIN_FREQ = 7000;
    private static final double DEFAULT_MAX_FREQ = 7067;
    private static final double DEFAULT_TUNE = 1.0;

    @FXML
    private ComboBox<String> bandComboBox;

    @FXML
    private TextArea displayTextArea;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider transmitFreqSlider;

    @FXML
    private Slider receiveFreqSlider;

    @FXML
    private TextArea statusTextArea;

    @FXML
    private Button pushToTalkButton;

    @FXML
    private Button englishOnButton;

    private String userOutput = "";
    private String cleanMorse = "";

    HamRadioSimulatorInterface radio;


    // To track if 'Start' button has been pressed
    private boolean isStartClicked = false;
    private boolean isBandSelected = false;


    @FXML
    private void switchToWelcomeScreen() throws IOException {
        App.setRoot("WelcomeScreen");
    }

    @FXML
    public void initialize() throws IOException {
        this.radio = new HamRadioSimulator(0,0,0,0
        ,0,0,1.0,0);
        radio.setVolume(volumeSlider.getValue());
    }

    public HamRadioSimulatorInterface getRadio(){
        return this.radio;
    }

    public void setRadio(HamRadioSimulatorInterface radio){
        this.radio = radio;
    }

    @FXML private void selectRangeAction(){ //FrequencyController
        isBandSelected = true;
        initialize_frequency(DEFAULT_MIN_FREQ, DEFAULT_MAX_FREQ);

        if(isStartClicked){
            displayTextArea.setText(displayTextString() + "\nYou are transmitting: "+ userOutput);
        } else {
            displayTextArea.setText("Your received frequency: " + radio.getReceiveFrequency() + "MHz \n" +
                    "Your transmit frequency: " + radio.getTransmitFrequency() + "MHz\n" +
                    "Frequency Bandwidth: " + radio.getBandWidth()
                    + "\nPlease hit Start to transmit and" + " receive CW signal" + "\n");
        }

    }

    @FXML
    private void startButton() throws IOException {
        if (!isBandSelected){
            String message = "Please select a frequency range and band before starting to transmit!";
            new Alert(Alert.AlertType.INFORMATION, message).show();
        }
        isStartClicked = true;
        displayTextArea.setText(displayTextString() + "\nYou are transmitting: " + userOutput);
    }

    @FXML public void volumeSliderAction(){ //volumeController
        double customizedVolume = volumeSlider.getValue();
        radio.setVolume(customizedVolume);
        if (!isStartClicked){
            displayTextArea.setText("Your volume changed to " + radio.getVolume() + "!\n" +
                    "Your received frequency: " + radio.getReceiveFrequency() + "MHz \n" +
                    "Your transmit frequency: " + radio.getTransmitFrequency() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");

        } else {
            displayTextArea.setText("Your volume changed!\n" + displayTextString() + "\nYou are transmitting: "+ userOutput);
        }
    }

    @FXML
    private void changeTransmittedFrequency(){
        radio.setReceiveFrequency(transmitFreqSlider.getValue());
        if(!isStartClicked){
            displayTextArea.setText("Your received frequency: " + radio.getReceiveFrequency() + "MHz \n" +
                    "Your transmit frequency: " + radio.getTransmitFrequency() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");

        } else {
            displayTextArea.setText("Your received frequency changed!\n" + displayTextString() + "\nYou are transmitting: "+ userOutput);
        }
    }

    @FXML
    private void changeReceivedFrequency(){
        radio.setReceiveFrequency(receiveFreqSlider.getValue());
        if(!isStartClicked){
            displayTextArea.setText("Your received frequency: " + radio.getReceiveFrequency() + "MHz \n" +
                    "Your transmit frequency: " + radio.getTransmitFrequency() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");
        } else {
            displayTextArea.setText("Your transmit frequency changed!\n" + displayTextString() + "\nYou are transmitting: " + userOutput);
        }
    }
    private void initialize_frequency(double min, double max) { //freqController
        radio.setMinReceiveFrequency(min);
        radio.setMaxReceiveFrequency(max);
        radio.setReceiveFrequency((radio.getMaxReceiveFrequency() + radio.getMinReceiveFrequency()) / 2);
        radio.setTransmitFrequency((radio.getMaxReceiveFrequency() + radio.getMinReceiveFrequency()) / 2);
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

    @FXML
    private void dashAction() {
        if (!isStartClicked) {
            showAlert();
            return;
        }

        radio.playTone(1500, 300); //bug: frequency se luon la 1500 Hz regardless
        cleanMorse += "-";
        userOutput += "- ";
        displayTextArea.setText(displayTextString() + "\nYou are transmitting: " + userOutput);
    }

    @FXML
    public void dotAction() {
        if (!isStartClicked) {
            showAlert();
            return;
        }

        radio.playTone(1500, 100); //bug: frequency se luon la 1500 Hz regardless
        cleanMorse += ".";
        userOutput += ". ";
        displayTextArea.setText(displayTextString() + "\nYou are transmitting: " +userOutput);
    }

    private void showAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Action Required");
        alert.setHeaderText("Please select the band and frequency first");
        alert.setContentText("You must hit the 'Start' button, select the band, and set the frequency before using dot or dash.");
        alert.showAndWait();
    }

    @FXML
    public void spaceAction(ActionEvent event) {
        if (!isStartClicked) {
            showAlert();
            return;
        }
        userOutput += "  ";
        cleanMorse += " ";
        displayTextArea.setText(displayTextString() +"\nYou are transmitting: " + userOutput);
    }

    @FXML
    public void splashAction(ActionEvent event) {
        if (!isStartClicked) {
            showAlert();
            return;
        }
        userOutput += " / ";
        cleanMorse += " / ";
        displayTextArea.setText(displayTextString() +"\nYou are transmitting: " + userOutput);
    }

    @FXML
    public void speedDownAction() { //speed controller
        radio.setPlaybackSpeed(radio.getPlaybackSpeed() - 0.1);
        if(!isStartClicked){
            displayTextArea.setText("Your playback speed changed to " + radio.getPlaybackSpeed() +
                    "\nYour received frequency: " + radio.getReceiveFrequency() + "MHz \n" +
                    "\nYour transmit frequency: " + radio.getTransmitFrequency() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");
        } else {
            displayTextArea.setText("Your Playback Speed changed to " + radio.getPlaybackSpeed() +
                    "\n" + displayTextString() +
                    "\nYou are transmitting: " + userOutput);
        }
    }

    @FXML
    public void speedUpAction() { //speed controller
        radio.setPlaybackSpeed(radio.getPlaybackSpeed() + 0.1);
        if(!isStartClicked){
            displayTextArea.setText("Your playback speed changed to " + radio.getPlaybackSpeed() +
                    "\nYour received frequency: " + radio.getReceiveFrequency() + "MHz \n" +
                    "\nYour transmit frequency: " + radio.getTransmitFrequency() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");
        } else {
            displayTextArea.setText("Your Playback Speed changed to " + radio.getPlaybackSpeed() + "\n" + displayTextString() +
                    "\nYou are transmitting: " + userOutput);
        }
    }

    @FXML
    public void playBackAction() { //playback controller
        if (!isStartClicked){
            String message = "Please hit Start and type in before Playback!";
            new Alert(AlertType.INFORMATION, message).show();
            return;
        }
        if (Math.abs(radio.getReceiveFrequency() - radio.getTransmitFrequency()) <= radio.getBandWidth() / 2) {
            MorseCodePlayer player = new MorseCodePlayer(radio.getPlaybackSpeed(), radio);
            player.playMorseCode(userOutput);

            displayTextArea.setText("Start play back!\n" + displayTextString() + "\nYou are transmitting: " + userOutput);
        }
    }

    @FXML
    public void bandwidthUpAction(){ //freq controller
        radio.setBandWidth(radio.getBandWidth() + this.DEFAULT_TUNE);

        if(!isStartClicked){
            displayTextArea.setText("Your received frequency: " + radio.getReceiveFrequency() + "MHz \n" +
                    "Your transmit frequency: " + radio.getTransmitFrequency() + "MHz \n" +
                    "Your bandwidth: " + radio.getBandWidth() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");
        } else {
            displayTextArea.setText("Your bandwidth changed to \n" + displayTextString() + "\nYou are transmitting: " + userOutput);
        }
    }

    @FXML
    public void bandwidthDownAction(){ //freq controller
        radio.setBandWidth(radio.getBandWidth() - this.DEFAULT_TUNE);

        if(!isStartClicked){
            displayTextArea.setText("Your received frequency: " + radio.getReceiveFrequency() + "MHz \n" +
                    "Your transmit frequency: " + radio.getTransmitFrequency() + "MHz \n" +
                    "Your bandwidth: " + radio.getBandWidth() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");
        } else {
            displayTextArea.setText("Your bandwidth changed to \n" + displayTextString() + "\nYou are transmitting: " + userOutput);
        }
    }

    @FXML
    public void morseToTextAction() { //morsecode controller
        if (!isBandSelected){
            String message = "Please hit Start and type in before Playback!";
            new Alert(Alert.AlertType.INFORMATION, message).show();
        }
        String morseToText = MorseCodeTranslator.morseToText(cleanMorse);
        displayTextArea.setText("You typed: " + userOutput + "\n" + "Translated as: " + morseToText);
    }

    @FXML
    public void textToMorseAction() { //morse code controller
        if (!isBandSelected){
            String message = "Please hit Start and type in before Playback!";
            new Alert(Alert.AlertType.INFORMATION, message).show();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type English to be translated: ");
        String textToBeTranslated = scanner.nextLine();
        String textToMorse = MorseCodeTranslator.textToMorse(textToBeTranslated);
        userOutput = textToMorse;
        displayTextArea.setText("You typed: " + textToBeTranslated + "\n"
                + "Translated as: " + textToMorse);
    }

    @FXML private void createTestUser() throws IOException {
        App.setRoot("RadioUserTest");
    }

    public void pushToTalkButton(ActionEvent actionEvent) {
    }


    public void englishOnButton(ActionEvent actionEvent) {
    }
}

//add: PTTController -> change the state
//listen to key event
