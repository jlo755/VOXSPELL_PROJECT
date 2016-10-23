package voxspell_gallery;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import voxspell_media_handler.MediaPlayer;
import voxspell_media_handler.MusicPlayer;
import voxspell_media_handler.SceneMediator;
import voxspell_project.FileHandler;
import voxspell_project.User;

/**
 * This class represents the controller for the Gallery GUI. Its purpose is to enable and disable
 * buttons according to the user's state - and also apply the current id to them to appropriately
 * the correct view.
 * 
 * @author jacky
 *
 */


public class galleryController implements Initializable{

	@FXML ImageView video1;
	@FXML ImageView video2;
	@FXML ImageView pirateTheme;
	@FXML ImageView preview;
	@FXML ImageView forestTheme;
	@FXML ScrollPane scroll;
	@FXML Button setMedia;
	@FXML Button back;
	@FXML TextArea text;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*
		 * This method is called on setup of the FXML loader, it will evaluate the available
		 * galley options to the user.
		 */
		scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollBarPolicy.NEVER);
		evaluateAvailableVideos();
		evaluateAvailableThemes();
		setMedia.setVisible(false);
		text.setDisable(true);
		text.setOpacity(100);
	}

	private void evaluateAvailableVideos(){
		/*
		 * This method evaluates the available videos that have been purchased by the user. It then
		 * allocates them with the appropriate image and action so that they are 'enabled' for the user
		 * to click - which will display a video.
		 */
		ArrayList<String> videosAvailable = new FileHandler().getUserItems("Videos:", User.getInstance().getUserSettings());
		for(String videos: videosAvailable)
			switch (videos) {
			case "video1":  video1.setId("video");
			video1.setOnMouseClicked((event5) ->{
				preview.setId("galleryPreviewBuck"); // sets the preview image to a .gif image
				setMedia.setVisible(true);
				setMedia.setText("Play Video");
				setMedia.setOnAction((event7)->{
					new MediaPlayer(".media/big_buck_bunny_1_minute.avi",false).setupGUI();
					MusicPlayer.getInstance().stop(); // stops the music so it will not play over the video
				});
			});
			break;
			case "video2":  video2.setId("video");
			video2.setOnMouseClicked((event6) ->{
				preview.setId("galleryPreviewSpooky");
				setMedia.setVisible(true);
				setMedia.setText("Play Video");
				setMedia.setOnAction((event8)->{
					new MediaPlayer(".media/SPOOKY.avi",true).setupGUI();
					MusicPlayer.getInstance().stop();
				});
			});
			break;
			}
	}

	private void evaluateAvailableThemes(){
		/*
		 * This method evaluates the available themes that have been purchased by the user. It then
		 * allocates them with the appropriate image and action so that they are 'enabled' for the user
		 * to click - which will set the theme.
		 */
		ArrayList<String> themesAvailable = new FileHandler().getUserItems("Themes:", User.getInstance().getUserSettings());
		for(String themes: themesAvailable)
			switch (themes) {
			case "Pirate":  pirateTheme.setId("theme");
			pirateTheme.setOnMouseClicked((event) ->{
				preview.setId("galleryPreviewPirate"); // setting the id to a new css selector to change image
				setMedia.setVisible(true);
				setMedia.setText("Set Theme");
				setMedia.setOnAction((event3)->{ // anon inner clas to register a new action associated with the preview button
					new FileHandler().removingWord(User.getInstance().getUserSettings(), "Theme: Forest");
					new FileHandler().writeToFile(User.getInstance().getUserSettings(), "Theme: Pirate");
			        Stage stage = (Stage)setMedia.getScene().getWindow();
			        // reloading the screen and changing the theme
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
