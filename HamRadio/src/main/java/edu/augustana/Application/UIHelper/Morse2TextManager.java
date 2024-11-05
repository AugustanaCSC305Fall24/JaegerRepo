package edu.augustana.Application.UIHelper;

import edu.augustana.Application.UI.HelperClass;
import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import edu.augustana.Application.UI.App;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
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
        App.getKeyBindManager().registerKeybind(KeyCode.SHIFT, this::onDotKeyPressed, this::onDotKeyReleased);

        String morseToText = translator.morseToText(cleanMorse);
        translateTextField.setText("You typed: " + userOutput + "\n" + "Translated as: " + morseToText);
    }

    private void onDotKeyPressed() {
        currentPressTime = System.currentTimeMillis();

        //compute the duration between this press and the last press
        long timeSinceLastPress;
        if (isFirstTime) {
            timeSinceLastPress = 0;
            isFirstTime = false;
        } else {
            timeSinceLastPress = currentPressTime - lastPressTime;
        }

        //compute the number of space based on duration
        int numOfSpaces = HelperClass.calculateSpaces(radio.getWPM(), timeSinceLastPress);

        String spaces = HelperClass.multiplyingSpace(numOfSpaces);

        cleanMorse += spaces;
        userOutput += " " + spaces;

        loopAddingMorse(".");
    }

    private void onDashKeyPressing() {

    }

    private void addDot() {
        addMorse(".");
    }

    private void addMorse(String morse) {
        cleanMorse += morse;
        userOutput += " " + morse;
        updateTranslateTextField();
    }

    private void onDotKeyReleased() {
        isKeyRelease = true;
        lastPressTime = currentPressTime;
    }

    private void onDashKeyReleased() {

    }

    private void loopAddingMorse(String morseCode) {
        new Thread(() -> {
            boolean isFirst = true;
            long duration;
            long rightNow;
            long lastTime = 0;
            do {
                rightNow = System.currentTimeMillis();
                duration = rightNow - lastTime;
                if (isFirst || duration == HelperClass.unitOfTime(radio.getWPM())) {
                    //add morseCode into cleanMorse
                    cleanMorse += morseCode;
                    userOutput += " " + morseCode;
                    //display the output:
                    updateTranslateTextField();

                    //update lastTime
                    lastTime = rightNow;

                    //set the boolean to false
                    isFirst = false;
                }
            } while (!isKeyRelease);
        }).start();
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
        long timeSinceLastPress;
        if (isFirstTime) {
            timeSinceLastPress = 0;
            isFirstTime = false;
        } else {
            timeSinceLastPress = currentPressTime - lastPressTime;
        }
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

        // Append the spaces and nextMorseCode to cleanMorseCode
        cleanMorse += HelperClass.nextMorseCodeForCleanMorse(nextMorseCode, numOfSpaces);
        userOutput += HelperClass.nextMorseCodeForUserOutput(nextMorseCode, numOfSpaces);

        //update translate text field
        updateTranslateTextField();
    }

    // Updates the TextField to display the current Morse sequence
    private void updateTranslateTextField() {
        System.out.println("textfield updated");
        translateTextField.setText("Current Morse Input: " + cleanMorse);
    }
}