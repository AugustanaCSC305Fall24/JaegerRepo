package edu.augustana.RadioModel.Practice.BotCollections;

import edu.augustana.AI.GeminiAPITest;
import edu.augustana.Application.UI.App;
import edu.augustana.RadioModel.Practice.ChatMessage;
import edu.augustana.RadioModel.Practice.PracticeScenario;
import javafx.scene.paint.Color;
import swiss.ameri.gemini.api.*;
import swiss.ameri.gemini.gson.GsonJsonParser;
import swiss.ameri.gemini.spi.JsonParser;

public class GeminiBirdBot extends Bot {

    private String systemPromptText;
    private JsonParser parser;
    GenAi genAi;


    public GeminiBirdBot(String name, Color textColor, PracticeScenario room, String systemPromptText) {
        super(0, name, Bot.getRandomFreq());
        this.systemPromptText = systemPromptText;
        this.parser = new GsonJsonParser();
        this.genAi = new GenAi(GeminiAPITest.getGeminiApiKey(), parser);
    }
    public String toString() {
        return getIDCode() + " [Birdwatcher]";
    }

    private PracticeScenario getRoom(){
        return App.getCurrentPracticeScenerio();
    }
    public void requestMessage() {
        StringBuilder transcript = new StringBuilder();
        for (ChatMessage message : App.getCurrentPracticeScenerio().getChatLogMessageList()) {
            transcript.append(message.getSender()).append(": ").append(message.getText()).append("\n");
        }
        String fullPrompt = systemPromptText + "\n" +
                "Your name is: " + getIDCode() + "\n" +
                "Read the following past history/transcript, and reply by stating exactly what you would say next in this conversation.  (If the transcript is mostly empty, then just make a new comment about a funny bird.) \n" +
                transcript.toString();

        var model = createBotModel(fullPrompt);

        genAi.generateContent(model)
                .thenAccept(gcr -> {
                    String geminiResponse = gcr.text();
                    System.out.println("Debug: GeminiBirdBot received response: " + geminiResponse);
                    getRoom().addChatMessage(new ChatMessage(geminiResponse, getIDCode(), Color.RED, false));

                });
        // Note: don't include the .get() because we DON'T want to wait/block until the task completes,
        // or else it will make the UI hang / less responsive
        //.get(20, TimeUnit.SECONDS);



    }

    private GenerativeModel createBotModel(String fullPrompt) {
        return GenerativeModel.builder()
                .modelName(ModelVariant.GEMINI_1_5_FLASH)
                .addContent(Content.textContent(
                        Content.Role.USER,
                        fullPrompt
                ))
                .addSafetySetting(SafetySetting.of(
                        SafetySetting.HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT,
                        SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH
                ))
                .generationConfig(new GenerationConfig(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                ))
                .build();
    }

}
