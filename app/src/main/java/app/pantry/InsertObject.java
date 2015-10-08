package app.pantry;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import android.os.AsyncTask;
import android.util.Log;

public class InsertObject {
	
	Boolean STATUS = true;
	
	public void InsertObject(){
		
	}
	
	public void newInsert(String[] strings){
		SubmitPurchase submit = new SubmitPurchase();
		submit.execute(strings);
	}
	
	
	private class SubmitPurchase extends AsyncTask<String, Void, String>{
		private InputStream is = null;
		private StringBuilder sb = null;
		private String result = null;
		private JSONArray jArray;
		@Override
		protected String doInBackground(String... arg0) {
			Log.v("ASYNC1", "Working");
			String url = "http://nubbins.dyndns-remote.com/grocery/crunch.php";
			try{
				
				ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>(1);
				pairs.add(new BasicNameValuePair("use",arg0[0]));
				pairs.add(new BasicNameValuePair("store",arg0[1]));		
				pairs.add(new BasicNameValuePair("upc",arg0[2]));
				pairs.add(new BasicNameValuePair("price",arg0[3]));
				pairs.add(new BasicNameValuePair("date",arg0[4]));
				pairs.add(new BasicNameValuePair("uname",arg0[5]));
				pairs.add(new BasicNameValuePair("unqID",arg0[6]));
				pairs.add(new BasicNameValuePair("eDate",arg0[7]));
				pairs.add(new BasicNameValuePair("desc",arg0[8]));
				pairs.add(new BasicNameValuePair("quant",arg0[9]));
				pairs.add(new BasicNameValuePair("brand",arg0[10]));
				pairs.add(new BasicNameValuePair("category",arg0[11]));
				
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
				Log.v("JSON", "before");
				jArray = new JSONArray(result);
				Integer error_code;
				JSONObject json_data = null;
				json_data = jArray.getJSONObject(0);
				error_code = json_data.getInt("error");
				String msg = json_data.get("msg").toString();
				
				if(error_code == 1){
					STATUS = false;
					Log.v("JSON", "Data not found: "+msg);
					result = "Insert Failed";
					return result;
				}
				else{
					Log.v("JSON", "good: "+msg);
					STATUS = true;
					return result;
				}
			}
			catch(JSONException e){
				String tempString = e.toString();
				//Toast.makeText(getApplicationContext(), "No name found " + tempString, Toast.LENGTH_LONG).show();
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
			Log.v("ASYNC", "DONE");
			//Toast.makeText(getApplicationContext(), "Submitted Succesfully", Toast.LENGTH_LONG).show();
			//successfull();
			
		}
	}//END SUBMIT CLASS	
}
