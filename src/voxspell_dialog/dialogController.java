package voxspell_dialog;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
/**
 * This class represents the controller for the general purpose dialog popup. It has two
 * buttons, yes and no, which will return a boolean based on which is clicked.
 * 
 * @author jacky
 *
 */


public class dialogController {
	@FXML Button yes;
	@FXML Button no;
	@FXML Label output;
	
	@FXML
	public boolean yesAction(){
		Stage stage = (Stage) yes.getScene().getWindow();
	    stage.close();
		return true;
	}
	
	@FXML
	public boolean noAction(){
		Stage stage = (Stage) no.getScene().getWindow();
	    stage.close();
		return false;
	}
	
	public void setLabel(String text){
		output.setText(text);
	}
}
