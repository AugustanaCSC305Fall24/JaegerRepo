package edu.augustana.RadioModel.Practice;

import javafx.scene.paint.Color;

public class ChatMessage {
    private String text;
    private String sender;
    private Color colorCode;
    private boolean isBold;

    public ChatMessage(String text, String sender, Color color, boolean isBold) {
        this.text = text;
        this.sender = sender;
        this.colorCode = color;
        this.isBold = isBold;
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }

    public Color getColorCode(){return colorCode;}

    @Override
    public String toString() {
        return "ChatMessage{" +
                "text='" + text + '\'' +
                ", sender='" + sender + '\'' +
                ", " +
                '}';
    }

}
