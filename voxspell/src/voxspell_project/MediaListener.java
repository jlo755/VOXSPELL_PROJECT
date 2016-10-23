package voxspell_project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import voxspell_media_handler.MusicPlayer;

public class MediaListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	public void fireGameEvent(){
		FileHandler fileHandle = new FileHandler();
		new MusicPlayer().getInstance().setFile(fileHandle.getSetting("GameBGM:", ".settings.ini"));
		new MusicPlayer().getInstance().execute();
	}
	
	public void fireMainEvent(){
		FileHandler fileHandle = new FileHandler();
		new MusicPlayer().getInstance().setFile(fileHandle.getSetting("BGM:", ".settings.ini"));
		new MusicPlayer().getInstance().execute();
	}

}
