package voxspell_login;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import voxspell_project.FileHandler;

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
		if(pwField.getText().equals(confirmPwField.getText()) &&
				!pwField.getText().isEmpty() && !userField.getText().isEmpty()
				&& !confirmPwField.getText().isEmpty() && !new FileHandler().fileExists(".usr/"+userField.getText())){
			makeRequiredFiles();
			generateRequiredUserSettings();
		    Stage stage = (Stage) confirm.getScene().getWindow();
		    stage.close();
		}
	}
	
	private void makeRequiredFiles(){
		new FileHandler().writeToFile(".users", userField.getText() +" "+calculateHash());
		new FileHandler().makeDirectory(".usr");
		new FileHandler().makeDirectory(".usr/"+userField.getText());
		new FileHandler().makeDirectory(".usr/"+userField.getText()+"/.stats");
		new FileHandler().makeFile(".usr/"+userField.getText()+"/"+userField.getText());
		new FileHandler().makeFile(".usr/"+userField.getText()+"/.stats/"+"NZCER-spelling-lists.txt");
		new FileHandler().makeFile(".usr/"+userField.getText()+"/userHistory.txt");
		for(String e: new FileHandler().generateLevels()){
			new FileHandler().writeToFile(".usr/"+userField.getText()+"/.stats/"+"NZCER-spelling-lists.txt", e.replaceAll("\\s+", "")+"Success: 0");
			new FileHandler().writeToFile(".usr/"+userField.getText()+"/.stats/"+"NZCER-spelling-lists.txt", e.replaceAll("\\s+", "")+"Failure: 0");
		}
	}
	
	private void generateRequiredUserSettings() {
		new FileHandler().writeToFile(".usr/"+userField.getText()+"/"+userField.getText()	, "Coins: 0\n"
				+ "Level: 1\nExperience: 0\nWordsCorrect: 0\nWordsAttempt: 0\n"
				+ "Videos:\nThemes: Forest\n"
				+ "BGM: Ambler.wav Rynos_Theme.wav\n"
				+ "Theme: Forest\n"
				+ "currentBGM: Ambler.wav");
		
	}

	private String calculateHash(){
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
