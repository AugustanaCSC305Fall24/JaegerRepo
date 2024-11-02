package edu.augustana.Application.UI;

import edu.augustana.RadioModel.HamRadioSimulatorInterface;

public class HelperClass {
    public static String displayTextString(HamRadioSimulatorInterface radio) { //TextFieldController
        String radioStatus = "Your received frequency: " + radio.getReceiveFrequency() + "MHz \n" +
                "Your transmit frequency: " + radio.getTransmitFrequency() + "MHz \n"+
                "Radio Status: Connected. You can start transmitting right now." +
                "\n" + "Radio Volume: " + radio.getVolume() +
                "\n" + "Radio Playback Speed: " + radio.getPlaybackSpeed() +
                "\n" + "Frequency Bandwidth: " + radio.getBandWidth();

        return radioStatus;
    }
}