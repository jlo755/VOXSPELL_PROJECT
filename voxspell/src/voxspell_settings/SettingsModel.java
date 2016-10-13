package voxspell_settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import voxspell_project.FileHandler;

public class SettingsModel {
	private ArrayList<String> levels;
	private ArrayList<String> voices = new ArrayList<String>();
	private String fileName;
	private ArrayList<String> themes = new ArrayList<String>();
	private ArrayList<String> BGM = new ArrayList<String>();
	private ArrayList<String> gameBGM = new ArrayList<String>();

	public SettingsModel(){
		makeRequiredSettingsFile();
		levels = new FileHandler().generateLevels();
		generateVoices();
		generateBGM();
		addThemes();
		fileName = new FileHandler().getSetting("File:", ".settings.ini");

	}

	public void generateVoices(){
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
	
	private void generateBGM(){
		String bashCmd = "ls .resources/BGM/MainMenu_BGM";
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", bashCmd);
		Process process;
		try {
			process = builder.start();
			InputStream stdout = process.getInputStream();
			BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
			String bgm = stdoutBuffered.readLine();
			while ((bgm  != null)) {

				BGM.add(bgm);
				bgm = stdoutBuffered.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void generateGameBGM(){
		String bashCmd = "ls .resources/BGM/Game_BGM";
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", bashCmd);
		Process process;
		try {
			process = builder.start();
			InputStream stdout = process.getInputStream();
			BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
			String bgm = stdoutBuffered.readLine();
			while ((bgm  != null)) {

				gameBGM.add(bgm);
				bgm = stdoutBuffered.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void makeRequiredSettingsFile(){
		if(!new FileHandler().fileExists(".settings.ini")){
			new FileHandler().makeFile(".settings.ini");
			new FileHandler().writeToFile(".settings.ini", "File: NZCER-spelling-lists.txt");
			levels = new FileHandler().generateLevels();
			new FileHandler().writeToFile(".settings.ini", "Level: "+levels.get(0));
			generateVoices();
			new FileHandler().writeToFile(".settings.ini", "Voice: "+voices.get(0));
			new FileHandler().writeToFile(".settings.ini", "Theme: Forest");
			generateBGM();
			new FileHandler().writeToFile(".settings.ini", "BGM: .resources/BGM/MainMenu_BGM/Ambler.mp3");
			generateGameBGM();
			new FileHandler().writeToFile(".settings.ini", "GameBGM: .resources/BGM/Game_BGM/Call to Adventure.mp3");
		}
	}

	public ArrayList<String> getThemes() {
		return themes;
	}

	public void setThemes(ArrayList<String> themes) {
		this.themes = themes;
	}

	public ArrayList<String> getBGM() {
		return BGM;
	}
}
