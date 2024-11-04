package edu.augustana.Application.UIHelper;

import edu.augustana.Application.UI.HelperClass;
import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import edu.augustana.Application.UI.App;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

public class Morse2TextManager {
    private TextArea translateTextField;
    private boolean isBandSelected;
    private HamRadioSimulatorInterface radio;
    private MorseCodeTranslator translator;
    String cleanMorse;
    String userOutput;
    KeyBindManager manager;
    private long lastPressTime; // Stores the time of the last key press
    private long currentPressTime;
    private long currentReleaseTime;
    private static final long DOT_THRESHOLD = 300;// Threshold for letter separation// Threshold for word separation

    public Morse2TextManager(TextArea translateTextField, HamRadioSimulatorInterface radio) {
        this.translateTextField = translateTextField;
        this.radio = radio;
        this.translator = new MorseCodeTranslator(translateTextField, radio);
        this.cleanMorse = "";
        this.userOutput = "";
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
        System.out.println("morseToTextAction runs");
        //set the algoithm to bind with "enter" and "shift"
        App.getKeyBindManager().registerKeybind(KeyCode.SHIFT, this::onKeyPressed, this::onKeyReleased);

        String morseToText = translator.morseToText(cleanMorse);
        translateTextField.setText("You typed: " + userOutput + "\n" + "Translated as: " + morseToText);
    }

    private void onKeyPressed() {
        // Stop the timer and calculate current time
        currentPressTime = System.currentTimeMillis();
    }

    private void onKeyReleased() {
        //Stop the timer and calculate current time
        currentReleaseTime = System.currentTimeMillis();
        long duration = currentReleaseTime - currentPressTime;
        displayMorseCode(duration);
        lastPressTime = currentPressTime;
    }

    private void displayMorseCode(long pressDuration) {
        System.out.println("displayMorseCode runs");
        long timeSinceLastPress = currentPressTime - lastPressTime;
        if (pressDuration <= DOT_THRESHOLD) {
            display(".", timeSinceLastPress);
        } else { //then display dash
            display("-", timeSinceLastPress);
        }
    }

    private void display(String nextMorseCode, long timeSinceLastPress) {
        System.out.println("display runs");
        int numOfSpaces = HelperClass.calculateSpaces(radio.getWPM(), timeSinceLastPress);
        System.out.println("Calculate space really is finished: " + numOfSpaces);

        // Create a string with the calculated number of spaces
        String spaces = "";
        for (int i = 0; i < numOfSpaces; i++) {
            spaces += " ";
        }
        System.out.println("update spacing ez");

        // Append the spaces and nextMorseCode to cleanMorseCode
        cleanMorse += spaces + nextMorseCode;
        userOutput += spaces + " " + nextMorseCode;

        //update translate text field
        updateTranslateTextField();
    }

    // Updates the TextField to display the current Morse sequence
    private void updateTranslateTextField() {
        System.out.println("textfield updated");
        translateTextField.setText("Current Morse Input: " + cleanMorse);
    }
}