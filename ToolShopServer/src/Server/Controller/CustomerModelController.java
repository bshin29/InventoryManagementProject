package Server.Controller;

import java.util.ArrayList;
import Server.Model.CustomerModel.*;


public class CustomerModelController {

	//Instances of DatabaseController and Model (CustomerList)
	private DatabaseController dbc;
	private CustomerList model;
	
	//Constructor
	public CustomerModelController(DatabaseController d, CustomerList c) {
		this.dbc = d;
		this.model = c;
		populateCustomerModel();
	}

	//Uses DatabaseController to get an arraylist of all Customers as a String, then parses this
	//string to instantiate all Cstuomer objects and store in an arraylist
	private void populateCustomerModel() {
		ArrayList<String> stringList = dbc.getAllCustomers();
		ArrayList<Customer> cList = new ArrayList<Customer>();
		Customer c;
		
		for(int i=0; i<stringList.size();i++) {
			String[] temp = stringList.get(i).split(";");
			if(temp[6].equals("C")) {
				c = new Commercial(Integer.parseInt(temp[0]), temp[1], temp[2], temp[3], temp[4], temp[5]);
			}
			else {
				c = new Residential(Integer.parseInt(temp[0]), temp[1], temp[2], temp[3], temp[4], temp[5]);
			}
			cList.add(c);
		}
		model.setCustomerList(cList);
	}
	
	//1. search customer based on id
	public String searchById(int id) {
		Customer temp = model.findCustomer(id);
		if(temp==null) {
			return("FAILURE:No client with id "+id);
		}
		
		String result = "SUCCESS:"+temp.getId()+";"+temp.getfName()+";"+temp.getlName()+
				";"+temp.getAddress()+";"+temp.getPostalCode()+";"+temp.getPhoneNum()+";"+temp.getType();
		
		return result;
	}
	
	//2. search customer based on name
	public String searchByName(String fname, String lname) {
		Customer temp = model.findCustomer(fname,lname);
		
		if(temp==null) {
			return("FAILURE:No client with that name");
		}
		
		return "SUCCESS:"+temp.getId()+";"+temp.getfName()+";"+temp.getlName()+
				";"+temp.getAddress()+";"+temp.getPostalCode()+";"+temp.getPhoneNum()+";"+temp.getType();
	}
	
	//3. search customer based on type
	public String searchByType(char type){
		ArrayList<Customer> list = model.findCustomerType(type);
		String result = "SUCCESS:"; 
		//there will not be a fail case for this search method, because erroneous input handled by view
		
		for(Customer c:list) {
			String temp = c.getId()+";"+c.getfName()+";"+c.getlName()+";"+c.getAddress()+";"+c.getPostalCode()+";"+c.getPhoneNum()+";"+c.getType();
			result +=temp+"&";
		}
		return result;
	}
	
	//4. Add a new customer (i.e. client)	
	public String addNewCustomer(int id, String fname, String lname, String address, String postal, String phone, char type) {
		String result;
		
		//update the model. If this method is true, customer has been added to model
		if(model.addCustomerToModel(id,fname,lname,address,postal,phone,type)==true) {
			result = "SUCCESS:"; 
			//then update the database to reflect this change
			dbc.addCustomer(id,fname,lname,address,postal,phone,type);
		}
		else { //if false, then customer was not added to the model (already exists in db)
			result = "FAILURE:";
		}
		return result;
	}
	
	//5. Modify existing information for a customer
	public String updateCustomer(int id, String fname, String lname, String address, String postal, String phone, char type) {
		String result;
		
		//returns true if customer successfully updated in model
		if(model.updateCustomerInModel(id,fname,lname,address,postal,phone,type)==true) {
			dbc.updateCustomer(id, fname, lname, address, postal, phone, type); //then update the database to reflect this change
			result = "SUCCESS:";
		}
		else { //if false, then customer was not added to the model (already exists in db)
			result = "FAILURE:";
		}
		return result;
	}
	
	//6. Remove a customer
	public String deleteCustomer(int id) {
		String result;
		
		//returns true if customer successfully deleted in model
		if(model.deleteCustomerFromModel(id)==true) {
			//update database
			dbc.removeCustomer(id);
			result = "SUCCESS:";
		}
		else { //if false, then customer was not deleted from model (doesnt exist in db)
			result = "FAILURE:";
		}
		return result;
	}
	
	//7. Get all customers
	public String allCustomers(){
		ArrayList<Customer> list = model.getCustomerList();
		String result = "SUCCESS:"; 
		
		for(Customer c:list) {
			String temp = c.getId()+";"+c.getfName()+";"+c.getlName()+";"+c.getAddress()+";"+c.getPostalCode()+";"+c.getPhoneNum()+";"+c.getType();
			result +=temp+"&";
		}
		return result;
	}
	
}
