package edu.augustana.Application.UIHelper;

import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

public class MorseCodeTranslator {

    private static final Map<String, Character> MORSE_TO_CHAR = new HashMap<>();
    private static final Map<Character, String> CHAR_TO_MORSE = new HashMap<>();

    static {
        // Initialize the Morse code mappings
        String[] morse = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---",
                "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-",
                "..-", "...-", ".--", "-..-", "-.--", "--..", "-----", ".----", "..---",
                "...--", "....-", ".....", "-....", "--...", "---..", "----."};
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        for (int i = 0; i < morse.length; i++) {
            MORSE_TO_CHAR.put(morse[i], alphabet.charAt(i));
            CHAR_TO_MORSE.put(alphabet.charAt(i), morse[i]);
        }
    }

    public static String morseToText(String morseCode) {
        if (morseCode.isEmpty()) {
            return "";
        } else {
            StringBuilder text = new StringBuilder();
            String[] words = morseCode.split(" / "); // Use " / " as the word separator in Morse code
            for (String word : words) {
                String[] letters = word.split(" ");
                for (String letter : letters) {
                    Character ch = MORSE_TO_CHAR.get(letter);
                    if (ch != null) {
                        text.append(ch);
                    } else {
                        text.append("#");
                    }
                }
                text.append(" "); // Add space between words
            }
            return text.toString().trim();
        }

    }

    public static String textToMorse(String text) {
        StringBuilder morse = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            if (c == ' ') {
                morse.append(" / "); // Use " / " to represent a space between words
            } else {
                String morseChar = CHAR_TO_MORSE.get(c);
                if (morseChar != null) {
                    morse.append(morseChar).append(" ");
                }
            }
        }
        return morse.toString().trim();
    }
}