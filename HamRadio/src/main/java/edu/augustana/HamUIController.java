package edu.augustana;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.shape.Circle;

public class HamUIController {
    @FXML
    private ComboBox<String> bandComboBox;

    @FXML
    private Button helpBtn;

    @FXML
    private Button moreBtn;

    @FXML
    private Button returnBtn;

    @FXML
    private Button trainingBtn;

    @FXML
    private Slider volumeSlider;

    @FXML
    private void switchToWelcomeScreen() throws IOException {
        App.setRoot("WelcomeScreen");
    }

}