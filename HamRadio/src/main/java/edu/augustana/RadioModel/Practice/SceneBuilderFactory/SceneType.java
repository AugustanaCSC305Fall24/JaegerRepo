package edu.augustana.RadioModel.Practice.SceneBuilderFactory;

public interface SceneType {
    int getNumBaseBot();
    int getNumHintBot();
    int getNumConversationalBot();
    String toString();
    String getNameBaseBot();
    String[] getNameHintBotList();
    String getNameConvoBot();
}
