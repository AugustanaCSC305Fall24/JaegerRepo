package edu.augustana.RadioModel;

import edu.augustana.Application.UI.HelperClass;

import javax.sound.sampled.AudioFormat;
import java.nio.ByteBuffer;

public class SignalGenerator {

    public byte[] generateByteSignal(String morseString, double frequencyDifference, int wpm) {
        int unitOfTime = (int) HelperClass.unitOfTime(wpm);
        int sampleRate = 44100; // Set the sample rate
        int totalSamples = calculateTotalSamples(morseString, unitOfTime, sampleRate);

        // Allocate byte array buffer (2 bytes per sample for 16-bit audio)
        ByteBuffer buffer = ByteBuffer.allocate(totalSamples * 2);

        boolean addNoise = frequencyDifference > 5 || frequencyDifference < -5;
        for (int i = 0; i < morseString.length(); i++) {
            char symbol = morseString.charAt(i);
            if (symbol == '.') {
                if (addNoise) {
                    generateNoiseWithPitch(unitOfTime, sampleRate, buffer, frequencyDifference);
                } else {
                    // Generate "dit" sound (600 Hz for dot)
                    generateSineWave(600, unitOfTime, sampleRate, buffer);
                }
                // Add silence (gap between symbols)
                generateSilence(unitOfTime, sampleRate, buffer);
            } else if (symbol == '-') {
                if (addNoise) {
                    generateNoiseWithPitch(3 * unitOfTime, sampleRate, buffer, frequencyDifference);
                } else {
                    // Generate "dah" sound (600 Hz for dash)
                    generateSineWave(600, 3 * unitOfTime, sampleRate, buffer);
                }
                // Add silence (gap between symbols)
                generateSilence(unitOfTime, sampleRate, buffer);
            } else if (symbol == ' ') {
                // Add silence between letters (3 dot units)
                generateSilence(3 * unitOfTime, sampleRate, buffer);
            } else if (symbol == '/') {
                // Add silence between words (7 dot units)
                generateSilence(7 * unitOfTime, sampleRate, buffer);
            }
        }

        return buffer.array();
    }

    private static void generateSineWave(int frequency, int durationMs, int sampleRate, ByteBuffer buffer) {
        int numSamples = (int) ((durationMs / 1000.0) * sampleRate);

        for (int i = 0; i < numSamples; i++) {
            double angle = 2.0 * Math.PI * i * frequency / sampleRate;
            short sample = (short) (Math.sin(angle) * Short.MAX_VALUE);
            buffer.put((byte) (sample & 0xff));
            buffer.put((byte) ((sample >> 8) & 0xff));
        }
    }

    private static void generateSilence(int durationMs, int sampleRate, ByteBuffer buffer) {
        int numSamples = (int) ((durationMs / 1000.0) * sampleRate);

        for (int i = 0; i < numSamples; i++) {
            buffer.put((byte) 0x00);
            buffer.put((byte) 0x00);
        }
    }

    private void generateNoiseWithPitch(int durationMs, int sampleRate, ByteBuffer buffer, double frequencyDifference) {
        int adjustedFrequency = (int) (frequencyDifference / 5);
        generateSineWave(600 * adjustedFrequency, durationMs, sampleRate, buffer);
    }

    private int calculateTotalSamples(String morseString, int unitOfTime, int sampleRate) {
        int totalUnits = 0;
        for (char symbol : morseString.toCharArray()) {
            if (symbol == '.') {
                totalUnits += 2; // 1 unit for sound, 1 unit for silence
            } else if (symbol == '-') {
                totalUnits += 4; // 3 units for sound, 1 unit for silence
            } else if (symbol == ' ') {
                totalUnits += 3; // 3 units for letter gap
            } else if (symbol == '/') {
                totalUnits += 7; // 7 units for word gap
            }
        }
        return totalUnits * unitOfTime * sampleRate / 1000;
    }
}
