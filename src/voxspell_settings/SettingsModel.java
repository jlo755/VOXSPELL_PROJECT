package voxspell_settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import voxspell_project.FileHandler;
import voxspell_project.User;

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
		/*String bashCmd = "ls .resources/BGM/MainMenu_BGM";
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
		}*/
		ArrayList<String> bgm  = new FileHandler().getUserItems("BGM:", User.getInstance().getUserSettings());
		BGM.addAll(bgm);
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

	public ArrayList<String> getBGM() {
		return BGM;
	}
}
