package edu.augustana;

import java.io.IOException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    @FXML private Slider volumeSlider;

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

    @FXML
    private void switchToWelcomeScreen() throws IOException {
        App.setRoot("WelcomeScreen");
    }

    @FXML
    public void initialize() {
         // DOT: 100 ms
         // DASH: 300 ms
        rangeComboBox.getItems().addAll("HF", "VHF", "UHF");
        //rangeComboBox.getSelectionModel().select(0);

//        if(rangeComboBox.getSelectionModel().getSelectedItem().equals("HF")){
//            bandComboBox.getItems().addAll("160m", "80m", "40m");
//            bandComboBox.getSelectionModel().select(0);
//        } else if (rangeComboBox.getSelectionModel().getSelectedItem().equals("VHF")){
//            bandComboBox.getItems().addAll("6m", "2m");
//            bandComboBox.getSelectionModel().select(0);
//        } else if (rangeComboBox.getSelectionModel().getSelectedItem().equals("UHF")){
//            bandComboBox.getItems().addAll("70cm", "33cm", "23cm");
//            bandComboBox.getSelectionModel().select(0);
//        }
    }

    @FXML
    private void startButton(){
        if(rangeComboBox.getSelectionModel().getSelectedItem().equals("HF")){
            bandComboBox.getItems().clear();
            bandComboBox.getItems().addAll("160m", "80m", "40m");
            bandComboBox.getSelectionModel().select(0);
        } else if (rangeComboBox.getSelectionModel().getSelectedItem().equals("VHF")){
            bandComboBox.getItems().clear();
            bandComboBox.getItems().addAll("6m", "2m");
            bandComboBox.getSelectionModel().select(0);
        } else if (rangeComboBox.getSelectionModel().getSelectedItem().equals("UHF")){
            bandComboBox.getItems().clear();
            bandComboBox.getItems().addAll("70cm", "33cm", "23cm");
            bandComboBox.getSelectionModel().select(0);
        }

        String band = bandComboBox.getSelectionModel().getSelectedItem();
        if (band.equals("160m")){
            initialize_frequency(1.8,2.0);
        } else if (band.equals("80m")){
            initialize_frequency(3.5,4.0);
        } else if (band.equals("40m")){
            initialize_frequency(7.0, 7.3);
        } else if (band.equals("6m")){
            initialize_frequency(50.0, 54.0);
        } else if (band.equals("2m")){
            initialize_frequency(144.0, 148.0);
        } else if (band.equals("70cm")){
            initialize_frequency(420.0, 450.0);
        } else if (band.equals("33cm")){
            initialize_frequency(902.0, 928.0);
        } else if (band.equals("23cm")){
            initialize_frequency(124.0, 130.0);
        }
        displayFrequencyTextArea();
        double customizedVolume = volumeSlider.getValue();
        client.setVolume(customizedVolume);
    }

    @FXML
    private void tuneUpButton(){
        String range = rangeComboBox.getSelectionModel().getSelectedItem();
        if (range.equals("HF")){
            client.setReceivingFrequency(client.getReceivingFrequency() + this.minTune);
        } else {
            client.setReceivingFrequency(client.getReceivingFrequency() + this.maxTune);
        }
        displayFrequencyTextArea();
    }

    @FXML
    private void tuneDownButton(){
        String range = rangeComboBox.getSelectionModel().getSelectedItem();
        if (range.equals("HF")){
            client.setReceivingFrequency(client.getReceivingFrequency() - this.minTune);
        } else {
            client.setReceivingFrequency(client.getReceivingFrequency() - this.maxTune);
        }
        displayFrequencyTextArea();
    }

    private void initialize_frequency(double min, double max){
        client.setMinFrequency(min);
        client.setMaxFrequency(max);
        client.setReceivingFrequency((client.getMaxFrequency() + client.getMinFrequency())/2);
    }

    public void displayFrequencyTextArea(){
        displayTextArea.setText("Your frequency: " + client.getReceivingFrequency() + "MHz \n"
                + "If you want to change channel," +"\n" + "please adjust the band and hit 'Start' ");
    }

    @FXML
    private void dashAction() {
        client.playTone(1500, 300);
        userOutput += "- ";
        displayTextArea.setText("You typed: " + userOutput);
    }

    @FXML
    public void dotAction() {
        client.playTone(1500, 100);
        userOutput += ". ";
        displayTextArea.setText("You typed: " + userOutput);

    }

    @FXML
    public void spaceAction(ActionEvent event) {
        userOutput += "  ";
        displayTextArea.setText("You typed: " + userOutput);
    }

    @FXML
    public void splashAction(ActionEvent event) {
        userOutput += " / ";
        displayTextArea.setText("You typed: " + userOutput);

    }

    @FXML
    public void speedDownAction() {
        client.setPlaybackSpeed(client.getPlaybackSpeed() - 0.1);
        displayTextArea.setText("You typed: " + userOutput + "\n" + "Playback Speed: " + client.getPlaybackSpeed());

    }

    @FXML
    public void speedUpAction() {
        client.setPlaybackSpeed(client.getPlaybackSpeed() + 0.1);
        displayTextArea.setText("You typed: " + userOutput + "\n" + "Playback Speed: " + client.getPlaybackSpeed());

    }

    @FXML
    public void playBackAction(){
        MorseCodePlayer player = new MorseCodePlayer(client.getPlaybackSpeed());
        player.playMorseCode(userOutput);
        displayTextArea.setText("You typed: " + userOutput + "\n" + "Playback Speed: " + client.getPlaybackSpeed());
    }

    @FXML
    public void morseToTextAction() {
        String morseToText = MorseCodeTranslator.morseToText(userOutput);
        translateTextField.setText("You typed: " + userOutput + "\n" + "Translated as: " + morseToText);

    }

    @FXML
    public void textToMorseAction() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type English to be translated: ");
        String textToBeTranslated = scanner.nextLine();
        String textToMorse = MorseCodeTranslator.textToMorse(textToBeTranslated);
        translateTextField.setText("You typed: " + textToBeTranslated + "\n"
                + "Translated as: " + textToMorse);
    }




}