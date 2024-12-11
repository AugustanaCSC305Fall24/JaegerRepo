package edu.augustana.Application.UIHelper;

import edu.augustana.Application.UI.HamGUIController;
import edu.augustana.RadioModel.HamRadioSimulatorInterface;

public class VolumeManager {
    private HamGUIController controller;

    public VolumeManager(HamGUIController controller) {
        this.controller = controller;
    }

    public void volumeSliderAction() {
        HamRadioSimulatorInterface radio = controller.getRadio();
        double customizedVolume = controller.getVolumeControl();
        radio.setVolume(customizedVolume);
        String displayText = controller.displayTextString();
    }
}