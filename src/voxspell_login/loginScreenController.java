package voxspell_login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import voxspell_media_handler.MusicPlayer;
import voxspell_media_handler.SceneMediator;
import voxspell_project.FileHandler;
import voxspell_project.User;

/**
 * This class is the controller for the login screen GUI. It handles the login interaction with the user.
 * 
 * @author jacky
 *
 */

public class loginScreenController implements Initializable{
	
	@FXML Button login;
	@FXML Button cancel;
	@FXML TextField userField;
	@FXML TextField pwField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	public void loginAction(){
		/*
		 * This method checks that the user enters a correct password and username by checking
		 * it with the stored file. If they do not, it throws an error message relative
		 * to what the error is.
		 */
		String password = new FileHandler().getSetting(userField.getText(), ".users");
		try {
			if(!password.isEmpty() && password.equals(ToHash(pwField.getText()))){
				User.getInstance().setUser(".usr/"+userField.getText()+"/");
			    Stage stage = (Stage) login.getScene().getWindow();
			    // This will get the singleton music class, set the music file to be played, and execute
			    // that music.
			    MusicPlayer.getInstance().setFile(new FileHandler().getSetting("currentBGM:", User.getInstance().getUserSettings()));
				MusicPlayer.getInstance().execute();
				new SceneMediator().changeScene(stage, "/voxspell_main_menu/MainMenuFXML.fxml", "Main Menu");
			} else {
				// general purpose error that associates with wrong pw or username.
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error logging in!");	
				alert.setContentText("Incorrect password/username.");
				alert.show();
			}
		} catch (NullPointerException e){
			// Username does not exist or blank fields.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error logging in!");	
			alert.setContentText("Invalid login.");
			alert.show();
		}
	}
	
	private String ToHash(String text) {
		String hash = "";
		int count = 0;
		for(char e: text.toCharArray()){
			if(count%2 == 0){
				hash += e+"^";
			} else if(count%3 == 0){
				hash += e+"!";
			} else {
				hash += e+"&";
			}
			count++;
		}
		return hash;
	}

	@FXML
	public void cancelAction(){
		Stage stage = (Stage) login.getScene().getWindow();
	    Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/voxspell_login/startScreenFXML.fxml"));
	        stage.setTitle("Login Screen");
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
