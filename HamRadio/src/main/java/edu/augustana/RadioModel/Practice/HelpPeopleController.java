package edu.augustana.RadioModel.Practice;

import edu.augustana.Application.UI.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.List;

public class HelpPeopleController {
    @FXML
    private Button helpButton;

    @FXML
    private Button leaveButton;

    @FXML
    private ListView peopleList;

    private PracticeScenerio room;

    @FXML
    private void initialize(){
        room = App.getCurrentPracticeScenerio();
        List<Bot> identifiedBots = room.getIdentifiedBotList();
        peopleList.getItems().addAll(identifiedBots);
    }

    @FXML
    void helpPeople(ActionEvent event) {
        Bot peopleToHelp = (Bot) peopleList.getSelectionModel().getSelectedItem();
        room.readyToHelpPeople(peopleToHelp);
    }

    @FXML
    void leavePeople(ActionEvent event) {

    }

    @FXML
    void exitAction(){

    }

}
