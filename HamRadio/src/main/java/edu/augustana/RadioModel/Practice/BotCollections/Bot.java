package edu.augustana.RadioModel.Practice.BotCollections;

import edu.augustana.RadioModel.Practice.TaskCollection.TaskForPractice;

import java.util.Random;

public abstract class Bot implements Bots{
    private int level;
    private String idCode;
    private double frequency;
    private boolean isDiscovered = false;
    private boolean askedForHelp = false;
    private boolean isAddedToIdentifiedList = false;
    private TaskForPractice task;
    private String botType;

    public static final String[] nameList = {"Andy", "Mason", "Hoang", "Hieu",
                                            "Minh", "Unknown", "Yike", "Beef", "Cow",
                                            "Fish", "Stonedalh", "Vincent"};
    private static final Random randomGen = new Random();

    public Bot(int level, String idCode, double frequency){
        this.level = level;
        this.idCode = idCode;
        this.frequency = frequency;
    }

    public int getLevel(){
        return level;
    }

    public String getIDCode(){
        return idCode;
    }

    public String getRandomBotName() {
        //String name =nameList[randomGen.nextInt(nameList.length)];
        return idCode;
    }

    public static int getRandomLevel(){
        int level = randomGen.nextInt(3);
        return level;
    }

    public static double getRandomFreq(){
        int min = 7000;
        int max = 7067;
        double randomFreq = (double) (randomGen.nextInt(max - min + 1) + min) + randomGen.nextDouble();
        return randomFreq;
    }

    public double getBotFrequency(){
        return frequency;
    }

    public void setDiscovered(){
        isDiscovered = true;
    }

    public void setDidAskForHelp(){
        askedForHelp = true;
    }

    public boolean didAskForHelp(){
        return askedForHelp;
    }

    public boolean isDiscovered(){
        return isDiscovered;
    }

    public boolean isAddedToIdentifiedList(){
        return isAddedToIdentifiedList;
    }

    public void setAddedToIdentifiedList(){
        isAddedToIdentifiedList = true;
    }

    public TaskForPractice getTask(){
        return task;
    }

    public void setTask(TaskForPractice task){
        this.task = task;
    }
    public String getBotType(){
        return botType;
    }
    public String toString(){
        return "Name: " + idCode + ", Level: " + level;
    }

}
