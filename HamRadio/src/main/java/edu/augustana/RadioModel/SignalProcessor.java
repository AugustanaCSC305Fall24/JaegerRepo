package edu.augustana.RadioModel;

import sun.misc.Signal;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SignalProcessor {
    private double transmitFrequency;
    private double receiveFrequency;
    private double bandWidth;
    private double volume;
    private int wpm;
    private SignalGenerator signalGenerator;
    private SignalFilter signalFilter;
    private SignalMixer signalMixer;
    private SoundPlayer soundPlayer = new SoundPlayer(volume);

    public SignalProcessor(double transmitFrequency, double receiveFrequency, double bandWidth, int wpm, double volume) {
        int sampleRate = 44100;
        double lowCutoff = receiveFrequency - (bandWidth/2);
        double highCutoff = receiveFrequency + (bandWidth/2);
        int filterOrder = 101;

        this.transmitFrequency = transmitFrequency;
        this.receiveFrequency = receiveFrequency;
        this.bandWidth = bandWidth;
        this.wpm = wpm;
        this.volume = volume;
        signalGenerator = new SignalGenerator();
        signalFilter = new SignalFilter(sampleRate, lowCutoff, highCutoff, filterOrder);
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

    public void process(ChatMessage chatMessage) {
        String message = chatMessage.getText();
        byte[] byteBuffer = signalGenerator.generateByteSignal(message, Math.abs(transmitFrequency - receiveFrequency), wpm);
        signalMixer.mix(byteBuffer);

    }

    private void continue_process(byte[] byteBuffer) {
        //soundPlayer.playSound(signalFilter.filter(byteBuffer));
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

    public byte[] generateSignalFromChatMessage(ChatMessage chatMessage) {
        if (chatMessage.getText() == ".") {
            return fromDotToSignal();
        } else if (chatMessage.getText() == "-") {
            return fromDashToSignal();
        } else {
            System.out.println("Invalid message");
            return new byte[0];
        }
    }

    private byte[] fromDotToSignal() {
        return new byte[1];
    }

    private byte[] fromDashToSignal() {
        return new byte[1];
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