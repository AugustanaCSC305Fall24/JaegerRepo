package edu.augustana.RadioModel.Practice.TaskCollection;

import edu.augustana.RadioModel.Practice.BotCollections.Bot;

public class ReceivingMessageTask implements TaskForPractice {

    private String description;
    private Bot sender;

    public ReceivingMessageTask(String description, Bot sender){
        this.description = description;
        this.sender = sender;
    }

    @Override
    public String getDescription(){
        return description;
    }

    @Override
    public Bot getSender(){
        return sender;
    }

    @Override
    public String toString(){
        return "Task Description: " + description + "/n Sender: " + sender;
    }
}
