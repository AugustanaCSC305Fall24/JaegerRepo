package edu.augustana.RadioModel;

import javax.sound.sampled.LineUnavailableException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HamRadioClient implements HamRadioClientInterface {
    private Socket socket;
    private DataInputStream inputHandler;
    private DataOutputStream outputHandler;
    ServerSignalListener listener;

    //establish a connection to server
    public void connectToServer(String serverIp, int serverPort, ServerSignalListener listener) throws IOException {
        this.socket = new Socket(serverIp, serverPort);
        this.inputHandler = new DataInputStream(socket.getInputStream());
        this.outputHandler = new DataOutputStream(socket.getOutputStream());
        this.listener = listener;

        // Thread to receive signal from server
        new Thread(new ReceiveSignalThread()).start();
    }

    //Thread to handle signals from server
    class ReceiveSignalThread implements Runnable {
        private volatile boolean running = true;

        public void stop() {
            running = false;
        }

        @Override
        public void run() {
            try {
                int loop = 1;
                while (running) {
                    System.out.println(loop);
                    int signalSize = inputHandler.readInt();
                    byte[] receivedSignal = new byte[signalSize];
                    inputHandler.readFully(receivedSignal);
                    try {
                        listener.onSignalReceived(receivedSignal);
                        loop++;
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                        System.out.println("Error processing signal: " + e.getMessage());
                        // Optionally stop the thread in case of this specific error
                        stop();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.print(e.getMessage());
            }
        }
    }

    public void sendBufferToServer(byte[] buffer) throws IOException {
        outputHandler.writeInt(buffer.length);
        outputHandler.write(buffer);
        outputHandler.flush();
    }
}