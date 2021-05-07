package Server.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerController {
	
	//instance variables used to create server
	private ServerSocket serverSocket;
	private ExecutorService pool;
	private Socket socket;
	private BufferedReader fromClient;
	private PrintWriter toClient;
	
	//instances of both model controllers aggregated by this class, to tell model controllers
	//what to do
	CustomerModelController CMC;
	InventoryModelController IMC;

	//Constructor
	public ServerController(CustomerModelController c, InventoryModelController i) {
		CMC = c;
		IMC = i;
		
		try {
			serverSocket = new ServerSocket(9898);
			pool = Executors.newFixedThreadPool(5);
			
		} catch (IOException e) {
			System.out.println("Error creating the socket");
			System.out.println(e.getMessage());
		}
	}
	
	//run the server and interact with client connections
	public void runServer() {
		try {
			while(true) {
				//establish connections
				socket = serverSocket.accept();
				System.out.println("Server: Connection accepted by the server");
				fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				toClient = new PrintWriter(socket.getOutputStream(),true);
				
				//capitalize runnable
				Messenger connector = new Messenger(toClient,fromClient);
				pool.execute(connector);
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}

		pool.shutdown();
		
		//disconnect all connections
		try {
			fromClient.close();
			toClient.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//getters
	public PrintWriter getSocketOut() {
		return toClient;
	}
	public BufferedReader getSocketIn() {
		return fromClient;
	}

	//private runnable class that handles client/server communication
	private class Messenger implements Runnable {
			
		private PrintWriter socketOut;
		private BufferedReader socketIn;
		
		public Messenger(PrintWriter sOut, BufferedReader sIn) {
			this.socketOut = sOut;
			this.socketIn = sIn;
		}

		//run method used to wait for client input using socketIn, and send a message to 
		//client using socketOut
		public void run() {
			String line = null;
			while(true) {
				try {
					line = socketIn.readLine();
					if(line.equals("QUIT")) {
						line = "Terminating program ...";
						System.out.println("Client has closed their application");
						break;
					}
					System.out.println("Server: request from client: "+line);
					translateClientMsg(line);
					
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		//translates the message from client and informs server of the query number the user has chosen
		//For each query number, the ServerController tells the model controllers to execute some command, 
		//then sends a response back to client.
		private void translateClientMsg(String s) {
			
			//ALL CLIENT View Responses
			//Query 1: Search for client by ID
			if(s.contains("Query 1")) {
				String[] temp = s.split(":");
				int id = Integer.parseInt(temp[1]);
				String match = CMC.searchById(id); //tells model to search by id
				socketOut.println(match); //return string sent as response back to client
			}
			
			//Query 2: Search for client by first and last name			
			else if(s.contains("Query 2")) {
				String[] temp = s.split(":");
				String[] name = temp[1].split(" ");
				String match;
				try {
					match = CMC.searchByName(name[0],name[1]);
				} catch(NullPointerException e) {
					match = "FAILURE:No client with that name"; //"FAILURE" keyword will be caught by client, so it knows their query failed
				} catch(ArrayIndexOutOfBoundsException a) {
					match = "FAILURE:No client with that name";
				}
				socketOut.println(match);
			}
			
			//Query 3: Search for clients by type
			else if(s.contains("Query 3")) {
				String[] temp = s.split(":");
				String match = CMC.searchByType(temp[1].charAt(0));
				socketOut.println(match);
			}
			
			
			//Query 4: Add a new client
			else if(s.contains("Query 4")) {
				String[] temp = s.split(":");
				String[] temp2 = temp[1].split(";");
				
				String result = CMC.addNewCustomer(Integer.parseInt(temp2[0]), temp2[1], temp2[2], 
						temp2[3], temp2[4], temp2[5], temp2[6].charAt(0));
				
				socketOut.println(result); //returns SUCCESS to client change made, FAILURE otherwise
			}
			
			//Query 5: Update existing client
			else if(s.contains("Query 5")) {
				String[] temp = s.split(":");
				String[] temp2 = temp[1].split(";");
				
				String result = CMC.updateCustomer(Integer.parseInt(temp2[0]), temp2[1], temp2[2], 
						temp2[3], temp2[4], temp2[5], temp2[6].charAt(0));
				
				socketOut.println(result); //returns SUCCESS to client change made, FAILURE otherwise
			}
			
			//Query 6: Deleting existing client
			else if(s.contains("Query 6")) {
				String[] temp = s.split(":");
				int id = Integer.parseInt(temp[1]);
				
				String result = CMC.deleteCustomer(id);
				socketOut.println(result);
			}
			
			//Query 7: Get all customers
			else if(s.contains("Query 7")) {
				String result = CMC.allCustomers();
				socketOut.println(result);
			}
			

			//ALL INVENTORY View Responses (i = Inventory)
			//Query 8: Get all tools
			else if(s.contains("Query i1")) {
				String match = IMC.getAllItems();
				socketOut.println(match);
			}
			
			//Query 9: Search for item by id number
			else if(s.contains("Query i2")) {
				String[] temp = s.split(":");
				int id = Integer.parseInt(temp[1]);
				String match = IMC.searchForItemById(id);
				socketOut.println(match);
			}
			
			//Query 10: Search for item by name
			else if(s.contains("Query i3")) {
				String[] temp = s.split(":");
				String match = IMC.searchForItemByName(temp[1]);
				socketOut.println(match);
			}
			
			//Query 11: Get Item Quantity
			else if(s.contains("Query i4")) {
				String[] temp = s.split(":");
				String result = IMC.checkItemQuantity(temp[1]);
				socketOut.println(result);
			}
			
			//Query 12: Decrement Item Quantity
			else if(s.contains("Query i5")) {
				String[] temp = s.split(":");
				String result = IMC.decreaseItem(temp[1]);
				socketOut.println(result);
			}
			
			//Query 13: Get all suppliers
			else if(s.contains("Query i6")) {
				String match = IMC.getAllSuppliers();
				socketOut.println(match);
			}
			
			//Query 14: Print Orders
			else if(s.contains("Query i7")) {
				String result = IMC.getAllOrders();
				socketOut.println(result);
			}
		}
		
	}

}
