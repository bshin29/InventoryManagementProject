package Client.View;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

public class InventoryView extends JFrame {
	
	//Variables for GUI components
	JPanel inputPanel = new JPanel();
	JTextArea display = new JTextArea(200,200);
	JTextField inputField = new JTextField(15); 
	JButton button1 = new JButton("Clear Display");
	JButton button2 = new JButton("Get All Tools");
	JButton button3 = new JButton("Search Tool by ID");
	JButton button4 = new JButton("Search Tool by Name");
	JButton button5 = new JButton("Get Quantity of Item"); //by name
	JButton button6 = new JButton("Decrease Item Quantity"); //by name
	JButton button7 = new JButton("Get All Suppliers");
	JButton button8 = new JButton("Print Orders");

	
	//Constructor
	public InventoryView() {
		makeGUI();
	}
	
	//makes all aspects of GUI
	private void makeGUI() {
		
		JLabel menu = new JLabel("Menu Options",SwingConstants.CENTER);
		menu.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		inputPanel.add(menu);
		JButton[] buttons = {button1,button2,button3,button4,button5,button6,button7,button8};
		for(int i=0;i<8;i++) {
			inputPanel.add(buttons[i]);
		}
		inputPanel.setLayout(new GridLayout(9,1));

		JSplitPane panes = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPanel, 
				new JScrollPane(display,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        panes.setDividerLocation(300);
        setContentPane(panes);
        
		setPreferredSize(new Dimension(650,400));
		setTitle("Inventory Management System");   
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
	}

	//Adds actionlisteners to each button, this is done in VIEW CONTROLLER
	public void addButtonListeners(ActionListener listener) {
		JButton[] buttons = {button1,button2,button3,button4,button5,button6,button7,button8};
		for(int i=0;i<8;i++) {
			buttons[i].addActionListener(listener);
		}
	}
	
	//gets input from user for item id (as string, converted to int by controller)
	public String getItemId() {
		String text = JOptionPane.showInputDialog("Please enter item ID");
		return text;
	}
	//gets input from user for item name
	public String getItemName() {
		String text = JOptionPane.showInputDialog("Please enter item name");
		return text;
	}
	
	//GUI DISPLAY METHODS
	public void clearDisplay() {
		display.setText("");
	}
	
	//formats a list of items/suppliers
	public void translateList(String serverMsg) {
		clearDisplay();

		String[] temp = serverMsg.split("%");
		for(String s:temp) {
			String out = s;
			display.append(out+"\n");
		}
	}
	
	//formats the view's display when an item is found
	public void itemSearchResult(String serverMsg) {
		clearDisplay();

		String temp[] = serverMsg.split(", ");
		display.append("Item ID: "+temp[0]+"\n");
		display.append("Name: "+temp[1]+"\n");
		display.append("Quantity: "+temp[2]+"\n");
		display.append("Price: "+temp[3]+"\n");
		display.append("Supplier ID: "+temp[4]+"\n");
		display.append("Item Type: "+temp[5]+"\n");
		try {
			display.append("Power Type: "+temp[6]+"\n");
		} catch(ArrayIndexOutOfBoundsException e) {
			
		}
	}
	
	//prints quantity of item to display
	public void displayItemQuantity(String name, String quantity) {
		display.setText("Quantity of "+name+" is: "+quantity);
	}
	
	//display for decrease item quantity button
	public void displayItemDecrement(String name, String serverMsg) {
		display.setText("Decreased quantity of "+name+" to "+serverMsg.replace("%CASE2",""));
		
		if(serverMsg.contains("%CASE2")) { //if server message says "CASE 2" it means that an order has been automatically placed
			display.append("\nNOTE: Quantity running low, order automatically placed");
		}
	}
	
	//prints all orders to display, while also creating orders.txt file
	public void displayOrderList(String serverMsg) {
		clearDisplay();
		display.append("NOTE: Generated Orders.txt file with the following orderlines:\n\n");
		
		generateOrderFile(serverMsg); //generates order.txt file using message from server
		String[] temp = serverMsg.split("%");
		for(String s:temp) {
			String out = s.replace(";",", ").replaceAll(", $", "");
			display.append(out+"\n");
		}
	}
	
	
	//make orders.txt file 
	private void generateOrderFile(String serverMsg) {
		String[] temp = serverMsg.split("%");
		int previousOrder=0;
		
		try {
			FileWriter writer = new FileWriter("Orders.txt");
			for(String s:temp) {
				String[] orderLine = s.split(";");
				int currentOrder = Integer.parseInt(orderLine[0]);
				
				if(currentOrder!=previousOrder) {
					writer.write("\n****************************************"
							+ "************************************"+System.lineSeparator()+System.lineSeparator());
					writer.write(("ORDER ID:\t"+currentOrder+System.lineSeparator()));
					writer.write(("Order Date:\t"+orderLine[1])+System.lineSeparator());
					previousOrder = currentOrder;
				}
				writer.write(System.lineSeparator());
				writer.write("Item id:\t"+orderLine[2]+System.lineSeparator());
				writer.write("Supplier id:\t"+orderLine[3]+System.lineSeparator());
				writer.write("Quantity:\t"+orderLine[4]+System.lineSeparator());
			}
			writer.close();
		}
		catch(IOException e) { 
			e.printStackTrace(); 
			System.exit(1); 
		}
	}
	
	
	//ALL error messages to interact with the user
	public void displayNoMatchFoundMessage() {
		JOptionPane.showMessageDialog(this, "No matching item found in database");
	}
	public void displayDecreaseError(String caseNo) {
		if(caseNo.equals("CASE1")) {
			JOptionPane.showMessageDialog(this, "Item does not exist, cannot decrease");
		}
		else if(caseNo.equals("CASE2")) {
			JOptionPane.showMessageDialog(this, "Item's quantity already 0, cannot decrease");
		}
	}
	public void displayIDErrorMessage() {
		JOptionPane.showMessageDialog(this, "Please enter an int for id");
	}
	
	
	//Getter methods
	public JButton getButton1() {
		return this.button1;
	}
	public JButton getButton2() {
		return button2;
	}
	public JButton getButton3() {
		return button3;
	}
	public JButton getButton4() {
		return button4;
	}
	public JButton getButton5() {
		return button5;
	}
	public JButton getButton6() {
		return button6;
	}
	public JButton getButton7() {
		return button7;
	}
	public JButton getButton8() {
		return button8;
	}

}
