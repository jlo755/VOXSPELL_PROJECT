package voxspell_stats;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import voxspell_project.FileHandler;
import voxspell_project.User;

/**
 * This class is the model associated with a Stats GUI. It handles operations such as
 * handling the data associated with th charts in the view. And notifying the controller
 * to update its view.
 * 
 * @author jacky
 *
 */

public class StatsModel {
	private ArrayList<String> spellingList = new ArrayList<String>();
	private StatsController _controller;
	private ArrayList<String> levelList = new ArrayList<String>();
	private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
	private XYChart.Series lineChartData = new XYChart.Series();

	public ObservableList<PieChart.Data> getPieChartData() {
		return pieChartData;
	}

	public void setPieChartData(ObservableList<PieChart.Data> pieChartData) {
		this.pieChartData = pieChartData;
	}

	public ArrayList<String> getLevelList() {
		return levelList;
	}

	public void setLevelList(ArrayList<String> levelList) {
		this.levelList = levelList;
	}

	public ArrayList<String> getSpellingList() {
		return spellingList;
	}

	public void setSpellingList(ArrayList<String> spellingList) {
		this.spellingList = spellingList;
	}

	public StatsModel(StatsController controller){
		controller = _controller;
	}

	public void clearData(){
		this.levelList.clear();
		this.pieChartData.clear();
	}

	public void generateList(){
		/*
		 * This method generates the spelling list the user has uploaded/attempted.
		 */
		String bashCmd = "ls "+User.getInstance().getUser()+"/.stats";
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", bashCmd);
		Process process;
		try {
			process = builder.start();
			InputStream stdout = process.getInputStream();
			BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
			String list = stdoutBuffered.readLine();
			while ((list  != null)) {
				spellingList.add(list);
				list = stdoutBuffered.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateLevel(String fileName){
		/*
		 * This method generates the levels associated with that spelling list.
		 */
		FileHandler fileHandle = new FileHandler();
		fileHandle.setSpellingList(User.getInstance().getUser()+".stats/"+fileName);
		levelList = fileHandle.generateStatsLevels();
	}

	public void generatePieChartData(String level, String list){
		/*
		 * This method generates the pie chart data associated with a spelling level.
		 */
		pieChartData.clear();
		String fileName = User.getInstance().getUser()+".stats/"+list;
		String success = new FileHandler().getSetting(level.replaceAll("\\s+", "")+"Success:", fileName);
		String failure = new FileHandler().getSetting(level.replaceAll("\\s+", "")+"Failure:", fileName);
		//System.out.println(success);
		//System.out.println(fileName);
		//System.out.println(level.replaceAll("\\s+", "")+"Success:");
		if(success != null && failure != null){
			pieChartData.add(new PieChart.Data("Success", Integer.parseInt(success)));
			pieChartData.add(new PieChart.Data("Failure", Integer.parseInt(failure)));

		}
	}
	
	public void generateLineChartData(){
		/*
		 * this method generates the data of the user's spelling history from their
		 * previous attempts.
		 */
		ArrayList<Integer> scores = new FileHandler().generateHistoryScore();
		int count = 1;
		for(int e: scores){
			lineChartData.getData().add(new XYChart.Data(""+count, e));
			count++;
		}
	}

	public XYChart.Series getLineChartData() {
		return lineChartData;
	}

	public void setLineChartData(XYChart.Series lineChartData) {
		this.lineChartData = lineChartData;
	}
}
