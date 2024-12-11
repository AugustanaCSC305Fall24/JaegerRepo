package edu.augustana.RadioModel.Practice.TaskCollection;

import edu.augustana.RadioModel.Practice.BotCollections.Bot;
import edu.augustana.RadioModel.Practice.ChatMessage;

public class ConvoTask implements TaskForPractice{
    private Bot sender;

    private ChatMessage message;

    public ConvoTask(Bot sender, ChatMessage message){
        this.sender = sender;
        this.message = message;
    }
    @Override
    public String getDescription() {
        return message.getText();
    }

    public ChatMessage getMessage() {
        return message;
    }

    @Override
    public Bot getSender() {
        return sender;
    }

    public String toString(){
        return "message";
    }
}
