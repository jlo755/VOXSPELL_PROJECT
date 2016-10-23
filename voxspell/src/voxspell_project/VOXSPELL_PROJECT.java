/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voxspell_project;

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
 *
 * @author jacky
 */
public class VOXSPELL_PROJECT extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		SettingsModel settings = new SettingsModel();
		stage.setResizable(false);
		stage.setTitle("Main Menu");
		MusicPlayer media = new MusicPlayer();
		media.getInstance().setFile(new FileHandler().getSetting("BGM:", ".settings.ini"));
		media.getInstance().execute();
		Parent root = FXMLLoader.load(getClass().getResource("../voxspell_main_menu/MainMenuFXML.fxml"));
		//root.getStylesheets().add(getClass().getResource("mainMenuStyle.css").toExternalForm());
		
		Scene scene = new Scene(root);
		//new SceneMediator().addScene("mainMenu",scene);

		//root = FXMLLoader.load(getClass().getResource("NewGameFXML.fxml"));
		//scene = new Scene(root);
		//new SceneMediator().addScene("newGame", scene);

		//new SceneMediator().setSceneStyle();

		//new SceneMediator().showScene(stage, "mainMenu");
		stage.setScene(scene);
		new SceneMediator().setSceneStyle(scene);


		stage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
