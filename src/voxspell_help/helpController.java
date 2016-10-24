package voxspell_help;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;

/**
 * This class is the controller class for the help GUI. It handles the user selection of buttons, and
 * correctly displays the text accordingly to what information the user requires.
 * 
 * @author jacky
 *
 */

public class helpController implements Initializable{

	@FXML RadioButton newGame;
	@FXML RadioButton visitShop;
	@FXML RadioButton gallery;
	@FXML TextArea textField;
	@FXML RadioButton credits;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// This method is initialised on startup, and is essentially to set the state of the textField
		// so it wraps.
		textField.setWrapText(true);
	}

	@FXML
	public void newGameAction(){
		// Deselects all other buttons, and displays the information for a new game.
		visitShop.setSelected(false);
		gallery.setSelected(false);
		credits.setSelected(false);
		textField.setText("In the new game screen, you will be prompted with a white transparent box"
				+ " that has a start button. By pressing this start button, a new quiz will begin. A new"
				+ " quiz consist of 10 words to spell (by default) - if the word list chosen is smaller"
				+ " than this, then it will only spell that many words. The volume icon to the right"
				+ " of the text field is a relisten button - there is no penalty to clicking this."
				+ " When you think you are confident in your submission, press submit, and the program"
				+ " will tell you whether you are right or wrong. You get two tries! After the"
				+ " spelling quiz is complete, a dialog will show up, indicating how well you did, and"
				+ " also awarding you with coins! Pressing OK closes this dialog, and you will"
				+ " be greeted with two options - restart and shop. Restart will restart the quiz again"
				+ " with new words, but the same level. Shop will take you to the shop screen to buy "
				+ " items.");
	}

	@FXML
	public void visitShopAction(){
		// Deselects all other buttons, and displays the information for the visit shop.
		newGame.setSelected(false);
		gallery.setSelected(false);
		credits.setSelected(false);
		textField.setText("In the visit shop screen, you are able to purchase items such as videos"
				+ ", BGM, and also themes. These items cost money and cost 300, 100 and 500 respectively"
				+ ". When you wish to buy an item, clicking a button will prompt you with a dialog that"
				+ " asks for your confirmation and also indicates what video, theme or BGM it is. If yes,"
				+ " the coins will be deducted and the theme and video will be stored onto your account."
				+ " To play videos or change theme, use the gallery, else to change BGM use the settings.");
	}

	@FXML
	public void galleryAction(){
		// Deselects all other buttons, and displays the information related to the gallery.
		visitShop.setSelected(false);
		newGame.setSelected(false);
		credits.setSelected(false);
		textField.setText("In the gallery screen, you are able to set your themes, and also "
				+ "display other items such as the purchased videos. When clicking a button, "
				+ "it will provide a small preview of the video/theme to the right, and a button "
				+ "which will enable you to play the video or set the theme will occur. Clicking "
				+ "this button will set the theme, or open a new window and play the video.");

	}

	@FXML
	public void creditsAction(){
		// Deselects all other buttons, and displays the credits associated with the project.
		visitShop.setSelected(false);
		newGame.setSelected(false);
		gallery.setSelected(false);
		textField.setText("Backgrounds are drawn by my friends: Lucille Liu, and Mikaela Douglas.\n"
				+ "Buttons are designed by Mikaela Douglas.\n\n"
				+ "Interface graphic by http://www.flaticon.com/authors/freepik is licensed under Creative Commons BY 3.0 CC BY 3.0. Made with http://logomakr.com.\n\n"
				+ "All music produced by Kevin MacLeod (incompetech.com)\n"
				+ "Licensed under Creative Commons: By Attribution 3.0\n"
				+ "http://creativecommons.org/licenses/by/3.0/\n\n"
				+ "Media Player icons are produced specifically by Kelly Lester in Noun Project");

	}

}
