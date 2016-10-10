package voxspell_project;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ShopController implements Initializable  {

	@FXML ScrollPane scroll;
	@FXML Button back;
	@FXML ImageView video1;
	@FXML ImageView video2;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollBarPolicy.NEVER);

	}

	@FXML
	public void video1Action(){
		new MediaPlayer(".media/big_buck_bunny_1_minute.avi",false).setupGUI();
	}
	
	@FXML
	public void video2Action(){
		new MediaPlayer(".media/SPOOKY.avi",true).setupGUI();
	}
	
	@FXML
	public void backAction(){
		//new SceneMediator().showScene((Stage)back.getScene().getWindow(), "mainMenu");
		Stage stage = (Stage) back.getScene().getWindow();
		new SceneMediator().changeScene(stage, "MainMenuFXML.fxml", "Main Menu");
	}

}
