package edu.augustana.RadioModel;

import com.github.psambit9791.jdsp.filter.FIRWin1;
import java.util.Arrays;

public class SignalFilter {

    private final int sampleRate; // Sampling rate of the audio signal (e.g., 44100 Hz)
    private final double lowCutoff; // Low cutoff frequency (Hz)
    private final double highCutoff; // High cutoff frequency (Hz)
    private final int filterOrder; // Order of the filter

    /**
     * Constructor for SignalFilter.
     *
     * @param sampleRate Sampling rate of the audio signal.
     * @param lowCutoff  Low cutoff frequency for the band-pass filter.
     * @param highCutoff High cutoff frequency for the band-pass filter.
     * @param filterOrder Order of the FIR filter.
     */
    public SignalFilter(int sampleRate, double lowCutoff, double highCutoff, int filterOrder) {
        this.sampleRate = sampleRate;
        this.lowCutoff = lowCutoff;
        this.highCutoff = highCutoff;
        this.filterOrder = filterOrder;
    }

    /**
     * Filters the given signal using a band-pass filter.
     *
     * @param inputSignal The input signal as a byte array.
     * @return The filtered signal as a byte array.
     */
    public byte[] filter(byte[] inputSignal) {
        // Convert byte[] to double[] for processing
        double[] doubleSignal = byteArrayToDoubleArray(inputSignal);

        // Define the cutoff frequencies for the band-pass filter
        double[] cutoffFrequencies = {lowCutoff, highCutoff};

        // Initialize the FIRWin1 filter
        FIRWin1 firWin1 = new FIRWin1(filterOrder, sampleRate);

        // Compute the filter coefficients for a band-pass filter
        double[] filterCoefficients = firWin1.computeCoefficients(cutoffFrequencies, FIRWin1.FIRfilterType.BANDPASS, true);

        // Apply the filter to the input signal
        double[] filteredSignal = firWin1.firfilter(filterCoefficients, doubleSignal);

        // Convert filtered double[] back to byte[]
        return doubleArrayToByteArray(filteredSignal);
    }

    /**
     * Converts a byte array to a double array, normalizing values to the range [-1, 1].
     */
    private double[] byteArrayToDoubleArray(byte[] byteArray) {
        double[] doubleArray = new double[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            doubleArray[i] = byteArray[i] / 128.0; // Normalize to [-1, 1]
        }
        return doubleArray;
    }

    /**
     * Converts a double array to a byte array, scaling values back to the range [-128, 127].
     */
    private byte[] doubleArrayToByteArray(double[] doubleArray) {
        byte[] byteArray = new byte[doubleArray.length];
        for (int i = 0; i < doubleArray.length; i++) {
            byteArray[i] = (byte) Math.min(Math.max(doubleArray[i] * 128, -128), 127); // Scale to [-128, 127]
        }
        return byteArray;
    }
}
