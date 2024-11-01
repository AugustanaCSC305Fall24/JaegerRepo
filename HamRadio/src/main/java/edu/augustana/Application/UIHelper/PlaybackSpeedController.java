package edu.augustana.Application.UIHelper;

import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import javafx.scene.control.TextField;

public class PlaybackSpeedController {
    private TextField translateTextField;
    private boolean isBandSelected;
    private HamRadioSimulatorInterface radio;

    public PlaybackSpeedController(TextField translateTextField, HamRadioSimulatorInterface radio) {
        this.translateTextField = translateTextField;
        this.radio = radio;
    }

    public void setBandSelected(boolean bandSelected) {
        isBandSelected = bandSelected;
    }
}