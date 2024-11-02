package edu.augustana.RadioModel;

import java.util.ArrayList;
import java.util.List;

public class SignalProcessor {
    private double transmitFrequency;
    private double receiveFrequency;
    private double bandWidth;

    public SignalProcessor(double transmitFrequency, double receiveFrequency, double bandWidth) {
        this.transmitFrequency = transmitFrequency;
        this.receiveFrequency = receiveFrequency;
        this.bandWidth = bandWidth;
    }

    public void setTransmitFrequency(double transmitFrequency) {
        this.transmitFrequency = transmitFrequency;
    }

    public double getTransmitFrequency() {
        return transmitFrequency;
    }

    public double getReceiveFrequency() {
        return receiveFrequency;
    }

    public void setReceiveFrequency(double receiveFrequency) {
        this.receiveFrequency = receiveFrequency;
    }

    public double getBandWidth() {
        return bandWidth;
    }

    public void setBandWidth(double bandWidth) {
        this.bandWidth = bandWidth;
    }

    public void filterSignal(byte[] signal) {
        //filtering...
    }

    //private helper methods:
    public byte[] createSignalArray(double duration) {
        List<Byte> buffer = new ArrayList<>();

        //Calculate the number of samples
        double samplingRate = 42000;
        int samples = (int) (duration * samplingRate / 1000);

        //Generate sine wave
        byte[] result = generateSineWave(transmitFrequency, samplingRate, samples);

        System.out.println("created success");

        return result;
    }

    private byte[] generateSineWave(double frequency, double samplingRate, int samples) {
        byte[] sineWave = new byte[samples];
        double angularFrequency = 2 * Math.PI * frequency / samplingRate;

        for (int i = 0; i < samples; i++) {
            //Sine wave value the scaled to fit into the byte range
            double sineValue = Math.sin(angularFrequency * i);
            //Scale the value to fit between -128 and 127
            sineWave[i] = (byte) (sineValue * 127);
        }
        System.out.println("Generate success");
        return sineWave;
    }
}