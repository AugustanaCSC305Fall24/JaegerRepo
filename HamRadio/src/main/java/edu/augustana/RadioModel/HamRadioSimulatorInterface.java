package edu.augustana.RadioModel;

import java.util.List;

public interface HamRadioSimulatorInterface {
    //methods for sending and receiving
    //send a dot
    void sendDot() throws Exception;

    //send a dash
    void sendDash() throws Exception;

    //
    void startRadio(String username) throws Exception;

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
    int getWPM();

    //setter of WPM
    void setWPM(int WPM);

    //setter for is
    void setIsKeyReleased(boolean isKeyRelease);

    //playTone
    void playTone(double frequency);

    //
    void broadcastCWSignal(CWMessage chatMessage);

    //stopTone
    void stopTone();

    //setlistener
    void setOnChatMessage(ServerSignalListener listener);

    List<CWMessage> getChatMessageList();

    void addMessage(CWMessage message) throws Exception;

}