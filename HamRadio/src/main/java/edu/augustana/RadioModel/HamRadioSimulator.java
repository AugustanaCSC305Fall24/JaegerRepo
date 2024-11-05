package edu.augustana.RadioModel;

import edu.augustana.Application.UI.HamPracticeUIController;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HamRadioSimulator implements HamRadioSimulatorInterface {
    private HamRadioClientInterface client;
    private double transmitFrequency;
    private double minimumReceiveFrequency;
    private double maximumReceiveFrequency;
    private double receiveFrequency;
    private double bandWidth;
    private double volume;
    private double playbackSpeed;
    private double WPM;
    private SoundPlayer soundPlayer;
    private SignalProcessor signalProcessor;
    private boolean isKeyReleased = true;
    private SourceDataLine sdl;

    //constructor
    public HamRadioSimulator(double transmitFrequency, double minimumReceiveFrequency,
                             double maximumReceiveFrequency, double receiveFrequency,
                             double bandWidth, double volume, double playbackSpeed, double WPM) throws IOException {
        this.client = new HamRadioClient();
        this.transmitFrequency = transmitFrequency;
        this.minimumReceiveFrequency = minimumReceiveFrequency;
        this.maximumReceiveFrequency = maximumReceiveFrequency;
        this.receiveFrequency = receiveFrequency;
        this.bandWidth = bandWidth;
        this.volume = volume;
        this.playbackSpeed = playbackSpeed;
        this.WPM = WPM;

        this.soundPlayer = new SoundPlayer(volume);
        this.signalProcessor = new SignalProcessor(transmitFrequency, receiveFrequency, bandWidth);

        //this.client.connectToServer("localhost", 8080, this::processSignalFromServer);
    }

    @Override
    public void sendDot() throws IOException {
        double dotDuration = 100;
        byte[] buffer = signalProcessor.createSignalArray(dotDuration);
        this.client.sendBufferToServer(buffer);
    }

    @Override
    public void sendDash() throws IOException {
        double dashDuration = 300;
        byte[] buffer = signalProcessor.createSignalArray(dashDuration);
        this.client.sendBufferToServer(buffer);
    }

    @Override
    public double getTransmitFrequency() {
        return Math.round(this.transmitFrequency * 100.0) / 100.0;
    }

    @Override
    public void setTransmitFrequency(double frequency) {
        this.transmitFrequency =  frequency;
    }

    @Override
    public double getMinReceiveFrequency() {
        return this.minimumReceiveFrequency;
    }

    @Override
    public void setMinReceiveFrequency(double frequency) {
        this.minimumReceiveFrequency = frequency;
    }

    @Override
    public double getMaxReceiveFrequency() {
        return this.maximumReceiveFrequency;
    }

    @Override
    public void setMaxReceiveFrequency(double frequency) {
        this.maximumReceiveFrequency = frequency;
    }

    @Override
    public double getReceiveFrequency() {
        return Math.round(this.receiveFrequency * 100.0) / 100.0;
    }

    @Override
    public void setReceiveFrequency(double frequency) {
        this.receiveFrequency = frequency;
    }

    @Override
    public double getBandWidth() {
        return this.bandWidth;
    }

    @Override
    public void setBandWidth(double bandWidth) {
        this.bandWidth = bandWidth;
    }

    @Override
    public double getVolume() {
        return this.volume;
    }

    @Override
    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public double getPlaybackSpeed() {
        return this.playbackSpeed;
    }

    @Override
    public void setPlaybackSpeed(double playbackSpeed) {
        this.playbackSpeed = playbackSpeed;
    }

    @Override
    public double getWPM() {
        return this.WPM;
    }

    @Override
    public void setWPM(double WPM) {
        this.WPM = WPM;
    }

    // Adjusted `playTone` method with threading to avoid blocking the JavaFX UI thread
    @Override
    public void playTone(double frequency) {
        new Thread(() -> {
            try {
                float sampleRate = 42000;
                byte[] buf = new byte[1];
                AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);
                SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
                sdl.open(af);

                // Check if volume adjustment is supported and set it
                if (sdl.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl volumeControl = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);
                    float minVolume = volumeControl.getMinimum();
                    float maxVolume = volumeControl.getMaximum();
                    float volumeInDb = (float) ((volume / 100) * (maxVolume - minVolume) + minVolume);
                    volumeControl.setValue(volumeInDb);
                } else {
                    System.out.println("MASTER_GAIN control is not supported on this system.");
                }

                sdl.start();
                int i = 0;
                while (!isKeyReleased) {  // Check isKeyReleased status
                    double angle = i / (sampleRate / frequency) * 2.0 * Math.PI;
                    buf[0] = (byte) (Math.sin(angle) * 127);
                    sdl.write(buf, 0, 1);
                    i++;
                }

                // Clean up audio line after release
                sdl.drain();
                sdl.stop();
                sdl.close();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }).start();  // Run on a separate thread
    }


    @Override
    public void stopTone() {
        System.out.println("Stop!");
        if (sdl != null && sdl.isOpen()) {
            sdl.stop();
            sdl.close();
        }
    }

    private void processSignalFromServer(byte[] signal) throws LineUnavailableException {
        signalProcessor.filterSignal(signal);
        soundPlayer.playSound(signal);
    }

    @Override
    public void setIsKeyReleased(boolean isKeyReleased) {
        this.isKeyReleased = isKeyReleased;
    }

}