package Server.Model.CustomerModel;


public class Customer {

	//instance variables
	private int id;
	private String fName;
	private String lName;
	private String address;
	private String postalCode;
	private String phoneNum;
	protected char type;
	
	//Constructor
	public Customer(int id, String fname, String lname, String address, String postal, String phone) {
		setId(id);
		setfName(fname);
		setlName(lname);
		setAddress(address);
		setPostalCode(postal);
		setPhoneNum(phone);
	}
	

	@Override
	public String toString() {
		String s = id+" "+fName+" "+lName+" "+address+" "+postalCode+" "+phoneNum+" "+type;
		return s;
	}
	

	//Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public char getType() {
		return type;
	}

	
}
