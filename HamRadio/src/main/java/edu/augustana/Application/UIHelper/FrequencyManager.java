package edu.augustana.Application.UIHelper;

import edu.augustana.Application.UI.HamGUIController;
import edu.augustana.Application.UI.HelperClass;
import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FrequencyManager {
    private final double MIN_TUNE = 0.1;
    private final double MAX_TUNE = 1.0;
    private HamGUIController controller;

    public FrequencyManager(HamGUIController controller) {
        this.controller = controller;
    }

    public void setStartClicked(boolean startClicked) {
        controller.setIsStartClicked(startClicked);
    }

    public void changeTransmittedFrequencyController() {
        controller.getRadio().setTransmitFrequency(controller.getTransmitFrequencyControl());
        controller.setStatusTextControl(controller.displayTextString());
    }

    public void changeReceivedFrequencyController() {
        controller.getRadio().setReceiveFrequency(controller.getReceivedFrequencyControl());
        controller.setStatusTextControl(controller.displayTextString());
    }

    public void bandWidthAction() {
        double bandWidth = controller.getBandwidthControl();
        controller.getRadio().setBandWidth(bandWidth);
    }












}