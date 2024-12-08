package edu.augustana.RadioModel.Practice.TaskCollection;

import edu.augustana.RadioModel.Practice.BotCollections.Bot;

public class TransmittingTask implements TaskForPractice {
    private String description;
    private Bot sender;
    public static String[] desscriptionOptions = {"Augustana", "Ambrose",
            "Chicago", "Tampa Bay", "Rock Island", "39 Street", "20 Street",
            "Westerlin", "Erickson", "Olin Center", "Oldmain", "Swanson Commons"};

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
