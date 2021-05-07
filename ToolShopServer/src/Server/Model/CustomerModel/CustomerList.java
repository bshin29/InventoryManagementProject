package Server.Model.CustomerModel;

import java.util.ArrayList;


public class CustomerList {

	//Variable used to store a list of customers
	public ArrayList<Customer> customerList;
	
	//Constructor
	public CustomerList() {}

	//1. search customer based on id
	public Customer findCustomer(int id) {
		for(Customer c:customerList) {
			if(c.getId()==id) {
				return c;
			}
		}
		return null;
	}
	
	//2. search customer based on name
	public Customer findCustomer(String fname, String lname) {
		for(Customer c:customerList) {
			if(c.getfName().equals(fname) && c.getlName().equals(lname)) {
				return c;
			}
		}
		return null;
	}
	
	//3. search customer based on type
	public ArrayList<Customer> findCustomerType(char type) {
		ArrayList<Customer> result = new ArrayList<Customer>();
		
		for(Customer c:customerList) {
			if(c.getType()==type) {
				result.add(c);
			}
		}
		return result;
	}
	
	
	//4. Add a new customer to CustomerList model. returns true if successfully added, false if a 
	//client with that ID already exists
	public boolean addCustomerToModel(int id, String fname, String lname, String address, String postal, String phone, char type) {
		boolean exists=false;
		for(Customer c:customerList) {
			if(c.getId()==id) {
				exists=true;
			}
		}
		if(exists==true) {
			return false;
		}
		else {
			Customer c = null;
			
			if(type=='C') {
				c = new Commercial(id,fname,lname,address,postal,phone);
			}
			else if(type=='R') {
				c = new Residential(id,fname,lname,address,postal,phone);
			}
			customerList.add(c); //adds new customer to model
			return true;
		}
	}

	//5. Modify existing information for a customer. Returns true if successfully updated, false
	//if a client with given ID doesnt exist
	public boolean updateCustomerInModel(int id, String fname, String lname, String address, String postal, String phone, char type) {
		int indexToUpdate=-1;
		Customer updated = null;
		
		for(Customer c:customerList) {
			if(c.getId()==id) {
				indexToUpdate = customerList.indexOf(c); //look for index in arraylist to update based on client ID
			}
		}

		if(indexToUpdate == -1) { //this means client with given ID not found in model, cannot update
			return false;
		}
		else {
			if(type=='C') {
				updated = new Commercial(id,fname,lname,address,postal,phone);
			}
			else if(type=='R') {
				updated = new Residential(id,fname,lname,address,postal,phone);
			}
			customerList.set(indexToUpdate, updated); //sets new customer in old index location
			return true;
		}
	}
	
	//6. Remove a customer from the model. Returns true if successful, false if client
	//with given ID doesnt exist
	public boolean deleteCustomerFromModel(int id) {
		int indexToDelete=-1;
		
		for(Customer c:customerList) {
			if(c.getId()==id) {
				indexToDelete = customerList.indexOf(c); //look for index in arraylist to delete based on client ID
			}
		}
		if(indexToDelete==-1) { //this means client with given ID not found in model, cannot delete
			return false;
		}
		else {
			customerList.remove(indexToDelete);
			return true;
		}
	}
	
	//Getter and Setter
	public ArrayList<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(ArrayList<Customer> c) {
		this.customerList = c;
	}

}
