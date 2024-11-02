package edu.augustana.RadioModel.Practice;

import java.util.Random;

public class Bot {
    private int level;
    private String idCode;

    public static final String[] nameList = {"Andy", "Mason", "Hoang", "Hieu",
                                            "Minh", "Unknown", "Yike", "Beef", "Cow",
                                            "Fish"};
    private static final Random randomGen = new Random();

    public Bot(int level, String idCode){
        this.level = level;
        this.idCode = idCode;
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

    public String toString(){
        return "Name: " + idCode + ", Level: " + level;
    }

}
