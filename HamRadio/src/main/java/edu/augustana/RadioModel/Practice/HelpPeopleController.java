package edu.augustana.RadioModel.Practice;

import edu.augustana.Application.UI.App;
import edu.augustana.RadioModel.Practice.BotCollections.Bot;
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

    private PracticeScenario room;

    @FXML
    private void initialize(){
        room = App.getCurrentPracticeScenerio();
        List<Bot> identifiedBots = room.getIdentifiedBotList();
        peopleList.getItems().addAll(identifiedBots);
    }

    @FXML
    void leavePeople(ActionEvent event) {

    }

    @FXML
    void exitAction(){

    }

}
