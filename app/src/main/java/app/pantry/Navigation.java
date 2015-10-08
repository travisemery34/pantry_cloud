package app.pantry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

/*
 * 
 */
public class Navigation extends Activity {
	private Button newPurchase;
	private Button fridgeButton;
	private Button submitButton;
	private Button recipeButton;
	private Spinner storeArray;
	private String  location = "nav";
	
	Button submitOrder;
	
	Button next;
	Button button1;
	String contents;
	TextView code;
	DatePicker date;
	
	//Third View Properties
	private TableLayout purchaseTable;
	
	public static  PurchaseObject pobj = new PurchaseObject();
	private ViewFlipper switcher;
	//private ViewSwitcher switcher;
	
	List<String> stores = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.navigation);
	    	    
	    switcher = (ViewFlipper) findViewById(R.id.profileSwitcher);
	    //switcher = (ViewSwitcher) findViewById(R.id.profileSwitcher);
	    newPurchase = (Button)findViewById(R.id.newPurchase);
	    fridgeButton = (Button)findViewById(R.id.fridgeList);
	    recipeButton = (Button)findViewById(R.id.recipeList);
	    submitButton = (Button)findViewById(R.id.submit);	    
        addListenerOnButton();
        date = (DatePicker) findViewById(R.id.purchaseDate);
        
        submitOrder = new Button(this);
        
        //Third View - Purchased List
        purchaseTable = (TableLayout)findViewById(R.id.purchaseList);
        
        
        //Get Store List from Controller
        String[] list = updateStoreList();
        storeArray 	 = (Spinner) findViewById(R.id.storeArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeArray.setAdapter(adapter);
        
        //Action Listeners
	    newPurchase.setOnClickListener(new OnClickListener(){ public void onClick(android.view.View v){ createNewPurchase();}});
	    fridgeButton.setOnClickListener(new OnClickListener(){ public void onClick(android.view.View v){ getFridge();       }});
	    recipeButton.setOnClickListener(new OnClickListener(){ public void onClick(android.view.View v){ getRecipeList();   }});
	    submitButton.setOnClickListener(new OnClickListener(){ public void onClick(android.view.View v){ submitData();      }});
	    submitOrder.setOnClickListener(new OnClickListener(){ public void onClick(android.view.View v){ submitPurchase();      }});
	}//end onCreate
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(location == "nav"){
			if(keyCode == KeyEvent.KEYCODE_BACK) {
	            Intent Act2Intent = new Intent(this, PantryProjectActivity.class);              
	            startActivity(Act2Intent);          
	            finish();
	            return true;
			}
			return false;
		}
		else{
	        if(keyCode == KeyEvent.KEYCODE_BACK) {
	                Intent Act2Intent = new Intent(this, Navigation.class);              
	                startActivity(Act2Intent);          
	                finish();
	                return true;
	        }
	        return false;
		}
    }
	
	/*
	 * 
	 */
	private void createNewPurchase()
	{	
		location = "newPurchase";
		switcher.showNext();
	}
	
	/*
	 * 
	 */
	private void getFridge()
	{
		Intent intent = new Intent(this, Pantry.class);              
        startActivity(intent); 
	}
	
	private void getRecipeList()
	{
	}
	
	/*
	 * 
	 */
	private void submitData()
	{	
		
		location = "newPurchase";
		reviewOrder();
		//Provide options to delete items...or update(harder)
		
		//allow user to submit the purchase		
		//submitPurchase();
	}
	
	static int i = 0;
	
	public void reviewOrder(){
		i+=5;
		switcher.setDisplayedChild(2);
		
		//switcher.showNext();
		Resources res = this.getResources();
		//build list of items
		
		
		//Simulate Purchase
		/*
		pobj.setPurchaseDate("2012-05-1");
		pobj.setStore("Wegmans");
		pobj.newItem("11", "Brand"+i, "Desc"+i, "2012-07-01", "Dairy", i+"2.89", "1oz");
		pobj.newItem("22", "Brand1"+i, "Desc1"+i, "2012-07-01", "Dairy", i+"2.69", "1oz");
		pobj.newItem("33", "Brand2"+i, "Des2"+i, "2012-07-01", "Dairy", i+"2.19", "1oz");
		*/
		String date = "";
		String purchaseDate = pobj.getPurchaseDate();
		
		for(int i=0; i<pobj.getItems().size(); i++){
			//pobj.getItems();
			//Check the data making sure it is unique
			if( !date.equals(purchaseDate) ){
				//set old date to equal new date
				date = purchaseDate;
				
				//Colored Header Row
				TableRow dateRow = new TableRow(this);
				dateRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
				
	            //Date
				TextView newDate = new TextView(this);
	            newDate.setText("Date: "+date);
	            newDate.setTextSize(20);
	            newDate.setTextColor(res.getColor(android.R.color.white));
	            newDate.setPadding(5, 0, 0, 0);
	            
	            //Store 
	            TextView storeName = new TextView(this);
	            storeName.setText(pobj.getStore());
	            storeName.setTextSize(20);
	            storeName.setTextColor(res.getColor(android.R.color.white));
	            storeName.setPadding(5, 0, 0, 0);
	            
	            dateRow.setBackgroundColor(Color.parseColor("#6792b7"));
	            dateRow.addView(storeName);
	            dateRow.addView(newDate);
	            dateRow.setId(2);
	            purchaseTable.addView(dateRow, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT) );
	            
			}
			
			//Outer Table
			TableRow tableRow = new TableRow(this);
			tableRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
				//Inner Table
		    	TableLayout tableLayout = new TableLayout(this);//create the table
		    	
		    	TableRow dataTableDesc = new TableRow(this);//create a row
		    	dataTableDesc.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		    	//TableRow dataTableBrand = new TableRow(this);//create another row
		    	//dataTableBrand.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		    	
		        // BRAND
		        TextView textBrand = new TextView(this);
		        textBrand.setText(pobj.item.get(i).getBrand());
		        textBrand.setTextSize(18);
		        textBrand.setTextColor(Color.parseColor("#484746"));
		        textBrand.setPadding(5, 5, 10, 5);
		        dataTableDesc.addView(textBrand);//add to first row
		
		    	//Add first row to table
		    	tableLayout.addView(dataTableDesc, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT) );
		    	//DONE WITH INNER TABLE
		    	
		    	//add inner table to outer table
		    	tableRow.addView(tableLayout);
	    	
		    	// Desc
		    	TextView textDesc = new TextView(this);
		    	textDesc.setText(pobj.item.get(i).getDescription());
		    	textDesc.setTextSize(14);
		    	textDesc.setPadding(0, 5, 30, 0);
		    	textDesc.setTextColor(Color.parseColor("#484746"));
		    	tableRow.addView(textDesc);
		    	
		    	// PRICE
		    	TextView textPrice = new TextView(this);
		    	textPrice.setText("$"+pobj.item.get(i).getPrice());
		    	textPrice.setTextSize(14);
		    	textPrice.setPadding(0, 5, 30, 0);
		    	textPrice.setTextColor(Color.parseColor("#879c67"));
		    	tableRow.addView(textPrice);
		    	
		    	
		    	
		    //Complete Outer Table
		    purchaseTable.addView(tableRow, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT) );
	    	//purchaseTable.addView(tableRow );
		}
		TableRow tableRowButton = new TableRow(this);
		tableRowButton.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		//Add Submit Button
		
		
		//submitOrder.setBackgroundResource(R.drawable.submit);
		submitOrder.setWidth(200);
		submitOrder.setHeight(80);
		submitOrder.setText("Submit Order");
		submitOrder.setPadding(5, 5, 5, 10);
		submitOrder.setTextSize(20);
		submitOrder.setTextColor(Color.parseColor("#000000"));
		tableRowButton.addView(submitOrder, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		
		purchaseTable.addView(tableRowButton );
	}
	
	public String[] updateStoreList() {
		ArrayList<String> list = new ArrayList<String>();
		String[] sArray = new String[list.size()];
		Controller cont = new Controller();
		String str = "";
		try {
			str = cont.getStoreList();
		} 
		catch (InterruptedException e) { e.printStackTrace();} 
		catch (ExecutionException e) { e.printStackTrace(); }
		try { 
			JSONArray data = new JSONArray(str);
			JSONObject job = data.getJSONObject(0);
			JSONArray name = new JSONArray(job.getString("list") );
			for(int i=0; i<name.length(); i++){
				JSONObject obj = name.getJSONObject(i);
				list.add(obj.getString("name"));
				Log.v("LIST", obj.getString("name") );
			}
		} 
		catch (JSONException e) { e.printStackTrace(); }
		sArray = list.toArray(sArray);
		return sArray;
	}
	
	/*
	 * Below are functions for newPurchase View
	 */
	//Action Listener for 
	public void addListenerOnButton() {
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Integer month = date.getMonth() + 1;
				String setDate = ""+date.getYear()+"-"+month+""+"-"+date.getDayOfMonth()+"";
				String setStore = storeArray.getSelectedItem().toString();
				String username = PantryProjectActivity.username.getText().toString();
				String uniqueId = "1";
				Log.v("DUMP", setDate+", "+setStore+", "+username+", "+uniqueId);
				if(setDate.isEmpty() || uniqueId.isEmpty() || username.isEmpty() || setStore.isEmpty()){
					Toast.makeText(getBaseContext(), "Invalid Purchase Setup, select store, and date." , Toast.LENGTH_LONG).show();
				}
				else{
					//pobj = new PurchaseObject();
					pobj.setupPurchase(setDate, uniqueId, username, setStore);
					Intent intent = new Intent(getApplicationContext(), Purchase.class);
					startActivity(intent);
				}
			}
		});
	 }
	
	 /*
	  * 
	  */
	 public void submitPurchase(){
		 if(pobj.getItems().isEmpty()){
			 Toast.makeText(getBaseContext(), "No Purchase to submit." , Toast.LENGTH_LONG).show(); 
		 }
		 else{
			 Controller controller = new Controller();
			 for(int i=0; i < pobj.item.size(); i++){
				 Log.v("ITEM: ", "\nItem: "+i+": "+pobj.item.get(i).getUpc()+"\nDesc "+pobj.item.get(i).getDescription());
				 controller.newInsert( new String[] {
						 "submitPurchase",
						 pobj.getStore(),
						 pobj.item.get(i).getUpc(),
						 pobj.item.get(i).getPrice(),
						 pobj.getPurchaseDate(),
						 pobj.getUserID(),
						 pobj.getUniqueId(),
						 pobj.item.get(i).getExpirationDate(),
						 pobj.item.get(i).getDescription(),
						 pobj.item.get(i).getQuantity(),
						 pobj.item.get(i).getBrand(),
						 pobj.item.get(i).getCategory()
				 		});
			 }
			 // for use when an order has been submitted, 
			 // destroy the object, so one cannot re-submit
			 // the same order again
			 pobj.item.clear();
			 Intent intent = new Intent(getApplicationContext(), Navigation.class);
			 startActivity(intent);
		 }
	 }
	
}//end class