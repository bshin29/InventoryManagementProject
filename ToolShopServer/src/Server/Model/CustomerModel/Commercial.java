package Server.Model.CustomerModel;

public class Commercial extends Customer {

	//instantiates a commercial type customer
	public Commercial(int id, String fname, String lname, String address, String postal, String phone) {
		super(id, fname, lname, address, postal, phone);
		type = 'C';
	}
	
}
