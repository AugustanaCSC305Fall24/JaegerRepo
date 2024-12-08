package edu.augustana.RadioModel.Practice.SceneBuilderFactory;

import edu.augustana.Application.UI.App;
import edu.augustana.RadioModel.Practice.BotCollections.BaseBot;
import edu.augustana.RadioModel.Practice.BotCollections.Bot;
import edu.augustana.RadioModel.Practice.BotCollections.ConversationalBot;
import edu.augustana.RadioModel.Practice.BotCollections.HintBot;
import edu.augustana.RadioModel.Practice.PracticeScenario;
import edu.augustana.RadioModel.Practice.TaskCollection.TaskFactory;
import edu.augustana.RadioModel.Practice.TaskCollection.TaskForPractice;
import edu.augustana.RadioModel.Practice.UserPreferences;
import edu.augustana.RadioModel.User;

public class RoomBuilder {
    int numBaseBot;
    int numHintBot;
    int numConvoBot;
    UserPreferences userPreferences = App.getUserPrefs();
    SceneType sceneType = userPreferences.getSceneType();
    TaskFactory taskFactory = new TaskFactory(sceneType);
    PracticeScenario room;

    public RoomBuilder(PracticeScenario room, UserPreferences userPreferences){
        numBaseBot = userPreferences.getNumBaseBot();
        numHintBot = userPreferences.getNumHintBot();
        numConvoBot = userPreferences.getNumConvoBot();
        this.room = room;
    }

    public void buildRoom(){
        for(int i = 0; i < numBaseBot; i++){
            Bot baseBot = new BaseBot(0, sceneType.getNameBaseBot(), Bot.getRandomFreq());
            room.getBotList().add(baseBot);
            room.getTaskList().add(taskFactory.buildTask(baseBot));
            debugRoom(baseBot);
        }

        for (int i = 0; i< numHintBot; i++){
            Bot hintBot = new HintBot(0, sceneType.getNameHintBotList()[i], Bot.getRandomFreq());
            room.getBotList().add(hintBot);
            room.getTaskList().add(taskFactory.buildTask(hintBot));
            debugRoom(hintBot);

        }

        for (int i = 0; i < numConvoBot; i++){
            Bot convoBot = new ConversationalBot(0, Bot.nameList[i],Bot.getRandomFreq());
            room.getBotList().add(convoBot);
            room.getTaskList().add(taskFactory.buildTask(convoBot));
            debugRoom(convoBot);
        }
    }

    private void debugRoom(Bot newBot){
        System.out.println("For testing in initialize() Practice UI: " + newBot + ", Freq: " + newBot.getBotFrequency());
    }
}

