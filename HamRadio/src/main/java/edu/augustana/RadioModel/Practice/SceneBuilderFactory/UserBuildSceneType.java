package edu.augustana.RadioModel.Practice.SceneBuilderFactory;

public class UserBuildSceneType implements SceneType{
    private int numBaseBot = 0;
    private int numHintBot = 0;
    private int numConversationalBot = 0;
    @Override
    public int getNumBaseBot() {
        return numBaseBot;
    }

    @Override
    public int getNumHintBot() {
        return numHintBot;
    }

    @Override
    public int getNumConversationalBot() {
        return numBaseBot;
    }

    public String toString(){
        return "UserBuiltSceneType";
    }

    @Override
    public String getNameBaseBot() {
        return "";
    }

    @Override
    public String[] getNameHintBotList() {
        return new String[0];
    }

    @Override
    public String getNameConvoBot() {
        return "";
    }
}
