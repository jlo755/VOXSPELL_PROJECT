/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voxspell_utility;

import java.io.File;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import voxspell_media_handler.MusicPlayer;
import voxspell_media_handler.SceneMediator;
import voxspell_settings.SettingsModel;

/**
 * The main class which initialises the application thread for JAVAFX components to run from.
 *
 * @author jacky
 */
public class VOXSPELL_PROJECT extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		new FileHandler().makeFile(".users");
		stage.setResizable(false);
		stage.setTitle("Login Screen");
		MusicPlayer media = new MusicPlayer();
		Parent root = FXMLLoader.load(getClass().getResource("/voxspell_login/startScreenFXML.fxml"));		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/styling_sheet/StartMenu.css").toExternalForm());
		stage.setScene(scene);


		stage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
