package voxspell_stats;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import voxspell_project.FileHandler;
import voxspell_project.User;

/**
 * This class is the controller to the Stats GUI. It handles user related input such as notifying
 * registered models of changes to the combobox, such that it has to update its view.
 * 
 * @author jacky
 *
 */

public class StatsController implements Initializable {

	@FXML Label userLabel;
	@FXML Label wordsCorrectLabel;
	@FXML Label wordsAttemptLabel;
	@FXML Button back;
	@FXML PieChart pieChart;
	@FXML ComboBox listCombo;
	@FXML ComboBox levelCombo;
	@FXML LineChart lineChart;
	private StatsModel _model;
	
	private User _user = new User().getInstance();
	
	@FXML
	public void listComboAction(){
		String levelGenerate = listCombo.getValue()+"";
		levelCombo.getItems().clear();
		_model.getLevelList().clear();
		_model.generateLevel(levelGenerate);
		levelCombo.getItems().addAll(_model.getLevelList());
		levelCombo.setValue(_model.getLevelList().get(0));
		_model.generatePieChartData(""+levelCombo.getValue(), ""+listCombo.getValue());
		setGraphData();
	}
	
	@FXML
	public void levelComboAction(ActionEvent e){
		_model.generatePieChartData(""+levelCombo.getValue(), ""+listCombo.getValue());
		setGraphData();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*
		 * Sets the controller and its view to the correct state so it is consistent
		 * with what the user sees. Also generate the list, and levels associated
		 * with the user.
		 */
		_model = new StatsModel(this);
		_model.generateList();
		_model.generateLevel(""+_model.getSpellingList().get(0));		
		setComboBox();
		_model.generatePieChartData(""+levelCombo.getValue(), ""+listCombo.getValue());
		_model.generateLineChartData();
		setGraphData();
		setLabels();
		lineChart.setTitle("Spelling History");
		lineChart.getData().add(_model.getLineChartData());
	}
	
	@FXML
	public void backAction(){
	    Stage stage = (Stage) back.getScene().getWindow();
	    stage.close();
	}
	
	private void setLabels(){
		FileHandler fileHandler = new FileHandler();
		String wordsCorrect = fileHandler.getSetting("WordsCorrect:", _user.getUserSettings());
		String wordsAttempt = fileHandler.getSetting("WordsAttempt:", _user.getUserSettings());
		wordsCorrectLabel.setText("Words Correct: "+wordsCorrect);
		wordsAttemptLabel.setText("Words Attempted: "+wordsAttempt);
		userLabel.setText("User: "+_user.getUser().split("/")[1]);
	}
	
	private void setComboBox(){
		listCombo.getItems().addAll(_model.getSpellingList());
		listCombo.setValue(_model.getSpellingList().get(0));
		levelCombo.getItems().addAll(_model.getLevelList());
		levelCombo.setValue(_model.getLevelList().get(0));
	}
	
	private void setGraphData(){
		pieChart.setTitle(""+levelCombo.getValue());
		pieChart.setData(_model.getPieChartData());
	}

}
