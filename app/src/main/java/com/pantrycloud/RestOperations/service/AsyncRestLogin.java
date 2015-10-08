package com.pantrycloud.RestOperations.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncRestLogin extends AsyncTask<String, Void, String>{

	private static final String TAG = "AsyncRestLogin";
	private static final String URL = "http://www.emeree.net/pc/account/rest_user_functions.php";
	private static final String ACTION = "?action=login";
	
	@Override
	protected String doInBackground(String... arg0) {
		Log.v(TAG, "doInBackground");
		
		String user = "";
		
		HttpPost request = new HttpPost(URL+ACTION);
        HttpClient httpClient = new DefaultHttpClient();//new http client
        try {
   
        	List<NameValuePair> loginParams = new ArrayList<NameValuePair>(2);
        	loginParams.add(new BasicNameValuePair("username", arg0[0]));
        	loginParams.add(new BasicNameValuePair("password", arg0[1]));
        	request.setEntity(new UrlEncodedFormEntity(loginParams, "UTF-8"));
        	
			HttpResponse response = httpClient.execute(request);//make the request to the URI
			HttpEntity entity = response.getEntity();//get the response content
			
			user = EntityUtils.toString(entity);//convert to string and assign here
			
		} catch (ClientProtocolException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
		Log.v(TAG,"Rest User"+user);
		return user;
	}
	
	protected void onPostExecute(String result){
		Log.v(TAG, "onPostExecute");
	}

}
