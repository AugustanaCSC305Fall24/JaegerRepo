package edu.augustana.RadioModel.Practice.SceneBuilderFactory;

public class ForrestSceneType implements SceneType{
    private int numBaseBot = 1;
    private int numHintBot = 2;
    private int numConversationalBot = 0;
    String nameBaseBot = "Police";
    String[] nameHintBotList = {"Hint1", "Hint2"};

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
        return numConversationalBot;
    }

    public String toString(){
        return "ForrestSceneType";
    }

    @Override
    public String getNameBaseBot() {
        return nameBaseBot;
    }

    @Override
    public String[] getNameHintBotList() {
        return nameHintBotList;
    }

    @Override
    public String getNameConvoBot() {
        return "";
    }
}
