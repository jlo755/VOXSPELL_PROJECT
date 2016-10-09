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
 *
 * @author jacky
 */
public class FileHandler {
	String[] _files = {".mastered.txt",".stats.txt",".failed.txt",".faulted.txt"};
	String spellingList = "NZCER-spelling-lists.txt";

	public void clearFile(String fileName){
		PrintWriter pw;
		try {
			pw = new PrintWriter(fileName);
			pw.close();
		} catch (FileNotFoundException e) {
		}
	}


	public void writeToFile(String fileName, String currentWord){
		// this method writes to the specified fileName - it checks if the word exists yet or not
		// if it does, it will then add it. it will also add the word to stats along with whether
		// it was faulted, failed, or mastered.
		try {
			if(wordNotExists(fileName, currentWord)){
				// true set to enable appending to file writer
				BufferedWriter typeOfSuccess = new BufferedWriter(new FileWriter(fileName, true));
				appendingFile(typeOfSuccess, currentWord);
				typeOfSuccess.close();
			}
			//BufferedWriter stats = new BufferedWriter(new FileWriter(".stats.txt", true));
			//appendingFile(stats, currentWord+": "+fileName);
			//stats.close();
		} catch (IOException e1) {
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
		}
		return true;
	}

	private void appendingFile(BufferedWriter fileName, String toAdd){
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
		}
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
		ArrayList<String> levels = new ArrayList<String>();
		FileReader fileReader;
		try {
			fileReader = new FileReader(spellingList);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String word = "";
			while((word = bufferedReader.readLine()) != null) {
				if(word.split(" ")[0].equals("%Level")){
					levels.add(word.substring(1));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return levels;

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
				while(word != null && !word.split(" ")[0].equals("%Level")) {
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
	
	public String getSetting(String detail){
		FileReader fileReader;
		try {
			fileReader = new FileReader(".settings.ini");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String word = "";
			while((word = bufferedReader.readLine()) != null) {
				if(word.split(" ")[0].equals(detail)){
					return word.substring(word.indexOf(" ")+1);
				}
			}

		} catch (Exception e){
			
		}
		return null;
	}
}
