package edu.augustana.RadioModel.Practice;

import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

public class PracticeScenerio {
    private List<Bot> botList;
    private List<TaskForPractice> taskList;
    private List<Bot> identifiedBotList;
    private List<TaskForPractice> transmitTaskList;
    private int score = 0;

    public PracticeScenerio(){
        botList = new ArrayList<>();
        taskList = new ArrayList<>();
        identifiedBotList = new ArrayList<>();
    }

    private void addBot(Bot botToAdd){
        botList.add(botToAdd);

    }

    private void deleteBot(Bot botToRemove){
        botList.remove(botToRemove);

    }

    private void addTask(){

    }

    private void removeTask(){

    }

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

    public void addPeopleToHelpList(Bot bot){
        return;
    }

    public int getScore() {
        return score;
    }
}
