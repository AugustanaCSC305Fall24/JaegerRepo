package edu.augustana.RadioModel.Practice.BotCollections;

import edu.augustana.RadioModel.Practice.SceneBuilderFactory.SceneType;

public class HintBot extends Bot{
    private String botType = "hint";
    public HintBot(int level, String idCode, double frequency) {
        super(level, idCode, frequency);
    }
    public String getBotType() {
        return botType;
    }
}
