/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voxspell_project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


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
    private NewGameModel _model;

    @FXML
    private void backAction(ActionEvent event){
        //new SceneMediator().showScene((Stage)back.getScene().getWindow(), "mainMenu");
	    Stage stage = (Stage) back.getScene().getWindow();
    	new SceneMediator().changeScene(stage, "MainMenuFXML.fxml", "Main Menu");
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
