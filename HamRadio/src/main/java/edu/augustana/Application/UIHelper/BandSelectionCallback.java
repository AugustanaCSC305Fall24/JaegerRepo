package edu.augustana.Application.UIHelper;

import javax.sound.sampled.LineUnavailableException;

// Functional interface for the callback
@FunctionalInterface
public interface BandSelectionCallback {
    void onBandSelection(boolean bandSelected);
}