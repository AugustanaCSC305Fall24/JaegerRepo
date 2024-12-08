package edu.augustana.RadioModel.Practice.SceneBuilderFactory;

import edu.augustana.RadioModel.User;

public class HamRadioSceneTypeFactory implements SceneBuilderFactory{
    private String sceneType;

    public HamRadioSceneTypeFactory(String sceneType){
        this.sceneType = sceneType;
    }

    @Override
    public SceneType build() {
        if(sceneType.equalsIgnoreCase("open")){
            return buildSceneAsOpenType();
        } else if (sceneType.equalsIgnoreCase("detective")){
            return buildSceneAsDetectiveStyle();
        } else if(sceneType.equalsIgnoreCase("forrest")){
            return buildSceneAsForrestStyle();
        } else {
            return buildSceneAsBuildStyle();
        }
    }

    private UserBuildSceneType buildSceneAsBuildStyle() {
        return new UserBuildSceneType();
    }

    private DetectiveSceneType buildSceneAsDetectiveStyle() {
        return new DetectiveSceneType();

    }

    private ForrestSceneType buildSceneAsForrestStyle() {
        return new ForrestSceneType();

    }

    private UserOpenSceneType buildSceneAsOpenType() {
        return new UserOpenSceneType();
    }

    public String toString(){
        return sceneType;
    }
}
