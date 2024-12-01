package edu.augustana.Application.UI;

import edu.augustana.Application.UIHelper.Dictionary;
import edu.augustana.Application.UIHelper.MorseCodePlayer;
import edu.augustana.Application.UIHelper.MorseCodeTranslator;
import edu.augustana.RadioModel.HamRadioSimulator;
import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import edu.augustana.RadioModel.SoundPlayer;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;


public class BasicTrainingController {
    private String randomWord;
    private String randomMorse;
    private Dictionary dictionary;
    HamRadioSimulatorInterface radio;
    private boolean isLevelPicked;



    @FXML
    private TextField morseInputField;

    @FXML
    private TextField englishInputField;

    @FXML
    private Label resultLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Slider volumeSlider;

    @FXML
    private ComboBox levelComboBox;

    private int score = 0;
    private String result;

    @FXML
    public void initialize() throws IOException {
        // Initialize volume slider
        volumeSlider.setValue(50);

        // Set initial score label
        updateScoreLabel();
        radio =
                new HamRadioSimulator(600.0, 500.0,
                700.0, 600.0, 0.0, 0,
                1, 10);
        radio.setVolume(50);
        System.out.println(volumeSlider.getValue());
        dictionary = new Dictionary();
        levelComboBox.getItems().addAll(1,2);
    }

    @FXML
    private void playMorseCode() {
        if (!isLevelPicked) {
            new Alert(Alert.AlertType.ERROR, "Pick the level before you learn").show();
            return;
        }
        randomWord = dictionary.getRandomString();
        randomMorse = MorseCodeTranslator.textToMorse(randomWord);
        MorseCodePlayer player = new MorseCodePlayer(radio.getWPM(), radio);
        player.playMorse(randomMorse);
    }

    @FXML
    private void submitAnswer() {
        String userWordAnswer = englishInputField.getText().trim();
        String userMorseAnswer = morseInputField.getText().trim();
        if (userWordAnswer.isEmpty() || userMorseAnswer.isEmpty()) {
            String message = "You must enter something!!!";
            new Alert(Alert.AlertType.INFORMATION, message).show();
            return;
        }
        if (userWordAnswer.equalsIgnoreCase(randomWord)) {
            englishInputField.setText(userWordAnswer + " ✓");
        } else {
            englishInputField.setText(userWordAnswer + " ✗");
        }

        if (userMorseAnswer.equalsIgnoreCase(randomMorse)) {
            morseInputField.setText(userMorseAnswer + " ✓");
        } else {
            morseInputField.setText(userMorseAnswer + " ✗");
        }

        // Check if user's input matches the current Morse code
        if (userWordAnswer.equalsIgnoreCase(randomWord)
                && userMorseAnswer.equalsIgnoreCase(randomMorse)) {
            score++;
            result = "Correct!";


        } else {
            result = "Incorrect!";
        }
        setResultLabel();
        updateScoreLabel();
    }

    @FXML
    private void resetGame() {
        reset();
    }

    @FXML
    private void adjustVolume() {
        double customizedVolume = volumeSlider.getValue();
        radio.setVolume(customizedVolume);
        System.out.println(volumeSlider.getValue());
    }

    private void updateScoreLabel() {
        scoreLabel.setVisible(false);
        scoreLabel.setText("Score: " + score );
    }

    private void setResultLabel() {
        resultLabel.setText("Result: " + result);
    }

    private void reset() {
        score = 0;
        englishInputField.clear();
        morseInputField.clear();
        updateScoreLabel();
        result = "";
        setResultLabel();
    }
    @FXML
    private void switchToWelcomeScreen() throws IOException {
        App.setRoot("WelcomeScreen");
    }

    @FXML
    private void setDifficulty() {
        isLevelPicked = true;
        int level = (int) levelComboBox.getSelectionModel().getSelectedItem();
        dictionary.setDifficulty(level);
    }

    private int getDifficulty() {
        return (int) levelComboBox.getSelectionModel().getSelectedItem();
    }

}
