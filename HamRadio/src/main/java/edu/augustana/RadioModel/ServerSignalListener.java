package edu.augustana.RadioModel;

import javax.sound.sampled.LineUnavailableException;

// Functional interface for the callback
@FunctionalInterface
public interface ServerSignalListener {
    void onSignalReceived(CWMessage signal) throws LineUnavailableException;
}