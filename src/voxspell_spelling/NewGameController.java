/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voxspell_spelling;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import voxspell_dialog.CompleteLevelDialogController;
import voxspell_media_handler.SceneMediator;
import voxspell_project.FileHandler;
import voxspell_project.MediaListener;
import voxspell_project.User;


/**
 * This class is the controller associated with the New Game GUI. It handles user-related
 * actions such as user input, buttons being pressed and such.
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
    @FXML private Button shop;
    @FXML private Button restart;
    @FXML private ImageView relisten;
    private NewGameModel _model;

    @FXML
    private void backAction(ActionEvent event){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Are you sure?");
		alert.setHeaderText("Are you sure you want to exit?");	
		alert.setContentText("All progress will be lost.");
		Optional<ButtonType> result = alert.showAndWait();
		if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
		    Stage stage = (Stage) back.getScene().getWindow();
		    new MediaListener().fireMainEvent();
	    	new SceneMediator().changeScene(stage, "/voxspell_main_menu/MainMenuFXML.fxml", "Main Menu");
		}
    }
    
    @FXML
    private void submitAction(){
    	/*
    	 * This method is called when submit is pressed, it checks if what the user entered
    	 * is a valid character or not. And appropriately places the game in the correct
    	 * state.
    	 */
    	String userInput = userSpell.getText();
    	userSpell.clear();
    	if(!_model.isValid(userInput)){
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Invalid input!");	
			alert.setContentText("Invalid characters such as punctuation or numbers.");
			alert.show();
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
    private void shopAction(){
    	Stage stage = (Stage) back.getScene().getWindow();
 	    new MediaListener().fireMainEvent();
     	new SceneMediator().changeScene(stage, "/voxspell_shop/ShopFXML.fxml", "Main Menu");
    }
    
    @FXML
    private void restartAction(){
    	 _model = new NewGameModel(false, this);
         _model.execute();
     	userSpell.setVisible(false);
     	submit.setVisible(false);
     	restart.setVisible(false);
     	shop.setVisible(false);
     	start.setVisible(true);
     	wordLabel.setText("Press start to begin...");
    }
    
    @FXML
    private void startAction(ActionEvent event){
    	userSpell.setVisible(true);
    	submit.setVisible(true);
    	wordLabel.setText("Spell word: "+(_model.getIterations()+1)+" of "+_model.getWordListSize()+"");
    	start.setVisible(false);
    	relisten.setVisible(true);
    	_model.execute();
    	_model.generateRandomWord();
    	_model.spell();
    }
    
    @FXML
    private void relistenAction(){
    	// relisten to the word.
    	_model.spell();
    }
    
    @FXML
    private void statsAction(ActionEvent event){
    	/*
    	 * Opens the stats panel.
    	 */
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/voxspell_stats/StatsScreen.fxml"));
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
			root = FXMLLoader.load(getClass().getResource("/voxspell_settings/Settings.fxml"));
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
     	restart.setVisible(false);
     	shop.setVisible(false);
     	relisten.setVisible(false);
     	wordLabel.setText("Press start to begin...");
     	settings.setDisable(true);
     	settings.setOpacity(100);
    }
    
    public void setUserSpell(String label){
    	wordLabel.setText(label);
    }
    
    public void endGameDisableButtons(){
    	restart.setVisible(true);
    	shop.setVisible(true);
    	userSpell.setVisible(false);
    	submit.setVisible(false);
    	relisten.setVisible(false);
    }
    
    public void endGameDialog(){
    	// this is the end of a game, and the appropriate dialog is pop-up. It sets
    	// the labels to the correct state and redisplays the relevant buttons.
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/voxspell_dialog/CompleteLevelDialogFXML.fxml"));
	        VBox vbox = loader.load();
			CompleteLevelDialogController controller = loader.getController();
			int coins = _model.getWordsCorrect()*10;
			controller.setCongratLabel("Congratulations you have earned: "+coins+" coins for completing this level!\n\n You now have: "
					+new FileHandler().getSetting("Coins:", User.getInstance().getUserSettings())+" coins.");
            Stage stage = new Stage();
            stage.setTitle("Congratulations!");
            stage.setResizable(false);
            stage.setScene(new Scene(vbox));
            stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
