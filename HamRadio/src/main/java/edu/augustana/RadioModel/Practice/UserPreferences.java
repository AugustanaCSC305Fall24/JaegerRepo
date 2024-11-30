package edu.augustana.RadioModel.Practice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class UserPreferences {
    public static final File DEFAULT_USER_PREFERENCES_FILE = new File("user_preferences.json");

    private String primaryUserName = "User";
    private String serverAddress = "localhost";
    private int numBot = 0;

    public UserPreferences() {
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
}
