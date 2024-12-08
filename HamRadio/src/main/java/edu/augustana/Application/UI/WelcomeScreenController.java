package edu.augustana.Application.UI;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class WelcomeScreenController {
    @FXML
    private Label appTitleLabel;

    @FXML
    private Button logInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Label sloganLabel;

    @FXML
    private ImageView userAvatar;

    @FXML
    private Button basicTrainingBtn;

    @FXML
    private void switchToHamUI() throws IOException {
        App.setRoot("HamGUI");
    }
    @FXML
    private void switchToHamPractice() throws IOException {
        App.setRoot("ScenarioOption");
    }

    @FXML
    private void switchtoBasicTraining() throws IOException {
        App.setRoot("BasicTraining");
    }


}