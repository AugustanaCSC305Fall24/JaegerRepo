package edu.augustana;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
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

    private HamRadioClient hamRadioClient;

    public HamUIController() {
        hamRadioClient = new HamRadioClient();
    }

    @FXML
    private void switchToWelcomeScreen() throws IOException {
        App.setRoot("WelcomeScreen");
    }

    @FXML
    public void initialize() {
        dotButton.setOnAction(event -> hamRadioClient.playTone(1500, 100));  // DOT: 100 ms
        dashButton.setOnAction(event -> hamRadioClient.playTone(1500, 300)); // DASH: 300 ms
    }







}