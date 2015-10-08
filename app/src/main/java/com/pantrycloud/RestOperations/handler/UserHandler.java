package com.pantrycloud.RestOperations.handler;

import java.util.concurrent.ExecutionException;

import android.util.Log;

import com.pantrycloud.RestOperations.service.AsyncRestLogin;
import com.pantrycloud.RestOperations.service.AsyncRestSignup;
import com.pantrycloud.RestOperations.utils.RestUserUtils;
import com.pantrycloud.state.userstate.ResponseDataAccount;
import com.pantrycloud.state.userstate.User;

/**
 * 
 * @author temery
 *
 */

public class UserHandler {

	private static final String TAG = "Handler";
	private static final String SOURCE_CLASS = "UserHandler";
	
	/**
	 * Signup a new user using rest service on server
	 * 
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public User userSignup(String username, String password, String firstName, String lastName){
		Log.v(TAG,"userSignup");
		AsyncRestSignup login = new AsyncRestSignup();
		login.execute( new String[] {username, password, firstName, lastName} );
		User newUser = new User();
		try {
			String json = login.get();//service response in json
			ResponseDataAccount response = new ResponseDataAccount();
			response = RestUserUtils.initUserUtil( json );
			newUser = response.getUser();
			if(response.getError() == 0){
				newUser.setAuth(true);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return newUser;
	}
	
	/**
	 * Login the user, and return the string response
	 * 
	 * @param username
	 * @param password
	 */
	public User userLogin(String username, String password){
		Log.v(TAG,"userLogin");
		AsyncRestLogin login = new AsyncRestLogin();
		login.execute( new String[] {username, password} );
		User newUser = new User();
		try {
			String json = login.get();//service response in json
			ResponseDataAccount response = new ResponseDataAccount();
			response = RestUserUtils.initUserUtil( json );
			newUser = response.getUser();
			if(response.getError() == 0){
				newUser.setAuth(true);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return newUser;
	}
}
