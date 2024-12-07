package edu.augustana.RadioModel;

import javax.sound.sampled.*;
import java.io.IOException;

public class HamRadioSimulator implements HamRadioSimulatorInterface {
    private HamRadioClientInterface client;
    private double transmitFrequency;
    private double minimumReceiveFrequency;
    private double maximumReceiveFrequency;
    private double receiveFrequency;
    private double bandWidth;
    private double volume;
    private double playbackSpeed;
    private int WPM = 10;
//    private String userName;
    private SoundPlayer soundPlayer;
    private SignalProcessor signalProcessor;
    private boolean isKeyReleased = true;
    private SourceDataLine sdl;
    ServerSignalListener listener;

    //constructor
    public HamRadioSimulator(double transmitFrequency, double minimumReceiveFrequency,
                             double maximumReceiveFrequency, double receiveFrequency,
                             double bandWidth, double volume, double playbackSpeed, int WPM) throws IOException {
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
        this.signalProcessor = new SignalProcessor(transmitFrequency, receiveFrequency, bandWidth, WPM, soundPlayer);

        //this.client.connectToServer("ws://localhost:8080/signal", this::processSignalFromServer);
    }

    @Override

    public void startRadio(String username) throws Exception {
        this.client.connectToServer("ws://34.133.2.6:8000/ws/" + username, this::processSignalFromServerByMixing);

    }

    @Override
    public void sendDot() throws Exception {
        double dotDuration = 100;
        byte[] buffer = signalProcessor.createSignalArray(dotDuration);
        this.client.sendBufferToServer(buffer);
    }

    @Override
    public void sendDash() throws Exception {
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
        signalProcessor.setTransmitFrequency(frequency);
    }

    @Override
    public double getMinReceiveFrequency() {
        return this.minimumReceiveFrequency;
    }

    @Override
    public void setMinReceiveFrequency(double frequency) {
        this.minimumReceiveFrequency = frequency;
        double bandWidth = getMaxReceiveFrequency() - getMinReceiveFrequency();
        setBandWidth(bandWidth);
    }

    @Override
    public double getMaxReceiveFrequency() {
        return this.maximumReceiveFrequency;
    }

    @Override
    public void setMaxReceiveFrequency(double frequency) {
        this.maximumReceiveFrequency = frequency;
        double bandWidth = getMaxReceiveFrequency() - getMinReceiveFrequency();
        setBandWidth(bandWidth);
    }

    @Override
    public double getReceiveFrequency() {
        return Math.round(this.receiveFrequency * 100.0) / 100.0;
    }

    @Override
    public void setReceiveFrequency(double frequency) {
        this.receiveFrequency = frequency;
        signalProcessor.setReceiveFrequency(frequency);
    }

    @Override
    public double getBandWidth() {
        return this.bandWidth;
    }

    @Override
    public void setBandWidth(double bandWidth) {
        this.bandWidth = bandWidth;
        signalProcessor.setBandWidth(bandWidth);
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
    public int getWPM() {
        return this.WPM;
    }

    @Override
    public void setWPM(int WPM) {
        this.WPM = WPM;
    }

    @Override
    public void setOnChatMessage(ServerSignalListener listener) {
        this.listener = listener;
    }

    // Adjusted `playTone` method with threading to avoid blocking the JavaFX UI thread
    @Override
    public void playTone(double frequency) {
        soundPlayer.playTone(frequency, volume);// Run on a separate thread
    }

    @Override
    public void stopTone() {
        System.out.println("Stop!");
        if (sdl != null && sdl.isOpen()) {
            sdl.stop();
            sdl.close();
        }
    }

    @Override
    public void broadcastCWSignal(CWMessage chatMessage) {

        client.sendChatMessageToServer(chatMessage);
    }

    private void processSignalFromServerByMixing(CWMessage chatMessage) throws LineUnavailableException {
        new Thread(() -> {signalProcessor.process(chatMessage);}).run();
        listener.onSignalReceived(chatMessage);
    }

    private void processSignalFromServerByMultithreading(CWMessage chatMessage) {
        new Thread(() -> {
            try {
                signalProcessor.processMultithread(chatMessage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Override
    public void setIsKeyReleased(boolean isKeyReleased) {
        this.isKeyReleased = isKeyReleased;
        soundPlayer.setIsKeyRelease(isKeyReleased);
    }


}