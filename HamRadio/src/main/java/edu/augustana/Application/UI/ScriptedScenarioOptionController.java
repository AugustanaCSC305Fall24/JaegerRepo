package edu.augustana.Application.UI;

import edu.augustana.RadioModel.Practice.BotCollections.AIBot;
import edu.augustana.RadioModel.Practice.SceneBuilderFactory.RoomBuilder;
import edu.augustana.RadioModel.Practice.UserPreferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ScriptedScenarioOptionController {
    private PopUpManager pop = new PopUpManager();
    public static String scriptedSceneListener;

    @FXML
    void backAction(ActionEvent event) throws IOException {
        App.setRoot("ScenarioOption");
    }

    @FXML
    void detectiveAction(ActionEvent event) throws IOException {
        UserPreferences detectivePreference = new UserPreferences("User1", 10, "detective", false);
        App.returnApp().setUserPrefs(detectivePreference);
        scriptedSceneListener = "detective";
        RoomBuilder roomBuilder = new RoomBuilder(App.getCurrentPracticeScenerio(), App.getUserPrefs());
        roomBuilder.buildRoom();
        String systemPromptText = "You are police detective in a chatroom. Your role is just waited for the user " +
                "to text your the result of the string 'Clara'. If" +
                "the user type it right, your will respond YES, THANK YOU FOR HELPING. Otherwise, " +
                "just say TRY AGAIN, YOU GET IT!!!";
        AIBot newBot = new AIBot("Police Officer", Color.RED, App.getCurrentPracticeScenerio(), systemPromptText);
        App.getCurrentPracticeScenerio().getBotList().add(newBot);
        //System.out.println(App.getUserPrefs());
        App.setRoot("ScenarioSetScreen");
    }

    @FXML
    void forrestAction(ActionEvent event) throws IOException {
        UserPreferences forrestPreference = new UserPreferences("User2", 10, "forrest", true);
        App.returnApp().setUserPrefs(forrestPreference);
        scriptedSceneListener = "forrest";
        RoomBuilder roomBuilder = new RoomBuilder(App.getCurrentPracticeScenerio(), App.getUserPrefs());
        roomBuilder.buildRoom();
        String systemPromptText = "You are police officer in a chatroom. Your role is just waited for the user " +
                "to text your the result of the string '7 people at Palatino St'. If" +
                "the user type it right, your will respond YES, THANK YOU FOR HELPING. Otherwise, " +
                "just say TRY AGAIN, YOU GET IT!!!";
        AIBot newBot = new AIBot("Police Officer", Color.RED, App.getCurrentPracticeScenerio(), systemPromptText);
        App.getCurrentPracticeScenerio().getBotList().add(newBot);
        //System.out.println(App.getUserPrefs());
        App.setRoot("ScenarioSetScreen");

    }

    @FXML
    void helpAction(ActionEvent event) {
        pop.popUpWindow("SceneBuilderHelp", 400, 500, "Instruction");
    }
}
