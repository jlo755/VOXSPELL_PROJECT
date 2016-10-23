package voxspell_media_handler;

import javafx.concurrent.Task;

/**
 * This class utilizes the process builder, and places the process on a background thread. It also enables buttons, changes output messages,
 * and calls the festival method of the bash environment.
 * 
 * 
 * @author jacky
 *
 */

public class VoiceWorker extends Task<Void>{
	String _command;
	String _outputMsg;
	boolean _btnEnable;

	// Now this also takes a String for the purpose of outputting next word on txtField after Festival finishes - Victor
	public VoiceWorker(String command, String outputMsg, boolean btnEnable) { // Modified the constructor to take newGame as parameter - Victor
		_command = command;
		_outputMsg = outputMsg;
		_btnEnable = btnEnable;
	}

        @Override
	protected Void call() throws Exception {
		ProcessBuilder pb = new ProcessBuilder("bash", "-c",  _command);
		try {
			Process process = pb.start();
			process.waitFor(); // waiting for the process to finish
		} catch (Exception e) {
		}
		return null;
	}
	

}
