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
import voxspell_project.FileHandler;
import voxspell_project.User;

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
	private boolean requireChange;
	private AudioInputStream audioInputStream;
	private Clip clip;

	public static MusicPlayer getInstance(){
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
		/*stop();
		File f = new File(_file);
		Media hit = new Media(f.toURI().toString());
		mediaPlayer = new MediaPlayer(hit);
		// Credit to http://stackoverflow.com/questions/23498376/ahow-to-make-a-mp3-repeat-in-javafx
		// for the following looping music code for javaFX.
		mediaPlayer.setOnEndOfMedia(new Runnable() {
		       public void run() {
		         mediaPlayer.seek(Duration.ZERO);
		       }
		   });
		  mediaPlayer.play();
		mediaPlayer.setVolume(getVolume());
		_playing = true;*/
	    try {
	    	stop();
	        audioInputStream = AudioSystem.getAudioInputStream(new File(_file).getAbsoluteFile());
	        clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	        volume.setValue(-30.0f);
	        clip.start();
	        // credit to http://stackoverflow.com/questions/11919009/using-javax-sound-sampled-clip-to-play-loop-and-stop-mutiple-sounds-in-a-game
	        // for the following loop code.
	        clip.loop(clip.LOOP_CONTINUOUSLY);
	    } catch(Exception ex) {
	        ex.printStackTrace();
	    }

	}

	public void stop(){
		if(_playing && !_file.equals(new FileHandler().getSetting("currentBGM:", User.getInstance().getUserSettings()))){
			clip.stop();
			//mediaPlayer.stop();
			_playing = false;
		}  /*else if (mediaPlayer != null){
			//mediaPlayer.pause();
		}*/ else if (clip != null){
			clip.stop();
		}
	}

	public double getVolume() {
		return volume;
	}
	

	public void setVolume(double volume) {
		this.volume = volume;
	}
}
