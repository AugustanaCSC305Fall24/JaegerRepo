package edu.augustana.RadioModel;

import javax.sound.sampled.*;

public class SoundPlayer {
    private double volume;

    public SoundPlayer(double volume) {
        this.volume = volume;
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

    public void playTone(double frequency, double volume, boolean isKeyReleased) {
        try {
            float sampleRate = 42000;
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
            int i = 0;
            // Sinh tín hiệu và phát qua loa
            while(!isKeyReleased) {
                playSoundAtInstance(sampleRate, frequency, sdl, i);
            }

            // Kết thúc phát âm thanh
            sdl.drain();
            sdl.stop();
            sdl.close();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playSoundAtInstance(double sampleRate, double frequency, SourceDataLine sdl, int i) {
        double angle = i / (sampleRate / frequency) * 2.0 * Math.PI;
        byte[] buf = new byte[1];
        buf[0] = (byte) (Math.sin(angle) * 127);  // Tạo sóng âm thanh
        sdl.write(buf, 0, 1);
    }
}