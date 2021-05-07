package Client.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.DefaultListModel;
import Client.View.CustomerView;

public class CustomerViewController {
	
	//private instance variables
	private CustomerView view;
	private String messageToServer;
	private ClientController client;
	
	//Constructor - aggregates CustomerView and ClientController
	public CustomerViewController(CustomerView v, ClientController c) {
		this.view = v;
		this.client = c;
		view.addButtonListeners(new CustomerGUIListener());
		
		//add a window listener to the view, when GUI closed, can close client connections
		view.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	client.getSocketOut().println("QUIT");
		        client.closeSocket();
		    }
		});
	}
	
	class CustomerGUIListener implements ActionListener {

		//each button click will send a different query number to the server. The server reads which
		//query number to perform, and executes it on the server side before relaying a response back to the client.
		//Based on server's response, controller tells the view how to interact with the user
		public void actionPerformed(ActionEvent e) {

			//The Search button handles 3 different search types
			if(e.getSource()==view.getSearchButton()) {
				String searchInfo = view.getInput().getText();
				DefaultListModel<String> DLM = new DefaultListModel();
				
				//Button 1: search by client ID
				if(view.getB1().isSelected()) {
					int id = -1;
					try {
						id = Integer.parseInt(searchInfo); //test to see if input is an int or not
					}
					catch(NumberFormatException ne) {
						view.displaySearchErrorMessage();
					}
					if(id!=-1) {
						client.getSocketOut().println("Query 1:"+id);
						translateOneCustomer(DLM);
					}
				}
				
				//Button 2: search by client name
				else if(view.getB2().isSelected()) {
					client.getSocketOut().println("Query 2:"+searchInfo);
					translateOneCustomer(DLM);
				}
				
				//Button 3: search by client type
				else if(view.getB3().isSelected()) {
					//first test if the input is valid, can either be a C or R ONLY
					boolean valid = false;
					if(searchInfo.equals("C") || searchInfo.equals(("R"))){
						valid = true;
					}
					else {
						view.displaySearchTypeErrorMessage();
					}
					
					//valid char input for client type
					if(valid==true) {
						client.getSocketOut().println("Query 3:"+searchInfo);
						translateManyCustomers(DLM);
					}
				}
				else {
					view.searchTypeErrorMessage();
				}
			}
			
			//Button 4: clear search field (no connection to server)
			else if(e.getSource()==view.getClearButton()) {
				view.clearInput();
			}
			
			//Button 5: add OR update client info, both use the "save" button
			else if(e.getSource()==view.getSaveButton()) {
				
				if(testUserInput()==true) { //first must test if all inputs are valid (ie. id is an int)
					
					//Add new customer selected
					if(view.getB4().isSelected()) {
						client.getSocketOut().println("Query 4:"+messageToServer);
						try {
							if(checkServerMsg(client.getSocketIn().readLine())==true) {
								view.displayAddMessage();
								view.clearListDisplay();
							}
							else {
								view.displayAddErrorMessage();
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					
					//Update existing customer selected
					else if(view.getB5().isSelected()) {
						client.getSocketOut().println("Query 5:"+messageToServer);
						try {
							if(checkServerMsg(client.getSocketIn().readLine())==true) {
								view.displayUpdateMessage();
								view.clearListDisplay();
							}else {
								view.displayUpdateErrorMessage();
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					
					//if none selected, tells customer to make a choice
					else {
						view.updateOrAddMessage();
					}
				}
			}
			
			//button 6: delete selected client
			else if(e.getSource()==view.getDeleteButton()) {
				if(testUserInput()==true) {
					String id = view.getClientIDOut().getText();
					view.displayDeleteCheckMessage(id);
					client.getSocketOut().println("Query 6:"+id);
					
					try {
						if(checkServerMsg(client.getSocketIn().readLine())==true) {
							view.displayDeleteMessage();
							view.clearListDisplay();
						}else {
							view.displayDeleteErrorMessage();
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}				
			}
			
			//Button 7: clear client fields in view
			else if(e.getSource()==view.getClearButtonR()) {
				view.clearClientUpdateFields();
			}
			
			//Button 8: Get list of all clients
			else if(e.getSource()==view.getAllButton()) {
				DefaultListModel<String> DLM = new DefaultListModel();
				client.getSocketOut().println("Query 7:get all customers");
				translateManyCustomers(DLM);
			}
			
		}
	}
	
	//check if message from server is SUCCESS or FAILURE
	private boolean checkServerMsg(String s) {
		if(s.contains("SUCCESS")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//Tests to see if all inputs on right-hand-side panel are valid. Returns true only when
	//ALL conditions are satisfied
	private boolean testUserInput() {
		String id = view.getClientIDOut().getText();
		String fname = view.getfNameOut().getText();
		String lname = view.getlNameOut().getText();
		String address = view.getAddressOut().getText();
		String postal = view.getPostalOut().getText();
		String phone = view.getPhoneOut().getText();
		String type = view.getClientTypeOut().getText();
		
		//test to see if ID is an integer
		try {
			Integer.parseInt(id);
		}
		catch(NumberFormatException ne) {
			view.displaySearchErrorMessage();
			return false;
		}
		//test to see if postal code is formatted properly
		if(postal.length()!=7 || postal.charAt(3)!=' ') {
			view.displayPostalErrorMessage();
			return false;
		}
		
		//test to see if postal code is formatted properly
		if(phone.length()!=12 || phone.charAt(3)!='-' || phone.charAt(7)!='-') {//|| phone.charAt(7)!='-') {			
			view.displayPhoneErrorMessage();
			return false;
		}
		
		//test to see if phone number is int values
		String[] temp = phone.split("-");
		try {
			Integer.parseInt(temp[0]);
			Integer.parseInt(temp[1]);
			Integer.parseInt(temp[2]);
		}
		catch(NumberFormatException ne){
			view.displayPhoneErrorMessage();
			return false;
		}

		//test to see if client type is valid (either C or R valid)
		if((type.equals("C")) || (type.equals("R"))){
			//if all conditions have passed
			messageToServer = id+";"+fname+";"+lname+";"+address+";"+postal+";"+phone+";"+type;
			return true;

		}
		else {
			view.displaySearchTypeErrorMessage();
			return false;
		}
	}
	
	//Used to display search result of 1 customer after searching by ID or name. 
	//Gets message from server and then tells the view to display search result to user
	private void translateOneCustomer(DefaultListModel<String> DLM) {
		try {
			String serverMsg = client.getSocketIn().readLine();
			if(checkServerMsg(serverMsg)==true) {
				DLM.addElement(serverMsg.replace("SUCCESS:", "").replace(";", ","));
			}
			else {
				DLM.addElement("");
				view.displayNoMatchMessage();
			}
			view.setListDisplay(DLM); //calls method from view to display
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	//Used to display search result of many customers in a list after searching 
	//by client type
	//Gets message from server and then tells the view to display search result to user
	private void translateManyCustomers(DefaultListModel<String> DLM) {
		try {
			String serverMsg = client.getSocketIn().readLine();
			serverMsg = serverMsg.replace("SUCCESS:", "");
			String[] list = serverMsg.split("&"); //delimited by "&" symbol by the server
			for(String s:list) {
				DLM.addElement(s.replace(";", ","));
			}
			
			view.setListDisplay(DLM); //calls method from view to display
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}

