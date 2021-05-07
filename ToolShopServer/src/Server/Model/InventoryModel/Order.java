package Server.Model.InventoryModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Class name: Order
 * 
 * This class represents the daily order that the shop makes. All orders include a unique
 * 5-digit order id, and the date it was ordered. Each order creates a "receipt" of sorts,
 * containing an order line for each item that was ordered this day. 
 * 
 * @author: Branden Shin
 * @since: Oct 12th, 2020
 */
public class Order {
	/**
	 * Variable used to define todays date for the order
	 */
	private String todaysDate;
	/**
	 * Variable used to define the unique 5-digit order id number
	 */
	private int orderId;
	/**
	 * List of OrderLine objects used to create the receipt
	 */
	private ArrayList<OrderLine> todaysOrderLine;
	
	//Constructor
	/**
	 * This constructor constructs an Order object and sets the orderId instance variable
	 * to a random 5 digit number, and todaysDate to today's date. It also instantiates
	 * todaysOrderLine with an ArrayList of orderLine objects.
	 */
	public Order() {
		todaysOrderLine = new ArrayList <OrderLine>();
		Random rng = new Random();
		setOrderId(10000 + rng.nextInt(90000));
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		setTodaysDate(df.format(new Date()));
	}

	//Order Methods
	/**
	 * Responsible for adding an order line to the running order receipt for the day
	 * each time a new item is ordered. This method is called by the Inventory class
	 * when an item falls below a quantity of 40, in order to replenish stock. 
	 * 
	 * @param oL the orderLine to be added to the daily order receipt
	 */
	public void addOrderLine(OrderLine oL) {
		todaysOrderLine.add(oL);
	}
	
	/**
	 * Responsible for creating a string that formats the order receipt with all 
	 * orderlines for the day. Prints the daily orders to the output console per user's request.
	 * 
	 * @return s formatted order with all orders for the day
	 */
	public String printOrder() {
		String s = orderId+", "+todaysDate;
		if(todaysOrderLine.isEmpty()==false) {
			for(OrderLine oL:todaysOrderLine) {
				s = s+", "+oL;
			}
		}
		return s;
	}

	//Getters and Setters
	/**
	 * Getter method for todaysDate variable
	 * 
	 * @return todaysDate the date today, needed for the order
	 */
	public String getTodaysDate() {
		return todaysDate;
	}
	/**
	 * Setter method for todaysDate variable
	 * 
	 * @param todaysDate the date today, needed for the order
	 */
	public void setTodaysDate(String todaysDate) {
		this.todaysDate = todaysDate;
	}
	/**
	 * Getter method for orderId variable
	 * 
	 * @return orderId the order id number
	 */
	public int getOrderId() {
		return orderId;
	}
	/**
	 * Setter method for orderId variable
	 * 
	 * @param orderId the order id number
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	/**
	 * Getter method for todaysOrderLine variable
	 * 
	 * @return todaysOrderLine the list of all orderlines generated today
	 */
	public ArrayList<OrderLine> getTodaysOrderLine() {
		return todaysOrderLine;
	}
	/**
	 * Setter method for todaysOrderLine variable
	 * 
	 * @param todaysOrderLine the list of all orderlines generated today
	 */
	public void setTodaysOrderLine(ArrayList<OrderLine> todaysOrderLine) {
		this.todaysOrderLine = todaysOrderLine;
	}
}
