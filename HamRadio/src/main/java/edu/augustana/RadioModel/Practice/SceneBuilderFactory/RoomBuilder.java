package edu.augustana.RadioModel.Practice.SceneBuilderFactory;

import edu.augustana.Application.UI.App;
import edu.augustana.RadioModel.Practice.BotCollections.BaseBot;
import edu.augustana.RadioModel.Practice.BotCollections.Bot;
import edu.augustana.RadioModel.Practice.BotCollections.ConversationalBot;
import edu.augustana.RadioModel.Practice.BotCollections.HintBot;
import edu.augustana.RadioModel.Practice.PracticeScenario;
import edu.augustana.RadioModel.Practice.TaskCollection.ForrestScriptedGame.HintPoliceTask;
import edu.augustana.RadioModel.Practice.TaskCollection.HintTask;
import edu.augustana.RadioModel.Practice.TaskCollection.TaskFactory;
import edu.augustana.RadioModel.Practice.TaskCollection.TaskForPractice;
import edu.augustana.RadioModel.Practice.UserPreferences;
import edu.augustana.RadioModel.User;
import javafx.concurrent.Task;

public class RoomBuilder {
    int numBaseBot;
    int numHintBot;
    int numConvoBot;
    UserPreferences userPreferences = App.getUserPrefs();
    SceneType sceneType = userPreferences.getSceneType();
    TaskFactory taskFactory = new TaskFactory(sceneType);
    PracticeScenario room;
    HintAssigner hintAssigner;

    public RoomBuilder(PracticeScenario room, UserPreferences userPreferences){
        numBaseBot = userPreferences.getNumBaseBot();
        numHintBot = userPreferences.getNumHintBot();
        numConvoBot = userPreferences.getNumConvoBot();
        this.room = room;
        hintAssigner = new HintAssigner(userPreferences);
    }

    public void buildRoom(){
        for(int i = 0; i < numBaseBot; i++){
            Bot baseBot = new BaseBot(0, sceneType.getNameBaseBot(), Bot.getRandomFreq());
            room.getBotList().add(baseBot);
            TaskForPractice task = taskFactory.buildTask(baseBot);
            room.getTaskList().add(task);
            baseBot.setTask(task);
            debugRoom(baseBot);
        }

        for (int i = 0; i< numHintBot; i++){
            Bot hintBot = new HintBot(0, sceneType.getNameHintBotList()[i], Bot.getRandomFreq());
            room.getBotList().add(hintBot);
            HintTask task = (HintTask) taskFactory.buildTask(hintBot);
            hintAssigner.assignTask(task, i);
            room.getTaskList().add(task);
            hintBot.setTask(task);
            debugRoom(hintBot);
        }

        for (int i = 0; i < numConvoBot; i++){
            Bot convoBot = new ConversationalBot(0, Bot.nameList[i],Bot.getRandomFreq());
            room.getBotList().add(convoBot);
            TaskForPractice task = taskFactory.buildTask(convoBot);
            room.getTaskList().add(task);
            convoBot.setTask(task);
            debugRoom(convoBot);
        }
    }


    public void debugRoom(Bot newBot){
        //System.out.println("For testing in RoomBuilder Practice UI: " + newBot + ", Freq: " + newBot.getBotFrequency() + ", Task: " + newBot.getTask());
        //System.out.println(room.getTaskList());
    }
}

