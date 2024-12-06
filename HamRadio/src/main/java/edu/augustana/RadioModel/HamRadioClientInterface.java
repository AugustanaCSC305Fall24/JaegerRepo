package edu.augustana.RadioModel;

public interface HamRadioClientInterface {
    //connect to server
    void connectToServer(String serverUri, ServerSignalListener listener) throws Exception;

    //send byte array signal to server
    void sendBufferToServer(byte[] buffer) throws Exception;

    //send chatMessage to server
    void sendChatMessageToServer(CWMessage chatMessage);
}