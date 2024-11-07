package edu.augustana.Application.UIHelper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class Dictionary {
    int numOfTimes;
    private Set<String> characterSet;
    private Random random;

    // Constructor to initialize the set
    public Dictionary() {
        characterSet = new HashSet<>();
        random = new Random();
        numOfTimes = 1;

        // Add characters A-Z
        for (char c = 'A'; c <= 'Z'; c++) {
            characterSet.add(String.valueOf(c));
        }

        // Add numbers 0-9
        for (char c = '0'; c <= '9'; c++) {
            characterSet.add(String.valueOf(c));
        }

        // Add 30 random words with a maximum of 3 characters
        String[] randomWords = {
                "cat", "dog", "ant", "bat", "owl",
                "rat", "pig", "cow", "bee", "ram",
                "fox", "hen", "ape", "elk", "cod",
                "jay", "emu", "eel", "bug", "orb",
                "sap", "fig", "oak", "ash", "fir",
                "ice", "sun", "ink", "lip", "joy"
        };
        characterSet.addAll(Arrays.asList(randomWords));
    }

    // Getter to access the set
    public Set<String> getCharacterSet() {
        return characterSet;
    }

    // Method to display the set
    public void displaySet() {
        System.out.println("Set of characters, numbers, and random words:");
        System.out.println(characterSet);
    }

    // Method to return a random string from the set
    public String getRandomString() {
        if (numOfTimes == 1) {
            numOfTimes++;
            return "cq";
        } else if (numOfTimes == 2) {
            numOfTimes++;
            return "A";

        } else if (numOfTimes == 3) {
            numOfTimes++;
            return "B";

        } else {
            numOfTimes++;
            int index = random.nextInt(characterSet.size());
            return characterSet.toArray(new String[0])[index];
        }
    }
}
