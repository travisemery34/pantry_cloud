package app.pantry;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
import android.os.AsyncTask;
import android.util.Log;


public class Controller extends Activity{

	public String getPantryList() {
		//Return from these functions are a JSON string
		PantryList pantryList = new PantryList();
		pantryList.execute(new String[] {"pantry"});
		String str = "";
		try {
			str = pantryList.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		Log.v("CONTROLLER", str);
		return str;
	}

	
	//Asynchronous Class for HTTP REST Operation
	private class PantryList extends AsyncTask<String, Void, String>{
		
		private InputStream is = null;
		private StringBuilder sb = null;
		private String result = null;
		private JSONArray jArray;
		@Override
		protected String doInBackground(String... arg0) {
			Log.v("Getting List", "Working ");
			String url = "http://pantrycloud.com/crunch.php";
			try{
				ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>(1);
				pairs.add(new BasicNameValuePair("use",arg0[0]));
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
				if(error_code == 1){
					STATUS = false;
					Log.v("JSON", "Data not found");
					return result;
				}
				else{
					Log.v("JSON", result);
					STATUS = true;
					return result;
				}
			}
			catch(JSONException e){
				String tempString = e.toString();
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
		}
	}
	

	public String getStoreList() throws InterruptedException, ExecutionException{
		PopulateStoreList storeList = new PopulateStoreList();
		storeList.execute(new String[] {"storeList"});
		String str = storeList.get();
		Log.v("CONTROLLER", str);
		return str;
	}
	
	//Asynchronous Class for HTTP REST Operation
	private class PopulateStoreList extends AsyncTask<String, Void, String>{
		
		private InputStream is = null;
		private StringBuilder sb = null;
		private String result = null;
		private JSONArray jArray;
		@Override
		protected String doInBackground(String... arg0) {
			Log.v("Getting List", "Working ");
			String url = "http://pantrycloud.com/crunch.php";
			try{
				ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>(1);
				pairs.add(new BasicNameValuePair("use",arg0[0]));
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
				if(error_code == 1){
					STATUS = false;
					Log.v("JSON", "Data not found");
					return result;
				}
				else{
					Log.v("JSON", result);
					STATUS = true;
					return result;
				}
			}
			catch(JSONException e){
				String tempString = e.toString();
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
		}
	}
	
	Boolean STATUS = true;
	
	public Boolean newInsert(String[] strings){
		SubmitPurchase submit = new SubmitPurchase();
		submit.execute(strings);
		if(STATUS){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	private class SubmitPurchase extends AsyncTask<String, Void, String>{
		private InputStream is = null;
		private StringBuilder sb = null;
		private String result = null;
		private JSONArray jArray;
		@Override
		protected String doInBackground(String... arg0) {
			
			
			
			Log.v("ASYNC1", "Working");
			String url = "http://pantrycloud.com/crunch.php";
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
	
	
	/*
	 * 
	 */
	
	public String[] getMetaData(String[] strings) {
		GetMetaData metaData = new GetMetaData();
		metaData.execute(strings);
		JSONArray job = null;
		JSONObject data = null;
		try {
			job = new JSONArray( metaData.get() );
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			data = job.getJSONObject(0);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String desc = "";
		String quant = "";
		try {
			desc = data.getString("desc");
			quant = data.getString("quantity");
		} catch (JSONException e) { e.printStackTrace(); }
		String[] str ={ desc, quant };
		return str;
	}
	
	//Asynchronous Class for HTTP REST Operation
	private class GetMetaData extends AsyncTask<String, Void, String>{
		
		private InputStream is = null;
		private StringBuilder sb = null;
		private String result = null;
		private JSONArray jArray;
		@Override
		protected String doInBackground(String... arg0) {
			Log.v("ASYNC", "Working ");
			Log.v("TEST", "Data: "+arg0[0]+" : "+arg0[1]);
			String url = "http://pantrycloud.com/crunch.php";
			try{
				
				ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>(1);
				pairs.add(new BasicNameValuePair("use",arg0[0]));
				pairs.add(new BasicNameValuePair("upc",arg0[1]));
				Log.v("TEST", "Data: "+arg0[0]+" : "+arg0[1]);
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
				if(error_code == 1){
					STATUS = false;
					Log.v("JSON", "Data not found");
					json_data.put("desc", "N/A");
					json_data.put("quantity", "N/A");
					return result;
				}
				else{
					Log.v("JSON", "good");
					STATUS = true;
					return result;
				}
			}
			catch(JSONException e){
				String tempString = e.toString();
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
			//updateFields();
			
		}
	}

}
