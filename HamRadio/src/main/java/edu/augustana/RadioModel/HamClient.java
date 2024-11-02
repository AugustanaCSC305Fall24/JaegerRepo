package edu.augustana.RadioModel;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class HamClient  {
    private Socket socket;
    private DataInputStream inputHandler;
    private DataOutputStream outputHandler;
    private double transmitFrequency;
    private double receiveFrequency;
    private double bandwidth;

    public void connectToServer(String serverIp, int serverPort) throws IOException {
        this.socket = new Socket(serverIp, serverPort);
        this.inputHandler = new DataInputStream(socket.getInputStream());
        this.outputHandler = new DataOutputStream(socket.getOutputStream());

        //Thread to receive signal from server
        new Thread(new ReceiveSignalThread()).start();
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

    public void receiveAndProcessSignal(byte[] signal) {
        //parseFromCWSignalToMorsep

        //code to filering and shit...
        //play sound that morse
        System.out.println("Received signal from server.");
    }

    // Phương thức gửi tín hiệu CW và lắng nghe nhập liệu từ console
    public void sendCWSignal() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Morse code ('.' for dot, '-' for dash, 'exit' to quit): ");

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                closeConnection();
                break;
            }

            try {
                if (input.equals(".")) {
                    sendDot();
                } else if (input.equals("-")) {
                    sendDash();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        scanner.close();
    }

    // Gửi tín hiệu CW tương ứng với dấu chấm
    private void sendDot() throws IOException {
        int dotDuration = 100; // Thời gian cho một dấu chấm (ms)
        byte[] buffer = createSignalArray(dotDuration);
        sendBufferToServer(buffer);
    }

    // Gửi tín hiệu CW tương ứng với dấu gạch
    private void sendDash() throws IOException {
        int dashDuration = 300; // Thời gian cho một dấu gạch (ms)
        byte[] buffer = createSignalArray(dashDuration);
        sendBufferToServer(buffer);
    }

    // Tạo mảng byte tín hiệu CW bật (ON)
    private byte[] createSignalArray(int duration) {
        byte[] buffer = new byte[duration];
        byte onSignal = 127; // Giá trị byte cho tín hiệu ON

        // Tạo mảng byte với tín hiệu ON kéo dài trong thời gian duration
        for (int i = 0; i < duration; i++) {
            buffer[i] = onSignal;
        }
        return buffer;
    }

    // Gửi mảng byte tới server
    private void sendBufferToServer(byte[] buffer) throws IOException {
        outputHandler.writeInt(buffer.length); // Gửi kích thước tín hiệu
        outputHandler.write(buffer); // Gửi toàn bộ buffer đến server
        outputHandler.flush(); // Đảm bảo dữ liệu được gửi ngay lập tức
    }

    public void setReceivingFrequency(double freq) {
        this.receiveFrequency = freq;
    }

    public void filerBandWidth(double bandWidth) {
        this.bandwidth = bandWidth;
    }

    public void setTransmitFrequency(double freq) {
        this.transmitFrequency = freq;
    }

    public void closeConnection() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (inputHandler != null) {
                inputHandler.close();
            }
            if (outputHandler != null) {
                outputHandler.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}