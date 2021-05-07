package Server.Model.InventoryModel;

/**
 * Class name: OrderLine
 * 
 * This class represents the order line that is generated each time an item needs to be 
 * ordered. The order line contains the following information: The item to be ordered,
 * the quantity to be ordered, and the supplier that sells the item. Each order line is added
 * to the overall order "receipt" in the Order class.
 * 
 * @author: Branden Shin
 * @since: Oct 12th, 2020
 */

public class OrderLine {
	/**
	 * Variable used to define the item to be ordered
	 */
	private Item orderItem;
	/**
	 * Variable used to define the quantity of the item order
	 */
	private int orderQuantity;

	//Constructor
	/**
	 * This constructor constructs an OrderLine object by setting the orderItem and
	 * orderQuantity instance variables.
	 * 
	 * @param item the item to be ordered
	 * @param quantity amount of the item to be ordered
	 */
	public OrderLine(Item item, int quantity) {
		setOrderItem(item);
		setOrderQuantity(quantity);
	}

	/**
	 * Overrides toString() method to format each orderLine with the item name, the 
	 * amount of the item ordered, and the name of the supplier that sells the item.
	 * 
	 * @return s formatted orderLine string
	 */
	@Override
	public String toString() {
		String s=orderItem.getItemName()+", "+getOrderQuantity()+", "+orderItem.getItemSupplier().getCompanyName();
		return s;
	}
	
	//Getters and Setters
	/**
	 * Getter method for orderItem variable
	 * 
	 * @return orderItem item ordered in this order
	 */
	public Item getOrderItem() {
		return orderItem;
	}
	/**
	 * Setter method for orderItem variable
	 * 
	 * @param orderItem item ordered in this order
	 */
	public void setOrderItem(Item orderItem) {
		this.orderItem = orderItem;
	}
	/**
	 * Getter method for orderQuantity variable
	 * 
	 * @return orderQuantity amount of the item to be ordered
	 */
	public int getOrderQuantity() {
		return orderQuantity;
	}
	/**
	 * Setter method for orderQuantity variable
	 * 
	 * @param orderQuantity: amount of the item to be ordered
	 */
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
}
