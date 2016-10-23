package voxspell_gallery;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import voxspell_media_handler.SceneMediator;
import voxspell_project.FileHandler;
import voxspell_project.User;

public class galleryController implements Initializable{

	@FXML ImageView video1;
	@FXML ImageView video2;
	@FXML ImageView pirateTheme;
	@FXML ImageView preview;
	@FXML ImageView forestTheme;
	@FXML ScrollPane scroll;
	@FXML Button setMedia;
	@FXML Button back;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollBarPolicy.NEVER);
		evaluateAvailableVideos();
		evaluateAvailableThemes();
		setMedia.setVisible(false);
	}

	private void evaluateAvailableVideos(){
		ArrayList<String> videosAvailable = new FileHandler().getUserItems("Videos:", User.getInstance().getUserSettings());
		for(String videos: videosAvailable)
			switch (videos) {
			case "video1":  video1.setId("video");
			break;
			case "video2":  video2.setId("video");
			break;
			}
	}

	private void evaluateAvailableThemes(){
		ArrayList<String> themesAvailable = new FileHandler().getUserItems("Themes:", User.getInstance().getUserSettings());
		for(String themes: themesAvailable)
			switch (themes) {
			case "Pirate":  pirateTheme.setId("theme");
			pirateTheme.setOnMouseClicked((event) ->{
				preview.setId("galleryPreviewPirate");
				setMedia.setVisible(true);
				setMedia.setText("Set Theme");
				setMedia.setOnAction((event3)->{
					new FileHandler().removingWord(User.getInstance().getUserSettings(), "Theme: Forest");
					new FileHandler().writeToFile(User.getInstance().getUserSettings(), "Theme: Pirate");
			        Stage stage = (Stage)setMedia.getScene().getWindow();
			    	new SceneMediator().changeScene(stage, "/voxspell_gallery/galleryFXML.fxml", "Gallery");
				});
			});
			break;
			case "Forest":  forestTheme.setId("theme");
			forestTheme.setOnMouseClicked((event2) ->{
				preview.setId("galleryPreviewForest");
				setMedia.setText("Set Theme");
				setMedia.setVisible(true);
				setMedia.setOnAction((event4)->{
					new FileHandler().removingWord(User.getInstance().getUserSettings(), "Theme: Pirate");
					new FileHandler().writeToFile(User.getInstance().getUserSettings(), "Theme: Forest");
			        Stage stage = (Stage)setMedia.getScene().getWindow();
			    	new SceneMediator().changeScene(stage, "/voxspell_gallery/galleryFXML.fxml", "Gallery");
				});
			});
			break;
			}
	}
	
	@FXML
	public void backAction(){
		Stage stage = (Stage) back.getScene().getWindow();
		new SceneMediator().changeScene(stage, "/voxspell_main_menu/MainMenuFXML.fxml", "Main Menu");
	}
}
