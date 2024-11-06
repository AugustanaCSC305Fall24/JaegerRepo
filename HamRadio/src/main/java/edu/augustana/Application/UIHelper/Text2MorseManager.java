package edu.augustana.Application.UIHelper;

import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Scanner;

public class Text2MorseManager {
    private TextArea translateTextField;
    private boolean isBandSelected;
    private HamRadioSimulatorInterface radio;
    private PlaybackSpeedManager speedController;
    private MorseCodeTranslator translator;
    private MorseCodePlayer player;

    public Text2MorseManager(TextArea translateTextField, HamRadioSimulatorInterface radio) {
        this.translateTextField = translateTextField;
        this.radio = radio;
        this.speedController = new PlaybackSpeedManager(translateTextField, radio);
        this.translator = new MorseCodeTranslator(translateTextField, radio);
        this.player = new MorseCodePlayer((int) radio.getPlaybackSpeed(), radio);
    }

    public void setBandSelected(boolean bandSelected) {
        isBandSelected = bandSelected;
        speedController.setBandSelected(bandSelected);
        translator.setBandSelected(bandSelected);
    }

    public String textToMorseAction() {
        String userOutput = "";
        if (!isBandSelected){
            String message = "Please hit Start and type in before Playback!";
            new Alert(Alert.AlertType.INFORMATION, message).show();
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Type English to be translated: ");
            String textToBeTranslated = scanner.nextLine();
            String textToMorse = translator.textToMorse(textToBeTranslated);
            userOutput += textToMorse;
            translateTextField.setText("You typed: " + textToBeTranslated + "\n"
                    + "Translated as: " + textToMorse);

        }
        return userOutput;
    }

}