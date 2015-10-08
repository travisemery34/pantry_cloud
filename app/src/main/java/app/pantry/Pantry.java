package app.pantry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class Pantry extends Activity{
	
	private TableLayout table;
	
	 public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);	 
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pantry);
        table = (TableLayout)findViewById(R.id.tableItems);
        //END ACTIVITY FORMATTING
        
        
		Controller cont = new Controller();
		String str = "";
		str = cont.getPantryList();
		try { 
			JSONArray data = new JSONArray(str);
			JSONObject job = data.getJSONObject(0);
			JSONArray name = new JSONArray( job.getString("list") );
			Resources res = this.getResources();
			
			String date = "";
			//LOOP FOR BUILDING THE TABLE ELEMENTS
			for(int i=0; i<name.length(); i++){
				JSONObject obj = name.getJSONObject(i);
				
				String brand	= obj.getString("brand");
				String desc 		= obj.getString("description");
				String price 	= obj.getString("price");
				String purchaseDate = obj.getString("date");
				
				if( !date.equals(purchaseDate) ){
					//set old date to equal new date
					date = purchaseDate;
					//add new row
					TableRow dateRow = new TableRow(this);
					dateRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		            //add text to row
					TextView newDate = new TextView(this);
		            newDate.setText("Date: "+date);
		            newDate.setTextSize(20);
		            newDate.setTextColor(res.getColor(android.R.color.white));
		            newDate.setPadding(5, 0, 0, 0);
		            dateRow.setBackgroundColor(Color.parseColor("#6792b7"));
		            dateRow.addView(newDate);
		            dateRow.setId(2);
		            table.addView(dateRow, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT) );
		            
				}
				
				TableRow tableRow = new TableRow(this);
				tableRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
				
	            	
            	TableLayout tableLayout = new TableLayout(this);//create the table
            	
            	TableRow dataTableDesc = new TableRow(this);//create a row
            	dataTableDesc.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
            	TableRow dataTableBrand = new TableRow(this);//create another row
            	dataTableBrand.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
            	
            	
	            // BRAND
	            TextView textBrand = new TextView(this);
	            textBrand.setText(brand);
	            textBrand.setTextSize(18);
	            textBrand.setTextColor(Color.parseColor("#484746"));
	            textBrand.setPadding(5, 5, 10, 5);
	            dataTableDesc.addView(textBrand);//add to first row
	        	// Description
	        	TextView textDesc = new TextView(this);
	        	textDesc.setText(desc);
	        	textDesc.setTextSize(12);
	        	textDesc.setTextColor(Color.parseColor("#484746"));
	        	textDesc.setPadding(10,0,10,0);
	        	dataTableBrand.addView(textDesc);//add to second row
	        	
	        	//Add first row to table
	        	tableLayout.addView(dataTableDesc, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT) );
	        	//Add second row to table
	        	tableLayout.addView(dataTableBrand, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT) );
	        	//DONE WITH INNER TABLE
	        	
	        	//add inner table to outer table
	        	tableRow.addView(tableLayout);
	        	
	        	// PRICE
	        	TextView textPrice = new TextView(this);
	        	
	        	textPrice.setText("$"+price);
	        	textPrice.setTextSize(14);
	        	textPrice.setPadding(0, 5, 30, 0);
	        	textPrice.setTextColor(Color.parseColor("#879c67"));
	        	tableRow.addView(textPrice);
	        	table.addView(tableRow, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT) );
			}
		} 
		catch (JSONException e) { e.printStackTrace(); }
	 }
 }



