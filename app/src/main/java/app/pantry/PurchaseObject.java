package app.pantry;

import java.util.ArrayList;

import android.app.Application;
import android.util.Log;

public class PurchaseObject {

	private String purchaseDate = "";
	private String uniqueId = "";
	private String userID = "";
	private String store = "";
	ArrayList<ShopItem> item = new ArrayList<ShopItem>();
	
	public PurchaseObject(String purchaseDate, String uniqueId, String userID, String store){
		this.purchaseDate = purchaseDate;
		this.uniqueId = uniqueId;
		this.userID = userID;
		this.store = store;
	}

	public PurchaseObject(){
		
	}
	
	public void setupPurchase(String purchaseDate, String uniqueId, String userID, String store){
		this.purchaseDate = purchaseDate;
		this.uniqueId = uniqueId;
		this.userID = userID;
		this.store = store;
	}
	
	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public void newItem( String upc, String brand, String description,
			String expirationDate, String category, String price,
			String quantity ){
		Log.v("POBJ","pDate: "+purchaseDate+"\nUID "+uniqueId+"\nUser "+userID+"\nStore "+store);
		ShopItem shopItem = new ShopItem(upc, brand, description, expirationDate, category, price, quantity);
		
		item.add(shopItem);
	}
	
	public ArrayList<ShopItem> getItems(){
		return item;
	}
	
	public void emptyList(){
		item.clear();
	}
}
