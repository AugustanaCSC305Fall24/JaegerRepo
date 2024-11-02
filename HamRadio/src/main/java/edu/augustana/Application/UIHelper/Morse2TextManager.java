package edu.augustana.Application.UIHelper;

import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import edu.augustana.Application.UI.App;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private static final long LETTER_GAP = 300;  // Threshold for letter separation
    private static final long WORD_GAP = 700;    // Threshold for word separation

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
        //set the algoithm to bind with "enter" and "shift"
        App.getKeyBindManager().registerKeybind(KeyCode.ENTER, this::onDot);
        App.getKeyBindManager().registerKeybind(KeyCode.SHIFT, this::onDash);

        String morseToText = translator.morseToText(cleanMorse);
        translateTextField.setText("You typed: " + userOutput + "\n" + "Translated as: " + morseToText);
    }

    //private methods
    private void onDot() {
        onClick(".");
    }
    private void onDash() {
        onClick("-");
    }
    // This method is called when a dot or dash key is clicked, passing either "." or "-" as 'key'
    private void onClick(String key) {
        // Stop the timer and calculate time since last press
        long currentTime = System.currentTimeMillis();
        long timeSinceLastPress = currentTime - lastPressTime;

        // Determine placement based on time since last press
        if (timeSinceLastPress >= WORD_GAP) {
            // Long gap, add three spaces (word separator)
            cleanMorse += "   " + key;
            userOutput += "    " + key;
        } else if (timeSinceLastPress >= LETTER_GAP) {
            // Medium gap, add one space (letter separator)
            cleanMorse += " " + key;
            userOutput += "  ";
        } else {
            // Short gap, add directly without spacing (same letter)
            cleanMorse += key;
            userOutput += " " + key;
        }

        // Update lastPressTime for the next click
        lastPressTime = currentTime;

        // Update the display to show the Morse code sequence
        updateTranslateTextField();
    }

    // Updates the TextField to display the current Morse sequence
    private void updateTranslateTextField() {
        translateTextField.setText("Current Morse Input: " + cleanMorse);
    }
}