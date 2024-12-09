package edu.augustana.RadioModel.Practice.TaskCollection;

import edu.augustana.RadioModel.Practice.BotCollections.Bot;

public abstract class HintTask implements TaskForPractice{
    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Bot getSender() {
        return null;
    }
    public abstract void setHintDescription(String hint);
    public abstract String toString();
}
