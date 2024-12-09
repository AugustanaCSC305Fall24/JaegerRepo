package edu.augustana.RadioModel.Practice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.augustana.RadioModel.Practice.SceneBuilderFactory.HamRadioSceneTypeFactory;
import edu.augustana.RadioModel.Practice.SceneBuilderFactory.SceneBuilderFactory;
import edu.augustana.RadioModel.Practice.SceneBuilderFactory.SceneType;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class UserPreferences {
    public static File DEFAULT_USER_PREFERENCES_FILE = new File("user_preferences2.json");
    private String primaryUserName = "User";
    private String serverAddress = "localhost";
    private int wpm = 0;
    private String sceneTypeString = "forrest";
    private SceneBuilderFactory sceneBuilderFactory;
    private boolean isWhiteNoise = false;
    private SceneType sceneType;
    private int numBaseBot;
    private int numHintBot;
    private int numConvoBot;

    public UserPreferences(){
    }

    public UserPreferences(String primaryUserName, int wpm, String sceneTypeString, boolean isWhiteNoise) {
        this.primaryUserName = primaryUserName;
        this.wpm = wpm;
        this.sceneTypeString = sceneTypeString;
        this.isWhiteNoise = isWhiteNoise;
        sceneBuilderFactory = new HamRadioSceneTypeFactory(sceneTypeString);
        sceneType = sceneBuilderFactory.build();
        numBaseBot = sceneType.getNumBaseBot();
        numHintBot = sceneType.getNumHintBot();
        numConvoBot = sceneType.getNumConversationalBot();
    }

    public static File getCurrentUserDataFile() {
        return DEFAULT_USER_PREFERENCES_FILE;
    }

    public static void setCurrentUserDataFile(File chosenFile) {
        DEFAULT_USER_PREFERENCES_FILE = chosenFile;
    }

    public String getPrimaryUserName() {
        return primaryUserName;
    }

    public void setPrimaryUserName(String primaryUserName) {
        this.primaryUserName = primaryUserName;
    }


    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public void setWPM(int wpm){this.wpm = wpm;}

    public int getWPM(){return this.wpm;}

    public boolean getWhiteNoise(){return isWhiteNoise;}

    public void setWhiteNoise(boolean isOnorOff){isWhiteNoise = isOnorOff;}

    public int getNumBaseBot(){
        return numBaseBot;
    }
    public int getNumHintBot(){
        return numHintBot;
    }
    public int getNumConvoBot(){
        return numConvoBot;
    }
    public SceneType getSceneType(){
        return sceneType;
    }
    public void setSceneTypeString(String sceneTypeString){
        this.sceneTypeString = sceneTypeString;
    }

    public String toJSON() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public void saveToJSONFile(File preferencesFile) {
        try {
            PrintWriter out = new PrintWriter(preferencesFile);
            out.println(toJSON());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UserPreferences loadFromJSONFile(File preferencesFile) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(new FileReader(preferencesFile), UserPreferences.class);
        } catch (Exception e) {
            System.out.println("Preferences file " + preferencesFile + " not found or invalid. Loading default preferences instead.");
            return new UserPreferences();
        }
    }

    public String toString(){
        return "From UserPreference: UserName...." + getPrimaryUserName()
                + "\nFrom UserPreference: WPM...." + getWPM()
                + "\nFrom UserPreference: White Noise.... " + getWhiteNoise()
                + "\nFrom UserPreference: SceneTypeString..." + sceneTypeString
                + "\nFrom UserPreference: NumBaseBot..." + numBaseBot
                + "\nFrom UserPreference: NumHintBot..." + numHintBot
                + "\nFrom UserPreference: NumConvoBot..." + numConvoBot;

    }
}
