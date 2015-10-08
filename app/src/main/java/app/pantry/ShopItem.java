package app.pantry;

public class ShopItem {

	private String upc;
	private String brand;
	private String description;
	private String expirationDate;
	private String category;
	private String price;
	private String quantity;
	
	public ShopItem(String upc, String brand, String description,
			String expirationDate, String category, String price,
			String quantity) {
		super();
		this.upc = upc;
		this.brand = brand;
		this.description = description;
		this.expirationDate = expirationDate;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
}
