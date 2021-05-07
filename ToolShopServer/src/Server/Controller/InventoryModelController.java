package Server.Controller;
import java.util.ArrayList;
import Server.Model.InventoryModel.*;


public class InventoryModelController {
	
	//Instances of DatabaseController and Model (shop)
	private DatabaseController dbc;
	private Shop iModel;
	private ArrayList<Supplier> suppliers;
	
	//Constructor
	public InventoryModelController(DatabaseController d,Shop s) {
		iModel = s;
		dbc = d;
		populateModelSupplierList();
		populateModelInventory();
	}

	//Uses DatabaseController to get an arraylist of all suppliers as a String, then parses this
	//string to instantiate all Supplier objects and store in an arraylist
	private void populateModelSupplierList() {
		ArrayList<String> list = dbc.getAllSuppliers();
		Supplier s;
		suppliers = new ArrayList<Supplier>();
		
		for(int i=0; i<list.size();i++) {
			String[] temp = list.get(i).split(";");
			
			if(temp[1].equals("Local")) {
				s = new Local(Integer.parseInt(temp[0]), temp[2], temp[3], temp[4]);
			}
			else {
				s = new International(Integer.parseInt(temp[0]), temp[2], temp[3], temp[4],getImportTax(temp[0]));
			}
			
			suppliers.add(s);
		}
		iModel.setTheSuppliers(new SupplierList(suppliers));
	}
	
	//Finds the corresponding import tax for a given SupplierID
	private double getImportTax(String id) {
		ArrayList<String> list = dbc.getAllInternationals();
		String tax=null;
		
		for(String s:list) {
			if(s.contains(id)) {
				tax = s.replace(""+id+";","");
				tax = tax.replace(";", "");
			}
		}
		return Double.parseDouble(tax);
	}
	
	//Finds the supplier object of a tool using supplierID
	private Supplier findSupplier(int supplierId) {
		Supplier theSupplier = null;
		for(Supplier s:suppliers) {
			if(s.getSupplierId()==supplierId) {
				theSupplier=s;
				break;
			}
		}
		return theSupplier;
	}
	
	//Uses DatabaseController to get an arraylist of all items as a String, then parses this
	//string to instantiate all Item objects and store in an arraylist
	private void populateModelInventory() {
		ArrayList<String> list = dbc.getAllTools();
		Item item;
		ArrayList<Item> tempList = new ArrayList<Item>();
		
		for(int i=0; i<list.size();i++) {
			String[] temp = list.get(i).split(";");
			
			if(temp[1].equals("Electrical")) {
				item = new Electrical(Integer.parseInt(temp[0]), temp[2], Integer.parseInt(temp[3]), 
						Double.parseDouble(temp[4]), findSupplier(Integer.parseInt(temp[5])),getPowerType(temp[0]));
			}
			else {
				item = new NonElectrical(Integer.parseInt(temp[0]), temp[2], Integer.parseInt(temp[3]), 
						Double.parseDouble(temp[4]), findSupplier(Integer.parseInt(temp[5])));
			}
			
			tempList.add(item);
		}
		iModel.setTheInventory(new Inventory(tempList));
	}
	 
	//Finds the corresponding power type for a given ToolID
	private String getPowerType(String id) {
		ArrayList<String> list = dbc.getAllElectricals();
		String powerType=null;
		
		for(String s:list) {
			if(s.contains(id)) {
				powerType = s.replace(""+id+";","");
				powerType = powerType.replace(";", "");
			}
		}
		return powerType;
	}
	
	//1. list all items
	public String getAllItems() {
		return iModel.listAllItems();
	}
	
	//2. list all suppliers
	public String getAllSuppliers() {
		return iModel.listAllSuppliers();
	}
	
	//3. search for tool by id
	public String searchForItemById(int id) {
		Item temp = iModel.getItem(id);
		if(temp==null) {
			return "FAILURE:No tool with id "+id;
		}

		String result = "SUCCESS:"+temp.toString();
		return result;
	}
	
	//4. search for tool by name
	public String searchForItemByName(String name) {
		Item temp = iModel.getItem(name);
		if(temp==null) {
			return "FAILURE:No tool with name "+name;
		}
		String result = "SUCCESS:"+temp.toString();
		return result;
	}
		
	//5. check item quantity
	public String checkItemQuantity(String name) {
		int quantity = iModel.getItemQuantity(name);
		if(quantity < 0) { //quantity will = -1 If the item does not exist in inventory
			return "FAILURE:";
		}
		else {
			return "SUCCESS:"+quantity;
		}
	}
	
	//6. decrease item quantity
	public String decreaseItem(String name) {
		
		//update the model
		String result = iModel.decreaseItem(name);
		
		if(result.contains("SUCCESS")) { //if model is successfully updated, then...
			//update database
			dbc.updateQuantity(name);
			
			if(iModel.orderLineCreated(name)==true) { //if a new order-line was added to the order in the model
				int orderId = iModel.getTheInventory().getMyOrder().getOrderId();
				
				if(newOrder(orderId)==true) { //check if orderID already exists, if doesnt exist we must make a new row in ORDER table
					dbc.addOrder(orderId,iModel.getTheInventory().getMyOrder().getTodaysDate()); //add the order to database
				}
				
				ArrayList<OrderLine> list = iModel.getTheInventory().getMyOrder().getTodaysOrderLine();
				OrderLine oL = list.get(list.size()-1); //get the LAST element, this will be the most recent orderline
				dbc.addOrderLine(orderId, oL.getOrderItem().getItemId(), 
						oL.getOrderItem().getItemSupplier().getSupplierId(), oL.getOrderQuantity()); //add ORDERLINE row in database
				
				result+="%CASE2"; //"CASE2" represents the fact that a new orderline was automatically generated, tell the client this info
			}
		}
		return result;
	}
	
	//returns true if the order ID does not already exist in the database. This means
	//we can make a new ORDER tuple
	private boolean newOrder(int id) {
		ArrayList<String> orderList = dbc.getAllOrders();
		for(String s:orderList) {
			String[] temp = s.split(";");
			if(Integer.toString(id).equals(temp[0])) {
				return false;
			}
		}
		return true;
	}
	
	//7. print all orders
	public String getAllOrders() {
		String result = "SUCCESS:";
		ArrayList<String> list = dbc.getAllOrderLines();
		for(String s:list) {
			result += s+"%";
		}
		return result;
	}

}
