package Server.Controller;

import Server.Model.CustomerModel.*;
import Server.Model.InventoryModel.*;

public class ServerMain {

	public static void main(String[] args) {
		
		//Create the Models
		Shop iModel = new Shop();
		CustomerList cModel = new CustomerList();
		
		//Database controller
		DatabaseController DBC = new DatabaseController();
	
		//Model Controllers
		CustomerModelController CMC = new CustomerModelController(DBC,cModel);
		InventoryModelController IMC = new InventoryModelController(DBC,iModel);
		
		//Set up server
		ServerController server = new ServerController(CMC,IMC);
		System.out.println("Server running ... ");
		server.runServer();
		
	}

}


