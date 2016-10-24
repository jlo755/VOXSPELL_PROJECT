package voxspell_media_handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;

import javafx.concurrent.Task;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import voxspell_utility.FileHandler;
import voxspell_utility.User;

/**
 * This class utilises the javaFX thread, and can only operate on it. This class is a singletown
 * in charge of playing music when given a file. The volume can be set, and the song can change
 * accordingly.
 * 
 * @author jacky
 *
 */

public class MusicPlayer{
	private static MusicPlayer _instance;
	private String _file;
	private MediaPlayer mediaPlayer;
	private boolean _playing;
	private double volume = 0.1;

	public static MusicPlayer getInstance(){
		/*
		 * This method instantiates the singleton, and returns the MediaPlayer object.
		 */
		if(_instance == null){
			_instance = new MusicPlayer();
		}
		return _instance;
	}

	public void setFile(String file){
		_file = file;
	}

	public String getFile(){
		return _file;
	}

	public void execute(){
		stop();
		File f = new File(_file);
		Media hit = new Media(f.toURI().toString()); // generating the url to the media file
		mediaPlayer = new MediaPlayer(hit);
		// Credit to http://stackoverflow.com/questions/23498376/ahow-to-make-a-mp3-repeat-in-javafx
		// for the following looping music code for javaFX.
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				mediaPlayer.seek(Duration.ZERO); // this method loops the mediaPlayer once the duration is 0
			}
		});
		mediaPlayer.play();
		mediaPlayer.setVolume(getVolume()); // sets the volume of the media player (in decimal units)
		_playing = true;
	}

	public void stop(){
		/*
		 * This method stops the media player from playing music - if the music file is different, it stops
		 * the song completely, else if there has been no change, it merely pauses the media player.
		 */
		if(_playing && !_file.equals(new FileHandler().getSetting("currentBGM:", User.getInstance().getUserSettings()))){
			mediaPlayer.stop();
			_playing = false;
		}  else if (mediaPlayer != null){
			mediaPlayer.pause();
		}
	}

	public double getVolume() {
		return volume;
	}


	public void setVolume(double volume) {
		this.volume = volume;
	}
}
