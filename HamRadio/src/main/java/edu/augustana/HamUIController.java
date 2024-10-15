package edu.augustana;

import java.io.IOException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Circle;

public class HamUIController {
    @FXML
    private Slider chanelSlider;

    @FXML
    private Button clarifierDown;

    @FXML
    private Button clarifierUp;

    @FXML
    private Button helpBtn;

    @FXML
    private Button moreBtn;

    @FXML
    private Circle recordBtn;

    @FXML
    private Button returnBtn;

    @FXML
    private Button trainingBtn;

    @FXML
    private Button volumeDownBtn;

    @FXML
    private Button volumeUpBtn;

    @FXML
    private Button dotButton;

    @FXML
    private Button dashButton;

    @FXML
    private ComboBox<String> rangeComboBox;

    @FXML
    private ComboBox<String> bandComboBox;

    @FXML
    private TextArea displayTextArea;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Button speedDownButton;

    @FXML
    private Button speedUpButton;

    @FXML
    private Button morseToTextButton;

    @FXML
    private Button textToMorseButton;

    @FXML
    private TextField translateTextField;

    private String userOutput = "";

    HamRadioClientInterface client = new HamRadioClient();

    private final double minTune = 0.1;
    private final double maxTune = 1.0;

    // To track if 'Start' button has been pressed
    private boolean isStartClicked = false;
    private boolean isBandSelected = false;

    @FXML
    private void switchToWelcomeScreen() throws IOException {
        App.setRoot("WelcomeScreen");
    }

    @FXML
    public void initialize() {
        rangeComboBox.getItems().addAll("HF", "VHF", "UHF");
        client.setVolume(volumeSlider.getValue());
    }

    @FXML private void selectRangeAction(){
        isBandSelected = true;
        if (rangeComboBox.getSelectionModel().getSelectedItem().equals("HF")) {
            bandComboBox.getItems().clear();
            bandComboBox.getItems().addAll("160m", "80m", "40m");
            bandComboBox.getSelectionModel().select(0);
        } else if (rangeComboBox.getSelectionModel().getSelectedItem().equals("VHF")) {
            bandComboBox.getItems().clear();
            bandComboBox.getItems().addAll("6m", "2m");
            bandComboBox.getSelectionModel().select(0);
        } else if (rangeComboBox.getSelectionModel().getSelectedItem().equals("UHF")) {
            bandComboBox.getItems().clear();
            bandComboBox.getItems().addAll("70cm", "33cm", "23cm");
            bandComboBox.getSelectionModel().select(0);
        }

        String band = bandComboBox.getSelectionModel().getSelectedItem();
        if (band.equals("160m")) {
            initialize_frequency(1.8, 2.0);
        } else if (band.equals("80m")) {
            initialize_frequency(3.5, 4.0);
        } else if (band.equals("40m")) {
            initialize_frequency(7.0, 7.3);
        } else if (band.equals("6m")) {
            initialize_frequency(50.0, 54.0);
        } else if (band.equals("2m")) {
            initialize_frequency(144.0, 148.0);
        } else if (band.equals("70cm")) {
            initialize_frequency(420.0, 450.0);
        } else if (band.equals("33cm")) {
            initialize_frequency(902.0, 928.0);
        } else if (band.equals("23cm")) {
            initialize_frequency(124.0, 130.0);
        }
        if(isStartClicked){
            displayTextArea.setText(displayTextString() + "\nYou are transmitting: "+ userOutput);
        } else {
            displayTextArea.setText("Your frequency: " + client.getReceivingFrequency() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");
        }

    }

    @FXML
    private void startButton() {
        if (!isBandSelected){
            String message = "Please select a frequency range and band before starting to transmit!";
            new Alert(Alert.AlertType.INFORMATION, message).show();
        }
        isStartClicked = true;
        displayTextArea.setText(displayTextString() + "\nYou are transmitting: " + userOutput);
    }

    @FXML private void volumeSliderAction(){
        double customizedVolume = volumeSlider.getValue();
        client.setVolume(customizedVolume);
        if (!isStartClicked){
            displayTextArea.setText("Your volume changed to " + client.getVolume() + "!\n" + "Your frequency: " + client.getReceivingFrequency() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");

        } else {
            displayTextArea.setText("Your volume changed!\n" + displayTextString() + "\nYou are transmitting: "+ userOutput);
        }
    }

    @FXML
    private void tuneUpButton() {
        String range = rangeComboBox.getSelectionModel().getSelectedItem();
        if (range.equals("HF")) {
            client.setReceivingFrequency(client.getReceivingFrequency() + this.minTune);
        } else {
            client.setReceivingFrequency(client.getReceivingFrequency() + this.maxTune);
        }
        if(!isStartClicked){
            displayTextArea.setText("Your frequency: " + client.getReceivingFrequency() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");

        } else {
            displayTextArea.setText("Your frequency changed!\n" + displayTextString() + "\nYou are transmitting: "+ userOutput);
        }
    }

    @FXML
    private void tuneDownButton() {
        String range = rangeComboBox.getSelectionModel().getSelectedItem();
        if (range.equals("HF")) {
            client.setReceivingFrequency(client.getReceivingFrequency() - this.minTune);
        } else {
            client.setReceivingFrequency(client.getReceivingFrequency() - this.maxTune);
        }
        if(!isStartClicked){
            displayTextArea.setText("Your frequency: " + client.getReceivingFrequency() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");
        } else {
            displayTextArea.setText("Your frequency changed!\n" + displayTextString() + "\nYou are transmitting: " + userOutput);
        }
    }
    private void initialize_frequency(double min, double max) {
        client.setMinFrequency(min);
        client.setMaxFrequency(max);
        client.setReceivingFrequency((client.getMaxFrequency() + client.getMinFrequency()) / 2);
    }

    public String displayTextString() {
        String radioStatus = "Your frequency: " + client.getReceivingFrequency() + "MHz \n" +
                "Radio Status: Connected. You can start transmitting right now." +
                "\n" + "Radio Volume: " + client.getVolume() +
                "\n" + "Radio Playback Speed: " + client.getPlaybackSpeed();

        return radioStatus;
    }



    @FXML
    private void dashAction() {
        if (!isStartClicked) {
            showAlert();
            return;
        }

        client.playTone(1500, 300); //bug: frequency se luon la 1500 Hz regardless
        userOutput += "- ";
        displayTextArea.setText(displayTextString() + "\nYou are transmitting: " + userOutput);
    }

    @FXML
    public void dotAction() {
        if (!isStartClicked) {
            showAlert();
            return;
        }

        client.playTone(1500, 100); //bug: frequency se luon la 1500 Hz regardless
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
        displayTextArea.setText(displayTextString() +"\nYou are transmitting: " + userOutput);
    }

    @FXML
    public void splashAction(ActionEvent event) {
        if (!isStartClicked) {
            showAlert();
            return;
        }
        userOutput += " / ";
        displayTextArea.setText(displayTextString() +"\nYou are transmitting: " + userOutput);
    }

    @FXML
    public void speedDownAction() {
        client.setPlaybackSpeed(client.getPlaybackSpeed() - 0.1);
        if(!isStartClicked){
            displayTextArea.setText("Your playback speed changed to " + client.getPlaybackSpeed() + "\nYour frequency: " + client.getReceivingFrequency() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");
        } else {
            displayTextArea.setText("Your Playback Speed changed to " + client.getPlaybackSpeed() + "\n" + displayTextString() +
                    "\nYou are transmitting: " + userOutput);
        }
    }

    @FXML
    public void speedUpAction() {
        client.setPlaybackSpeed(client.getPlaybackSpeed() + 0.1);
        if(!isStartClicked){
            displayTextArea.setText("Your playback speed changed to " + client.getPlaybackSpeed() + "\nYour frequency: " + client.getReceivingFrequency() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");
        } else {
            displayTextArea.setText("Your Playback Speed changed to " + client.getPlaybackSpeed() + "\n" + displayTextString() +
                    "\nYou are transmitting: " + userOutput);
        }
    }

    @FXML
    public void playBackAction() {
        if (!isStartClicked){
            String message = "Please hit Start and type in before Playback!";
            new Alert(Alert.AlertType.INFORMATION, message).show();
            return;
        }
        if (Math.abs(client.getReceivingFrequency() - client.getTransmitFrequency()) <= client.getBandWidth() / 2) {
            MorseCodePlayer player = new MorseCodePlayer(client.getPlaybackSpeed(), client);
            player.playMorseCode(userOutput);
            displayTextArea.setText("Start play back!\n" + displayTextString() + "\nYou are transmitting: " + userOutput);
        }
    }

    @FXML
    public void morseToTextAction() {
        if (!isBandSelected){
            String message = "Please hit Start and type in before Playback!";
            new Alert(Alert.AlertType.INFORMATION, message).show();
        }
        String morseToText = MorseCodeTranslator.morseToText(userOutput);
        translateTextField.setText("You typed: " + userOutput + "\n" + "Translated as: " + morseToText);
    }

    @FXML
    public void textToMorseAction() {
        if (!isBandSelected){
            String message = "Please hit Start and type in before Playback!";
            new Alert(Alert.AlertType.INFORMATION, message).show();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type English to be translated: ");
        String textToBeTranslated = scanner.nextLine();
        String textToMorse = MorseCodeTranslator.textToMorse(textToBeTranslated);
        translateTextField.setText("You typed: " + textToBeTranslated + "\n"
                + "Translated as: " + textToMorse);
    }

}
