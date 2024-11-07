package edu.augustana.Application.UIHelper;

import edu.augustana.Application.UI.HelperClass;
import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import edu.augustana.Application.UI.App;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Morse2TextManager {
    private TextArea translateTextField;
    private boolean isBandSelected;
    private HamRadioSimulatorInterface radio;
    private MorseCodeTranslator translator;
    String cleanMorse;
    String userOutput;
    private boolean isFirstTime = true;
    private long lastPressTime; // Stores the time of the last key press
    private long currentPressTime;
    private long currentReleaseTime;
    private static final long DOT_THRESHOLD = 300;// Threshold for letter separation// Threshold for word separation
    boolean isKeyRelease;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Morse2TextManager(TextArea translateTextField, HamRadioSimulatorInterface radio) {
        this.translateTextField = translateTextField;
        this.radio = radio;
        this.translator = new MorseCodeTranslator(translateTextField, radio);
        this.cleanMorse = "";
        this.userOutput = "";
        this.isKeyRelease = true;
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
       // System.out.println(morseToText);
//        if (!cleanMorse.isEmpty() && morseToText.isEmpty()) {
//            morseToText = "Invalid Morse Code";
//        }
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
        lastPressTime = currentReleaseTime;
    }

    private void displayMorseCode(long pressDuration) {
        System.out.println("displayMorseCode runs");
        long timeSinceLastPress;
        if (isFirstTime) {
            timeSinceLastPress = 0;
            isFirstTime = false;
        } else {
            timeSinceLastPress = currentPressTime - lastPressTime;
        }
        if (pressDuration <= 3 * HelperClass.unitOfTime(radio.getWPM())) {
            display(".", timeSinceLastPress);
        } else { //then display dash
            display("-", timeSinceLastPress);
        }
    }

    private void display(String nextMorseCode, long timeSinceLastPress) {
        System.out.println("UOF: " + HelperClass.unitOfTime(radio.getWPM()));
        System.out.println("display runs");
        if (timeSinceLastPress < 6 * HelperClass.unitOfTime(radio.getWPM())) {
            cleanMorse += nextMorseCode;
            userOutput += nextMorseCode;
        } else if (timeSinceLastPress < 30 * HelperClass.unitOfTime(radio.getWPM())) {
            cleanMorse += " " + nextMorseCode;
            userOutput += "  " + nextMorseCode;
        } else {
            cleanMorse += " / " + nextMorseCode;
            userOutput += "  / " + nextMorseCode;
        }

        //update translate text field
        updateTranslateTextField();
    }

    // Updates the TextField to display the current Morse sequence
    private void updateTranslateTextField() {
        System.out.println("textfield updated");
        translateTextField.setFont(Font.font(18));
        translateTextField.setText(userOutput);
    }
}