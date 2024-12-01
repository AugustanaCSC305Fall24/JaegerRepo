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
                "CQ", "QTH", "QRZ", "73", "88",
                "OM", "YL", "SK", "DE", "RST",
                "K", "BK", "AR", "KN", "QSL",
                "QRM", "QRN", "QRS", "QRQ", "QSB",
                "ANT", "PWR", "CW", "QRP", "QRO",
                "HV", "WX", "SIG", "FREQ", "QSO"
        };

        // Initialize the sentences array
        sentences = new String[] {
                "CQ CQ CQ DE CALLSIGN K",  // General call
                "CALLSIGN DE CALLSIGN UR RST 599 QTH LOCATION K",  // Signal report and location
                "CALLSIGN DE CALLSIGN TNX FER QSO 73 SK",  // Thank you and signing off
                "QRL? IS THIS FREQUENCY IN USE?",  // Checking if a frequency is busy
                "QTH IS CITY NAME WX IS SUNNY 25C",  // Sharing location and weather
                "QRZ? DE CALLSIGN",  // Asking who’s calling
                "QRS PSE I AM NEW TO CW",  // Requesting slower Morse speed
                "QRM IS STRONG HR",  // Reporting interference
                "ANT IS DIPOLE UP 30FT PWR IS 100W",  // Antenna and power information
                "CALLSIGN DE CALLSIGN HW? AR KN"  // How do you copy? (asking signal quality)
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

