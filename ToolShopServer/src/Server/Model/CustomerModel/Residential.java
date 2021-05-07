package Server.Model.CustomerModel;

public class Residential extends Customer {

	//instantiates a residential type customer
	public Residential(int id, String fname, String lname, String address, String postal, String phone) {
		super(id, fname, lname, address, postal, phone);
		type = 'R';
	}
	
}
