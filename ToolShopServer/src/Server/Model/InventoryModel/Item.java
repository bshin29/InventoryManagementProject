package Server.Model.InventoryModel;

/**
 * Class name: Item
 * 
 * This class represents an item that is sold at the shop. Each item object contains information
 * such as the item ID number, the item name, the quantity of the item left in stock, the price
 * of the item, and the item's supplier's information. This class also shares the responsibility
 * of automatically generating an order for an item if its quantity falls below the minimum
 * allowable stock (40).
 * 
 * @author: Branden Shin
 * @since: Nov 25th, 2020
 */
public class Item {
	/**
	 * Variable used to define the item ID number
	 */
	protected int itemId;
	/**
	 * Variable used to define the item's name
	 */
	protected String itemName;
	/**
	 * Variable used to define the quantity of the item in stock
	 */
	protected int itemQuantity;
	/**
	 * Variable used to define the items price
	 */
	protected double itemPrice;
	/**
	 * Boolean variable that keeps track of whether or not the item has been ordered today. 
	 * False if has not been ordered today, true if it has. This prevents the same item from being
	 * ordered twice in one day. 
	 */
	private boolean orderedToday;
	/**
	 * Variable used to define the item's supplier
	 */
	protected Supplier itemSupplier;
	
	/**
	 * Variable used to define the item's type
	 */
	protected String itemType;
	
	//Constructor
	/**
	 * This constructor constructs an Item object by setting the item's id, name, quantity, price
	 * and supplier. This information is provided in the items.txt file. 
	 * 
	 * @param itemId the item ID number
	 * @param itemName the item's name
	 * @param quantity the quantity of the item in stock
	 * @param price the items price
	 * @param supplier the item's supplier
	 */
	public Item(int itemId, String itemName, int quantity, double price, Supplier supplier) {
		setItemId(itemId);
		setItemName(itemName);
		setItemQuantity(quantity);
		setItemPrice(price);
		setItemSupplier(supplier);
		setOrderedToday(false);
	}
	
	/*** 
	 * This method determines whether or not we can decrease the quantity of the item any further.
	 * If it can be decreased, the itemQuantity variable is decremented by 1. This boolean return
	 * will be used by the Inventory and Shop classes to decrease item quantity.
	 * 
	 * @return true if the current quantity of the item is greater than 0, false if not
	 **/
	//Boolean method to tell us if we indeed CAN decrease quantity further
	public boolean decreaseItemQuantity() {
		if(itemQuantity > 0) {
			itemQuantity--;
			return true; 
		}
		else {
			return false; //this boolean is VERY important for Inventory and Shop classes
		}
	}
	
	/** 
	 * This method used to generate an order line for an item when (1) the quantity drops below 40, 
	 * and (2) if the item has not been ordered already today. If these conditions are met, a new 
	 * orderline with a default quantity of 50 items is created automatically. This method will
	 * be called by the Inventory class' manageItem method when decreasing the quantity of the item 
	 * by 1, to check if an item needs to be ordered afterwards.
	 * 
	 * @return orderline for the item if a new order must be created, null otherwise
	 */
	public OrderLine generateOrderLine() {
		OrderLine orderline;
		if(getItemQuantity() < 40 && orderedToday==false) { 
//			System.out.println("NOTE: Quantity of \""+getItemName()+"\" is below allowable threshold(40). Order has been automatically generated.");
			orderline = new OrderLine(this,50);
			setOrderedToday(true); //this item has now been ordered today, set the boolean to true
			return orderline;
		}
		return null;
	}
	
	
	//Getters and Setters
	/**
	 * Getter method for itemId variable
	 * 
	 * @return itemId id number of the item
	 */
	public int getItemId() {
		return itemId;
	}
	/**
	 * Setter method for itemId variable
	 * 
	 * @param itemId id number of the item
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	/**
	 * Getter method for itemName variable
	 * 
	 * @return itemName name of the item
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * Setter method for itemName variable
	 * 
	 * @param itemName name of the item
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * Getter method for itemQuantity variable
	 * 
	 * @return itemQuantity quantity of item left in stock
	 */
	public int getItemQuantity() {
		return itemQuantity;
	}
	/**
	 * Setter method for itemQuantity variable
	 * 
	 * @param itemQuantity quantity of item left in stock
	 */
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	/**
	 * Getter method for itemPrice variable
	 * 
	 * @return itemPrice price of the item
	 */
	public double getItemPrice() {
		return itemPrice;
	}
	/**
	 * Setter method for itemPrice variable
	 * 
	 * @param price price of the item
	 */
	public void setItemPrice(double price) {
		this.itemPrice = price;
	}
	/**
	 * Getter method for orderedToday variable
	 * 
	 * @return orderedToday boolean variable that keeps track of whether or not this
	 * item has been ordered today, to prevent duplicate orders of the same item
	 */
	public boolean isOrderedToday() {
		return orderedToday;
	}
	/**
	 * Setter method for orderedToday variable
	 * 
	 * @param orderedToday boolean variable that keeps track of whether or not this
	 * item has been ordered today, to prevent duplicate orders of the same item
	 */
	public void setOrderedToday(boolean orderedToday) {
		this.orderedToday = orderedToday;
	}
	/**
	 * Getter method for itemSupplier variable
	 * 
	 * @return itemSupplier the supplier that sells this item
	 */
	public Supplier getItemSupplier() {
		return itemSupplier;
	}
	/**
	 * Setter method for itemSupplier variable
	 * 
	 * @param itemSupplier the supplier that sells this item
	 */
	public void setItemSupplier(Supplier itemSupplier) {
		this.itemSupplier = itemSupplier;
	}
	
	/**
	 * Getter method for itemType variable
	 * 
	 * @return itemType the item's type
	 */
	public String getType() {
		return itemType;
	}

}
