package edu.augustana.Application.UI;

import edu.augustana.RadioModel.Practice.UserPreferences;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class FileManager {
    public FileManager(){}

    public void menuActionOpenUserData(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open User Data File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(filter);

        // Use the event's source to get the stage safely
        Window mainWindow = ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        File chosenFile = fileChooser.showOpenDialog(mainWindow);

        if (chosenFile != null) {
            try {
                // Load user preferences from the selected file
                UserPreferences prefs = UserPreferences.loadFromJSONFile(chosenFile);
                UserPreferences.setCurrentUserDataFile(chosenFile); // Update the current file reference

                // Update application state with loaded preferences
                applyLoadedPreferences(prefs);

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error loading user data file: " + chosenFile).show();
            }
        }
    }


    public void applyLoadedPreferences(UserPreferences prefs) throws IOException {
        // Example of applying preferences to the application
        System.out.println("User Name: " + prefs.getPrimaryUserName());
        System.out.println("Number of Bots: " + prefs.getNumBot());
        System.out.println("WPM: " + prefs.getWPM());
        System.out.println("White noise: " + prefs.getWhiteNoise());

    }

    public void menuActionSaveUserData(ActionEvent event) {
        if (UserPreferences.getCurrentUserDataFile() == null) {
            menuActionSaveUserDataAs(event);
        } else {
            saveUserDataToFile(UserPreferences.getCurrentUserDataFile());
        }
    }

    public void menuActionSaveUserDataAs(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save User Data File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("JSON Files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(filter);

        // Use the MenuItem's parent popup to get the owner window
        Window mainWindow = ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        File chosenFile = fileChooser.showSaveDialog(mainWindow);

        if (chosenFile != null) {
            UserPreferences.setCurrentUserDataFile(chosenFile); // Update the current file reference
            saveUserDataToFile(chosenFile);
        }
    }

    public void saveUserDataToFile(File chosenFile) {
        if (chosenFile != null) {
            try {
                UserPreferences prefs = App.getUserPrefs();
                // Save preferences to the chosen file
                prefs.saveToJSONFile(chosenFile);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error saving user data file: " + chosenFile).show();
            }
        }
    }
}
