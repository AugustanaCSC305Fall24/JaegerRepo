package edu.augustana.RadioModel.Practice.BotCollections;

import edu.augustana.Application.UIHelper.MorseCodePlayer;
import edu.augustana.Application.UIHelper.MorseCodeTranslator;
import edu.augustana.RadioModel.HamRadioSimulatorInterface;
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
    boolean isThreadStop;
    Thread thread;

    public static final String[] nameList = {"Andy", "Mason", "Hoang", "Hieu",
                                            "Minh", "Unknown", "Yike", "Beef", "Cow",
                                            "Fish", "Stonedalh", "Vincent"};
    private static final Random randomGen = new Random();

    public Bot(int level, String idCode, double frequency){
        this.level = level;
        this.idCode = idCode;
        this.frequency = frequency;
        isThreadStop = false;
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
//    public String toString(){
//        return "Name: " + idCode + ", Level: " + level;
//    }

    public static String getRandomBotNameFromList() {
        String[] names = {"Alice", "Bubba", "Candy", "Doodles", "Egbert", "Fifi", "Gus", "Holly", "Iggy",
                "Jasper", "Kiki", "Lulu", "Mimi", "Noodles", "Oscar", "Penny", "Quincy", "Rufus", "Sally",
                "Toby", "Ursula", "Violet", "Wally", "Xander", "Yolanda", "Zelda"};
        String[] adjectives = {"Awesome", "Bodacious", "Clunker", "Dude", "Eery", "Funky", "Goosey", "Happy",
                "Hippy", "Irritable", "Jolly", "Kooky", "Lunker", "Messy", "Nut", "Optometrist", "Punky",
                "Quirky", "Rumpled", "Snarky", "Tree", "Unknown", "Vixen", "Wonk", "Xenial", "Yummy",
                "Zany"};
        String name =names[randomGen.nextInt(names.length)];
        String adjective = adjectives[randomGen.nextInt(adjectives.length)];
        return  name + " the " + adjective;
    }

    public void requestMessage(){
    }

    public void stopPlay() {
        isThreadStop = true;
        System.out.println("\n" + this + getIDCode() + "...." + " In Stop: thread is stopped: " + isThreadStop + "\n");
    }

    private boolean getIsThreadStop() {
        return isThreadStop;
    }

    public void play(HamRadioSimulatorInterface radio) {
        new Thread(() -> {
            MorseCodePlayer player1 = new MorseCodePlayer(radio.getWPM(), radio);

            //play the morse code of that bot
            System.out.println("Bot info: " + this + "Task: " + this.getTask());
            String botTaskTranslated = MorseCodeTranslator.textToMorse(this.getTask().getDescription());
            while (!getIsThreadStop()) {
                System.out.println("\nIN Play: isThreadStop: " + this + "..." + getIDCode() + "...." +getIsThreadStop() + "\n");
                player1.playMorseForBot(botTaskTranslated, this);
            }
        }).start();
    }

    public void setPlayAgain(){
        isThreadStop = !isThreadStop;
    }
}
