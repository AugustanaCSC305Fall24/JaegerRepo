package edu.augustana.RadioModel;

import java.io.IOException;

public interface HamRadioClientInterface {
    //connect to server
    void connectToServer(String serverIp, int serverPort, ServerSignalListener listener) throws IOException;

    //send byte array signal to server
    void sendBufferToServer(byte[] buffer) throws IOException;
}