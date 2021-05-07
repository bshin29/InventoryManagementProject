package Server.Model.InventoryModel;

import java.util.ArrayList;

/**
 * Class name: SupplierList
 * 
 * This class serves as a data storage for the list of all known local tool suppliers. 
 * The shop inventory is stocked by purchasing items from this list of suppliers only.
 * The list is developed by storing all known Supplier objects in an ArrayList.
 * 
 * @author: Branden Shin
 * @since: Nov 22nd, 2020
 * 
 */
public class SupplierList {

	/**
	 * Variable used to define an ArrayList of Supplier objects, to store a list of suppliers
	 */
	private ArrayList<Supplier> supplierList;
	
	//Constructor
	/**
	 * This constructor is responsible for setting the local supplierList variable with 
	 * an ArrayList of Supplier objects retrieved from the "database" (text file).
	 * 
	 * @param suppliers ArrayList of supplier objects, retrieved from text file
	 */
	public SupplierList(ArrayList<Supplier> suppliers) {
		setSupplierList(suppliers);
	}

	//Getter and Setter
	/**
	 * Getter method for supplierList variable
	 * 
	 * @return supplierList ArrayList of supplier objects, retrieved from text file
	 */
	public ArrayList<Supplier> getSupplierList() {
		return supplierList;
	}

	/**
	 * Setter method for supplierList variable
	 * 
	 * @param supplierList ArrayList of supplier objects, retrieved from text file
	 */
	public void setSupplierList(ArrayList<Supplier> supplierList) {
		this.supplierList = supplierList;
	}

}
