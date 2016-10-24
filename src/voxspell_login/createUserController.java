package voxspell_login;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import voxspell_utility.FileHandler;

public class createUserController implements Initializable {
	@FXML Button confirm;
	@FXML Button cancel;
	@FXML TextField userField;
	@FXML TextField pwField;
	@FXML TextField confirmPwField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	@FXML
	public void cancelAction(){
	    Stage stage = (Stage) cancel.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	public void confirmAction(){
		/*
		 * This method does error checking when the button confirm is pressed. It will
		 * check if the password field is empty, and that the two password fields are equal.
		 * It will also check if the user currently exists and if so, will throw an error message
		 * to the user.
		 */
		if(pwField.getText().equals(confirmPwField.getText()) &&
				!pwField.getText().isEmpty() && !userField.getText().isEmpty()
				&& !confirmPwField.getText().isEmpty() && !new FileHandler().fileExists(".usr/"+userField.getText()+"/"+userField.getText())){
			makeRequiredFiles(); // make the required files for a new user
			generateRequiredUserSettings(); // generate the default settings for a new user
		    Stage stage = (Stage) confirm.getScene().getWindow();
		    stage.close();
		} else if (new FileHandler().fileExists(".usr/"+userField.getText()+"/"+userField.getText())) {
			// user already exists, will throw an error
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error creating account!");	
			alert.setContentText("The username already exists!");
			alert.show();
		} else {
			// blank fields
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error creating account!");	
			alert.setContentText("Fields are blank.");
			alert.show();
		}
	}
	
	private void makeRequiredFiles(){
		/*
		 * This method will generate the required files for the game to work for a new user.
		 * If the directories do not exist, it will also create those files.
		 */
		FileHandler fileHandle = new FileHandler();
		fileHandle.setSpellingList("NZCER-spelling-lists.txt");
		// this stores the user and password to a file with the pw converted to a hashcode
		fileHandle.writeToFile(".users", userField.getText() +" "+calculateHash());
		fileHandle.makeDirectory(".usr");
		fileHandle.makeDirectory(".usr/"+userField.getText());
		fileHandle.makeDirectory(".usr/"+userField.getText()+"/.stats");
		fileHandle.makeFile(".usr/"+userField.getText()+"/"+userField.getText());
		fileHandle.makeFile(".usr/"+userField.getText()+"/.stats/"+"NZCER-spelling-lists.txt");
		fileHandle.makeFile(".usr/"+userField.getText()+"/userHistory.txt");
		for(String e: fileHandle.generateLevels()){
			fileHandle.writeToFile(".usr/"+userField.getText()+"/.stats/"+"NZCER-spelling-lists.txt", e.replaceAll("\\s+", "")+"Success: 0");
			fileHandle.writeToFile(".usr/"+userField.getText()+"/.stats/"+"NZCER-spelling-lists.txt", e.replaceAll("\\s+", "")+"Failure: 0");
		}
	}
	
	private void generateRequiredUserSettings() {
		/*
		 * Generates the default settings for the user into a file - a helper method.
		 */
		new FileHandler().writeToFile(".usr/"+userField.getText()+"/"+userField.getText()	, "Coins: 0\n"
				+ "Level: Level 1\nWordsCorrect: 0\nWordsAttempt: 0\n"
				+ "Videos:\nThemes: Forest\n"
				+ "BGM: Ambler.wav Rynos_Theme.wav\n"
				+ "Theme: Forest\n"
				+ "currentBGM: .resources/BGM/MainMenu_BGM/Ambler.wav\n"
				+ "File: NZCER-spelling-lists.txt\n"
				+ "GameBGM: .resources/BGM/Game_BGM/Call to Adventure.wav\n"
				+ "Voice: akl_nz_jdt_diphone");
		
	}

	private String calculateHash(){
		// This converts the password into a hash code to associate with the user.
		String hash = "";
		int count = 0;
		for(char e: pwField.getText().toCharArray()){
			if(count%2 == 0){
				hash += e+"^";
			} else if(count%3 == 0){
				hash += e+"!";
			} else {
				hash += e+"&";
			}
			count++;
		}
		return hash;
	}
	
}
