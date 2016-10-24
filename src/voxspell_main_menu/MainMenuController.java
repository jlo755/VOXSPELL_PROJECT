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
import voxspell_media_handler.MusicPlayer;
import voxspell_media_handler.SceneMediator;
import voxspell_utility.MediaListener;
import voxspell_utility.User;

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
    @FXML private ImageView galleryGame;
    @FXML private Button settings;
    @FXML private ImageView imageView;
    @FXML private Button stats;
    @FXML private Button logout;
    
    @FXML
    private void newGameAction(){ 
    	// this action event switches the current screen to the new game scene when
    	// the new game button is clicked
	    Stage stage = (Stage) newGame.getScene().getWindow();
	    new SceneMediator().changeScene(stage, "/voxspell_spelling/NewGameFXML.fxml", "New Game");
	    new MediaListener().fireGameEvent();
    }
    
    @FXML
    private void logoutAction(){
    	// this action event will log the user out, and switch the scene to the start screen scene
    	// when the logout button is clicked
		Stage stage = (Stage) logout.getScene().getWindow();
	    Parent root;
		try {
			MusicPlayer.getInstance().stop();
			root = FXMLLoader.load(getClass().getResource("/voxspell_login/startScreenFXML.fxml"));
	        stage.setTitle("Login Screen");
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        scene.getStylesheets().add(getClass().getResource("/styling_sheet/StartMenu.css").toExternalForm());
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    private void helpAction(){
    	// this action event will open up a new screen, displaying the help and information screen when
    	// the help/information icon is clicked
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/voxspell_help/helpFXML.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Information");
            stage.setScene(new Scene(root));
            stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    private void galleryGameAction(){
    	// this action event will switch the scene with the gallery screen when
    	// the gallery button is clicked
        Stage stage = (Stage)galleryGame.getScene().getWindow();
    	new SceneMediator().changeScene(stage, "/voxspell_gallery/galleryFXML.fxml", "Gallery");
    }
    
    @FXML
    private void visitShopAction(){
    	// this action event will switch the current scene with the shop screen when
    	// the visit shop button is clicked
        Stage stage = (Stage)visitShop.getScene().getWindow();
    	new SceneMediator().changeScene(stage, "/voxspell_shop/ShopFXML.fxml", "Shop");
    }
    
    @FXML
    private void statsAction(ActionEvent event){
    	// this action event will open a new window showing the statistics of the user when
    	// the stats button is clicked
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
    	// this action event will open a new window showing the settings screen when the 
    	// settings button is clicked
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
        label.setText(User.getInstance().getUser().substring(5,User.getInstance().getUser().length()-1));
    } 
    
}
