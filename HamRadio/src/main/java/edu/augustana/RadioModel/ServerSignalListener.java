package edu.augustana.RadioModel;

import javax.sound.sampled.LineUnavailableException;

// Functional interface for the callback
@FunctionalInterface
public interface ServerSignalListener {
    void onSignalReceived(ChatMessage signal) throws LineUnavailableException;
}