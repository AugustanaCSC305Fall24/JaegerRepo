package edu.augustana.RadioModel.Practice.BotCollections;

import edu.augustana.RadioModel.Practice.TaskCollection.TaskForPractice;

public class BaseBot extends Bot{
    private String botType = "base";
    private TaskForPractice task;
    private String answer;
    private String userAnswer;

    public BaseBot(int level, String idCode, double frequency) {
        super(level, idCode, frequency);
    }

    public void receiveAnswer(String userAnswer){
        this.userAnswer = userAnswer.strip();
    }

    public String getUserAnswer(){
        return userAnswer;
    }

    public boolean isAnswerCorrect(){
        return answer.equalsIgnoreCase(this.userAnswer);
    }

    public void setTask(TaskForPractice task){
        this.task = task;
    }

    public TaskForPractice getTask(){
        return this.task;
    }

    public String getBotType() {
        return botType;
    }
}
