package edu.augustana.Application.UIHelper;

import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PlaybackSpeedManager {
    private TextArea translateTextField;
    private boolean isBandSelected;
    private HamRadioSimulatorInterface radio;

    public PlaybackSpeedManager(TextArea translateTextField, HamRadioSimulatorInterface radio) {
        this.translateTextField = translateTextField;
        this.radio = radio;
    }

    public void setBandSelected(boolean bandSelected) {
        isBandSelected = bandSelected;
    }
}