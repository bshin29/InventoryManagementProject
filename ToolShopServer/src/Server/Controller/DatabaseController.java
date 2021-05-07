package Server.Controller;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseController {
	
	//JDBC Attributes
	private Connection conn;
   	private String DB_URL = "jdbc:mysql://localhost:3306/shopDB";
   	private String USERNAME = "root";
   	private String PASSWORD = "BshinSQL";	

   	//Constructor 
   	public DatabaseController() {
   		initializeConnection();
   	}

   	//initializes JDBC connection with MySQL database
	public void initializeConnection() {
		try {
			Driver driver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			System.out.println("Problem connecting to MySQL...");
			e.printStackTrace();
		}
		
		System.out.println("Connected to: "+DB_URL);
	}
	
	//function used to convert resultSet object into an arraylist of strings to return to Controller classes
	public ArrayList<String> convertToList(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData(); 
		int columns = md.getColumnCount();
		ArrayList<String> list = new ArrayList<>();
		
		while(rs.next()) {
			String row = "";
			int i=1;
			while(i<=columns) {
				row+=rs.getObject(i).toString()+";";
				i++;
			}
			list.add(row);
		}
		return list; 
	}
	
	//Database functionalities
	//1. get all tools
	public ArrayList<String> getAllTools() {
		
		String sqlSelect = "SELECT * FROM TOOL";
		ResultSet rs;
		ArrayList<String> list = null;
		
		try {
			PreparedStatement pState = conn.prepareStatement(sqlSelect);
			rs = pState.executeQuery();
			list= convertToList(rs); //convert ResultSet to single string
			rs.close();
			pState.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//2.get all suppliers
	public ArrayList<String> getAllSuppliers() {
		
		String sqlSelect = "SELECT * FROM SUPPLIER";
		ResultSet rs;
		ArrayList<String> list = null;
		
		try {
			PreparedStatement pState = conn.prepareStatement(sqlSelect);
			rs = pState.executeQuery();
			list= convertToList(rs);
			rs.close();
			pState.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//3. decrease item quantity
	public void updateQuantity(String name) {
		String sqlUpdate = "UPDATE TOOL SET Quantity=Quantity-1 WHERE Name = ?";
		
		try {
			PreparedStatement pState = conn.prepareStatement(sqlUpdate);
			pState.setString(1, name);
			pState.executeUpdate();
			pState.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//4. add new customer tuple
	public void addCustomer(int id, String fname, String lname, String address, String postal, String phone, char type) {
		String sqlInsert = "INSERT INTO CLIENT(ClientID,FName,LName,Address,PostalCode,PhoneNum,Type) VALUES(?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement pState = conn.prepareStatement(sqlInsert);
			pState.setInt(1,id);
			pState.setString(2, fname);
			pState.setString(3, lname);
			pState.setString(4, address);
			pState.setString(5, postal);
			pState.setString(6, phone);
			pState.setString(7, String.valueOf(type));
			
			pState.executeUpdate();
			pState.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//5. update customer tuple
	public void updateCustomer(int id, String fname, String lname, String address, String postal, String phone, char type) {
		String sqlInsert = "UPDATE CLIENT SET FName=?,LName=?,Address=?,PostalCode=?,PhoneNum=?,Type=? WHERE ClientID=?";
		
		try {
			PreparedStatement pState = conn.prepareStatement(sqlInsert);
			pState.setString(1, fname);
			pState.setString(2, lname);
			pState.setString(3, address);
			pState.setString(4, postal);
			pState.setString(5, phone);
			pState.setString(6, String.valueOf(type));
			pState.setInt(7,id);
			
			pState.executeUpdate();
			pState.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//6. remove customer tuple from database
	public void removeCustomer(int id) {
		String sqlDelete = "DELETE FROM CLIENT WHERE ClientID=?";
		
		try {
			PreparedStatement pState = conn.prepareStatement(sqlDelete);
			pState.setInt(1, id);
			pState.executeUpdate();
			pState.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
		
	//7. get arraylist of all customers
	public ArrayList<String> getAllCustomers() {
		String sqlSelect = "SELECT * FROM CLIENT";
		ResultSet rs;
		ArrayList<String> list = null;
		
		try {
			PreparedStatement pState = conn.prepareStatement(sqlSelect);
			rs = pState.executeQuery();
			list= convertToList(rs);
			rs.close();
			pState.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//8. Get all international suppliers to record the import tax
	public ArrayList<String> getAllInternationals(){
		String sqlSelect = "SELECT * FROM INTERNATIONAL";
		ResultSet rs;
		ArrayList<String> list = null;
		
		try {
			PreparedStatement pState = conn.prepareStatement(sqlSelect);
			rs = pState.executeQuery();
			list= convertToList(rs);
			rs.close();
			pState.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return list;	
	}
	
	//9. Get all electrical tools to record power type
	public ArrayList<String> getAllElectricals(){
		String sqlSelect = "SELECT * FROM ELECTRICAL";
		ResultSet rs;
		ArrayList<String> list = null;
		
		try {
			PreparedStatement pState = conn.prepareStatement(sqlSelect);
			rs = pState.executeQuery();
			list= convertToList(rs);
			rs.close();
			pState.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return list;	
	}
	
	
	//10. Get all Order ID's
	public ArrayList<String> getAllOrders() {
		String sqlSelect = "SELECT O.OrderID FROM ORDERS AS O";
		ResultSet rs;
		ArrayList<String> list = new  ArrayList<String>();
		
		try {
			PreparedStatement pState = conn.prepareStatement(sqlSelect);
			rs = pState.executeQuery();
			list = convertToList(rs);
			rs.close();
			pState.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	//11. Get all ORDERLINES
	public ArrayList<String> getAllOrderLines() {
		
		String sqlSelect = "SELECT O.OrderID,O.Date,T.Name,S.Name,OL.Quantity "+
				"FROM ORDERLINE AS OL, ORDERS AS O, TOOL AS T, SUPPLIER AS S "+
				"WHERE O.OrderID=OL.OrderID AND OL.ToolID=T.ToolID AND OL.SupplierID=S.SupplierID";
		
		ResultSet rs;
		ArrayList<String> list = new  ArrayList<String>();
		
		try {
			PreparedStatement pState = conn.prepareStatement(sqlSelect);
			rs = pState.executeQuery();
			list = convertToList(rs);
			rs.close();
			pState.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//12. Create a new ORDER tuple
	public void addOrder(int id, String date) {
		String sqlInsert = "INSERT INTO ORDERS(OrderID,Date) VALUES(?,?)";
		java.sql.Date sqlDate  = java.sql.Date.valueOf(date);
		try {
			PreparedStatement pState = conn.prepareStatement(sqlInsert);
			pState.setInt(1,id);
			pState.setDate(2, sqlDate);
			
			pState.executeUpdate();
			pState.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//13. Create new ORDERLINE tuple
	public void addOrderLine(int id, int tId, int sId, int quantity) {
		String sqlInsert = "INSERT INTO ORDERLINE(OrderID,ToolID,SupplierId,Quantity) VALUES(?,?,?,?)";
		
		try {
			PreparedStatement pState = conn.prepareStatement(sqlInsert);
			pState.setInt(1,id);
			pState.setInt(2, tId);
			pState.setInt(3, sId);
			pState.setInt(4, quantity);
			
			pState.executeUpdate();
			pState.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes the JDBC-SQL connection
	 */
	public void closeConnections() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
