package edu.augustana.RadioModel.Practice;

public class UserPrefBuilder {


    private UserPreferences userPreferences;
    private String primaryUserName;
    private int wpm;
    private String sceneTypeString;
    private boolean isWhiteNoise;
    private int numBaseBot;
    private int numHintBot;
    private int numConvoBot;

    public UserPrefBuilder(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
        this.primaryUserName = userPreferences.getPrimaryUserName();
        this.wpm = userPreferences.getWPM();
        this.sceneTypeString = userPreferences.getPrimaryUserName();
        this.isWhiteNoise = userPreferences.getWhiteNoise();
        this.numBaseBot = userPreferences.getNumBaseBot();
        this.numHintBot = userPreferences.getNumHintBot();
        this.numConvoBot = userPreferences.getNumConvoBot();
    }

    public void setPrimaryUserName(String primaryUserName) {
        this.primaryUserName = primaryUserName;
    }

    public void setWpm(int wpm) {
        this.wpm = wpm;
    }

    public void setSceneTypeString(String sceneTypeString) {
        this.sceneTypeString = sceneTypeString;
    }

    public void setWhiteNoise(boolean whiteNoise) {
        isWhiteNoise = whiteNoise;
    }



}
