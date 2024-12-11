package edu.augustana.RadioModel.Practice.TaskCollection;

import edu.augustana.RadioModel.Practice.BotCollections.Bot;
import edu.augustana.RadioModel.Practice.ChatMessage;
import edu.augustana.RadioModel.Practice.SceneBuilderFactory.ForrestSceneType;
import edu.augustana.RadioModel.Practice.SceneBuilderFactory.SceneType;
import edu.augustana.RadioModel.Practice.TaskCollection.DetectiveScriptedGame.DetectiveTask;
import edu.augustana.RadioModel.Practice.TaskCollection.DetectiveScriptedGame.HintDetectiveTask;
import edu.augustana.RadioModel.Practice.TaskCollection.ForrestScriptedGame.HintPoliceTask;
import edu.augustana.RadioModel.Practice.TaskCollection.ForrestScriptedGame.PoliceTask;

public class TaskFactory {
   private SceneType sceneType;

   public TaskFactory(SceneType sceneType){
       this.sceneType = sceneType;
   }

   public TaskForPractice buildTask(Bot bot){
       if (sceneType.toString().equalsIgnoreCase("DetectiveSceneType")){
           if(bot.getBotType().equalsIgnoreCase("base")){
               return new DetectiveTask(bot);
           } else {
               return new HintDetectiveTask(bot);
           }
       } else if (sceneType.toString().equalsIgnoreCase("ForrestSceneType")){
           if(bot.getBotType().equalsIgnoreCase("base")){
               return new PoliceTask(bot);
           } else {
               return new HintPoliceTask(bot);
           }
       } else {
           return null; //For extending the app later!!!
       }
   }

   public TaskForPractice buildTaskForConvo(Bot bot, ChatMessage message){
       return new ConvoTask(bot, message);
   }


}
