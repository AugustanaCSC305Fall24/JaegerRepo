package edu.augustana.RadioModel.Practice.TaskCollection.DetectiveScriptedGame;

import edu.augustana.RadioModel.Practice.BotCollections.Bot;
import edu.augustana.RadioModel.Practice.TaskCollection.TaskForPractice;

public class DetectiveTask implements TaskForPractice {
    private String introduction = "Killer uses watercolor";
    private String taskDescription = "Who killer?";
    private Bot sender;

    public DetectiveTask(Bot sender){
        this.sender = sender;
    }
    @Override
    public String getDescription() {
        return taskDescription;
    }
    public String getIntroduction(){
        return introduction;
    }

    @Override
    public Bot getSender() {
        return sender;
    }

    public String toString(){
        return introduction + "..." + taskDescription;
    }
}
