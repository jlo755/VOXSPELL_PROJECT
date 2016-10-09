package voxspell_project;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class StatsController implements Initializable {

	@FXML Label userLabel;
	@FXML Label wordsCorrectLabel;
	@FXML Label wordsAttemptLabel;
	@FXML Label accuracyLabel;
	private User _user = new User().getInstance();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		FileHandler fileHandler = new FileHandler();
		String wordsCorrect = fileHandler.getSetting("WordsCorrect:", _user.getUser());
		String wordsAttempt = fileHandler.getSetting("WordsAttempt:", _user.getUser());
		wordsCorrectLabel.setText("Words Correct: "+wordsCorrect);
		wordsAttemptLabel.setText("Words Attempted: "+wordsAttempt);
		accuracyLabel.setText("Accuracy Rate: "+(Float.parseFloat(wordsCorrect)/Float.parseFloat(wordsAttempt)*100)+"%");
		userLabel.setText("User: "+_user.getUser());
	}

}
