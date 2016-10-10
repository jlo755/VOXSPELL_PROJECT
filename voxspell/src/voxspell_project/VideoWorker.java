package voxspell_project;

import javax.swing.SwingWorker;

import javafx.concurrent.Task;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 * 
 * This class simply puts the start() method of _video, an EmbeddedMediaPlayer, in the background,
 * because it appears that it is a blocking call and the program can't progress past this line
 * until it's place in the background.
 * 
 * @author victor
 *
 */

public class VideoWorker extends Task<Void>{
	
	EmbeddedMediaPlayer _video;
	
	public VideoWorker(EmbeddedMediaPlayer video) {
		_video = video;
	}

	@Override
	protected Void call() throws Exception {
		_video.start();
		return null;
	}

}
