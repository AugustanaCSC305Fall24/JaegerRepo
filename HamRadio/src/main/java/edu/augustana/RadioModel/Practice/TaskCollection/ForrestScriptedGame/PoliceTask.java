package edu.augustana.RadioModel.Practice.TaskCollection.ForrestScriptedGame;

import edu.augustana.RadioModel.Practice.BotCollections.Bot;
import edu.augustana.RadioModel.Practice.TaskCollection.TaskForPractice;

public class PoliceTask implements TaskForPractice {
    private String introduction = "Change frequency. Collect info";
    private String taskDescription = "Send address. Send num people.";
    private Bot sender;

    public PoliceTask(Bot sender){
        this.sender = sender;
    }

    @Override
    public String getDescription() {
        return getIntroduction() + "..." + taskDescription ;
    }

    @Override
    public Bot getSender() {
        return sender;
    }

    public String getIntroduction(){
        return introduction;
    }

    public String toString(){
        return introduction + "..." + taskDescription;
    }

}
