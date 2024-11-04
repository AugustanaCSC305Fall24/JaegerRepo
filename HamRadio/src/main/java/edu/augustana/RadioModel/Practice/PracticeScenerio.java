package edu.augustana.RadioModel.Practice;

import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

public class PracticeScenerio {
    private List<Bot> botList;
    private List<TaskForPractice> taskList;

    public PracticeScenerio(){
        botList = new ArrayList<>();
        taskList = new ArrayList<>();
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


}
