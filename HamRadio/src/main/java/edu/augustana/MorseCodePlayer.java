package edu.augustana;

import javax.sound.sampled.*;

public class MorseCodePlayer extends HamUIController{
    private static final int BASE_DOT_DURATION = 100;
    private static final int BASE_DASH_DURATION = 300;
    private static final int BASE_ELEMENT_PAUSE = 100;
    private static final int BASE_LETTER_PAUSE = 300;
    private static final int BASE_WORD_PAUSE = 700;
    private static final int FREQUENCY = 800;

    private double speedFactor;

    public MorseCodePlayer(double speedFactor) {
        this.speedFactor = speedFactor;
    }

    public void setSpeedFactor(double speedFactor) {
        this.speedFactor = speedFactor;
    }

    private int adjustDuration(int baseDuration) {
        return (int) (baseDuration / speedFactor);
    }

    public void playMorseCode(String input) {
        String[] words = input.split("/");
        for (String word : words) {
            playWord(word.trim());
            pause(adjustDuration(BASE_WORD_PAUSE));
        }
    }

    private void playWord(String word) {
        String[] letters = word.split(" ");
        for (String letter : letters) {
            playLetter(letter);
            pause(adjustDuration(BASE_LETTER_PAUSE));
        }
    }

    private void playLetter(String letter) {
        for (char element : letter.toCharArray()) {
            if (element == '.') {
                playTone(FREQUENCY, adjustDuration(BASE_DOT_DURATION));
            } else if (element == '-') {
                playTone(FREQUENCY, adjustDuration(BASE_DASH_DURATION));
            } else {
                throw new IllegalArgumentException("Invalid Morse code character: " + element);
            }
            pause(adjustDuration(BASE_ELEMENT_PAUSE));
        }
    }

    private void pause(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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
                float volumeInDb = (float) ((client.getVolume() / 100) * (maxVolume - minVolume) + minVolume);

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
}