package voxspell_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SettingsModel {
	private ArrayList<String> levels;
	private ArrayList<String> voices = new ArrayList<String>();
	private String fileName;
	private ArrayList<String> themes = new ArrayList<String>();

	public SettingsModel(){
		levels = new FileHandler().generateLevels();
		System.out.println(levels);

		String bashCmd = "ls /usr/share/festival/voices/english";
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", bashCmd);
		Process process;
		try {
			process = builder.start();
			InputStream stdout = process.getInputStream();
			BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
			String voice = stdoutBuffered.readLine();
			while ((voice  != null)) {

				voices.add(voice);
				voice = stdoutBuffered.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		makeRequiredSettingsFile();
		addThemes();
		fileName = new FileHandler().getSetting("File:");

	}

	public ArrayList<String> getLevels() {
		return levels;
	}

	public void setLevels(ArrayList<String> levels) {
		this.levels = levels;
	}

	public void setVoice(ArrayList<String> voices) {
		this.setVoices(voices);
	}

	public ArrayList<String> getVoices() {
		return voices;
	}

	public void setVoices(ArrayList<String> voices) {
		this.voices = voices;
	}

	private void addThemes(){
		getThemes().add("Pirate");
		getThemes().add("Forest");
	}
	
	public void makeRequiredSettingsFile(){
		if(!new FileHandler().fileExists(".settings.ini")){
			new FileHandler().makeFile(".settings.ini");
			new FileHandler().writeToFile(".settings.ini", "Level: "+levels.get(0));
			new FileHandler().writeToFile(".settings.ini", "Voice: "+voices.get(0));
			new FileHandler().writeToFile(".settings.ini", "File: NZCER-spelling-lists.txt");
			new FileHandler().writeToFile(".settings.ini", "Theme: Forest");
		}
	}

	public ArrayList<String> getThemes() {
		return themes;
	}

	public void setThemes(ArrayList<String> themes) {
		this.themes = themes;
	}
}
