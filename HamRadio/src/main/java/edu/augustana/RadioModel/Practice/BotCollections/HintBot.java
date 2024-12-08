package edu.augustana.RadioModel.Practice.BotCollections;

public class HintBot extends Bot{
    private String botType = "hint";
    public HintBot(int level, String idCode, double frequency) {
        super(level, idCode, frequency);
    }

    public String getBotType() {
        return botType;
    }
}
