package edu.augustana.RadioModel;

public class CWMessage {
    public String text;
    public String user;
    public double frequency;
    public CWMessage(String text, String user, double frequency) {
        this.text = text;
        this.user = user;
        this.frequency = frequency;
    }
    public String getText() {
        return text;
    }
    public String getUser() { return user; }
    public double getFrequency() { return frequency; }
}