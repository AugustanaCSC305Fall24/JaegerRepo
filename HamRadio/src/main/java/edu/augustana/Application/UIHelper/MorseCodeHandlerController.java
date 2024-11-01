package edu.augustana.Application.UIHelper;

import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import javafx.scene.control.*;

public class MorseCodeHandlerController {
    private TextField translateTextField;
    private boolean isBandSelected;
    private HamRadioSimulatorInterface radio;
    String cleanMorse;
    String userOutput;

    private Text2MorseController text2MorseController;
    private Morse2TextController morse2TextController;

    public MorseCodeHandlerController(TextField translateTextField, HamRadioSimulatorInterface radio, Text2MorseController playerController, Morse2TextController displayerController) {
        this.translateTextField = translateTextField;
        this.radio = radio;
        this.text2MorseController = playerController;
        this.morse2TextController = displayerController;
    }

    void setUserOutput(String userOutput) {
        this.userOutput = userOutput;
        morse2TextController.setUserOutput(userOutput);
    }

    void setCleanMorse(String cleanMorse) {
        this.cleanMorse = cleanMorse;
        morse2TextController.setCleanMorse(cleanMorse);
    }

    public void setBandSelected(boolean bandSelected) {
        isBandSelected = bandSelected;
        text2MorseController.setBandSelected(bandSelected);
        morse2TextController.setBandSelected(bandSelected);
    }

    public void morseToTextAction() {
        morse2TextController.morseToTextAction();
    }

    public void textToMorseAction() {
        text2MorseController.textToMorseAction();
    }
}