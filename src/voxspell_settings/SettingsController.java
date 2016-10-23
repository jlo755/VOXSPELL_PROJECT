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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import voxspell_media_handler.MusicPlayer;
import voxspell_project.FileHandler;
import voxspell_project.User;

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
		FileHandler fileHandle = new FileHandler();
		generateSettings();
		new MusicPlayer().getInstance().setVolume((float) volume.getValue());
		new MusicPlayer().getInstance().setFile(new FileHandler().getSetting("BGM:", ".settings.ini"));
		new MusicPlayer().getInstance().execute();
		String fileName = User.getInstance().getUser()+"/.stats/"+fileField.getText().split("/")
				[fileField.getText().split("/").length-1];
		fileHandle.makeFile(fileName);
		for(String e: spellingLevelCombo.getItems()){
			fileHandle.writeToFile(fileName, e.replaceAll("\\s+", "")+"Success: 0");
			fileHandle.writeToFile(fileName, e.replaceAll("\\s+", "")+"Failure: 0");
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
		FileHandler fileHandle = new FileHandler();
		fileHandle.clearFile(".settings.ini");
		fileHandle.writeToFile(".settings.ini", "Level: "+spellingLevelCombo.getValue());
		fileHandle.writeToFile(".settings.ini", "Voice: "+voicesCombo.getValue());
	    fileHandle.writeToFile(".settings.ini", "File: "+fileField.getText());
		fileHandle.writeToFile(".settings.ini", "BGM: .resources/BGM/MainMenu_BGM/"+bgmCombo.getValue());
		fileHandle.writeToFile(".settings.ini", "GameBGM: .resources/BGM/Game_BGM/Call to Adventure.wav");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		_model = new SettingsModel();
		spellingLevelCombo.getItems().addAll(_model.getLevels());
		voicesCombo.getItems().addAll(_model.getVoices());
		bgmCombo.getItems().addAll(_model.getBGM());
		setComboBoxes();
		
	}
	
	
	public void setComboBoxes(){
		FileHandler fileHandler = new FileHandler();
			voicesCombo.setValue(fileHandler.getSetting("Voice:", ".settings.ini"));
			spellingLevelCombo.setValue(fileHandler.getSetting("Level:", ".settings.ini"));
			fileField.setText(fileHandler.getSetting("File:", ".settings.ini"));
			bgmCombo.setValue(fileHandler.getSetting("BGM:", ".settings.ini").split(".resources/BGM/MainMenu_BGM/")[1]);
			volume.setValue(new MusicPlayer().getInstance().getVolume()*1000);
	}
	
	public void uploadFileAction(){
		FileHandler fileHandle = new FileHandler();
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog((Stage)uploadFile.getScene().getWindow());
		fileField.setText((file.getAbsolutePath()));
		System.out.println(file.getAbsolutePath());
		fileHandle.setSpellingList(fileField.getText());
		_model.setLevels(fileHandle.generateLevels());
		spellingLevelCombo.getItems().clear();
		spellingLevelCombo.getItems().addAll(_model.getLevels());
		spellingLevelCombo.setValue(_model.getLevels().get(0));
	}

}
