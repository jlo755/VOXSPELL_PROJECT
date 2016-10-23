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
import javafx.stage.Stage;
import voxspell_media_handler.SceneMediator;

public class startScreenController implements Initializable {
	@FXML Button login;
	@FXML Button createUser;
	
	@FXML
	public void loginAction(){
	    Stage stage = (Stage) login.getScene().getWindow();
        //new SceneMediator().showScene((Stage)newGame.getScene().getWindow(), "newGame");
	    Parent root;
	    try {
			root = FXMLLoader.load(getClass().getResource("/voxspell_login/loginScreenFXML.fxml"));
	        stage.setTitle("Login");
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createAction(){
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/voxspell_login/createUserFXML.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Create New User");
            stage.setScene(new Scene(root));
            stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	
}
