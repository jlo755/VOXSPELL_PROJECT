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
import voxspell_utility.FileHandler;
import voxspell_utility.User;


/**
 * This class is a scene mediator, and is notified when the scene must change. It will change
 * the scene associated with the stage (top-container) and appropriately set the title and
 * apply the style sheet to the new scene.
 *
 * @author jacky
 */
public class SceneMediator{;
    String _theme = new FileHandler().getSetting("Theme:", User.getInstance().getUserSettings());
    
    public void setSceneStyle(Scene scene){
    	/*
    	 * This method sets the CSS associated with the scene.
    	 */
    	if(_theme.equals("Pirate")){
    		// credit to http://stackoverflow.com/questions/17769388/javafx-change-css-at-runtime
    		// for the following code on how to change CSS sheet dynamically
    		scene.getStylesheets().add(getClass().getResource("/styling_sheet/PirateTheme.css").toExternalForm());
    	} else if (_theme.equals("Forest")){
    		scene.getStylesheets().add(getClass().getResource("/styling_sheet/MagicalForestTheme.css").toExternalForm());
    	}
    }
    
    public void changeScene(Stage stage, String fxml, String titleOfScene){
    	/*
    	 * This method sets the scene to the stage by loading the appropriate fxml file. It also
    	 * sets the title of this stage to match the new FXML file.
    	 */
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
