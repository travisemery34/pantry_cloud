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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ViewNewUser extends Activity {
	//UI Fields 
	private EditText in_full_name;// = (EditText)findViewById(R.id.edit_full_name);
	private EditText in_username;// = (EditText)findViewById(R.id.edit_username);
	private EditText in_email;// = (EditText)findViewById(R.id.edit_email);
	private EditText in_password;// = (EditText)findViewById(R.id.edit_password);
	private EditText in_passwordX;// = (EditText)findViewById(R.id.edit_password_x);
	private Spinner in_state_spinner;// = (Spinner)findViewById(R.id.state_spinner);
	private EditText in_zipcode;// = (EditText)findViewById(R.id.edit_zipcode);
	private EditText in_city;// = (EditText)findViewById(R.id.edit_city);
	private Button button_create_account;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newaccount);
        
        //Initialize UI fields
        in_full_name = (EditText)findViewById(R.id.edit_full_name);
        in_username = (EditText)findViewById(R.id.edit_username);
        in_email = (EditText)findViewById(R.id.edit_email);
        in_password = (EditText)findViewById(R.id.edit_password);
        in_passwordX = (EditText)findViewById(R.id.edit_passwordX);
        in_zipcode = (EditText)findViewById(R.id.edit_zipcode);
        in_city = (EditText)findViewById(R.id.edit_city);
        in_state_spinner = (Spinner)findViewById(R.id.state_spinner);
        button_create_account = (Button)findViewById(R.id.button_create_account);
       
        //Spinner code, for Spinner Events 
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.city_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        in_state_spinner.setAdapter(adapter);
        in_state_spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        
        button_create_account.setOnClickListener(new OnClickListener(){
        	public void onClick(android.view.View v)
        	{
        		String name  	= in_full_name.getText().toString();
        		String user  	= in_username.getText().toString();
        		String email 	= in_email.getText().toString();
        		String pass  	= in_password.getText().toString();
        		String passX	= in_passwordX.getText().toString();
        		String zip 	 	= in_zipcode.getText().toString();
        		String city  	= in_city.getText().toString();
        		String state 	= in_state_spinner.getSelectedItem().toString();
        		//Checking that passwords match
        		
        		//KNOWN BUG condition is never met, pass never equals passX
        		if( pass.toString() == passX.toString() ){
        			Toast.makeText(getBaseContext(), "Password Mismatch "+pass+", "+passX , Toast.LENGTH_LONG).show();
        		}
        		else{
        			pass = bin2hex(getHash(pass));
        			CreateUser newUser = new CreateUser();
        			newUser.execute(new String[] {name,email,state,city,zip,user,pass});
        		}
        	}
        });
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
    /*
     * Event Handler for spinner selector
     * This is called when the spinner drop-down is selected, and 
     * then Toasts a massage of which state was selected.
     */
    public class MyOnItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
          Toast.makeText(parent.getContext(), "You selected " +
              parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
        }

        public void onNothingSelected(AdapterView parent) {
          // Do nothing.
        }
    }
    
  //Asynchronous Class for HTTP REST Operation
  	private class CreateUser extends AsyncTask<String, Void, String>{
  		private Boolean STATUS;
  		private String result = null;
  		private InputStream is = null;
  		private StringBuilder sb = null;
  		private JSONArray jArray;
  		@Override
  		protected String doInBackground(String... arg0) {
  			String url = "http://nubbins.dyndns-remote.com/grocery/add.php";			
  			try{
  				ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>(7);
  				pairs.add(new BasicNameValuePair("full_name",arg0[0]));
  				pairs.add(new BasicNameValuePair("email"	,arg0[1]));
  				pairs.add(new BasicNameValuePair("state"	,arg0[2]));
  				pairs.add(new BasicNameValuePair("city"		,arg0[3]));
  				pairs.add(new BasicNameValuePair("zipcode"	,arg0[4]));
  				pairs.add(new BasicNameValuePair("username"	,arg0[5]));
  				pairs.add(new BasicNameValuePair("password_new"	,arg0[6]));
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
      			Intent intent = new Intent(ViewNewUser.this, Pantry.class);
      			startActivity(intent);
      		}
      		else{
      			Toast.makeText(getBaseContext(), "Account Creation Failed: "+result , Toast.LENGTH_LONG).show();
      			Intent intent = new Intent(ViewNewUser.this, PantryProjectActivity.class);
      			startActivity(intent);
      		}
  		}
  	}
}