package edu.augustana.RadioModel.Practice.SceneBuilderFactory;

public class DetectiveSceneType implements SceneType{
    private int numBaseBot = 1;
    private int numHintBot = 4;
    private int numConversationalBot = 0;
    String nameBaseBot = "Detective";
    String[] nameHintBotList = {"Hint1", "Hint2", "Hint3", "Hint4"};

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
        return "DetectiveSceneType";
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
