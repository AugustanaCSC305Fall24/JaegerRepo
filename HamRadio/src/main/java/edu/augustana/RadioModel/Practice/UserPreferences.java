package edu.augustana.RadioModel.Practice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class UserPreferences {
    public static File DEFAULT_USER_PREFERENCES_FILE = new File("user_preferences2.json");
    private String primaryUserName = "User";
    private String serverAddress = "localhost";
    private int numBot = 0;
    private int wpm = 0;
    private boolean isWhiteNoise = false;

    public UserPreferences() {
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

    public int getNumBot() {return numBot;}

    public void setNumBot(int numBot) {this.numBot = numBot;}

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
        return "From Reference: UserName...." + getPrimaryUserName()
                + "\nFrom Reference: Num of Bots...." + getNumBot()
                + "\nFrom Reference: WPM...." + getWPM()
                + "\nFrom Reference: White Noise.... " + getWhiteNoise();
    }
}
