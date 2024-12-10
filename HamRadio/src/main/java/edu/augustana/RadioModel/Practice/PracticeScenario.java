package edu.augustana.RadioModel.Practice;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import edu.augustana.RadioModel.Practice.BotCollections.Bot;
import edu.augustana.RadioModel.Practice.SceneBuilderFactory.SceneType;
import edu.augustana.RadioModel.Practice.TaskCollection.TaskForPractice;

import java.util.ArrayList;
import java.util.List;

public class PracticeScenario {
    private List<Bot> botList;
    private List<TaskForPractice> taskList;
    private List<Bot> identifiedBotList;

    private List<TaskForPractice> transmitTaskList;
    private List<ChatMessage> chatLogMessageList;
    private NewMessageEventListener newMessageEventListener = null;
    private SceneType sceneType;
    private int score = 0;

    public PracticeScenario(){
        botList = new ArrayList<>();
        taskList = new ArrayList<>();
        identifiedBotList = new ArrayList<>();
        chatLogMessageList = new ArrayList<>();
    }

    private void addBot(Bot botToAdd){
        botList.add(botToAdd);
    }

    private void deleteBot(Bot botToRemove){
        botList.remove(botToRemove);
    }

    public List<ChatMessage> getChatLogMessageList() {
        return chatLogMessageList;
    }

    public void addChatMessage(ChatMessage message){
        chatLogMessageList.add(message);
    }

    private void addTask(){}

    private void removeTask(){}

    public List<Bot> getBotList(){
        return botList;
    }

    public List<TaskForPractice> getTaskList(){
        return taskList;
    }

    public List<Bot> getIdentifiedBotList(){
        return identifiedBotList;
    }

    public void addBotToIdentifiedList(Bot bot){
        if (!bot.isAddedToIdentifiedList()){
            identifiedBotList.add(bot);
            bot.setAddedToIdentifiedList();
        }
    }

    public List<TaskForPractice> getTransmitTask(){
        return transmitTaskList;
    }

    public int getScore() {
        return score;
    }

    public void setSceneType(SceneType sceneType) {
        this.sceneType = sceneType;
    }

    public SceneType getSceneType(){
        return sceneType;
    }
    public void setNewMessageEventListener(NewMessageEventListener listener) {
        newMessageEventListener = listener;
    }

    public void clearChatLogMessageList(){
        chatLogMessageList.clear();
    }


    public String toString(){
        return "\nPrinting The room....."+
                "\nBotList: ..............." + botList+
                "\n SceneType: ............" + sceneType;
    }
}
