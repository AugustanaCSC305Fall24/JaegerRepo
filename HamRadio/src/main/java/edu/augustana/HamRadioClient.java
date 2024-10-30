package edu.augustana;
import javax.sound.sampled.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class  HamRadioClient implements HamRadioClientInterface {
    private Socket socket;
    private DataInputStream inputHandler;
    private DataOutputStream outputHandler;
    private double minFrequency;
    private double maxFrequency;
    private double transmitFrequency;
    private double receiveFrequency;
    private double bandwidth;
    private double playbackspeed = 1.0;
    static final double DEFAULT_VOLUME = 50;
    private double volume = DEFAULT_VOLUME;

    public void connectToServer(String serverIp,int serverPort) throws IOException {
        this.socket = new Socket(serverIp, serverPort);
        this.inputHandler = new DataInputStream(socket.getInputStream());
        this.outputHandler = new DataOutputStream(socket.getOutputStream());

        // Thread to receive signal from server
        new Thread(new ReceiveSignalThread()).start();
        //String firstMorseCode = ".. . . ..";
        sendCWSignal();
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
                        receiveAndProcessSignal(receivedSignal);
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

    //HamRadioClient code
    public void sendCWSignal() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Morse code ('.' for dot, '-' for dash");

        while (true) {
            char input = (char) System.in.read(); //Read one char
            try {
                if (input == '.') {
                    System.out.println("Hello");
                    sendDot();
                } else if (input == '-') {
                    sendDash();
                } else {
                    break;
                }
            } catch (IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendDot() throws IOException, LineUnavailableException {
        double dotDuration = 100;
        byte[] buffer = createSignalArray(dotDuration);
        sendBufferToServer(buffer);
    }

    public void sendDash() throws IOException, LineUnavailableException {
        double dashDuration = 300;
        byte[] buffer = createSignalArray(dashDuration);
        sendBufferToServer(buffer);
    }

    private byte[] createSignalArray(double duration) {
        List<Byte> buffer = new ArrayList<>();

        //Calculate the number of samples
        double samplingRate = 42000;
        int samples = (int) (duration * samplingRate / 1000);

        //Generate sine wave
        byte[] result = generateSineWave(transmitFrequency, samplingRate, samples);

        System.out.println("created success");

        return result;
    }

    private byte[] generateSineWave(double frequency, double samplingRate, int samples) {
        byte[] sineWave = new byte[samples];
        double angularFrequency = 2 * Math.PI * frequency / samplingRate;

        for (int i = 0; i < samples; i++) {
            //Sine wave value the scaled to fit into the byte range
            double sineValue = Math.sin(angularFrequency * i);
            //Scale the value to fit between -128 and 127
            sineWave[i] = (byte) (sineValue * 127);
        }
        System.out.println("Generate success");
        return sineWave;
    }

    private void sendBufferToServer(byte[] buffer) throws IOException {
        outputHandler.writeInt(buffer.length);
        outputHandler.write(buffer);
        outputHandler.flush();
    }

    public void receiveAndProcessSignal(byte[] signal) throws LineUnavailableException {
        playSound(signal);
    }

    private void playSound(byte[] signal) throws LineUnavailableException {
        System.out.println("It'll play");
        System.out.println(signal);

        try {
            //Open audio data stream
            byte[] buf = new byte[1];
            AudioFormat af = new AudioFormat(42000, 8, 1, true, false);
            SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
            sdl.open(af);

            // Kiểm tra xem có hỗ trợ điều chỉnh âm lượng (MASTER_GAIN) không
            /*if (sdl.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl volumeControl = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);

                // Lấy giá trị âm lượng tối thiểu và tối đa từ hệ thống
                float minVolume = volumeControl.getMinimum(); // Thường là -80 dB
                float maxVolume = volumeControl.getMaximum(); // Thường là 6.02 dB

                // Chuyển đổi âm lượng từ phần trăm (0-100) sang giá trị dB
                float volumeInDb = (float) ((volume / 100) * (maxVolume - minVolume) + minVolume);

                volumeControl.setValue(volumeInDb);  // Điều chỉnh âm lượng sau khi quy đổi
            } else {
                System.out.println("MASTER_GAIN control không được hỗ trợ trên hệ thống này.");
            }*/

            sdl.start();

            //Play the sound
            for (int i = 0; i < signal.length; i++) {
                buf[0] = signal[i];
                sdl.write(buf, 0, 1);
            }

            //End
            sdl.drain();
            sdl.stop();
            sdl.close();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
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

    public void setPlaybackSpeed(double speed) {
        this.playbackspeed = speed;
    }

    public double getPlaybackSpeed() {
        return this.playbackspeed;
    }


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

    public double getVolume() {
        return this.volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
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
