package edu.augustana.RadioModel.Practice.SceneBuilderFactory;

import edu.augustana.RadioModel.Practice.BotCollections.Bot;
import edu.augustana.RadioModel.Practice.PracticeScenario;
import edu.augustana.RadioModel.Practice.TaskCollection.DetectiveScriptedGame.HintDetectiveTask;
import edu.augustana.RadioModel.Practice.TaskCollection.ForrestScriptedGame.HintPoliceTask;
import edu.augustana.RadioModel.Practice.TaskCollection.HintTask;
import edu.augustana.RadioModel.Practice.UserPreferences;

import java.util.Arrays;
import java.util.List;

public class HintAssigner {
    SceneType sceneType;
    String sceneTypeString;

    public HintAssigner(UserPreferences preferences){
        sceneType = preferences.getSceneType();
        sceneTypeString = sceneType.toString();
    }

    public void assignTask(HintTask task, int index){
        List<String> descriptionList;
        if(sceneTypeString.equalsIgnoreCase("DetectiveSceneType")){
            descriptionList = Arrays.asList(HintDetectiveTask.getHintList());
            task = (HintDetectiveTask) task;
            task.setHintDescription(descriptionList.get(index));
        } else if(sceneTypeString.equalsIgnoreCase("ForrestSceneType")){
            descriptionList = Arrays.asList(HintPoliceTask.getHintList());
            task = (HintPoliceTask) task;
            task.setHintDescription(descriptionList.get(index));
        } else {
            System.out.println("There is no hint....");
        }
    }
}
