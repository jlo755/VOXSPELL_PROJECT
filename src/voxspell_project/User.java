package voxspell_project;

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
