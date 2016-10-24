package voxspell_settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import voxspell_utility.FileHandler;
import voxspell_utility.User;

/**
 * This class is the model associated with the settings GUI. It generates the
 * output for the comboboxes for the settings controller.
 * 
 * @author jacky
 *
 */

public class SettingsModel {
	private ArrayList<String> levels;
	private ArrayList<String> voices = new ArrayList<String>();
	private String fileName;
	private ArrayList<String> BGM = new ArrayList<String>();
	private ArrayList<String> gameBGM = new ArrayList<String>();
	private FileHandler fileHandle = new FileHandler();

	public SettingsModel(){
		fileName = fileHandle.getSetting("File:", User.getInstance().getUserSettings());
		fileHandle.setSpellingList(fileName);
		generateLevels();
		generateVoices();
		generateBGM();
	}

	public void generateVoices(){
		/*
		 * This method generates the voices for the combo box to display - it utilises bash commands
		 * to generate the festival voices assuming the path is in the default location.
		 */
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
	
	public void generateLevels(){
		// This method generates the required levels for the level combobox for the view.
		levels = fileHandle.generateLevels();
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
	
	private void generateBGM(){
		/*
		 * This method merely generates the BGM list for the BGM combo box, by reading the available
		 * BGM available to the user.
		 */
		ArrayList<String> bgm  = new FileHandler().getUserItems("BGM:", User.getInstance().getUserSettings());
		BGM.addAll(bgm);
	}

	public ArrayList<String> getBGM() {
		return BGM;
	}
}
