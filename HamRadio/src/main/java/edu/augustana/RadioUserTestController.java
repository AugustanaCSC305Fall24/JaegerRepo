package edu.augustana;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class RadioUserTestController extends HamUIController{

    private HamRadioClientInterface client;

    @FXML
    private TextArea CWTextArea;

    @FXML
    private TextArea englishTextArea;

    @FXML
    private TextField frequencyArea;

    @FXML
    private Slider volumeSlider;

    @FXML
    public void speedDownAction() {
        super.speedDownAction();
    }

    @FXML
    public void speedUpAction() {
        super.speedUpAction();
    }

    @FXML
    public void volumeChangeAction() {
        super.volumeSliderAction();
    }

    @FXML public void initialize(){
        setClient(this.client);
    }

    @FXML private void switchToHamUI() throws IOException {
        App.setRoot("HamUI");
    }
}
