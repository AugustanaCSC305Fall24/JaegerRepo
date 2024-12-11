package edu.augustana.RadioModel.Practice.BotCollections;

import edu.augustana.AI.GeminiAPITest;
import edu.augustana.Application.UI.App;
import edu.augustana.Application.UIHelper.MorseCodeTranslator;
import edu.augustana.RadioModel.Practice.ChatMessage;
import edu.augustana.RadioModel.Practice.PracticeScenario;
import edu.augustana.RadioModel.Practice.TaskCollection.ConvoTask;
import edu.augustana.RadioModel.Practice.TaskCollection.TaskForPractice;
import javafx.scene.paint.Color;
import swiss.ameri.gemini.api.*;
import swiss.ameri.gemini.gson.GsonJsonParser;
import swiss.ameri.gemini.spi.JsonParser;

public class AIBot extends Bot {

    private String systemPromptText;
    private JsonParser parser;
    GenAi genAi;


    public AIBot(String name, Color textColor, PracticeScenario room, String systemPromptText) {
        super(0, name, Bot.getRandomFreq());
        this.systemPromptText = systemPromptText;
        this.parser = new GsonJsonParser();
        this.genAi = new GenAi(GeminiAPITest.getGeminiApiKey(), parser);
    }
    public String toString() {
        return getIDCode() + " [Your AI Agent]";
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
                "Read the following past history/transcript, and reply by stating exactly what you would say next in this conversation.  (If the transcript is mostly empty, then just make a new comment about a Elon Musk.) \n" +
                transcript.toString();

        var model = createBotModel(fullPrompt);

        genAi.generateContent(model)
                .thenAccept(gcr -> {
                    String geminiResponse = gcr.text();
                    System.out.println("Debug: AIBot received response: " + geminiResponse);
                    ChatMessage botTaskDescription = new ChatMessage(geminiResponse, this.getIDCode(), Color.PURPLE, true);
                    TaskForPractice task = new ConvoTask(this, botTaskDescription);
                    this.setTask(task);
                    getRoom().addChatMessage(new ChatMessage(geminiResponse, getIDCode(), Color.PURPLE, false));
                    getRoom().addChatMessage(new ChatMessage(MorseCodeTranslator.textToMorse(geminiResponse), getIDCode(), Color.PURPLE, false));

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
