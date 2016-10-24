package voxspell_settings;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import voxspell_media_handler.MusicPlayer;
import voxspell_utility.FileHandler;
import voxspell_utility.User;

/**
 * This class is the controller for the settings GUI. Specifically
 * it handles the generation and editting of the user's settings,
 * and provides the available options to the user.
 * 
 * @author jacky
 *
 */

public class SettingsController implements Initializable {

	SettingsModel _model;
	@FXML ComboBox<String> spellingLevelCombo;
	@FXML ComboBox<String> voicesCombo;
	@FXML Button confirm;
	@FXML Button cancel;
	@FXML Button uploadFile;
	@FXML TextField fileField;
	@FXML ComboBox<String> bgmCombo;
	@FXML Slider volume;

	@FXML
	public void confirmAction(ActionEvent event){
		/*
		 * Specifically, this method is to correctly generate the new settings, and to 
		 * create associated files for these new settings. It will also change the BGM.
		 */
		FileHandler fileHandle = new FileHandler();
		generateSettings();
		MusicPlayer musicPlayer = new MusicPlayer().getInstance();
		musicPlayer.setVolume((double) volume.getValue()/1000);
		musicPlayer.getInstance().setFile(new FileHandler().getSetting("currentBGM:", User.getInstance().getUserSettings()));
		musicPlayer.getInstance().execute(); // this plays the new bgm that has been set by the user
		String fileName = User.getInstance().getUser()+"/.stats/"+fileField.getText().split("/")
				[fileField.getText().split("/").length-1];
		// the following code creates the necessary stats file for the program to record statistics - only
		// called if the file does not exist.
		if(!fileHandle.fileExists(fileName)){
			fileHandle.makeFile(fileName);
			for(String e: spellingLevelCombo.getItems()){
				fileHandle.writeToFile(fileName, e.replaceAll("\\s+", "")+"Success: 0");
				fileHandle.writeToFile(fileName, e.replaceAll("\\s+", "")+"Failure: 0");
			}
		}
		Stage stage = (Stage) confirm.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void cancelAction(ActionEvent event){
		Stage stage = (Stage) confirm.getScene().getWindow();
		stage.close();
	}

	private void generateSettings(){
		/*
		 * This method will rewrite the settings in the user file. to what is selected in the
		 * settings combo boxes.
		 */
		FileHandler fileHandle = new FileHandler();
		String fileName = User.getInstance().getUserSettings();
		fileHandle.removingWord(fileName, "Level: "+fileHandle.getSetting("Level:", fileName));
		fileHandle.writeToFile(fileName, "Level: "+spellingLevelCombo.getValue());
		fileHandle.removingWord(fileName, "Voice: "+fileHandle.getSetting("Voice:", fileName));
		fileHandle.writeToFile(fileName, "Voice: "+voicesCombo.getValue());
		fileHandle.removingWord(fileName, "File: "+fileHandle.getSetting("File:", fileName));
		fileHandle.writeToFile(fileName, "File: "+fileField.getText());
		fileHandle.removingWord(fileName, "currentBGM: "+fileHandle.getSetting("currentBGM:", fileName));
		fileHandle.writeToFile(fileName, "currentBGM: .resources/BGM/MainMenu_BGM/"+bgmCombo.getValue());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*
		 * This method is called on initialisation of the FXML file and generates the required model to associate with this controller.
		 * It also generates the combo boxes required.
		 */
		_model = new SettingsModel();
		spellingLevelCombo.getItems().addAll(_model.getLevels());
		voicesCombo.getItems().addAll(_model.getVoices());
		bgmCombo.getItems().addAll(_model.getBGM());
		setComboBoxes();

	}


	public void setComboBoxes(){
		/*
		 * Sets the combobox to the user's current states.
		 */
		FileHandler fileHandler = new FileHandler();
		voicesCombo.setValue(fileHandler.getSetting("Voice:", User.getInstance().getUserSettings()));
		spellingLevelCombo.setValue(fileHandler.getSetting("Level:", User.getInstance().getUserSettings()));
		fileField.setText(fileHandler.getSetting("File:", User.getInstance().getUserSettings()));
		bgmCombo.setValue(fileHandler.getSetting("currentBGM:", User.getInstance().getUserSettings()).split(".resources/BGM/MainMenu_BGM/")[1]);
		volume.setValue(new MusicPlayer().getInstance().getVolume()*1000);
	}

	public void uploadFileAction(){
		/*
		 * This method handles the uploading of files. It will throw an error
		 * if the file does not follow the % format, or does not end in .txt.
		 */
		// credit to http://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
		// for the file choosing code
		FileHandler fileHandle = new FileHandler();
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog((Stage)uploadFile.getScene().getWindow());
		if(file.getAbsolutePath().contains(".txt")){ // checks if the file contaisn the .txt extension
			fileHandle.setSpellingList(file.getAbsolutePath());
			_model.setLevels(fileHandle.generateLevels());
			if(!_model.getLevels().isEmpty()){ // checks if the file contains the appropriate format
				fileField.setText((file.getAbsolutePath()));
				spellingLevelCombo.getItems().clear(); // clearing the level combo box to accomodate the new levels
				spellingLevelCombo.getItems().addAll(_model.getLevels());
				spellingLevelCombo.setValue(_model.getLevels().get(0));
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Invalid file!");	
				alert.setContentText("Uploaded file does not follow the format!");
				alert.show();
			}

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Invalid file!");	
			alert.setContentText("Uploaded file must be a text file.");
			alert.show();
		}

	}

}
