package edu.augustana.Application.UI;

import java.io.IOException;

import edu.augustana.Application.UIHelper.*;

import edu.augustana.RadioModel.HamRadioSimulator;
import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HamGUIController {
    @FXML
    private ComboBox<String> rangeComboBox; //this class
    @FXML
    private ComboBox<String> bandComboBox; //this class
    @FXML
    private TextArea displayTextArea; //this class
    @FXML
    private Slider volumeSlider; //this class
    @FXML
    private TextField translateTextField; //this class
    private HamRadioSimulatorInterface radio;
    MorseCodeHandlerManager morseCodeHandlerManager;

    private boolean isBandSelected;
    private boolean isStartClicked;
    private FrequencyManager frequencyManager;

    private void setBandSelected(boolean bandSelected) {
        isBandSelected = bandSelected;
        morseCodeHandlerManager.setBandSelected(bandSelected);
    }

    private void setSimulator(HamRadioSimulatorInterface radio) {
        this.radio = radio;
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    public void initialize() throws IOException {
        this.radio = new HamRadioSimulator(0,0,0,
                0, 0,0,0,0);
        rangeComboBox.getItems().addAll("HF", "VHF", "UHF");
        radio.setVolume(volumeSlider.getValue());
        frequencyManager = new FrequencyManager(radio, rangeComboBox, bandComboBox,
                displayTextArea, this::setBandSelected);
        //morseCodeHandlerController = new MorseCodeHandlerController();
    }

    @FXML private void selectRangeAction(){ //Frequency controller

    }

    @FXML
    private void startButton() throws IOException {
        if (!isBandSelected){
            String message = "Please select a frequency range and band before starting to transmit!";
            new Alert(Alert.AlertType.INFORMATION, message).show();
        }
        isStartClicked = true;
        this.frequencyManager.setStartClicked(true);
        //displayTextArea.setText(displayTextString() + "\nYou are transmitting: " + userOutput);
    }

    @FXML public void volumeSliderAction(){ //volumeController

    }

    @FXML
    private void tuneRUpButton() { //freqController

    }

    @FXML
    private void tuneRDownButton() { //freqCOntroller

    }

    @FXML
    private void tuneTDownButton() {

    }

    @FXML
    private void tuneTUpButton() {

    }

    @FXML
    public void speedDownAction() { //speed controller

    }

    @FXML
    public void speedUpAction() { //speed controller

    }

    @FXML
    public void bandwidthUpAction(){ //freq controller

    }

    @FXML
    public void bandwidthDownAction(){ //freq controller

    }

    @FXML
    public void morseToTextAction() {
        morseCodeHandlerManager.morseToTextAction();
    }

    @FXML
    public void textToMorseAction() { //morse code controller

    }

    public void playBackAction() { //playback controller

    }
}