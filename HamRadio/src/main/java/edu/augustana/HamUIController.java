package edu.augustana;

import java.io.IOException;

import javafx.event.ActionEvent;
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
    private void switchToWelcomeScreen() throws IOException {
        App.setRoot("WelcomeScreen");
    }



}