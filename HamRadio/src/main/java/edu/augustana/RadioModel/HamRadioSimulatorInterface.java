package edu.augustana.RadioModel;

import edu.augustana.Application.UI.HamPracticeUIController;

import java.io.IOException;

public interface HamRadioSimulatorInterface {
    //methods for sending and receiving
    //send a dot
    void sendDot() throws IOException;

    //send a dash
    void sendDash() throws IOException;

    //getter of transmit frequency
    double getTransmitFrequency();

    //setter of transmit frequency
    void setTransmitFrequency(double frequency);

    //getter of minimum frequency
    double getMinReceiveFrequency();

    //setter of minimum frequency
    void setMinReceiveFrequency(double frequency);

    //getter of max frequency
    double getMaxReceiveFrequency();

    //setter of max frequency
    void setMaxReceiveFrequency(double frequency);

    //getter of receive frequency
    double getReceiveFrequency();

    //setter of receive frequency
    void setReceiveFrequency(double frequency);

    //getter of bandwidth
    double getBandWidth();

    //setter of bandwidth
    void setBandWidth(double bandWidth);

    //getter of volume
    double getVolume();

    //setter of volume:
    void setVolume(double volume);

    //getter of playback speed
    double getPlaybackSpeed();

    //setter of playback speed
    void setPlaybackSpeed(double playbackSpeed);

    //getter of the WPM
    double getWPM();

    //setter of WPM
    void setWPM(double WPM);

    //setter for is
    void setIsKeyReleased(boolean isKeyRelease);

    //playTone
    void playTone(double frequency);

    //stopTone
    void stopTone();
}