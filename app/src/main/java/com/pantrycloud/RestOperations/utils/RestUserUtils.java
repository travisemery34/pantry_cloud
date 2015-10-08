package com.pantrycloud.RestOperations.utils;

import com.google.gson.Gson;
import com.pantrycloud.state.userstate.ResponseDataAccount;
import com.pantrycloud.state.userstate.User;

public class RestUserUtils {

	/**
	 * 
	 * @param jsonResponse
	 * @return User object
	 */
	public static ResponseDataAccount initUserUtil(String jsonResponse){
		ResponseDataAccount response = new ResponseDataAccount();
		Gson gson = new Gson();
		response = gson.fromJson(jsonResponse, response.getClass());
		return response;
	}
}
