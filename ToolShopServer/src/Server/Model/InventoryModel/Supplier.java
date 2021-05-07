package Server.Model.InventoryModel;

import java.util.ArrayList;

/**
 * Class name: Supplier
 * 
 * This class represents a supplier that the shop purchases tools from. Each Supplier
 * has an id number, a company name, an address, and a sales contact person. Each supplier also
 * contains a list of items that it sells.  
 * 
 * @author: Branden Shin
 * @since: Oct 12th, 2020
 */
public class Supplier {
	
	/**
	 * Variable used to store the supplier's id number
	 */
	protected int supplierId;
	/**
	 * Variable used to store the supplier company name
	 */
	protected String companyName;
	/**
	 * Variable used to store supplier's address
	 */
	protected String address;
	/**
	 * Variable used to store supplier's sales contact
	 */
	protected String salesContact;
	/**
	 * Variable used to store the list of items that the supplier sells
	 */
	protected ArrayList<Item> itemList; //2-way assoc==need Supplier to have knowledge of item
	
	protected String supplierType;
	
	//Constructor
	/**
	 * This constructor is responsible for setting the supplier's id, company name, address 
	 * and sales contact. It also creates an ArrayList of items to store a list of items that 
	 * the supplier sells. 
	 * 
	 * @param id supplier id number
	 * @param company supplier company name
	 * @param address supplier address
	 * @param contact supplier sales contact person
	 */
	public Supplier(int id, String company, String address, String contact) {
		setSupplierId(id);
		setCompanyName(company);
		setAddress(address);
		setSalesContact(contact);
		itemList = new ArrayList<Item>(); //not initializing anything, just instantiating the arrayList
	}
	
	//Getters and Setters
	/**
	 * Getter method for supplierId variable
	 * 
	 * @return supplierId supplier's id number
	 */
	public int getSupplierId() {
		return supplierId;
	}
	/**
	 * Setter method for supplierId variable
	 * 
	 * @param supplierId supplier's id number
	 */
	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}
	/**
	 * Getter method for companyName variable
	 * 
	 * @return companyName supplier's company name
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * Setter method for companyName variable
	 * 
	 * @param companyName supplier's company name
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * Getter method for address variable
	 * 
	 * @return address supplier's address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * Setter method for address variable
	 * 
	 * @param address supplier's address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * Getter method for salesContact variable
	 * 
	 * @return salesContact supplier's sales contact person
	 */
	public String getSalesContact() {
		return salesContact;
	}
	/**
	 * Setter method for salesContact variable
	 * 
	 * @param salesContact supplier's sales contact person
	 */
	public void setSalesContact(String salesContact) {
		this.salesContact = salesContact;
	}
	/**
	 * Getter method for itemList variable
	 * 
	 * @return itemList list of all items the supplier sells
	 */
	public ArrayList<Item> getItemList() {
		return itemList;
	}
	/**
	 * Setter method for itemList variable
	 * 
	 * @param itemList list of all items the supplier sells
	 */
	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}

}
