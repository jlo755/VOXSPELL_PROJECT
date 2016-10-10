/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voxspell_project;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author jacky
 */
public class MainMenuController implements Initializable {
    
    @FXML private Label label;
    @FXML private ImageView newGame;
    @FXML private ImageView visitShop;
    @FXML private ImageView exitGame;
    @FXML private Button settings;
    @FXML private ImageView imageView;
    @FXML private Button stats;
    
    @FXML
    private void newGameAction(){
	    Stage stage = (Stage) newGame.getScene().getWindow();
        //new SceneMediator().showScene((Stage)newGame.getScene().getWindow(), "newGame");
	    new SceneMediator().changeScene(stage, "NewGameFXML.fxml", "New Game");
    }
    
    @FXML
    private void exitGameAction(){
       Stage stage = (Stage)newGame.getScene().getWindow();
       stage.close();
    }
    
    @FXML
    private void usersAction(){
    	
    }
    
    @FXML
    private void visitShopAction(){
        Stage stage = (Stage)visitShop.getScene().getWindow();
    	new SceneMediator().changeScene(stage, "ShopFXML.fxml", "Shop");
    }
    
    @FXML
    private void statsAction(ActionEvent event){
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("StatsScreen.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Statistics");
            stage.setScene(new Scene(root));
            stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    private void settingsAction(ActionEvent event){
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root));
            stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
}
