package edu.augustana.RadioModel.Practice;

public class TransmittingTask implements TaskForPractice{
    private String description;
    private Bot sender;

    public TransmittingTask(String description, Bot sender){
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
