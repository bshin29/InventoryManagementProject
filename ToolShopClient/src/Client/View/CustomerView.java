package Client.View;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomerView extends JFrame {

	//searchUI panel (top-left)
	private JPanel searchUI = new JPanel();
	private JTextField input = new JTextField(15);
	
	//searchResults panel (bottom-left)
	private JPanel outPanel;
	private JList<String> searchList;
	
	//all buttons
	private JRadioButton b1,b2,b3,b4,b5;
	private JButton searchButton = new JButton("Search");
	private JButton clearButton = new JButton("Clear Search");
	private JButton saveButton = new JButton("Save Info");
	private JButton deleteButton = new JButton("Delete");
	private JButton clearButtonR = new JButton("Clear");
	private JButton allButton = new JButton("All");
	
	//JTextAreas
	private JTextArea clientIDOut,fNameOut,lNameOut,addressOut,postalOut,phoneOut,clientTypeOut;
	
	//Constructor
	public CustomerView() {
		makeGUI();
	}

	//method used to create all visual aspects of GUI
	private void makeGUI() {
		
		//search UI panel (top-left)
		searchUI.setLayout(new GridLayout(7,1));
		JLabel panelTitle = new JLabel("Search Clients",SwingConstants.CENTER);
		panelTitle.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		searchUI.add(panelTitle);
		searchUI.add(new JLabel("Select type of search to be performed"));
		
		b1 = new JRadioButton("Client ID");
		b2 = new JRadioButton("Full Name");
		b3 = new JRadioButton("Client Type");
		
		searchUI.add(b1);
		searchUI.add(b2);
		searchUI.add(b3);
		
		searchUI.add(new JLabel("Enter the search parameter below: "));
		
		JPanel searchBar = new JPanel();
		searchBar.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		searchBar.add(input,gbc);
		gbc.gridx = 1;
		searchBar.add(searchButton,gbc);
		gbc.gridx = 2;
		searchBar.add(clearButton,gbc);
		gbc.gridx = 3;
		searchBar.add(allButton,gbc);

		searchUI.add(searchBar);
		
		
		//search results UI
		outPanel = new JPanel();
		outPanel.setLayout(new FlowLayout());
		outPanel.add(new JLabel("Search Results"));
		
		searchList = new JList();
		searchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		JScrollPane listScrollPane = new JScrollPane(searchList);
		listScrollPane.setPreferredSize(new Dimension(450,200));
		
		outPanel.add(listScrollPane);
		
		//right-side Panel
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(10,1));
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JLabel rightTitle = new JLabel("Client Information",SwingConstants.CENTER);
		rightTitle.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		rightPanel.add(rightTitle);
		
		JLabel clientID = new JLabel("Client ID");
		clientIDOut = new JTextArea(1,5);
		formatPanel(clientID,clientIDOut,rightPanel);
		
		JLabel fName = new JLabel("First Name");
		fNameOut = new JTextArea(1,10);
		formatPanel(fName,fNameOut,rightPanel);
		
		JLabel lName = new JLabel("Last Name");
		lNameOut = new JTextArea(1,10);
		formatPanel(lName,lNameOut,rightPanel);
		
		JLabel address = new JLabel("Address");
		addressOut = new JTextArea(1,15);
		formatPanel(address,addressOut,rightPanel);
		
		JLabel postal = new JLabel("Postal Code");
		postalOut = new JTextArea(1,10);
		formatPanel(postal,postalOut,rightPanel);
		
		JLabel phone = new JLabel("Phone Number");
		phoneOut = new JTextArea(1,10);
		formatPanel(phone,phoneOut,rightPanel);
		
		JLabel clientType = new JLabel("Client Type");
		clientTypeOut = new JTextArea(1,2);
		formatPanel(clientType,clientTypeOut,rightPanel);

		b4 = new JRadioButton("Add New Client");
		b5 = new JRadioButton("Update Existing Client");
		JPanel temp = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridx = 1;
		temp.add(b4,gbc);
		gbc.gridx = 1;
		temp.add(b5,gbc);
		rightPanel.add(temp);
		
		JPanel buttons = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		buttons.add(saveButton,gbc);
		gbc.gridx = 1;
		buttons.add(deleteButton,gbc);
		gbc.gridx = 2;
		buttons.add(clearButtonR,gbc);
		rightPanel.add(buttons);
		
		//add mouse click event to search result list to display result to client-info panel
		MouseListener m = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==1) {
					try {
						String[] line = searchList.getSelectedValue().split(",");
						clientIDOut.setText(line[0]);
						fNameOut.setText(line[1]);
						lNameOut.setText(line[2]);
						addressOut.setText(line[3]);
						postalOut.setText(line[4]);
						phoneOut.setText(line[5]);
						clientTypeOut.setText(line[6]);
					} catch(NullPointerException n) { }
				}
			}
		};
		searchList.addMouseListener(m);
		
		//add everything to the left-panel
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(2,1));
		leftPanel.add(searchUI);
		leftPanel.add(outPanel);

		
		//add left and right sides to GUI
		this.setLayout(new GridLayout(1,2));
		this.add(leftPanel);
		this.add(rightPanel);
		
		//display GUI
		setPreferredSize(new Dimension(950,500));
		setTitle("Client Management System");    
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	//private method to format label - output - panel combos into one line
	private void formatPanel(JLabel label, JTextArea out, JPanel panel) {
		JPanel temp = new JPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		temp.add(label,gbc);
		gbc.gridx = 1;
		temp.add(out,gbc);
		
		panel.add(temp);
	}

	//method used to add action listeners to each button
	public void addButtonListeners(ActionListener listener) {
		JRadioButton[] radioButtons = {b1,b2,b3,b4,b5};
		for(int i=0;i<5;i++) {
			radioButtons[i].addActionListener(listener);
		}
		
		JButton[] buttons = {searchButton,clearButton,saveButton,deleteButton,clearButtonR,allButton};
		for(int i=0;i<6;i++) {
			buttons[i].addActionListener(listener);
		}		
	}
	
	//Methods used to alter the view - to be called by controller
	//displays a list of clients in left-hand-side search list
	public void setListDisplay(DefaultListModel<String> DLM) {
		searchList.setModel(DLM);
	}
	
	//clears the display
	public void clearListDisplay() {
		DefaultListModel<String> DLM = new DefaultListModel();
		DLM.addElement("");
		searchList.setModel(DLM);
	}
	
	//clears the input fields
	public void clearInput() {
		input.setText("");
		clearListDisplay();
	}
	
	//clears client update/input fields on right-side of GUI
	public void clearClientUpdateFields() {
		getClientIDOut().setText("");
		getfNameOut().setText("");
		getlNameOut().setText("");
		getAddressOut().setText("");
		getPostalOut().setText("");
		getPhoneOut().setText("");
		getClientTypeOut().setText("");
	}
	

	//ALL display messages to interact with the user
	public void displayDeleteMessage() {
		JOptionPane.showMessageDialog(this, "Successfully deleted customer");
	}
	public void updateOrAddMessage() {
		JOptionPane.showMessageDialog(this, "Are you updating or adding? Please make a selection");
	}
	public void searchTypeErrorMessage() {
		JOptionPane.showMessageDialog(this, "Please select a search type and try again.");
	}
	public void displayUpdateMessage() {
		JOptionPane.showMessageDialog(this, "Successfully updated customer");
	}
	public void displayAddMessage() {
		JOptionPane.showMessageDialog(this, "Succesfully added new customer");
	}
	public void displaySearchErrorMessage() {
		JOptionPane.showMessageDialog(this, "Error in input, ID must be an int value. Please try again");
	}
	public void displaySearchTypeErrorMessage() {
		JOptionPane.showMessageDialog(this, "Client type is either C or R only. Please try again");
	}
	public void displayPostalErrorMessage() {
		JOptionPane.showMessageDialog(this, "Postal code must have format ### ###. Please try again");
	}
	public void displayPhoneErrorMessage() {
		JOptionPane.showMessageDialog(this, "Phone number must have format ###-###-####. Please try again");
	}
	public void displayNoMatchMessage() {
		JOptionPane.showMessageDialog(this, "No match found for search criteria. Please try again");
	}
	public void displayUpdateErrorMessage() {
		JOptionPane.showMessageDialog(this, "Cannot update a customer that does not exist. Please try again");
	}
	public void displayDeleteErrorMessage() {
		JOptionPane.showMessageDialog(this, "Cannot delete a customer that does not exist. Please try again");
	}
	public void displayAddErrorMessage() {
		JOptionPane.showMessageDialog(this, "Customer already exists with that ID. Please try again");
	}
	public void displayDeleteCheckMessage(String id) {
		if(id.equals("")) {
			JOptionPane.showMessageDialog(this, "Please select a client to delete");
		}
	}

	//Getters
	public JTextField getInput() {
		return input;
	}
	public JRadioButton getB1() {
		return b1;
	}
	public JRadioButton getB2() {
		return b2;
	}
	public JRadioButton getB3() {
		return b3;
	}
	public JRadioButton getB4() {
		return b4;
	}
	public JRadioButton getB5() {
		return b5;
	}
	public JButton getSearchButton() {
		return searchButton;
	}
	public JButton getClearButton() {
		return clearButton;
	}
	public JButton getSaveButton() {
		return saveButton;
	}
	public JButton getDeleteButton() {
		return deleteButton;
	}
	public JButton getAllButton() {
		return allButton;
	}
	public JButton getClearButtonR() {
		return clearButtonR;
	}
	public JTextArea getfNameOut() {
		return fNameOut;
	}
	public JTextArea getlNameOut() {
		return lNameOut;
	}
	public JTextArea getAddressOut() {
		return addressOut;
	}
	public JTextArea getPostalOut() {
		return postalOut;
	}
	public JTextArea getPhoneOut() {
		return phoneOut;
	}
	public JTextArea getClientIDOut() {
		return clientIDOut;
	}
	public JTextArea getClientTypeOut() {
		return clientTypeOut;
	}
}


