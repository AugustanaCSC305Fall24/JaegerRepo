package edu.augustana.RadioModel;

import javax.sound.sampled.LineUnavailableException;

// Functional interface for the callback
@FunctionalInterface
public interface SignalMixedListener {
    void onSignalReceived(byte[] signalBuffer);
}