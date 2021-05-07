package Server.Model.InventoryModel;
import java.util.ArrayList;

/**
 * Class name: Inventory
 * 
 * This class represents the inventory of the shop, and is built from a list of Item objects.
 * This class also keeps track of the order for each day, and all orderlines that were generated.
 * Finally, this class shares the responsibility of decreasing item quantity (with Shop and Item).
 * 
 * @author: Branden Shin
 * @since: Oct 12th, 2020
 */
public class Inventory {
	/**
	 * Variable used to define the list of items that make up the shop's inventory
	 */
	private ArrayList<Item> itemList;
	/**
	 * Variable used to define the shop's order (and store all orderlines) for the day
	 */
	private Order myOrder;
	
	//Constructor
	/**
	 * This constructor constructs an Inventory object by setting the itemList instance variable.
	 * This list of items is created by the FileManager class by reading and scraping the items.txt
	 * text file.
	 * 
	 * @param itemList list of all items that make up shops inventory
	 */
	public Inventory(ArrayList<Item> itemList) {
		setItemList(itemList); //setting in constructor bc aggregation!
		myOrder = new Order(); //a new Order is created for each inventory
	}
	
	//Inventory Methods
	/**
	 * This method is responsible for searching for an item in the itemList by item name.
	 * 
	 * @param name the name of the item that we are searching for
	 * @return the matching item if it is in the list, null otherwise
	 */
	public Item searchForItem(String name) {
		for(Item i:itemList) {
			if(i.getItemName().equals(name)) {
				return i; //item is found, so return the item
			}
		}
		return null;  //null if not found
	}
	
	/**
	 * This method is responsible for searching for an item in the itemList by item id.
	 * 
	 * @param id the id number of the item that we are searching for
	 * @return the matching item if it is in the list, null otherwise
	 */
	public Item searchForItem(int id) {
		for(Item i:itemList) {
			if(i.getItemId()==id) {
				return i; //item is found, so return the item
			}
		}
		return null; //null if not found
	}
	
	/**
	 * This method is responsible for returning the current quantity of an item.
	 * 
	 * @param name the name of the item that we are searching for
	 * @return the quantity of the item, returns -1 if the item does not exist 
	 */
	public int getItemQuantity(String name) {
		Item theItem = searchForItem(name); //search for the item in item List
		if(theItem==null) { //if doesnt exist, return -1
			return -1;
		}
		else {
			return theItem.getItemQuantity(); //exists, so return its quantity
		}
	}
	
	/** 
	 * This method checks to see if the item quantity can be decreased (quantity greater than 0) by 
	 * calling the Item class' decreaseItem() method. If it cannot, this method returns null. 
	 * 
	 * @param name the name of the item to decrease quantity
	 * @return theItem the item that we want to decrease quantity of
	 */
	private Item decreaseItem(String name) {
		Item theItem = searchForItem(name); //first search for item
		if(theItem==null) { //case where we cannot find the item in inventory
			return null;
		}
		if(theItem.decreaseItemQuantity()==true) { //will return true if we CAN decrease quantity
			return theItem;
		}
		return null;
	}
	
	/** 
	 * This method is used by the Shop class to decrease the quantity of the item by 1. First 
	 * a check is done to see if the item can even be decreased (quantity greater than 0) by calling the 
	 * Item class' decreaseItem() method. If it cannot, this method returns null. If the quantity 
	 * can be decreased, an additional check is done to see if the quantity drops below 40 to 
	 * see if an order must be generated for the item.
	 * 
	 * @param name the name of the item to decrease quantity
	 * @return i the item that we want to decrease quantity of
	 */
	public Item manageItem(String name) {
		Item i = decreaseItem(name); //will return null if quantity is above 0
		return i; 
	}
	

	/** 
	 * This method calls the Item class' generateOrderLine() method to create an orderline
	 * for the item, and add it to the daily order (myOrder).
	 * 
	 * @param name item that requires an order
	 * @return true if orderline was created for given item, false otherwise
	 */
	public boolean checkIfOrderPlaced(String name) {
		Item item = searchForItem(name);
		OrderLine oL = item.generateOrderLine();
		if(oL==null) {
			return false; //item quantity above 40, no order-line needs to be generated
		}
		else {
			myOrder.addOrderLine(oL); //item quantity fell below 40, order-line generated automatically
			return true;
		}
	}
	
	
	
	/** 
	 * This method calls the Order class' printOrder() method to print the formatted order for the day.
	 * 
	 * @return formatted string to print entire order for the day (with all item order lines)
	 */
	public String printOrder() {
		return myOrder.printOrder();
	}
	
	//Getters and Setters
	/**
	 * Getter method for itemList variable
	 * 
	 * @return itemList list of items that make up the inventory
	 */
	public ArrayList<Item> getItemList() {
		return itemList;
	}
	/**
	 * Setter method for itemList variable
	 * 
	 * @param itemList list of items that make up the inventory
	 */
	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}
	/**
	 * Getter method for myOrder variable
	 * 
	 * @return myOrder single order object created for each day, and orderlines are added to it
	 */
	public Order getMyOrder() {
		return myOrder;
	}
	/**
	 * Setter method for myOrder variable
	 * 
	 * @param myOrder single order object created for each day, and orderlines are added to it
	 */
	public void setMyOrder(Order myOrder) {
		this.myOrder = myOrder;
	}

}
