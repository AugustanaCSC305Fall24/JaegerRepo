package edu.augustana.RadioModel.Practice;

public class ReceivingMessageTask implements TaskForPractice{

    private String description;
    private Bot sender;

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
