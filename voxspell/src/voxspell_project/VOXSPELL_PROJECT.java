/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voxspell_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 *
 * @author jacky
 */
public class VOXSPELL_PROJECT extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
    	SettingsModel hello = new SettingsModel();
        /*String tim = "file:///home/jacky/NetBeansProjects/VOXSPELL_PROJECT/src/voxspell_project/Ambler.mp3";
        Media hit = new Media(tim);
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();*/
    	stage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuFXML.fxml"));
        //root.getStylesheets().add(getClass().getResource("mainMenuStyle.css").toExternalForm());
        
        Scene scene = new Scene(root);
        //new SceneMediator().addScene("mainMenu",scene);
        
        //root = FXMLLoader.load(getClass().getResource("NewGameFXML.fxml"));
        //scene = new Scene(root);
        //new SceneMediator().addScene("newGame", scene);
        
        //new SceneMediator().setSceneStyle();
        
        //new SceneMediator().showScene(stage, "mainMenu");
        stage.setScene(scene);
        new SceneMediator().setSceneStyle(scene);
        
        
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
