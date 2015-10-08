package app.pantry;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pantrycloud.RestOperations.handler.UserHandler;
import com.pantrycloud.state.userstate.User;

/*
 * Travis Emery (emeryte)
 */
public class PantryProjectActivity extends Activity{
	
	
	//Declare My UI elements
	// such as the Buttons, or text area
	private Button button_login;
	private Button button_new_account;
	public static EditText username;
	private EditText password;
	private TextView textView;
    private Boolean STATUS = false;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        //Initialize by casting the findViewById 
        // to a button object
        button_login = (Button)findViewById(R.id.button_login);
        button_new_account = (Button)findViewById(R.id.button_new_account);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        
        
        //Create a listener for the button and
        // assign a function to be called when
        // the button is pressed. 
        button_login.setOnClickListener(new OnClickListener(){
        	public void onClick(android.view.View v)
        	{
        		user_login();
        	}
        });
        
        // ActionListener for new_account button
        button_new_account.setOnClickListener(new OnClickListener(){
        	public void onClick(android.view.View v)
        	{
        		new_user();
        	}
        });
    }
	
	public void user_login(){
		String uname = username.getText().toString();
		String pword = password.getText().toString();
		pword = bin2hex(getHash( pword ));
		
		//UserHandler uh = new UserHandler();
		//User user = uh.userLogin(uname, pword);
		
		//Controller cont = new Controller();
		//cont.getLogin(uname, pword, PantryProjectActivity.this);
		
		//UNCOMMENT
		//CheckLogin login = new CheckLogin();
		//login.execute(new String[] {uname, pword});
		//Log.v("user_login", "Login: "+user.toString());
		//if(user.isAuth()){
			Intent intent = new Intent(getBaseContext(), Navigation.class);
			startActivity(intent);
		//}
	}
	
	/*
	 * This is called from an actionListener and
	 * will bring the view to a new page where the
	 * user can create an account. 
	 */
	public void new_user(){
		Intent intent = new Intent(PantryProjectActivity.this, ViewNewUser.class);
		startActivity(intent);
	}
	
	
	public byte[] getHash(String password) {
	       MessageDigest digest=null;
	    try {
	        digest = MessageDigest.getInstance("SHA-256");
	    } catch (NoSuchAlgorithmException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	    }
	       digest.reset();
	       return digest.digest(password.getBytes());
	 }
	static String bin2hex(byte[] data) {
	    return String.format("%0" + (data.length*2) + "X", new BigInteger(1, data));
	}
	
	
	//Asynchronous Class for HTTP REST Operation
	private class CheckLogin extends AsyncTask<String, Void, String>{
		//private Boolean STATUS;
		private InputStream is = null;
		private StringBuilder sb = null;
		private String result = null;
		private JSONArray jArray;
		@Override
		protected String doInBackground(String... arg0) {
			String url = "http://nubbins.dyndns-remote.com/grocery/log.php";
			try{
				ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>(2);
				pairs.add(new BasicNameValuePair("username",arg0[0]));
				pairs.add(new BasicNameValuePair("password",arg0[1]));
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost( url );
				httpPost.setEntity(new UrlEncodedFormEntity(pairs));
				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
			}
			catch(Exception e){
				Log.e("log_tag", "Error in HTTP connection "+e.toString());
				STATUS = false;
			}
			try{
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
				sb= new StringBuilder();
				sb.append(reader.readLine() + "\n");
				String line = "0";
				while( (line = reader.readLine() ) != null){
					sb.append(line + "\n");
				}
				is.close();
				result = sb.toString();
			}
			catch(Exception e){
				Log.e("log_tag", "Error converting result "+e.toString());
				STATUS = false;
			}
			try{
				jArray = new JSONArray(result);
				Integer error_code;
				JSONObject json_data = null;
				json_data = jArray.getJSONObject(0);
				error_code = json_data.getInt("error");
				if(error_code == 1){
					STATUS = false;
					return result;
				}
				else{
					STATUS = true;
					return result;
				}
			}
			catch(JSONException e){
				String tempString = e.toString();
				Toast.makeText(getApplicationContext(), "No name found " + tempString, Toast.LENGTH_LONG).show();
				STATUS = false;
			}
			catch(Exception e){
				e.printStackTrace();
				STATUS = false;
			}
			STATUS = true;
			return result;
		}
		protected void onPostExecute(String result){
			if( STATUS ){
    			Intent intent = new Intent(getBaseContext(), Navigation.class);
    			startActivity(intent);
    		}
    		else{
    			Toast.makeText(getBaseContext(), "Login Failed" , Toast.LENGTH_LONG).show();
    		}
		}
	}
}

//Extras

//Log.v("JSON",":::"+error_code);
/*
for(int i = 0; i < jArray.length(); i++){
	json_data = jArray.getJSONObject(i);
	name = json_data.getString("error");
}
*/



/*
try{
	textView = (TextView) findViewById(R.id.TextView01);
	textView.setText(result);
}
catch(Exception e){
	e.printStackTrace();
}




//Get the value from a JSON object
 			String temp = null;
			try {
				temp = jArray.getJSONObject(0).getString("full_name");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Log.v("Tag", temp);




//external function for better management.
public boolean user_login(String uname, String pword){
	CheckLogin login = new CheckLogin();
	login.execute(new String[] {uname, pword});
	//Parse Data
	String name;
	try{
		jArray = new JSONArray(result);
		JSONObject json_data = null;
		for(int i = 0; i < jArray.length(); i++){
			json_data = jArray.getJSONObject(i);
			name = json_data.getString("full_name");
		}
	}
	catch(JSONException e){
		Toast.makeText(getBaseContext(), "No name found", Toast.LENGTH_LONG).show();
	}
	catch(Exception e){
		e.printStackTrace();
	}
	return true;
}//END userLogin
*/

