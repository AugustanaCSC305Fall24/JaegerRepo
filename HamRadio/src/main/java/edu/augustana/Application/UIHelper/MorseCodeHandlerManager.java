package edu.augustana.Application.UIHelper;

import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import javafx.scene.control.*;

public class MorseCodeHandlerManager {
    private TextArea translateTextField;
    private boolean isBandSelected;
    private HamRadioSimulatorInterface radio;
    String cleanMorse;
    String userOutput;

    private Text2MorseManager text2MorseManager;
    private Morse2TextManager morse2TextManager;

    public MorseCodeHandlerManager(TextArea translateTextField, HamRadioSimulatorInterface radio) {
        this.translateTextField = translateTextField;
        this.radio = radio;
        this.text2MorseManager = new Text2MorseManager(translateTextField, radio);
        this.morse2TextManager = new Morse2TextManager(translateTextField, radio);
    }

    void setUserOutput(String userOutput) {
        this.userOutput = userOutput;
        morse2TextManager.setUserOutput(userOutput);
    }

    void setCleanMorse(String cleanMorse) {
        this.cleanMorse = cleanMorse;
        morse2TextManager.setCleanMorse(cleanMorse);
    }

    public void setBandSelected(boolean bandSelected) {
        isBandSelected = bandSelected;
        text2MorseManager.setBandSelected(bandSelected);
        morse2TextManager.setBandSelected(bandSelected);
    }

    public void morseToTextAction() {
        morse2TextManager.morseToTextAction();
    }

    public void clearMorse(){
        morse2TextManager.clearMorse();
    }

    public String textToMorseAction() {
        return text2MorseManager.textToMorseAction();
    }
}