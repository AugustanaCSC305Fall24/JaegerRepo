package edu.augustana.RadioModel;

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

    //constructor
    public HamRadioSimulator(double transmitFrequency, double minimumReceiveFrequency,
                             double maximumReceiveFrequency, double receiveFrequency,
                             double bandWidth, double volume, double playbackSpeed, double WMP) throws IOException {
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
        return this.transmitFrequency;
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
        return this.receiveFrequency;
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
    public void playTone(double frequency, int duration) {
        try {
            float sampleRate = 42000;
            byte[] buf = new byte[1];
            AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);
            SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
            sdl.open(af);

            // Kiểm tra xem có hỗ trợ điều chỉnh âm lượng (MASTER_GAIN) không
            if (sdl.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl volumeControl = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);

                // Lấy giá trị âm lượng tối thiểu và tối đa từ hệ thống
                float minVolume = volumeControl.getMinimum(); // Thường là -80 dB
                float maxVolume = volumeControl.getMaximum(); // Thường là 6.02 dB

                // Chuyển đổi âm lượng từ phần trăm (0-100) sang giá trị dB
                float volumeInDb = (float) ((volume / 100) * (maxVolume - minVolume) + minVolume);

                volumeControl.setValue(volumeInDb);  // Điều chỉnh âm lượng sau khi quy đổi
            } else {
                System.out.println("MASTER_GAIN control không được hỗ trợ trên hệ thống này.");
            }

            sdl.start();

            // Sinh tín hiệu và phát qua loa
            for (int i = 0; i < duration * (float) sampleRate / 1000; i++) {
                double angle = i / (sampleRate / frequency) * 2.0 * Math.PI;
                buf[0] = (byte) (Math.sin(angle) * 127);  // Tạo sóng âm thanh
                sdl.write(buf, 0, 1);
            }

            // Kết thúc phát âm thanh
            sdl.drain();
            sdl.stop();
            sdl.close();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void processSignalFromServer(byte[] signal) throws LineUnavailableException {
        signalProcessor.filterSignal(signal);
        soundPlayer.playSound(signal);
    }
}