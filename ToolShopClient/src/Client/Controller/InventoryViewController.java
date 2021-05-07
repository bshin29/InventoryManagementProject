package Client.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import Client.View.InventoryView;

public class InventoryViewController {
	
	//private instance variables
	private ClientController client;
	private InventoryView view;
	
	//Constructor - aggregates InventoryView and ClientController
	public InventoryViewController(InventoryView v, ClientController c) {
		this.view = v;
		this.client = c;
		view.addButtonListeners(new InventoryGUIListener());
		
		//add a window listener to the view, when GUI closed, can close client connection
		view.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	client.getSocketOut().println("QUIT"); //notify server that client is quitting
		        client.closeSocket();
		    }
		});
	}
		
	//private class to add Listeners to each button
	class InventoryGUIListener implements ActionListener {

		//Checks to see if server response says SUCCESS or FAILURE to the query it was sent
		private boolean checkServerMsg(String s) {
			if(s.contains("SUCCESS")) { //if the query executed was successful, the server will say "SUCCESS"
				return true;
			}
			else { //otherwise, it will say "FAILURE"
				return false;
			}
		}
		
		//Gets message from the server and translates it to display an the item found from a search 
		//query. Then tells the VIEW to what to display to user (can be success or error message)
		private void itemSearchResult() {
			try {
				String serverMsg = client.getSocketIn().readLine();
				if(checkServerMsg(serverMsg)==true) {
					serverMsg = serverMsg.replace("SUCCESS:", "");
					view.itemSearchResult(serverMsg);
				}
				else {
					view.displayNoMatchFoundMessage();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		
		//each button click will send a different query number to the server. The server reads which
		//query number to perform, and executes it on the server side before relaying a response back to the client.
		//Based on server's response, controller tells the view how to interact with the user
		public void actionPerformed(ActionEvent e) {
			
			//Button 1: clear display field
			if(e.getSource()==view.getButton1()) {
				view.clearDisplay();
			}
			
			//Button 2: List all items
			else if(e.getSource()==view.getButton2()) {
				client.getSocketOut().println("Query i1:ALLTOOLS"); //send msg to server via socket
				try {
					String serverMsg = client.getSocketIn().readLine();
					serverMsg = serverMsg.replace("SUCCESS:", "");
					view.translateList(serverMsg); 	
				} catch (IOException e1) {
					e1.printStackTrace();
				}		
			}
			
			//Button 3: Search by tool id
			else if(e.getSource()==view.getButton3()) {
				String s = view.getItemId();
				int id = -1;
				try {
					id = Integer.parseInt(s); //check if the input is an int
				}
				catch(NumberFormatException ne) {
					view.displayIDErrorMessage();
				}
				if(id!=-1) {
					client.getSocketOut().println("Query i2:"+id);
					itemSearchResult();
				}
			}
			
			//Button 4: Search by tool name
			else if(e.getSource()==view.getButton4()) {
				String text = view.getItemName();
				client.getSocketOut().println("Query i3:"+text);
				itemSearchResult();
			}

			//Button 5: Get item quantity
			else if(e.getSource()==view.getButton5()) {
				String text = view.getItemName();
				client.getSocketOut().println("Query i4:"+text);
				
				try {
					String serverMsg = client.getSocketIn().readLine();
					if(checkServerMsg(serverMsg)==true){
						serverMsg = serverMsg.replace("SUCCESS:", "");
						view.displayItemQuantity(text, serverMsg);
					}
					else {
						view.displayNoMatchFoundMessage();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			//Button 6: Decrease item quantity
			else if(e.getSource()==view.getButton6()) {
				String text = view.getItemName();
				client.getSocketOut().println("Query i5:"+text);
				
				try {
					String serverMsg = client.getSocketIn().readLine();
					if(checkServerMsg(serverMsg)==true) {
						serverMsg = serverMsg.replace("SUCCESS:", "");
						view.displayItemDecrement(text, serverMsg);
					}
					else {
						serverMsg = serverMsg.replace("FAILURE:", "");
						view.displayDecreaseError(serverMsg);
					}
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			//Button 7: Get all suppliers
			else if(e.getSource()==view.getButton7()) {
				client.getSocketOut().println("Query i6:ALLSUPPLIERS");
				try {
					String serverMsg = client.getSocketIn().readLine();
					serverMsg = serverMsg.replace("SUCCESS:", "");
					view.translateList(serverMsg); 
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			//Button 8: Get all orders and generate Orders.txt file
			else if(e.getSource()==view.getButton8()) {
				client.getSocketOut().println("Query i7:ALLORDERS");
				
				try {
					String serverMsg = client.getSocketIn().readLine();
					serverMsg = serverMsg.replace("SUCCESS:", "");
					view.displayOrderList(serverMsg);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
