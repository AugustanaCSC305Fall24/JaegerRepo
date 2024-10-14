package edu.augustana;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class  HamRadioClient implements HamRadioClientInterface {
    private Socket socket;
    private DataInputStream inputHandler;
    private DataOutputStream outputHandler;
    private double minFrequency;
    private double maxFrequency;
    private double transmitFrequency;
    private double receiveFrequency;
    private double bandwidth;

    public void connectToServer(String serverIp,int serverPort) throws IOException {
        this.socket = new Socket(serverIp, serverPort);
        this.inputHandler = new DataInputStream(socket.getInputStream());
        this.outputHandler = new DataOutputStream(socket.getOutputStream());

        // Thread to receive signal from server
        new Thread(new ReceiveSignalThread()).start();
        //String firstMorseCode = ".. . . ..";
        //sendCWSignal(firstMorseCode);
    }

    public void startClientForDemo() {
        while (true) {
            //
        }
    }
    
    //Thread to handle signals from server
    class ReceiveSignalThread implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    int signalSize = inputHandler.readInt();
                    byte[] receivedSignal = new byte[signalSize];
                    inputHandler.readFully(receivedSignal);
                    receiveAndProcessSignal(receivedSignal);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.print(e.getMessage());
            }
        }
    }

    //HamRadioClient code
    public void sendCWSignal(String morseCode) throws IOException {
        byte[] signal = parseMorseCodeToCW(morseCode);
        outputHandler.writeInt(signal.length);
        outputHandler.write(signal);
    }
    
    public void receiveAndProcessSignal(byte[] signal) {
        //parseFromCWSignalToMorsep

        //code to filering and shit...
        //play sound that morse
    }

    public void setReceivingFrequency(double freq) {
        this.receiveFrequency = freq;
    }

    public double getReceivingFrequency() {
        return this.receiveFrequency;
    }

    public void setMinFrequency(double freq) {
        this.minFrequency = freq;
    }

    public double getMinFrequency() {
        return this.minFrequency;
    }

    public void setMaxFrequency(double freq) {
        this.maxFrequency = freq;
    }

    public double getMaxFrequency() {
        return this.maxFrequency;
    }
    
    public void filerBandWidth(double bandWidth) {
        this.bandwidth = bandWidth;
    }

    public double getBandWidth() {
        return this.bandwidth;
    }
    
    public void setTransmitFrequency(double freq) {
        this.transmitFrequency = freq;
    }

    public double getTransmitFrequency() {
        return this.transmitFrequency;
    }
    
    public void closeConnection() {

    }

    //helper methods:
    private byte[] parseMorseCodeToCW(String morseCode) {
        byte[] byteBuffer = new byte[1024];
        return byteBuffer;
    }
}

//next:
//signal processing code (tmr)
//WebSocket API client-server config (sat)
//test (sun)
