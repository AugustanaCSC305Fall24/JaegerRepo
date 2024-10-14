package edu.augustana;

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

    public String morseToText(String morseCode) {
        StringBuilder text = new StringBuilder();
        String[] words = morseCode.split("   ");
        for (String word : words) {
            String[] letters = word.split(" ");
            for (String letter : letters) {
                Character ch = MORSE_TO_CHAR.get(letter);
                if (ch != null) {
                    text.append(ch);
                }
            }
            text.append(" ");
        }
        return text.toString().trim();
    }

    public String textToMorse(String text) {
        StringBuilder morse = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            String morseChar = CHAR_TO_MORSE.get(c);
            if (morseChar != null) {
                morse.append(morseChar).append(" ");
            } else if (c == ' ') {
                morse.append("  ");
            }
        }
        return morse.toString().trim();
    }

}
