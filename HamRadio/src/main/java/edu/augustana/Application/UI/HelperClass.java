package edu.augustana.Application.UI;

import edu.augustana.RadioModel.HamRadioSimulatorInterface;

import javax.sound.sampled.*;

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

    public static int calculateSpaces(double wpm, long timeDuration) {
        System.out.println("calculateSpaces runs");
        System.out.println(timeDuration);
        // Convert WPM to characters per minute (assuming 5 characters per word)
        double charactersPerMinute = 40 * 5;

        // Convert characters per minute to characters per second
        double charactersPerSecond = charactersPerMinute / 60.0;

        // Calculate time per character in milliseconds
        double timePerCharacter = 1000 / charactersPerSecond;
        System.out.println(timePerCharacter);

        // Calculate the number of characters that can fit in the given time duration
        int characterCount = (int)(timeDuration / timePerCharacter);
        System.out.println("Calculate space finished: " + characterCount);

        // Calculate the number of spaces (gaps), which is one less than the character count
        return characterCount > 1 ? characterCount - 1 : 0;
    }
    
    private static String multiplyingSpace(int numOfSpaces) {
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < numOfSpaces; i++) {
            spaces.append(" ");
        }
        return spaces.toString();
    }

    public static String nextMorseCodeForCleanMorse(String nextMorse, int numOfSpaces) {
        return multiplyingSpace(numOfSpaces) + nextMorse;
    }

    public static String nextMorseCodeForUserOutput(String nextMorse, int numOfSpaces) {
        return " " + nextMorseCodeForCleanMorse(nextMorse, numOfSpaces);
    }

}