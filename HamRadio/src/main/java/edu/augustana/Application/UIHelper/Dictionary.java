package edu.augustana.Application.UIHelper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;


public class Dictionary {
    private String[] words;
    private String[] sentences;
    private int difficulty;
    private Random random;
    private int numOfTimes;

    // Constructor to initialize the arrays and variables
    public Dictionary() {
        random = new Random();
        numOfTimes = 1;
        difficulty = 1; // Default difficulty

        // Initialize the words array
        words = new String[] {
                "cat", "dog", "ant", "bat", "owl",
                "rat", "pig", "cow", "bee", "ram",
                "fox", "hen", "ape", "elk", "cod",
                "jay", "emu", "eel", "bug", "orb",
                "sap", "fig", "oak", "ash", "fir",
                "ice", "sun", "ink", "lip", "joy"
        };

        // Initialize the sentences array
        sentences = new String[] {
                "The quick brown fox jumps over the lazy dog.",
                "A journey of a thousand miles begins with a single step.",
                "To be or not to be, that is the question.",
                "All that glitters is not gold.",
                "A picture is worth a thousand words.",
                "Fortune favors the bold.",
                "Actions speak louder than words.",
                "The pen is mightier than the sword.",
                "An apple a day keeps the doctor away.",
                "When in Rome, do as the Romans do."
        };
    }

    // Setter for difficulty
    public void setDifficulty(int difficulty) {
        if (difficulty == 1 || difficulty == 2) {
            this.difficulty = difficulty;
        } else {
            throw new IllegalArgumentException("Difficulty must be 0 or 1.");
        }
    }

    // Getter for difficulty
    public int getDifficulty() {
        return difficulty;
    }

    // Method to return a random string based on difficulty
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
            if (difficulty == 1) {
                // Difficulty 0: Pick a random word
                return words[random.nextInt(words.length)];
            } else {
                // Difficulty 1: Pick a random sentence
                return sentences[random.nextInt(sentences.length)];
            }
        }
    }

    // Method to display the words array
    public void displayWords() {
        System.out.println("Words:");
        for (String word : words) {
            System.out.println(word);
        }
    }

    // Method to display the sentences array
    public void displaySentences() {
        System.out.println("Sentences:");
        for (String sentence : sentences) {
            System.out.println(sentence);
        }
    }
}

