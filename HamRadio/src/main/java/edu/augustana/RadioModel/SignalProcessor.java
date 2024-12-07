package edu.augustana.RadioModel;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
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
    private SoundPlayer soundPlayer;

    public SignalProcessor(double transmitFrequency, double receiveFrequency, double bandWidth, int wpm, SoundPlayer soundPlayer) {
        int sampleRate = 44100;
        double lowCutoff = receiveFrequency - (bandWidth/2);
        double highCutoff = receiveFrequency + (bandWidth/2);
        int filterOrder = 101;

        this.transmitFrequency = transmitFrequency;
        this.receiveFrequency = receiveFrequency;
        this.bandWidth = bandWidth;
        this.wpm = wpm;
        this.soundPlayer = soundPlayer;
        this.volume = soundPlayer.getVolume();
        signalGenerator = new SignalGenerator();
        signalFilter = new SignalFilter(sampleRate, lowCutoff, highCutoff, filterOrder);
        signalMixer = new SignalMixer(sampleRate, 50, this::continue_process);
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
        signalFilter.setLowCutoff(receiveFrequency - (bandWidth/2));
        signalFilter.setHighCutoff(receiveFrequency + (bandWidth/2));
    }

    public double getBandWidth() {
        return bandWidth;
    }

    public void setBandWidth(double bandWidth) {
        this.bandWidth = bandWidth;
        signalFilter.setLowCutoff(receiveFrequency - (bandWidth/2));
        signalFilter.setHighCutoff(receiveFrequency + (bandWidth/2));
    }

    public void process(CWMessage chatMessage) {
        String message = chatMessage.getText();
        double freq = chatMessage.getFrequency();
        byte[] byteBuffer = signalGenerator.generateByteSignal(message, Math.abs(freq - receiveFrequency), wpm);
        signalMixer.mix(byteBuffer);

    }

    private void continue_process(byte[] byteBuffer) throws LineUnavailableException, IOException {
        byte[] filteredSignal = signalFilter.filter(byteBuffer);
        soundPlayer.playSound(filteredSignal);
    }

    public void processMultithread(CWMessage cwMessage) throws IOException {
        String morseCode = cwMessage.getText();
        double frequency = cwMessage.getFrequency();
        double minBand = receiveFrequency - (bandWidth/2);
        double maxBand = receiveFrequency + (bandWidth/2);
        byte[] signal = signalGenerator.generateByteSignal(morseCode, frequency, wpm);
        soundPlayer.playMorseCode(signal, frequency, minBand, maxBand);
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