package voxspell_project;

/**
 * This class is a singleton as there can only be a single user. It keeps track of
 * what user is currently logged in and playing the game, so that the correct
 * stats can be awarded to the correct user.
 * 
 * @author jacky
 *
 */

public class User {
	private static User _instance = null;
	private String _user = "Default";
	
	public static User getInstance(){
		if(_instance == null){
			_instance = new User();
		}
		return _instance;
	}
	
	public void setUser(String user){
		_user = user;
	}
	
	public String getUser(){
		return _user;
	}
	
	public String getUserSettings(){
		if(!_user.equals("Default")){
			return _user+_user.split("/")[1];
		} else {
			return "Default";
		}

	}
}
