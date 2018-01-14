package sodabase.ui;

public class SodaByRestaurant {

	private String restaurantName;
	private String sodaName;
	private String manufacturer;
	private String restaurantContact;
	private double price;
	
	public SodaByRestaurant(String restName, String sodaName, String manf, String restContact, double price){
		this.restaurantName = restName;
		this.sodaName = sodaName;
		this.manufacturer = manf;
		this.restaurantContact = restContact;
		this.price = price;
	}
	
	//TODO: refactor this
	public String getValue(String propertyName) {
		switch (propertyName) {
			case "Restaurant":
				return this.restaurantName;
			case "Soda":
				return this.sodaName;
			case "Manufacturer":
				return this.manufacturer;
			case "RestaurantContact":
				return this.restaurantContact;
			case "Price":
				return this.price + "";
		}
		return null;
	}
	
	
	public String getRestaurantName() {
		return restaurantName;
	}
	public String getSodaName() {
		return sodaName;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public String getRestaurantContact() {
		return restaurantContact;
	}
	public double getPrice() {
		return price;
	}		
	
}