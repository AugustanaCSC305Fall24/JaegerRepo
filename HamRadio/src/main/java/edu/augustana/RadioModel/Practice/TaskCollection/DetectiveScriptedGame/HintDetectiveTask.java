package edu.augustana.RadioModel.Practice.TaskCollection.DetectiveScriptedGame;

import edu.augustana.RadioModel.Practice.BotCollections.Bot;
import edu.augustana.RadioModel.Practice.TaskCollection.TaskForPractice;

public class HintDetectiveTask implements TaskForPractice {
    private Bot sender;
    private String hintDescription;
    public static final String[] hintList = {"Helen Surrealism", "Impressionism no water color",
                                            "Clara no acrylics", "Surrealism acrylic",
                                            "Anna no watercolor"};

    public HintDetectiveTask(Bot sender){
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

    public static String[] getHintList(){
        return hintList;
    }
}
