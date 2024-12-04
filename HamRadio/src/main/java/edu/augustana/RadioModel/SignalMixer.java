package edu.augustana.RadioModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignalMixer {

    private byte[] mixedSignal; // Tín hiệu tổng hiện tại
    private final Object lock = new Object(); // Đảm bảo đồng bộ khi trộn tín hiệu
    private final int sampleRate; // Tần số mẫu của tín hiệu
    private final SignalMixedListener callback; // Callback interface cho tín hiệu đã trộn
    private final ExecutorService executor; // Thread quản lý reset tín hiệu
    private final int resetIntervalMs; // Thời gian giữa các lần reset

    /**
     * Constructor của SignalMixer.
     *
     * @param sampleRate      Tần số mẫu của tín hiệu (ví dụ: 44100 Hz).
     * @param resetIntervalMs Khoảng thời gian (ms) để reset tín hiệu.
     * @param callback        Listener được gọi khi tín hiệu được reset.
     */
    public SignalMixer(int sampleRate, int resetIntervalMs, SignalMixedListener callback) {
        this.sampleRate = sampleRate;
        this.callback = callback;
        this.resetIntervalMs = resetIntervalMs;
        this.mixedSignal = new byte[0]; // Khởi tạo tín hiệu trống
        this.executor = Executors.newSingleThreadExecutor();
        startResetThread(); // Khởi chạy thread reset tín hiệu
    }

    /**
     * Mix a signal.
     *
     * @param byteArray input type: byte[].
     */
    public void mix(byte[] byteArray) {
        synchronized (lock) {
            // Resize if necessary
            if (mixedSignal.length < byteArray.length) {
                byte[] newMixedSignal = new byte[byteArray.length];
                System.arraycopy(mixedSignal, 0, newMixedSignal, 0, mixedSignal.length);
                mixedSignal = newMixedSignal;
            }

            // start mixing
            for (int i = 0; i < byteArray.length; i++) {
                int mixedValue = (mixedSignal[i] & 0xFF) + (byteArray[i] & 0xFF);
                mixedSignal[i] = (byte) Math.min(mixedValue, 255); // Giới hạn giá trị trong khoảng byte
            }
        }
    }

    /**
     * Return the current mixed signal.
     *
     * @return Một bản sao của tín hiệu tổng.
     */
    public byte[] getMixedSignal() {
        synchronized (lock) {
            return mixedSignal.clone();
        }
    }

    /**
     * Khởi động một thread để reset tín hiệu định kỳ.
     */
    private void startResetThread() {
        executor.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(resetIntervalMs);

                    synchronized (lock) {
                        if (callback != null) {
                            callback.onSignalReceived(mixedSignal.clone()); // Gọi callback với tín hiệu đã trộn
                        }
                        // Reset the signal
                        mixedSignal = new byte[mixedSignal.length];
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    /**
     * Dừng SignalMixer và giải phóng thread.
     */
    public void shutdown() {
        executor.shutdownNow();
    }
}
