package com.pantrycloud.state.userstate;

public class User {

	/*
	{
	    "user": {
	        "username": "temery",
	        "password": "nubbins",
	        "first_name": "Travis",
	        "last_name": "Emery"
	    },
	    "error": "0",
	    "msg": "successful login"
	}
	*/
	
	public String username = "";
	public String password = "";
	public String first_name = "";
	public String last_name = "";
	public boolean auth = false;
	
	public User(){
		
	}
	
	public User(String username, String password, String first_name, String last_name) {
		this.username = username;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password="
				+ password + ", first_name=" + first_name + ", last_name="
				+ last_name + "]";
	}
}
