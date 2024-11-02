package edu.augustana.Application.UIHelper;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyBindManager {

    // Map to store a list of actions for each key code
    private final Map<KeyCode, List<Runnable>> keybindPool = new HashMap<>();

    // Register a new key bind by adding the action to the key's list of actions
    public void registerKeybind(KeyCode key, Runnable action) {
        keybindPool.computeIfAbsent(key, k -> new ArrayList<>()).add(action);
    }

    // Handle a key press event by running all actions for that key
    public void handleKeyPress(KeyEvent event) {
        KeyCode key = event.getCode();
        List<Runnable> actions = keybindPool.get(key);
        if (actions != null) {
            actions.forEach(Runnable::run);  // Execute each action for this key
        }
    }

    // Remove a specific action for a given key
    public void removeKeybind(KeyCode key, Runnable action) {
        List<Runnable> actions = keybindPool.get(key);
        if (actions != null) {
            actions.remove(action);
            if (actions.isEmpty()) {
                keybindPool.remove(key);
            }
        }
    }
}