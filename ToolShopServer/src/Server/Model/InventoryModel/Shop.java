package Server.Model.InventoryModel;

import java.util.ArrayList;


/**
 * Class name: Shop
 * 
 * This class represents the Shop, and is made up of a list of items (Inventory object) and a list of
 * suppliers (SupplierList object). Much of the shop functionality is handled in this class by 
 * calling the methods of the Inventory class.
 * 
 * @author: Branden Shin
 * @since: Nov 22nd, 2020
 */
public class Shop {
	/**
	 * Variable used to define the inventory of the shop
	 */
	private Inventory theInventory;
	/**
	 * Variable used to define the list of known suppliers for the shop
	 */
	private SupplierList theSuppliers;
	

	//Constructor
	/**
	 * This constructor constructs a Shop object with inventory and supplierlist
	 */
	public Shop() {}

	//Shop Methods

	/**
	 * Lists all items as 1 single string, delimited by the % key
	 * 
	 * @return list of all items
	 */
	public String listAllItems() {
		ArrayList<Item> items = theInventory.getItemList();
		String result = "SUCCESS:"; 
		
		for(Item i:items) {
			String temp = i.toString();
			result += temp+"%";
		}
		return result;
	}
	
	/**
	 * Lists all suppliers as 1 single string, delimited by the % key
	 * 
	 * @return list of all suppliers
	 */
	public String listAllSuppliers() {
		ArrayList<Supplier> suppliers = theSuppliers.getSupplierList();
		String result = "SUCCESS:"; 
		
		for(Supplier s:suppliers) {
			String temp = s.toString();
			result += temp+"%";
		}
		return result;
		
	}

	/**
	 * This method is used to decrease the quantity of an item by 1. This is done by calling the 
	 * Inventory class' manageItem method, which handles the decrement and any safety checks. 
	 * This method simply outputs a message telling the user if their operation was successful 
	 * or unsuccessful.
	 * 
	 * @param name name of the item for which we want to decrease quantity of
	 * @return a string outputting a successful or erroneous message
	 */
	public String decreaseItem(String name) {
		
		if(theInventory.manageItem(name)==null) { //manageItem will return null if quantity is already at 0
			Item item = theInventory.searchForItem(name);
			if(item==null) { //this is to check if the user input an item that is even in the system
				return "FAILURE:CASE1";
			}
			else { //if the item exists in system, but manageItem returns null, it is because the quantity is at 0
				return "FAILURE:CASE2";
			}
		}
		else { //if manageItem =/= null, then the quantity has successfully been decreased by 1
			return "SUCCESS:"+theInventory.getItemQuantity(name);
		}
	}
	
	/**
	 * This method is used to return whether or not an orderline was created for 
	 * a given item
	 * 
	 * @param name item of interest
	 * @return true if orderline was created for the item, false otherwise
	 */
	public boolean orderLineCreated(String name) {
		return theInventory.checkIfOrderPlaced(name);
	}
	
	/**
	 * This method is used to search for an Item by id number
	 * 
	 * @param id id number of the item of interest
	 * @return item that matches the id
	 */
	public Item getItem(int id) {
		Item item = theInventory.searchForItem(id);
		return item;
	}

	/**
	 * This method is used to search for an Item by item name
	 * 
	 * @param name the name of the item of interest
	 * @return item that matches given name
	 */
	public Item getItem(String name) {
		Item item = theInventory.searchForItem(name);
		return item;
	}
		
	/**
	 * This method is used to retrieve the current quantity of an item
	 * 
	 * @param name the name of the item of interest
	 * @return quantity of item in stock
	 */
	public int getItemQuantity(String name) {
		int quantity = theInventory.getItemQuantity(name);
		return quantity;
	}

	/**
	 * This method simply prints all orderlines in the daily order by calling the
	 * Inventory class' printOrder() method.
	 * 
	 * @return formatted string to print entire order for the day (with all item order lines)
	 */
	public String printOrder() {
		return theInventory.printOrder();
	}
	
	//Getters and Setters
	/**
	 * Getter method for theInventory variable
	 * 
	 * @return theInventory list of all items in the shop's inventory
	 */
	public Inventory getTheInventory() {
		return theInventory;
	}
	/**
	 * Setter method for theInventory variable
	 * 
	 * @param theInventory list of all items in the shop's inventory
	 */
	public void setTheInventory(Inventory theInventory) {
		this.theInventory = theInventory;
	}
	/**
	 * Getter method for theSuppliers variable
	 * 
	 * @return theSuppliers list of all suppliers for the shop
	 */
	public SupplierList getTheSuppliers() {
		return theSuppliers;
	}
	/**
	 * Setter method for theSuppliers variable
	 * 
	 * @param theSuppliers list of all suppliers for the shop
	 */
	public void setTheSuppliers(SupplierList theSuppliers) {
		this.theSuppliers = theSuppliers;
	}

}
