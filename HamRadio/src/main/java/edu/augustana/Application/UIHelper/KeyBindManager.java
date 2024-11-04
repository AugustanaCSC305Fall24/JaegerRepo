package edu.augustana.Application.UIHelper;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyBindManager {

    // Separate maps to store actions for key presses and releases
    private final Map<KeyCode, List<Runnable>> keyPressActions = new HashMap<>();
    private final Map<KeyCode, List<Runnable>> keyReleaseActions = new HashMap<>();

    // Register a new key press action by adding it to the key's list of press actions
    public void registerKeyPressAction(KeyCode key, Runnable action) {
        keyPressActions.computeIfAbsent(key, k -> new ArrayList<>()).add(action);
    }

    // Register a new key release action by adding it to the key's list of release actions
    public void registerKeyReleaseAction(KeyCode key, Runnable action) {
        keyReleaseActions.computeIfAbsent(key, k -> new ArrayList<>()).add(action);
    }

    // Register both a key press and a key release action for a given key
    public void registerKeybind(KeyCode key, Runnable onPressAction, Runnable onReleaseAction) {
        registerKeyPressAction(key, onPressAction);
        registerKeyReleaseAction(key, onReleaseAction);
    }

    // Handle a key press event by running all press actions for that key
    public void handleKeyPress(KeyEvent event) {
        KeyCode key = event.getCode();
        List<Runnable> actions = keyPressActions.get(key);
        if (actions != null) {
            actions.forEach(Runnable::run);  // Execute each press action for this key
        }
    }

    // Handle a key release event by running all release actions for that key
    public void handleKeyRelease(KeyEvent event) {
        KeyCode key = event.getCode();
        List<Runnable> actions = keyReleaseActions.get(key);
        if (actions != null) {
            actions.forEach(Runnable::run);  // Execute each release action for this key
        }
    }

    // Remove a specific press action for a given key
    public void removeKeyPressAction(KeyCode key, Runnable action) {
        List<Runnable> actions = keyPressActions.get(key);
        if (actions != null) {
            actions.remove(action);
            if (actions.isEmpty()) {
                keyPressActions.remove(key);
            }
        }
    }

    // Remove a specific release action for a given key
    public void removeKeyReleaseAction(KeyCode key, Runnable action) {
        List<Runnable> actions = keyReleaseActions.get(key);
        if (actions != null) {
            actions.remove(action);
            if (actions.isEmpty()) {
                keyReleaseActions.remove(key);
            }
        }
    }
}
