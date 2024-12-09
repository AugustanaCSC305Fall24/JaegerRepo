package edu.augustana.RadioModel.Practice.TaskCollection.ForrestScriptedGame;

import edu.augustana.RadioModel.Practice.BotCollections.Bot;
import edu.augustana.RadioModel.Practice.TaskCollection.HintTask;
import edu.augustana.RadioModel.Practice.TaskCollection.TaskForPractice;

public class HintPoliceTask extends HintTask implements TaskForPractice {
    private Bot sender;
    private String hintDescription;
    public static final String[] hintList = {"7 people", "Palatino St"};

    public HintPoliceTask(Bot sender){
        this.sender = sender;
    }
    @Override
    public String getDescription() {
        return hintDescription;
    }

    @Override
    public Bot getSender() {
        return sender;
    }

    public void setHintDescription(String hint){
        hintDescription = hint;
    }

    @Override
    public String toString() {
        return hintDescription;
    }

    public static String[] getHintList(){
        return hintList;
    }
}
