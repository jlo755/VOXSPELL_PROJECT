/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voxspell_project;

import java.util.HashMap;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author jacky
 */
public class SceneMediator{
    static private Stage _stage;
    static private HashMap<String,Scene> _scene = new HashMap<String,Scene>();
    
    public void SceneMediator(Stage stage){
        _stage = stage;
    }
    
    public void addScene(String nameOfScene, Scene scene){
        _scene.put(nameOfScene, scene);
    }
    
    public void showScene(Stage stage, String sceneToSet){
        stage.setScene(_scene.get(sceneToSet));

    }
    
    public void changeScene(){
        
    }
    
}
