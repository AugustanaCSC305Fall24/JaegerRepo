package edu.augustana.RadioModel.Practice.TaskCollection;

import edu.augustana.RadioModel.Practice.BotCollections.Bot;

public interface TaskForPractice {
    String getDescription();
    Bot getSender();
    @Override
    String toString();
}
