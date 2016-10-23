/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voxspell_spelling;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import voxspell_media_handler.SceneMediator;
import voxspell_project.MediaListener;


/**
 *
 * @author jacky
 */
public class NewGameController implements Initializable{
    @FXML private Button back;
    @FXML private Button submit;
    @FXML private Label wordLabel;
    @FXML private TextField userSpell;
    @FXML private Button start;
    @FXML private Button stats;
    @FXML private Button settings;
    private NewGameModel _model;

    @FXML
    private void backAction(ActionEvent event){
        //new SceneMediator().showScene((Stage)back.getScene().getWindow(), "mainMenu");
	    Stage stage = (Stage) back.getScene().getWindow();
	    new MediaListener().fireMainEvent();
    	new SceneMediator().changeScene(stage, "../voxspell_main_menu/MainMenuFXML.fxml", "Main Menu");
    }
    
    @FXML
    private void submitAction(){
    	String userInput = userSpell.getText();
    	userSpell.clear();
    	if(!_model.isValid(userInput)){
    		return;
    	} else {
    		_model.processState(userInput);
    	}
    }
    
    @FXML
    private void textFieldEnterAction(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER) {
            submitAction();
        }
    }
    
    @FXML
    private void startAction(ActionEvent event){
    	userSpell.setVisible(true);
    	submit.setVisible(true);
    	wordLabel.setText("Spell word: "+(_model.getIterations()+1)+" of "+_model.NUM_WORDS_TESTED+"");
    	start.setVisible(false);
    	_model.execute();
    	_model.generateRandomWord();
    	_model.spell();
    }
    
    @FXML
    private void statsAction(ActionEvent event){
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../voxspell_stats/StatsScreen.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Statistics");
            stage.setScene(new Scene(root));
            stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@FXML
	public void settingsAction(){
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../voxspell_settings/Settings.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root));
            stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         _model = new NewGameModel(false, this);
         _model.execute();
     	userSpell.setVisible(false);
     	submit.setVisible(false);
     	wordLabel.setText("Press start to begin...");
    }
    
    public void setUserSpell(String label){
    	wordLabel.setText(label);
    }
    
    public void endGameDisableButtons(){
    	userSpell.setVisible(false);
    	submit.setVisible(false);
    }
    
}
