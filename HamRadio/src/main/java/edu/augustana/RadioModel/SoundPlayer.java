package edu.augustana.RadioModel;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;

public class SoundPlayer {
    private double volume;
    private boolean isKeyReleased;

    public SoundPlayer(double volume) {
        this.isKeyReleased = true;
        this.volume = volume;
    }

    public double getVolume(){
        return volume;
    }

    public void playSound(byte[] signal) throws LineUnavailableException {
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

    public void playTone1(double frequency, double volume) {
        new Thread(() -> {
            try {
                float sampleRate = 3000;
                byte[] buf = new byte[1];

                //It might take some time creating either of these 3 out of the loop.
                AudioFormat af = new AudioFormat(sampleRate, 8, 1, true, false);
                SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
                sdl.open(af);

                // Check if volume adjustment is supported and set it
                if (sdl.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl volumeControl = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);
                    float minVolume = volumeControl.getMinimum();
                    float maxVolume = volumeControl.getMaximum();
                    float volumeInDb = (float) ((volume / 100) * (maxVolume - minVolume) + minVolume);
                    volumeControl.setValue(volumeInDb);
                } else {
                    System.out.println("MASTER_GAIN control is not supported on this system.");
                }

                sdl.start();
                int i = 0;
                while (!isKeyReleased) {  // Check isKeyReleased status
                    double angle = i / (sampleRate / frequency) * 2.0 * Math.PI;
                    buf[0] = (byte) (Math.sin(angle) * 127);
                    sdl.write(buf, 0, 1);
                    i++;
                }

                // Clean up audio line after release
                //sdl.drain();
                sdl.flush();
                sdl.stop();
                System.out.println("Stop");
                sdl.close();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }).start();  // Run on a separate thread
    }

    public void playTone(double frequency, double volume) {
        new Thread(() -> {
            try {
                ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

                AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 1, 2, 44100, false);
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);

                int sampleRate = (int) format.getSampleRate();
                int numSamples = (int) ((50 / 1000.0) * sampleRate);
                byte[] toneBuffer = new byte[2 * numSamples];

                sourceLine.open(format);

                // Check if volume adjustment is supported and set it
                if (sourceLine.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl volumeControl = (FloatControl) sourceLine.getControl(FloatControl.Type.MASTER_GAIN);
                    float minVolume = volumeControl.getMinimum();
                    float maxVolume = volumeControl.getMaximum();
                    float volumeInDb = (float) ((volume / 100) * (maxVolume - minVolume) + minVolume);
                    volumeControl.setValue(volumeInDb);
                } else {
                    System.out.println("MASTER_GAIN control is not supported on this system.");
                }

                sourceLine.start();
                while (!isKeyReleased) {
                    for (int i = 0; i < numSamples; i++) {
                        double angle = 2.0 * Math.PI * i * frequency / sampleRate;
                        short sample = (short) (Math.sin(angle) * Short.MAX_VALUE);
                        toneBuffer[2 * i] = (byte) (sample & 0xff);
                        toneBuffer[2 * i + 1] = (byte) ((sample >> 8) & 0xff);
                    }
                    sourceLine.write(toneBuffer, 0, toneBuffer.length);
                }

                // Clean up audio line after release
                //sdl.drain();
                sourceLine.flush();
                sourceLine.stop();
                System.out.println("Stop");
                sourceLine.close();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }).start();  // Run on a separate thread
    }

    public void playMorse(String morseString, int wordsPerMinute, double receivedFrequency, double transmitFrequency){
        int dotDuration = calculateDotDuration(wordsPerMinute);
        System.out.println("I'm working... playMorse in SoundPlayer");
        System.out.println("I'm speaking..." + morseString);
        System.out.println("I'm having the speed of... " + wordsPerMinute);
        System.out.println("My volume is... " + volume);

        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 1, 2, 44100, false);
        double frequencyDifference = Math.abs(receivedFrequency - transmitFrequency);
        System.out.println("The Difference in frequency is...." + frequencyDifference);
        boolean addNoise = frequencyDifference > 5;
        if (addNoise){
            System.out.println("You will hear pitch variance");
        } else {
            System.out.println("The sound should be beautiful right now!");
        }

        for (int i = 0; i < morseString.length(); i++) {
            char symbol = morseString.charAt(i);
            if (symbol == '.') {
                if (addNoise) {
                    generateNoiseWithPitch(dotDuration, format, byteBuffer, frequencyDifference);
                } else {
                    // Generate "dit" sound (600 Hz for dot)
                    generateTone(600, dotDuration, format, byteBuffer);
                }
                // Add silence (gap between symbols)
                generateSilence(dotDuration, format, byteBuffer);
            } else if (symbol == '-') {
                if (addNoise) {
                    generateNoiseWithPitch(3 * dotDuration, format, byteBuffer, frequencyDifference);
                } else {
                    // Generate "dah" sound (600 Hz for dash)
                    generateTone(600, 3 * dotDuration, format, byteBuffer);
                }
                // Add silence (gap between symbols)
                generateSilence(dotDuration, format, byteBuffer);
            } else if (symbol == ' ') {
                // Add silence between letters (3 dot units)
                generateSilence(3 * dotDuration, format, byteBuffer);
            } else if (symbol == '/') {
                // Add silence between words (7 dot units)
                generateSilence(7 * dotDuration, format, byteBuffer);
            }
        }


        // Playback the generated Morse code using SourceDataLine
        try {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(format);

            // Check if volume adjustment is supported and set it
            if (sourceLine.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl volumeControl = (FloatControl) sourceLine.getControl(FloatControl.Type.MASTER_GAIN);
                float minVolume = volumeControl.getMinimum();
                float maxVolume = volumeControl.getMaximum();
                float volumeInDb = (float) ((volume / 100) * (maxVolume - minVolume) + minVolume);
                volumeControl.setValue(volumeInDb);
            } else {
                System.out.println("MASTER_GAIN control is not supported on this system.");
            }
            sourceLine.start();

            byte[] audioData = byteBuffer.toByteArray();
            sourceLine.write(audioData, 0, audioData.length);

            sourceLine.drain();
            sourceLine.stop();
            sourceLine.close();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    private static int calculateDotDuration(int wordsPerMinute) {
        // The standard word used for WPM calculation in Morse is "PARIS" which has 50 units
        // 1200 ms / wordsPerMinute gives the duration of one dot in milliseconds
        return 1200 / wordsPerMinute;
    }

    private static void generateTone(int frequency, int durationMs, AudioFormat format, ByteArrayOutputStream byteBuffer) {
        int sampleRate = (int) format.getSampleRate();
        int numSamples = (int) ((durationMs / 1000.0) * sampleRate);
        byte[] toneBuffer = new byte[2 * numSamples];

        for (int i = 0; i < numSamples; i++) {
            double angle = 2.0 * Math.PI * i * frequency / sampleRate;
            short sample = (short) (Math.sin(angle) * Short.MAX_VALUE);
            toneBuffer[2 * i] = (byte) (sample & 0xff);
            toneBuffer[2 * i + 1] = (byte) ((sample >> 8) & 0xff);
        }
        byteBuffer.write(toneBuffer, 0, toneBuffer.length);
    }

    private static void generateSilence(int durationMs, AudioFormat format, ByteArrayOutputStream byteBuffer) {
        int sampleRate = (int) format.getSampleRate();
        int numSamples = (int) ((durationMs / 1000.0) * sampleRate);
        byte[] silenceBuffer = new byte[2 * numSamples];
        byteBuffer.write(silenceBuffer, 0, silenceBuffer.length);
    }

    public void playSoundAtInstance(double sampleRate, double frequency, SourceDataLine sdl, int i) {
        double angle = i / (sampleRate / frequency) * 2.0 * Math.PI;
        byte[] buf = new byte[1];
        buf[0] = (byte) (Math.sin(angle) * 127);  // Tạo sóng âm thanh
        sdl.write(buf, 0, 1);
    }

    private void generateNoiseWithPitch(int durationMs, AudioFormat format, ByteArrayOutputStream byteBuffer, double frequencyDifference) {
        int adjustedFrequency = (int) (frequencyDifference / 5);
        generateTone( 600*adjustedFrequency, durationMs, format, byteBuffer);
    }

    public void setIsKeyRelease(boolean isKeyReleased) {
        this.isKeyReleased = isKeyReleased;
    }
}