package edu.augustana.RadioModel;

import java.io.IOException;

public interface HamRadioClientInterface {
    //connect to server
    void connectToServer(String serverUri, ServerSignalListener listener) throws Exception;

    //send byte array signal to server
    void sendBufferToServer(byte[] buffer) throws Exception;
}