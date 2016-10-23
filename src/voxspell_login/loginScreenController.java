package voxspell_login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import voxspell_media_handler.SceneMediator;
import voxspell_project.FileHandler;
import voxspell_project.User;

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
		String password = new FileHandler().getSetting(userField.getText(), ".users");
		if(!password.isEmpty() && password.equals(ToHash(pwField.getText()))){
			User.getInstance().setUser(".usr/"+userField.getText()+"/");
		    Stage stage = (Stage) login.getScene().getWindow();
			new SceneMediator().changeScene(stage, "/voxspell_main_menu/MainMenuFXML.fxml", "Main Menu");
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
