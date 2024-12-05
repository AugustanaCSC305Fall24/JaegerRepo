package edu.augustana.Application.UIHelper;

import edu.augustana.Application.UI.HamGUIController;
import edu.augustana.RadioModel.HamRadioSimulator;
import edu.augustana.RadioModel.HamRadioSimulatorInterface;

public class WPMManager {
    private HamGUIController controller;

    public WPMManager(HamGUIController controller) {
        this.controller = controller;
    }

    public void selectWPMAction() {
        HamRadioSimulatorInterface radio = controller.getRadio();
        int wpm = controller.getWPMControl();
        radio.setWPM(wpm);
        System.out.println("wpm: " + radio.getWPM());
    }
}