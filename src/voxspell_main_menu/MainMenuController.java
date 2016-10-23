/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voxspell_main_menu;

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
import voxspell_media_handler.SceneMediator;
import voxspell_project.MediaListener;

/**
 * This class is the controller for the main menu GUI. It handles the operations opening the windows 
 * for new game, gallery, visiting the shop, statistics, and settings, and properly 
 * allocates the correct BGM.
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
	    new SceneMediator().changeScene(stage, "/voxspell_spelling/NewGameFXML.fxml", "New Game");
	    new MediaListener().fireGameEvent();
    }
    
    @FXML
    private void exitGameAction(){
        Stage stage = (Stage)visitShop.getScene().getWindow();
    	new SceneMediator().changeScene(stage, "/voxspell_gallery/galleryFXML.fxml", "Gallery");
    }
    
    @FXML
    private void usersAction(){
    	
    }
    
    @FXML
    private void visitShopAction(){
        Stage stage = (Stage)visitShop.getScene().getWindow();
    	new SceneMediator().changeScene(stage, "/voxspell_shop/ShopFXML.fxml", "Shop");
    }
    
    @FXML
    private void statsAction(ActionEvent event){
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/voxspell_stats/StatsScreen.fxml"));
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
			root = FXMLLoader.load(getClass().getResource("/voxspell_settings/Settings.fxml"));
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
