package edu.augustana.Application.UI;
import javafx.fxml.FXML;
import java.io.IOException;
import edu.augustana.RadioModel.Practice.UserPreferences;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ScenarioSetScreenController {
    @FXML
    private TextField primaryUserNameTextField;
    @FXML
    private ComboBox<Integer> numBotBox;
    @FXML
    private ComboBox<Integer> wpmBox;

    UserPreferences prefs = App.getUserPrefs();

    @FXML
    private void initialize() {
        numBotBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7);
        wpmBox.getItems().addAll(5, 10, 15, 20, 25, 30);
    }

    @FXML
    public void actionStartChatting() throws IOException {
        String primaryUserName = primaryUserNameTextField.getText();
        System.out.println("Debug in Controller:............." + App.getUserPrefs());
        App.getUserPrefs().setPrimaryUserName(primaryUserName);
        App.getUserPrefs().setNumBot((int) numBotBox.getSelectionModel().getSelectedItem());
        App.getUserPrefs().setWPM((int) wpmBox.getSelectionModel().getSelectedItem());
        //String serverIPAddress = serverAddressTextField.getText();
        //App.getUserPrefs().setServerAddress(serverIPAddress);
        App.getUserPrefs().saveToJSONFile(UserPreferences.DEFAULT_USER_PREFERENCES_FILE);
        App.setRoot("HamPracticeUI");
    }

}