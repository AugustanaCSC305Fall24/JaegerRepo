package edu.augustana.RadioModel.Practice.BotCollections;

public class ConversationalBot extends Bot{
    private String botType = "convo";
    public ConversationalBot(int level, String idCode, double frequency) {
        super(level, idCode, frequency);
    }

    public String getBotType() {
        return botType;
    }
}
