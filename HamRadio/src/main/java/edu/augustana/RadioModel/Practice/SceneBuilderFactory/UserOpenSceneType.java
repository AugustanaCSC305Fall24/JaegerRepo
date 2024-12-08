package edu.augustana.RadioModel.Practice.SceneBuilderFactory;

public class UserOpenSceneType implements SceneType{
    @Override
    public int getNumBaseBot() {
        return 0;
    }

    @Override
    public int getNumHintBot() {
        return 0;
    }

    @Override
    public int getNumConversationalBot() {
        return 0;
    }

    public String toString(){
        return "UserOpenedSceneType";
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
