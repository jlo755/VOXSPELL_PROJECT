package voxspell_project;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SettingsController implements Initializable {
	
	SettingsModel _model;
	@FXML ComboBox<String> spellingLevelCombo;
	@FXML ComboBox<String> voicesCombo;
	@FXML Button confirm;
	@FXML Button cancel;
	@FXML Button uploadFile;
	@FXML TextField fileField;
	@FXML ComboBox<String> themeCombo;

	@FXML
	public void confirmAction(ActionEvent event){
		FileHandler fileHandle = new FileHandler();
		fileHandle.clearFile(".settings.ini");
		fileHandle.writeToFile(".settings.ini", "Level: "+spellingLevelCombo.getValue());
		fileHandle.writeToFile(".settings.ini", "Voice: "+voicesCombo.getValue());
	    fileHandle.writeToFile(".settings.ini", "File: "+fileField.getText());
		fileHandle.writeToFile(".settings.ini", "Theme: "+themeCombo.getValue());
		Stage stage = (Stage) confirm.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	public void cancelAction(ActionEvent event){
	    Stage stage = (Stage) confirm.getScene().getWindow();
	    stage.close();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		_model = new SettingsModel();
		spellingLevelCombo.getItems().addAll(_model.getLevels());
		voicesCombo.getItems().addAll(_model.getVoices());
		themeCombo.getItems().addAll(_model.getThemes());
		setComboBoxes();
		
	}
	
	public void setComboBoxes(){
		FileHandler fileHandler = new FileHandler();
			voicesCombo.setValue(fileHandler.getSetting("Voice:"));
			spellingLevelCombo.setValue(fileHandler.getSetting("Level:"));
			themeCombo.setValue(fileHandler.getSetting("Theme:"));
			fileField.setText(fileHandler.getSetting("File:"));
	}
	
	public void uploadFileAction(){
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog((Stage)uploadFile.getScene().getWindow());
		fileField.setText((file.getAbsolutePath()));
	}

}
