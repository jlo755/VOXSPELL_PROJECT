package voxspell_shop;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import voxspell_dialog.CompleteLevelDialogController;
import voxspell_dialog.dialogController;
import voxspell_media_handler.MediaPlayer;
import voxspell_media_handler.SceneMediator;
import voxspell_utility.FileHandler;
import voxspell_utility.User;

/**
 * This class is the controller for the shop class. It handles the purchasing of themes, bgms, and
 * also videos for the user to play. It evaluates whether these purchases have been made previously, so
 * cannot be made again.
 * 
 * @author jacky
 *
 */

public class ShopController implements Initializable  {

	@FXML ScrollPane scroll;
	@FXML Button back;
	@FXML ImageView video1;
	@FXML ImageView video2;
	@FXML ImageView pirateTheme;
	@FXML ImageView bgm1;
	@FXML Label coinLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*
		 * This method is called upon the initialization of the fxml loader. It will
		 * evaluate all available items and set the corresponding image and action.
		 */
		scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollBarPolicy.NEVER);
		evaluateAvailableVideos();
		evaluateAvailableThemes();
		evaluateAvailableBGM();
		setCoinLabel("Coins: "+new FileHandler().getSetting("Coins:", User.getInstance().getUserSettings()));
	}

	private void evaluateAvailableVideos(){
		/*
		 * This method evaluates the available videos that have not been bought yet. If they have been bought,
		 * the purchasedButton id will be set to them, and the button will be disabled to prevent further purchases.
		 */
		ArrayList<String> videosAvailable = new FileHandler().getUserItems("Videos:", User.getInstance().getUserSettings());
		for(String videos: videosAvailable)
			switch (videos) {
			case "video1":  video1.setId("purchasedButton");
			video1.setDisable(true);
			break;
			case "video2":  video2.setId("purchasedButton");
			video2.setDisable(true);
			break;
			}
	}

	private void evaluateAvailableThemes(){
		/*
		 * This method evaluates the available themes that have not been bought yet. If they have been bought,
		 * the purchasedButton id will be set to them to apply the purchased image to the button, and the button will be 
		 * disabled to prevent further purchases.
		 */
		ArrayList<String> themesAvailable = new FileHandler().getUserItems("Themes:", User.getInstance().getUserSettings());
		for(String themes: themesAvailable)
			switch (themes) {
			case "Pirate":  pirateTheme.setId("purchasedButton");
			pirateTheme.setDisable(true);
			break;
			}
	}
	
	private void evaluateAvailableBGM(){
		/*
		 * This method evaluates the available BGM that have not been bought yet. If they have been bought,
		 * the purchasedButton id will be set to them to apply the purchased image to the button, and the button will be 
		 * disabled to prevent further purchases.
		 */
		ArrayList<String> bgmAvailable = new FileHandler().getUserItems("BGM:", User.getInstance().getUserSettings());
		for(String bgm: bgmAvailable)
			switch (bgm) {
			case "Happy_Alley.wav":  bgm1.setId("purchasedButton");
			bgm1.setDisable(true);
			break;
			}
	}

	@FXML
	public void buyVideoAction(MouseEvent e){
		/*
		 * This action event is called when the but video button is pressed. It will evaluate whether the
		 * user will have enough money to make the purchase, and prompt the user for confirmation.
		 */
		String video = "";
		if(e.getSource() == video1){
			video = "Big Bucks Bunny";
			confirmPurchase(video1, video);
		} else if(e.getSource() == video2){
			video ="Spooky Big Bucks Bunny";
			confirmPurchase(video2, video);		
		}
		
		
	}
	
	public void confirmPurchase(ImageView video1, String video){
		/*
		 * This method is to confirm a video purchase, and currently write the
		 * purchase to the user's file so it is available.
		 */
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Are you sure?");
		alert.setHeaderText("Video: "+video+"\nCost: 300 coins");	
		alert.setContentText("Are you sure you want to buy it?");
		Optional<ButtonType> result = alert.showAndWait();
		String name = "";
		if(video.equals("Big Bucks Bunny")){
			name = "video1";
		} else {
			name = "video2";
		}
		if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
			if(confirmPurchase(300)){
				new FileHandler().incrementValue(User.getInstance().getUserSettings(), "Coins:", -300);
				// removes 300 coins from the user's settings file
				new FileHandler().addPurchase(User.getInstance().getUserSettings(), name, "Videos:");
				// adds the purchase or video to the user's settings file
				video1.setId("purchasedButton"); // set the purchasedButton id to prevent further purchases
				video1.setDisable(true);
				// update the coin label after this purchase
				setCoinLabel("Coins: "+new FileHandler().getSetting("Coins:", User.getInstance().getUserSettings()));
			} else {
				Alert error = new Alert(AlertType.ERROR);
				error.setTitle("Error!");
				error.setHeaderText("Not enough coins!");	
				error.setContentText("Earn some coins by spelling!");
				error.show();
			}
		}
	}

	@FXML
	public void buyThemeAction(MouseEvent e){
		/*
		 * This action event is notified when the theme button has been pressed. It will evaluate
		 * whether the user has 500 coins to buy the theme, a confirmation will appear asking for
		 * the user to confirm his purchase.
		 */
		String theme = "";
		if(e.getSource() == pirateTheme){
			theme = "Pirate";
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Are you sure?");
		alert.setHeaderText("Theme: "+theme+"\nCost: 500 coins");	
		alert.setContentText("Are you sure you want to buy it?");

		Optional<ButtonType> result = alert.showAndWait();
		if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
			if(confirmPurchase(500)){
				new FileHandler().incrementValue(User.getInstance().getUserSettings(), "Coins:", -500);
				new FileHandler().addPurchase(User.getInstance().getUserSettings(), theme, "Themes:");
				pirateTheme.setId("purchasedButton");
				pirateTheme.setDisable(true);
				setCoinLabel("Coins: "+new FileHandler().getSetting("Coins:", User.getInstance().getUserSettings()));
			} else {
				Alert error = new Alert(AlertType.ERROR);
				error.setTitle("Error!");
				error.setHeaderText("Not enough coins!");	
				error.setContentText("Earn some coins by spelling!");
				error.show();
			}
		}
	}

	@FXML
	public void buyBGMAction(MouseEvent e){
		/*
		 * This action event is notified when the BGM button has been pressed. It will evaluate
		 * whether the user has 100 coins to buy the theme, a confirmation will appear asking for
		 * the user to confirm his purchase.
		 */
		String song = "";
		if(e.getSource() == bgm1){
			song = "Happy_Alley";
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Are you sure?");
		alert.setHeaderText("BGM: "+song+"\nCost: 100 coins");	
		alert.setContentText("Are you sure you want to buy it?");

		Optional<ButtonType> result = alert.showAndWait();
		if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
			if(confirmPurchase(100)){
				new FileHandler().incrementValue(User.getInstance().getUserSettings(), "Coins:", -100);
				new FileHandler().addPurchase(User.getInstance().getUserSettings(), song+".wav", "BGM:");
				bgm1.setId("purchasedButton");
				bgm1.setDisable(true);
				setCoinLabel("Coins: "+new FileHandler().getSetting("Coins:", User.getInstance().getUserSettings()));
			} else {
				Alert error = new Alert(AlertType.ERROR);
				error.setTitle("Error!");
				error.setHeaderText("Not enough coins!");	
				error.setContentText("Earn some coins by spelling!");
				error.show();
			}
		}
	}

	@FXML
	public void backAction(){
		Stage stage = (Stage) back.getScene().getWindow();
		new SceneMediator().changeScene(stage, "/voxspell_main_menu/MainMenuFXML.fxml", "Main Menu");
	}

	public boolean confirmPurchase(int cost){
		/*
		 * Confirms that the user has enough coins to make the purchase.
		 */
		if(cost<=Integer.parseInt(new FileHandler().getSetting("Coins:", User.getInstance().getUserSettings()))){
			return true;
		} else {
			return false;
		}
	}
	
	public void setCoinLabel(String text){
		coinLabel.setText(text);
	}
	
}
