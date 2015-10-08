package com.pantrycloud.state.userstate;

import android.util.Log;

/**
 * 
 * @author temery
 *
 */
public class ResponseDataAccount {

	//fields
	private User user;
	private Integer error;
	private String msg;

	/**
	 * 
	 */
	public ResponseDataAccount(){
		
	}
	
	/**
	 * 
	 * @param user
	 * @param error
	 * @param msg
	 */
	public ResponseDataAccount(User user, Integer error, String msg) {
		this.setUser(user);
		this.setError(error);
		this.setMsg(msg);
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Integer getError() {
		return error;
	}


	public void setError(Integer error) {
		this.error = error;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}

	
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

	@Override
	public boolean equals(Object o) {
		ResponseDataAccount rd = (ResponseDataAccount)o;
		if(rd == null){
			return false;
		}
		Log.v("equals", "rd: "+rd.getUser().toString()+", o: "+this.getUser().toString());
		if( rd.getUser().getUsername().equals(this.getUser().getUsername()) ){
			return true;
		}
		return false;
	}


	@Override
	public String toString() {
		return "ResponseDataAccount [user=" + user.toString() + ", error=" + error + ", msg="
				+ msg + "]";
	}
}
