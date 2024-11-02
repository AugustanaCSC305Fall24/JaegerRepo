package edu.augustana.Application.UIHelper;

import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class Morse2TextController {
    private TextField translateTextField;
    private boolean isBandSelected;
    private HamRadioSimulatorInterface radio;
    private MorseCodeTranslator translator;
    String cleanMorse;
    String userOutput;

    public Morse2TextController(TextField translateTextField, HamRadioSimulatorInterface radio, MorseCodeTranslator translator) {
        this.translateTextField = translateTextField;
        this.radio = radio;
        this.translator = translator;
    }

    public void setBandSelected(boolean bandSelected) {
        this.isBandSelected = bandSelected;
        translator.setBandSelected(bandSelected);
    }

    public void setUserOutput(String userOutput) {
        this.userOutput = userOutput;
    }

    public void setCleanMorse(String cleanMorse) {
        this.cleanMorse = cleanMorse;
    }

    public void morseToTextAction() {
        if (!isBandSelected){
            String message = "Please hit Start and type in before Playback!";
            new Alert(Alert.AlertType.INFORMATION, message).show();
        }
        String morseToText = translator.morseToText(cleanMorse);
        translateTextField.setText("You typed: " + userOutput + "\n" + "Translated as: " + morseToText);
    }
}