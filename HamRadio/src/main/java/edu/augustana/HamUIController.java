package edu.augustana;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
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

    HamRadioClientInterface client = new HamRadioClient();

    private final double minTune = 0.1;
    private final double maxTune = 1.0;

    @FXML
    private void switchToWelcomeScreen() throws IOException {
        App.setRoot("WelcomeScreen");
    }

    @FXML
    public void initialize() {
        double customizedVolume = volumeSlider.getValue();
        client.setVolume(customizedVolume);
        dotButton.setOnAction(event -> client.playTone(1500, 100));  // DOT: 100 ms
        dashButton.setOnAction(event -> client.playTone(1500, 300)); // DASH: 300 ms
        rangeComboBox.getItems().addAll("HF", "VHF", "UHF");
        rangeComboBox.getSelectionModel().select(0);

        if(rangeComboBox.getSelectionModel().getSelectedItem().equals("HF")){
            bandComboBox.getItems().addAll("160m", "80m", "40m");
            bandComboBox.getSelectionModel().select(0);
        } else if (rangeComboBox.getSelectionModel().getSelectedItem().equals("VHF")){
            bandComboBox.getItems().addAll("6m", "2m");
            bandComboBox.getSelectionModel().select(0);
        } else if (rangeComboBox.getSelectionModel().getSelectedItem().equals("UHF")){
            bandComboBox.getItems().addAll("70cm", "33cm", "23cm");
            bandComboBox.getSelectionModel().select(0);
        }
    }

    @FXML
    private void startButton(){
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





}