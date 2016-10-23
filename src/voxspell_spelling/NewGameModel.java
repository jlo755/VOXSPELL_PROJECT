/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voxspell_spelling;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import voxspell_media_handler.VoiceWorker;
import voxspell_project.FileHandler;
import voxspell_project.User;

/**
 * This is the model associated with a new game. It handles operations such as
 * generating random words, correctly processing user input and states, and ending the game
 * when the iteration is complete.
 *
 * @author jacky
 */
public class NewGameModel {

	private NewGameController _controller;
	private String _fileName;
	private List<String> _words = new ArrayList<String>();
	private String _currentWord = "";
	protected boolean _review;
	private int _iterations = 0;
	private int _count = 0;
	private List<Integer> _wordIndex = new ArrayList<Integer>();
	protected String _level = "%Level 1";
	protected int _wordsCorrect = 0;
	private String _voiceSelected = "";
	private int _listSize = 0;
	private String LIST_FILE_NAME = "NZCER-spelling-lists.txt"; // The constant name of the word list - Victor
	public static final int NUM_WORDS_TESTED = 10; // A constant of num words to be tested, refactored - Victor
	private String stringToCheck = "";
	private User _user = new User().getInstance();


	private ExecutorService _threadPool;

	public NewGameModel(boolean reviewBoolean, NewGameController controller){
		/*
		 * This constructor sets the game type of the spelling. If it's a review, then it will
		 * read from the .failed, whereas if its a new game, it reads from wordlist.
		 */
		_controller = controller;
		_review = reviewBoolean;

		// This supposedly solves the overlapping by only have one thread at a time. Not sure if this is optimal. - Victor
		_threadPool = Executors.newFixedThreadPool(1);
	}

	public void execute() {
		_voiceSelected = new FileHandler().getSetting("Voice:", User.getInstance().getUserSettings());
		_level = "%"+new FileHandler().getSetting("Level:", User.getInstance().getUserSettings());
		this._wordsCorrect = 0;
		this._iterations = 0;
		this._words.clear();
		this._currentWord = "";
		this._wordIndex.clear();
		/*
		 * This is signature method from the Command interface, and the execute method gets called
		 * Specifically, it is executing the model view so the spellingGUI can begin.
		 */
		_fileName = new FileHandler().getSetting("File:", User.getInstance().getUserSettings());
		_words = new FileHandler().getWordList(_fileName, _level);
		// setting the wordList to the required spelling list
		if(_words.size() == 0){
		} else if (_words.size() >= NUM_WORDS_TESTED){
			this._listSize = NUM_WORDS_TESTED;
		} else {
			this._listSize = _words.size();
		}
	}

	// Now this method only generates and returns a random word and sets current word - Victor
	public String generateRandomWord(){
		/*
		 * this method generates random words from the wordList obtained - it cannot repeat
		 * previous words within the same session
		 */
		stringToCheck = "";
		if(_words.size() > 0){
			int randomWord = (int) Math.ceil(Math.random()*_words.size()-1);
			while(_wordIndex.contains(randomWord)){ // this checks that the word has not been previously assessed
				randomWord = (int) Math.ceil(Math.random()*_words.size()-1);
			}
			_wordIndex.add(randomWord);
			_currentWord = _words.get(randomWord);
			for(int i = 0; i<_currentWord.toCharArray().length; i++){
				if(!(_currentWord.toCharArray()[i]+"").equals("'")){
					stringToCheck=stringToCheck+_currentWord.toCharArray()[i];
				}
			}
		}
		return null;
	}

	protected int getWordListSize(){
		return _words.size();
	}
	
	public int getWordsCorrect(){
		return this._wordsCorrect;
	}

	protected int getIterations(){
		return this._iterations;
	}
	
	public String getLevel(){
		return this._level;
	}

	public boolean isCorrect(String answer){
		/*
		 * this function merely checks that the user answer is equal to the current word being assessed
		 */
		if(answer.toLowerCase().trim().equals(_currentWord.toLowerCase().trim())){
			return true;
		} else {
			return false;
		}
	}

	public boolean isValid(String userInput) {
		/*
		 * checks the user input is valid and does not contain any non-letters except apostrophes
		 */
		if(userInput.equals("")){
			return false;
		}
		char[] characters = userInput.toCharArray();
		for(char c: characters){
			if(!Character.isLetter(c) && (c != '\'') && (!Character.isWhitespace(c))){
				return false;
			}
		}
		return true;
	}

	protected void processCondition(String condition){
		/*
		 * writes to a file depending on the condition, it will append the word to mastered/faulted/
		 * failed if it does not exist. if review is specified, it will prompt users if they
		 * want to know how to spell a word if it is failed again
		 */
		if(_iterations < _words.size()){
			_controller.setUserSpell("Spell word: "+(getIterations()+1)+" of "+_words.size()+"");
		} else {
			_controller.setUserSpell("Complete!\nYou have spelt: "+_wordsCorrect+" words correct!");
			_controller.endGameDisableButtons();
			new FileHandler().incrementValue(_user.getUserSettings(), "Coins:", _wordsCorrect*10);
			_controller.endGameDialog();
			new FileHandler().writeToFile(_user.getUser()+"userHistory.txt", "WordsCorrect: "+_wordsCorrect);
		}
		proceedToNextWord(condition);

	}

	protected void processState(String userInput){
		if(_count == 0 && isCorrect(userInput)){
			_iterations++;
			_wordsCorrect++;
			processCondition("mastered");
			new FileHandler().incrementValue(_user.getUser()+".stats/"+_fileName.split("/")
					[_fileName.split("/").length-1], _level.substring(1).replaceAll("\\s+", "")+"Success:", 1);
			_count = 0;
		} else if(isCorrect(userInput)){
			// this is the faulted branch - specifically if count > 0, then it means they've had another try
			_iterations++;
			_wordsCorrect++;
			processCondition("faulted");
			_count = 0;
			new FileHandler().incrementValue(_user.getUser()+".stats/"+_fileName.split("/")
			[_fileName.split("/").length-1], _level.substring(1).replaceAll("\\s+", "")+"Success:", 1);
		} else if(_count == 0){
			// this is if they've failed the word the first try
			textToSpeech("festival -b '(voice_"+_voiceSelected+")' '(SayText \"Incorrect, try once more: \")' '(SayText \""+_currentWord+"\")' '(SayText \""+_currentWord+"\")'", "",false);
			//textToSpeech("festival -b '(SayText \""+getCurrentWord()+"\")'", "",true);
			_count++;
		}
		else {
			// this is if they've failed the word two times in a row
			_iterations++;
			processCondition("failed");
			_count = 0;
			new FileHandler().incrementValue(_user.getUser()+".stats/"+_fileName.split("/")
			[_fileName.split("/").length-1], _level.substring(1).replaceAll("\\s+", "")+"Failure:", 1);
		}
	}

	// This method is an algorithm to generate, append text to output and then speaks the word to the user. - Victor
	public void proceedToNextWord(String condition) {

		String festivalMsg = "";

		// Changed below a bit to allow syncing of Festival and text output - Victor
		if (condition.equals("mastered") || condition.equals("faulted")) {
			new FileHandler().incrementValue(_user.getUserSettings(), "WordsCorrect:", 1);
			festivalMsg = "Correct!";
		} else if (condition.equals("failed")){
			festivalMsg = "Incorrect!";
		}
		new FileHandler().incrementValue(_user.getUserSettings(), "WordsAttempt:", 1);

		// this is necessary to ensure the next word is not read out after 10 iterations or word.size()
		// is met
		if(_iterations == NUM_WORDS_TESTED || _words.size()-1 < _iterations){
			textToSpeech("festival -b '(voice_"+_voiceSelected+")' '(SayText \""+festivalMsg+"\")'", "",false);
		} else {

			String nextWord = generateRandomWord();

			if (condition.equals("")) { // This is only for the first word of every spelling test session - Victor
			} else {
				textToSpeech("festival -b '(voice_"+_voiceSelected+")' '(SayText \""+festivalMsg+"\")'", nextWord,false);
			}
			spell(); // asks -tts to say the word outloud

		}
	}

	public void spell(){
		/*
		 * merely sends a string for the process builder to read through text to speech
		 */
		textToSpeech("festival -b '(voice_"+_voiceSelected+")' '(SayText \"Please spell "+stringToCheck+"\")'", "", true);
	}

	public void textToSpeech(String command, String msgOutput, boolean btnEnable){
		/*
		 * this function builds a process which is executed within the bash shell
		 */
		// these are disabling the buttons to prevent error-prone states i.e. too many VoiceWorker objects
		// being generated.
		VoiceWorker voice = new VoiceWorker(command,  msgOutput, btnEnable);

		// queues the voiceworker object on the single fixed thread
		_threadPool.submit(voice);
	}

}
