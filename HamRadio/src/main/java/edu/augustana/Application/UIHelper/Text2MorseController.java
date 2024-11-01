package edu.augustana.Application.UIHelper;

import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.Scanner;

public class Text2MorseController {
    private TextField translateTextField;
    private boolean isBandSelected;
    private HamRadioSimulatorInterface radio;
    private PlaybackSpeedController speedController;
    private MorseCodeTranslator translator;
    private MorseCodePlayer player;

    public Text2MorseController(TextField translateTextField, HamRadioSimulatorInterface radio,
                                MorseCodeTranslator translator, MorseCodePlayer player) {
        this.translateTextField = translateTextField;
        this.radio = radio;
        this.speedController = new PlaybackSpeedController(translateTextField, radio);
        this.translator = new MorseCodeTranslator(translateTextField, radio);
        //this.player = new MorseCodePlayer(speedFactor, radio);
    }

    public void setBandSelected(boolean bandSelected) {
        isBandSelected = bandSelected;
        speedController.setBandSelected(bandSelected);
        translator.setBandSelected(bandSelected);
    }

    public void textToMorseAction() {
        if (!isBandSelected){
            String message = "Please hit Start and type in before Playback!";
            new Alert(Alert.AlertType.INFORMATION, message).show();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type English to be translated: ");
        String textToBeTranslated = scanner.nextLine();
        String textToMorse = translator.textToMorse(textToBeTranslated);
        translateTextField.setText("You typed: " + textToBeTranslated + "\n"
                + "Translated as: " + textToMorse);
    }


}