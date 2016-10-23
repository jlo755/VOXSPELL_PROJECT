package voxspell_project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import voxspell_media_handler.MusicPlayer;

/*
 * This class is to observe the main menu class and determine which BGM has to be played according to what
 * is clicked.
 */

public class MediaListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	public void fireGameEvent(){
		FileHandler fileHandle = new FileHandler();
		new MusicPlayer().getInstance().setFile(fileHandle.getSetting("GameBGM:", User.getInstance().getUserSettings()));
		new MusicPlayer().getInstance().execute();
	}
	
	public void fireMainEvent(){
		FileHandler fileHandle = new FileHandler();
		new MusicPlayer().getInstance().setFile(fileHandle.getSetting("currentBGM:", User.getInstance().getUserSettings()));
		new MusicPlayer().getInstance().execute();
	}

}
