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
import javafx.stage.Stage;

/**
 *
 * @author jacky
 */
public class VOXSPELL_PROJECT extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        new SceneMediator().addScene("mainMenu",scene);
        
        root = FXMLLoader.load(getClass().getResource("NewGameFXML.fxml"));
        scene = new Scene(root);
        new SceneMediator().addScene("newGame", scene);
        
        new SceneMediator().showScene(stage, "mainMenu");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
