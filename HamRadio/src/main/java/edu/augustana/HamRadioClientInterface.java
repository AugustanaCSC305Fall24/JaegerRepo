package edu.augustana;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public interface HamRadioClientInterface {
    // connect to server
    void connectToServer(String serverIp, int serverPort) throws IOException;

    // Recieve and handle signals from server
    void receiveAndProcessSignal(byte[] signal) throws LineUnavailableException;

    //set the min frequency
    void setMinFrequency(double freq);

    //get the min frequency
    double getMinFrequency();

    //set the max frequency
    void setMaxFrequency(double freq);

    double getMaxFrequency();

    // set the frequency
    void setReceivingFrequency(double freq);

    //get the frequency
    double getReceivingFrequency();

    // set the bandwidth
    void filerBandWidth(double bandWidth);

    //get the bandwidth
    double getBandWidth();

    // set transmitting frequency
    void setTransmitFrequency(double freq);

    //get transmitting frequency
    double getTransmitFrequency();

    //set the playback speed
    void setPlaybackSpeed(double speed);

    //get the playback speed
    double getPlaybackSpeed();

    //volume getter
    double getVolume();

    //volume setter
    void setVolume(double volume);

    void sendDot() throws IOException, LineUnavailableException;

    void sendDash() throws IOException, LineUnavailableException;

    // close WebSocket connection
    void closeConnection();

    void playTone(double frequency, int duration);
}
