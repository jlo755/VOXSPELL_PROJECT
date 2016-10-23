/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voxspell_project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class does ALL file handling, it writes, clears, appends, increments, and does
 * all kinds of file-related work inorder for the spelling-app to work.
 *
 * @author jacky
 */
public class FileHandler {
	String spellingList;

	public FileHandler(){
		//spellingList = getSetting("File:", User.getInstance().getUserSettings());
	}

	public void clearFile(String fileName){
		PrintWriter pw;
		try {
			pw = new PrintWriter(fileName);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setSpellingList(String list){
		spellingList = list;
	}

	public void writeToFile(String fileName, String currentWord){
		// this method writes to the specified fileName - it checks if the word exists yet or not
		// if it does, it will then add it. it will also add the word to stats along with whether
		// it was faulted, failed, or mastered.
		try {
			//if(wordNotExists(fileName, currentWord)){
				// true set to enable appending to file writer
				BufferedWriter typeOfSuccess = new BufferedWriter(new FileWriter(fileName, true));
				appendingFile(typeOfSuccess, currentWord);
				typeOfSuccess.close();
			//}
			//BufferedWriter stats = new BufferedWriter(new FileWriter(".stats.txt", true));
			//appendingFile(stats, currentWord+": "+fileName);
			//stats.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
	}

	private boolean wordNotExists(String fileName, String currentWord) {
		// this method checks if a word exists in the specified filename
		try {
			String word = null;
			BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
			while((word = bufferedReader.readLine()) != null) {
				if(word.equals(currentWord)){
					bufferedReader.close();
					return false;
				}
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public void appendingFile(BufferedWriter fileName, String toAdd){
		/*
		 * This method appends to an existing file and adds the string to a new line.
		 */
		try {
			fileName.write(toAdd);
			fileName.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void makeFile(String name){
		try {
			new File(name).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void makeDirectory(String name){
		new File(name).mkdir();
	}

	public boolean fileExists(String file){
		File f = new File(file);
		if(f.exists() && !f.isDirectory()) { 
			return true;
		}
		return false;
	}

	public void removingWord(String fileName, String toRemove){
		/*
		 * this method finds a string in a file and removes it from the file - removing the blank space
		 * too. it uses a temporary file to do this.
		 */
		try {
			File tempFile = new File(".TempWordlist.txt");
			File inputFile = new File(fileName);

			BufferedWriter fileToWrite = new BufferedWriter(new FileWriter(tempFile, true));
			BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
			String word = "";
			while((word = bufferedReader.readLine()) != null) {
				if(!word.equals(toRemove)){
					appendingFile(fileToWrite, word);
				}
			}
			bufferedReader.close();
			fileToWrite.close();
			tempFile.renameTo(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> generateLevels(){
		/*
		 * This method generates the levels associated with a spelling list.
		 */
		// credit to http://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java
		// for the following code on how to read files line by line.
		ArrayList<String> levels = new ArrayList<String>();
		FileReader fileReader;
		try {
			fileReader = new FileReader(spellingList);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String word = "";
			while((word = bufferedReader.readLine()) != null) {
				if(word.substring(0,1).equals("%")){
					levels.add(word.substring(1));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return levels;

	}

	public ArrayList<String> generateStatsLevels(){
		/*
		 * This method generates the levels associated with a stats file.
		 */
		ArrayList<String> levels = new ArrayList<String>();
		FileReader fileReader;
		try {
			fileReader = new FileReader(spellingList);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String word = "";
			while((word = bufferedReader.readLine()) != null) {
				if(word.contains("Success:")){
					levels.add(word.substring(0,word.indexOf("Success:")));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return levels;
	}
	
	public ArrayList<Integer> generateHistoryScore(){
		/*
		 * This method generates the history score associated with a user.
		 */
		ArrayList<Integer> scores = new ArrayList<Integer>();
		FileReader fileReader;
		try {
			fileReader = new FileReader(User.getInstance().getUser()+"/userHistory.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String word = "";
			while((word = bufferedReader.readLine()) != null) {
				scores.add(Integer.parseInt(word.split("WordsCorrect: ")[1]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scores;
		
	}
	
	
	/* A rewritten getWordList method to take level as a parameter to process new wordlist format. Original, single
	 * fileName string method functionality are retained by all invokers of such methods use null for the level input,
	 * to indicate not reading from the NZCER wordlist. - Victor (method originally written by Jacky)
	 */
	public List<String> getWordList(String fileName, String level) { 
		/*
		 * retrieve the word list associated with a file.
		 */
		String word = null;
		List<String> words = new ArrayList<String>();
		FileReader fileReader;
		try {

			fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);


			if (level == null) {
				while((word = bufferedReader.readLine()) != null) {
					words.add(word);
				} 
			} else {
				while(!(word = bufferedReader.readLine()).equals(level)) {
				}
				word = bufferedReader.readLine();
				while(word != null && !word.substring(0,1).equals("%")) {
					words.add(word);
					word = bufferedReader.readLine();
				}
			}

			bufferedReader.close();         
		} catch (Exception e){
			e.printStackTrace();

		}
		return words;
	}

	public void incrementValue(String file, String detail, int increment){
		/*
		 * This method increments the value of a detail in a file. For example,
		 * coins associated with a user. The : must be included - in the format
		 * Coins: 100
		 */
		try {
			File tempFile = new File(".TempFile.txt");
			File inputFile = new File(file);

			BufferedWriter fileToWrite = new BufferedWriter(new FileWriter(tempFile, true));
			BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
			String word = "";
			while((word = bufferedReader.readLine()) != null) {
				if(!word.split(" ")[0].equals(detail)){
					appendingFile(fileToWrite, word);
				} else {
					appendingFile(fileToWrite, detail+" "+(Integer.parseInt(word.split(" ")[1])+increment));
				}
			}
			bufferedReader.close();
			fileToWrite.close();
			tempFile.renameTo(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getSetting(String detail, String file){
		/*
		 * This returns the detail associated with a setting. For example,
		 * detail Coin: will return the amount of coins associated.
		 */
		FileReader fileReader;
		try {
			fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String word = "";
			while((word = bufferedReader.readLine()) != null) {
				if(word.split(" ")[0].equals(detail)){
					return word.substring(word.indexOf(" ")+1);
				}
			}

		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> getUserItems(String detail, String user){
		FileReader fileReader;
		try {
			fileReader = new FileReader(user);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String word = "";
			ArrayList<String> boughtItems = new ArrayList<String>();
			while((word = bufferedReader.readLine()) != null) {
				if(word.split(" ")[0].equals(detail)){
					for(int i = 1; i< word.split(" ").length; i++){
						boughtItems.add(word.split(" ")[i]);
					}
				}
			}
			return boughtItems;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void addPurchase(String user, String purchase, String type){
		/*
		 * This adds a purchase to the user's text file.
		 */
		ArrayList<String> itemsBought = getUserItems(type, user);
		String toRemove = ""+type;
		for(String e: itemsBought){
			toRemove=toRemove+" "+e;
		}
		removingWord(user, toRemove);
		toRemove=toRemove+" "+purchase;
		writeToFile(user, toRemove);
	}
}
