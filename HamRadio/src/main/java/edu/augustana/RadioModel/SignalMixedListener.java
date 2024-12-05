package edu.augustana.RadioModel;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

// Functional interface for the callback
@FunctionalInterface
public interface SignalMixedListener {
    void onSignalReceived(byte[] signalBuffer) throws LineUnavailableException, IOException;
}