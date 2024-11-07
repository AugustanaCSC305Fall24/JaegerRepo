package edu.augustana.Application.UIHelper;

import edu.augustana.Application.UI.HamPracticeUIController;
import edu.augustana.Application.UI.HamUIController;
import edu.augustana.RadioModel.HamRadioSimulatorInterface;
import edu.augustana.RadioModel.SoundPlayer;

import javax.sound.sampled.*;

public class MorseCodePlayer{
    private int wordPerMin;
    private int BASE_DOT_DURATION;
    private int BASE_DASH_DURATION;
    private int BASE_ELEMENT_PAUSE;
    private int BASE_LETTER_PAUSE;
    private int BASE_WORD_PAUSE ;
    private SoundPlayer player;

    private double speedFactor;
    private HamRadioSimulatorInterface radio;

    public MorseCodePlayer(int wpm, HamRadioSimulatorInterface radio) {
        this.radio = radio;
        this.wordPerMin = wpm;
        this.BASE_DOT_DURATION = (int) ((1.2 / wordPerMin) * 1000); // Milliseconds for each dot
        this.BASE_DASH_DURATION = 3*BASE_DOT_DURATION;
        this.BASE_ELEMENT_PAUSE = BASE_DOT_DURATION;
        this.BASE_LETTER_PAUSE = BASE_DASH_DURATION;
        this.BASE_WORD_PAUSE = 7*BASE_DOT_DURATION;
        player = new SoundPlayer(radio.getVolume());

    }

    public void setSpeedFactor(double speedFactor) {
        this.speedFactor = speedFactor;
    }

    private int adjustDuration(int baseDuration) {
       // System.out.println(speedFactor);
        return baseDuration;

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
                System.out.println("Error in Adjust Duration in playLetter: " + adjustDuration(BASE_DOT_DURATION));
                playTone(HamPracticeUIController.TONE, adjustDuration(BASE_DOT_DURATION));
            } else if (element == '-') {
                playTone(HamPracticeUIController.TONE, adjustDuration(BASE_DASH_DURATION));
            } else {
                throw new IllegalArgumentException("Invalid Morse code character: " + element);
            }
            pause(adjustDuration(BASE_ELEMENT_PAUSE));
        }
    }

    public void playMorse(String userOutput){
        player.playMorse(userOutput, wordPerMin, radio.getReceiveFrequency(), radio.getTransmitFrequency(), radio.getBandWidth());
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
                float volumeInDb = (float) ((radio.getVolume() / 100) * (maxVolume - minVolume) + minVolume);

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