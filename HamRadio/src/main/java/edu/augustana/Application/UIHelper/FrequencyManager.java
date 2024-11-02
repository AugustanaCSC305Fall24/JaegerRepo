package edu.augustana.Application.UIHelper;

import edu.augustana.Application.UI.HelperClass;
import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FrequencyManager {
    private final double MIN_TUNE = 0.1;
    private final double MAX_TUNE = 1.0;
    private HamRadioSimulatorInterface radio;
    private BandSelectionCallback callback;
    private boolean startClicked;

    private ComboBox<String> rangeComboBox; //this class

    private ComboBox<String> bandComboBox; //this class

    private TextArea displayTextArea; //this class

    public FrequencyManager(HamRadioSimulatorInterface radio, ComboBox<String> rangeComboBox, ComboBox<String> bandComboBox
            , TextArea displayTextArea, BandSelectionCallback callback) {
        this.radio = radio;
        this.rangeComboBox = rangeComboBox;
        this.bandComboBox = bandComboBox;
        this.displayTextArea = displayTextArea;
        this.callback = callback;
    }

    public void setStartClicked(boolean startClicked) {
        this.startClicked = startClicked;
    }

    private void selectRangeAction(){ //Frequency controller
        callback.onBandSelection(true);
        if (rangeComboBox.getSelectionModel().getSelectedItem().equals("HF")) {
            bandComboBox.getItems().clear();
            bandComboBox.getItems().addAll("160m", "80m", "40m");
            bandComboBox.getSelectionModel().select(0);
        } else if (rangeComboBox.getSelectionModel().getSelectedItem().equals("VHF")) {
            bandComboBox.getItems().clear();
            bandComboBox.getItems().addAll("6m", "2m");
            bandComboBox.getSelectionModel().select(0);
        } else if (rangeComboBox.getSelectionModel().getSelectedItem().equals("UHF")) {
            bandComboBox.getItems().clear();
            bandComboBox.getItems().addAll("70cm", "33cm", "23cm");
            bandComboBox.getSelectionModel().select(0);
        }

        String band = bandComboBox.getSelectionModel().getSelectedItem();
        if (band.equals("160m")) {
            initialize_frequency(1.8, 2.0);
        } else if (band.equals("80m")) {
            initialize_frequency(3.5, 4.0);
        } else if (band.equals("40m")) {
            initialize_frequency(7.0, 7.3);
        } else if (band.equals("6m")) {
            initialize_frequency(50.0, 54.0);
        } else if (band.equals("2m")) {
            initialize_frequency(144.0, 148.0);
        } else if (band.equals("70cm")) {
            initialize_frequency(420.0, 450.0);
        } else if (band.equals("33cm")) {
            initialize_frequency(902.0, 928.0);
        } else if (band.equals("23cm")) {
            initialize_frequency(124.0, 130.0);
        }
        if(startClicked){
            displayTextArea.setText(HelperClass.displayTextString(radio));
        } else {
            displayTextArea.setText("Your received frequency: " + radio.getReceiveFrequency() + "MHz \n" +
                    "Your transmit frequency: " + radio.getTransmitFrequency() + "MHz\n" +
                    "Frequency Bandwidth: " + radio.getBandWidth()
                    + "\nPlease hit Start to transmit and" + " receive CW signal" + "\n");
        }
    }

    private void tuneRUpButton() { //freqController
        String range = rangeComboBox.getSelectionModel().getSelectedItem();
        if (range.equals("HF")) {
            radio.setReceiveFrequency(radio.getReceiveFrequency() + MIN_TUNE);
        } else {
            radio.setReceiveFrequency(radio.getReceiveFrequency() + MAX_TUNE);
        }
        if(!startClicked){
            displayTextArea.setText("Your received frequency: " + radio.getReceiveFrequency() + "MHz \n" +
                    "Your transmit frequency: " + radio.getTransmitFrequency() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");

        } else {
            displayTextArea.setText("Your received frequency changed!\n" + HelperClass.displayTextString(radio));
        }
    }

    private void tuneRDownButton() { //freqController
        String range = rangeComboBox.getSelectionModel().getSelectedItem();
        if (range.equals("HF")) {
            radio.setReceiveFrequency(radio.getReceiveFrequency() - MIN_TUNE);
        } else {
            radio.setReceiveFrequency(radio.getReceiveFrequency() - this.MAX_TUNE);
        }
        if(!startClicked){
            displayTextArea.setText("Your received frequency: " + radio.getReceiveFrequency() + "MHz \n" +
                    "Your transmit frequency: " + radio.getTransmitFrequency() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");

        } else {
            displayTextArea.setText("Your received frequency changed!\n" + HelperClass.displayTextString(radio));
        }
    }

    private void tuneTDownButton() {
        String range = rangeComboBox.getSelectionModel().getSelectedItem();
        if (range.equals("HF")) {
            radio.setTransmitFrequency(radio.getTransmitFrequency() - MIN_TUNE);
        } else {
            radio.setTransmitFrequency(radio.getTransmitFrequency() - MAX_TUNE);
        }
        if(!startClicked){
            displayTextArea.setText("Your received frequency: " + radio.getReceiveFrequency() + "MHz \n" +
                    "Your transmit frequency: " + radio.getTransmitFrequency() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");
        } else {
            displayTextArea.setText("Your transmit frequency changed!\n" + HelperClass.displayTextString(radio));
        }
    }

    private void tuneTUpButton() {
        String range = rangeComboBox.getSelectionModel().getSelectedItem();
        if (range.equals("HF")) {
            radio.setTransmitFrequency(radio.getTransmitFrequency() - MIN_TUNE);
        } else {
            radio.setTransmitFrequency(radio.getTransmitFrequency() - MAX_TUNE);
        }
        if(!startClicked){
            displayTextArea.setText("Your received frequency: " + radio.getReceiveFrequency() + "MHz \n" +
                    "Your transmit frequency: " + radio.getTransmitFrequency() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");
        } else {
            displayTextArea.setText("Your transmit frequency changed!\n" + HelperClass.displayTextString(radio));
        }
    }

    public void bandwidthUpAction(){ //freq controller
        String range = rangeComboBox.getSelectionModel().getSelectedItem();
        if (range.equals("HF")) {
            radio.setBandWidth(radio.getBandWidth() + MIN_TUNE);
        } else {
            radio.setBandWidth(radio.getBandWidth() + MAX_TUNE);
        }

        if(!startClicked){
            displayTextArea.setText("Your received frequency: " + radio.getReceiveFrequency() + "MHz \n" +
                    "Your transmit frequency: " + radio.getTransmitFrequency() + "MHz \n" +
                    "Your bandwidth: " + radio.getBandWidth() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");
        } else {
            displayTextArea.setText("Your bandwidth changed to \n" + HelperClass.displayTextString(radio));
        }
    }

    public void bandwidthDownAction(){ //freq controller
        String range = rangeComboBox.getSelectionModel().getSelectedItem();
        if (range.equals("HF")) {
            radio.setBandWidth(radio.getBandWidth() - MIN_TUNE);
        } else {
            radio.setBandWidth(radio.getBandWidth() - MAX_TUNE);
        }

        if(!startClicked){
            displayTextArea.setText("Your received frequency: " + radio.getReceiveFrequency() + "MHz \n" +
                    "Your transmit frequency: " + radio.getTransmitFrequency() + "MHz \n" +
                    "Your bandwidth: " + radio.getBandWidth() + "MHz \n"
                    + "Please hit Start to transmit and" + " receive CW signal" + "\n");
        } else {
            displayTextArea.setText("Your bandwidth changed to \n" + HelperClass.displayTextString(radio));
        }
    }

    private void initialize_frequency(double min, double max) { //freqController
        radio.setMinReceiveFrequency(min);
        radio.setMaxReceiveFrequency(max);
        radio.setReceiveFrequency((radio.getMaxReceiveFrequency() + radio.getMinReceiveFrequency()) / 2);
        radio.setTransmitFrequency((radio.getMaxReceiveFrequency() + radio.getMinReceiveFrequency()) / 2);
    }
}