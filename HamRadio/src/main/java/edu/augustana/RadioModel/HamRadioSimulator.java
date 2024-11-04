package edu.augustana.RadioModel;

import edu.augustana.Application.UI.HamPracticeUIController;
import edu.augustana.Application.UI.HelperClass;
import edu.augustana.SoundPlayer;

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
    private HamPracticeUIController practice_controller;

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

    @Override
    public void playTone(double frequency,HamPracticeUIController practice_controller, double volume) {
        HelperClass.playTone(frequency, practice_controller, volume);
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