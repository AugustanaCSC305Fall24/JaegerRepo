package edu.augustana.RadioModel.Practice.BotCollections;

import edu.augustana.RadioModel.Practice.TaskCollection.TaskForPractice;

import java.util.Random;

public interface Bots {
    public int getLevel();
    public String getIDCode();
    public String getRandomBotName();
    public double getBotFrequency();
    public void setDiscovered();
    public void setDidAskForHelp();
    public boolean didAskForHelp();
    public boolean isDiscovered();
    public boolean isAddedToIdentifiedList();
    public void setAddedToIdentifiedList();
    public TaskForPractice getTask();
    public void setTask(TaskForPractice task);

}
