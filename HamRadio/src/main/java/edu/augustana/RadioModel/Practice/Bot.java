package edu.augustana.RadioModel.Practice;

import java.util.Random;

public class Bot {
    private int level;
    private String idCode;
    private double frequency;

    public static final String[] nameList = {"Andy", "Mason", "Hoang", "Hieu",
                                            "Minh", "Unknown", "Yike", "Beef", "Cow",
                                            "Fish"};
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

    public void generateTask(){

    }

    public void generateResponse(){

    }

    public static String getRandomBotName() {
        String name =nameList[randomGen.nextInt(nameList.length)];
        return name;
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

    public String toString(){
        return "Name: " + idCode + ", Level: " + level;
    }

}
