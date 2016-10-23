package voxspell_dialog;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
/**
 * This class is the controller for the popup dialog that appears at the end of a spelling level.
 * 
 * @author jacky
 *
 */


public class CompleteLevelDialogController implements Initializable{

	@FXML Button confirm;
	@FXML Label congratLabel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void setCongratLabel(String text){
		congratLabel.setText(text);
	}

	@FXML
	public void confirmAction(){
		Stage stage = (Stage) confirm.getScene().getWindow();
	    stage.close();
	}
	
}
