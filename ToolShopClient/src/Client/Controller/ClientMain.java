package Client.Controller;

import java.util.InputMismatchException;
import java.util.Scanner;
import Client.View.CustomerView;
import Client.View.InventoryView;

public class ClientMain {

	public static void main(String args[]) {
		
		//change this field to swap between the GUI cases
		Scanner scan = new Scanner(System.in);
		System.out.println("Select from the following options below. What would you like to do?\n");
		System.out.println("1. Customer Management System");
		System.out.println("2. Inventory Management System");
		System.out.println("3. Both Management Systems");
		
		int choice;
		try {
			choice = scan.nextInt();
			scan.nextLine();
		}
		catch(InputMismatchException e){
			choice=0;
			scan.nextLine();
		}
		
		ClientController client = new ClientController("localHost",9898);
		
		switch(choice) {
		case 1: //just want to use Customer Management System
			CustomerView customerGUI = new CustomerView();
			CustomerViewController CVC = new CustomerViewController(customerGUI,client);
			break;
			
		case 2: //just want to use Inventory Management System
			InventoryView inventoryGUI = new InventoryView();
			InventoryViewController IVC = new InventoryViewController(inventoryGUI,client);
			break;
			
		case 3: //want to use both Customer and Inventory Management Systems
			customerGUI = new CustomerView();
			inventoryGUI = new InventoryView();
			CVC = new CustomerViewController(customerGUI,client);
			IVC = new InventoryViewController(inventoryGUI,client);
			break;
		}

	}

}