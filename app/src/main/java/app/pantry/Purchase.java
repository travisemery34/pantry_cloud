	package app.pantry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Purchase extends Activity{
	
	//Fields
	private Button add;
	private Button getUPC;
	private Button finishPurchase;
	private EditText editUPC;
	private EditText desc;
	private EditText quant;
	private EditText priceBox;
	private EditText brandBox;
	private Spinner category;
	private DatePicker expireDatePicker;
	private String UPC 			= "";
	private String description	= "";
	private String quantity 	= "";
	private String brand 		= "";
	private String eDate 		= "";
	private String cat 			= "";
	private String price 		= "";
	private String store 		= "";
	private String date 		= "";
	private String data 		= "";
	
	public static PurchaseObject purchaseObject = Navigation.pobj;

	public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.purchase);
	       
	        add 			= (Button) findViewById(R.id.add);
	        getUPC 			= (Button) findViewById(R.id.getUPC);
	        editUPC  		= (EditText) findViewById(R.id.editUPC);
	        finishPurchase 	= (Button)findViewById(R.id.finishPurchase);
	        priceBox 		= (EditText)findViewById(R.id.priceBox);
	        brandBox 		= (EditText)findViewById(R.id.brandBox);
	        desc 			= (EditText)findViewById(R.id.editText1);
	        quant 			= (EditText)findViewById(R.id.quantity);
	        category 		= (Spinner) findViewById(R.id.catList);
	        expireDatePicker= (DatePicker)findViewById(R.id.expireDate);
	        
	        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        category.setAdapter(adapter);
	        //Event Listeners
	        addGetUPCListener();
	        addAddButtonListener();
	        addFinishButtonListener();
	 }

	public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK) {
                Intent Act2Intent = new Intent(this, Navigation.class);              
                startActivity(Act2Intent);          
                finish();
                return true;
        }
        return false;
    }
	 
	 /*
	  * UPC Functionality
	  * (non-Javadoc)
	  * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	  */
	 public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		 IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		 if (scanResult != null) { UPC = scanResult.getContents(); }
		 editUPC.setText(UPC);
		 loadMetaData();
	 }
	 
	 
	 /*
	  * 
	  */
	 public void addGetUPCListener(){
		 getUPC = (Button)findViewById(R.id.getUPC);
		 getUPC.setOnClickListener(new OnClickListener(){
		     	public void onClick(android.view.View v){
		     		IntentIntegrator in = new IntentIntegrator(Purchase.this);
					in.initiateScan();
		     	}
		   });
	 }
	 
	 public String getDate(){
		 Integer month 	= expireDatePicker.getMonth() + 1;
		 Integer day 	= expireDatePicker.getDayOfMonth();
		 Integer year 	= expireDatePicker.getYear();
		 String expireDate = ""+year+"-"+month+"-"+day+"";
		 return expireDate;
	 }
	 
	 /*
	  * 
	  */
	 public void addAddButtonListener(){
		 add.setOnClickListener(new OnClickListener(){
			 public void onClick(android.view.View v){
				UPC = editUPC.getText().toString();
				description = desc.getText().toString();
				quantity 	= quant.getText().toString();
				brand 		= brandBox.getText().toString();
				eDate		= getDate();
				price 		= priceBox.getText().toString();
				cat = category.getSelectedItem().toString();
				
				if( checkFields(UPC, brand, description, eDate, cat, price, quantity) ){
					addItem(UPC, brand, description, eDate, cat, price, quantity);
					Toast.makeText(getBaseContext(), "Added" , Toast.LENGTH_LONG).show();
					//Intent intent = new Intent(Purchase.this, Purchase.class);
					//startActivity(intent);					 
				}
				else{
					Toast.makeText(getBaseContext(), "Missing field data" , Toast.LENGTH_LONG).show();
				}
	    	 }
	       });
	 }
	 
	 /*
	  * 
	  */
	 private void addItem(String upc, String brand, String description,String expirationDate, String category, String price,String quantity){
		 purchaseObject.newItem(upc, brand, description, expirationDate, category, price, quantity);
	 }
	 
	 /*
	  * TRYING TO REFACTOR THIS DEALIN WITH SUBMIT
	  */
	 public void addFinishButtonListener(){
		 finishPurchase.setOnClickListener(new OnClickListener(){
			 public void onClick(android.view.View v){
				 Intent intent = new Intent(Purchase.this, Navigation.class);
				 startActivity(intent);
	    	 }
	       });
	 }
	 
	 /*
	  * 
	  */
	 private boolean checkFields(String upc, String bnd, String desc, String eD, String category, String pc, String qty){
		 if(upc.isEmpty() || bnd.isEmpty() || desc.isEmpty() || eD.isEmpty() || category.isEmpty() || pc.isEmpty() || qty.isEmpty()){
			 return false;
		 }
		 return true;
	 }
	 
	 /*
	  * 
	  */
	 public void loadMetaData(){
		 Controller controller = new Controller();
		 String[] json = controller.getMetaData( new String[] {"lookup", UPC} );
		 description = json[0];
		 quantity = json[1];
		 updateFields();
		 Log.v("META", description+" : "+quantity);
	 }
	 /*
	  * 
	  */
	 private void updateFields()
	 {
		String noHTMLStringD = description.replaceAll("\\<.*?>","");
		description = noHTMLStringD;
		desc.setText(noHTMLStringD);
		String noHTMLStringQ = quantity.replaceAll("\\<.*?>","");
		quantity = noHTMLStringQ;
		quant.setText(noHTMLStringQ);
		Log.v("RESULT", "D: " +noHTMLStringD);
		Log.v("RESULT", "Q: " +noHTMLStringQ);
	 }
	 
	 /*
	  * 
	  */
	 public void print(){
		 Log.v("DUMP", "UPC "+UPC+"\nBrand "+brand+"\nDesc "+description+"\nEDate "+eDate+"\nCate "+cat+"\nPrice "+price+"\nQuant "+quantity);
		 //Log.v("DUMP", "\nUSER "+username+"\nStore "+store+"\nDate "+date+"\nUID "+uniqueID);
	 }
	 
}//END CLASS
