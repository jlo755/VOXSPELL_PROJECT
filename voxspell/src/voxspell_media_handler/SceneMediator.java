/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voxspell_media_handler;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import voxspell_project.FileHandler;


/**
 *
 * @author jacky
 */
public class SceneMediator{;
    String _theme = new FileHandler().getSetting("Theme:", ".settings.ini");
    
    
    /*public void addScene(String nameOfScene, Scene scene){
        _scene.put(nameOfScene, scene);
    }
    
    public void showScene(Stage stage, String sceneToSet){
        stage.setScene(_scene.get(sceneToSet));
    }*/
    
    public void setSceneStyle(Scene scene){
    	/*for(Scene scene:_scene.values()){
            scene.getStylesheets().add(getClass().getResource(_theme).toExternalForm());
    	}*/
    	if(_theme.equals("Pirate")){
    		scene.getStylesheets().add(getClass().getResource("../styling_sheet/PirateTheme.css").toExternalForm());
    	} else if (_theme.equals("Forest")){
    		scene.getStylesheets().add(getClass().getResource("../styling_sheet/MagicalForestTheme.css").toExternalForm());
    	}
    }
    
    public void changeScene(Stage stage, String fxml, String titleOfScene){
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource(fxml));
	        stage.setTitle(titleOfScene);
	        Scene scene = new Scene(root);
	        setSceneStyle(scene);
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
    
}
