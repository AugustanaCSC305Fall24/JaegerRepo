package edu.augustana.Application.UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;


public class BasicTrainingController {

    @FXML
    private Button playButton;

    @FXML
    private TextField inputField;

    @FXML
    private Button submitButton;

    @FXML
    private Label feedbackLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Button resetButton;

    @FXML
    private Slider volumeSlider;

    private int score = 0;
    private int attempts = 0;
    private String currentMorseCode = "..."; // Placeholder for Morse code to be played

    @FXML
    public void initialize() {
        // Initialize volume slider
        volumeSlider.setValue(50);
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> adjustVolume(newVal.doubleValue()));

        // Set initial score label
        updateScoreLabel();
    }

    @FXML
    private void playMorseCode() {
        // For testing, set the current Morse code pattern (this should match the played code)
        currentMorseCode = "... --- ..."; // Example: "SOS" in Morse code
    }

    @FXML
    private void submitAnswer() {
        String userAnswer = inputField.getText().trim();
        if (userAnswer.isEmpty()) {
            feedbackLabel.setText("Please enter your interpretation.");
            return;
        }

        // Check if user's input matches the current Morse code
        if (userAnswer.equalsIgnoreCase(morseCodeToText(currentMorseCode))) {
            feedbackLabel.setText("Correct!");
            score++;
        } else {
            feedbackLabel.setText("Incorrect. Try again.");
        }

        attempts++;
        updateScoreLabel();
        inputField.clear();
    }

    @FXML
    private void resetGame() {
        score = 0;
        attempts = 0;
        feedbackLabel.setText("");
        inputField.clear();
        updateScoreLabel();
    }

    @FXML
    private void adjustVolume(double newVolume) {

    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score + "/" + attempts);
    }

    // Placeholder method to convert Morse code to text
    private String morseCodeToText(String morseCode) {
        // For now, just return "SOS" if it matches the "..." format
        // You should replace this with a real Morse code translator
        if ("... --- ...".equals(morseCode)) {
            return "SOS";
        }
        return "";
    }
}
