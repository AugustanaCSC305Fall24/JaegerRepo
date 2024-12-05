package edu.augustana.RadioModel;

public class ChatMessage {
    private String text;
    private User user;
    public ChatMessage(String text, User user) {
        this.text = text;
        this.user = user;
    }
    public String getText() {
        return text;
    }
}