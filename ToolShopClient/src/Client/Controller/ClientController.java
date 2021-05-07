package Client.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientController  {
	
	//private instance variables
	private Socket socket;
	private BufferedReader stdIn;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	
	//Constructor
	public ClientController(String serverName, int portNumber) {
		//Establish connection with server
		try {
			socket = new Socket(serverName, portNumber);
			stdIn = new BufferedReader(new InputStreamReader(System.in));//keyboard input stream
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketOut = new PrintWriter(socket.getOutputStream(),true);
		} catch(UnknownHostException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//used to close connections with the server
	public void closeSocket() {
		System.out.println("CLOSING CONNECTIONS TO SERVER");
		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Getters
	public BufferedReader getSocketIn() {
		return socketIn;
	}
	public PrintWriter getSocketOut() {
		return socketOut;
	}

	
}
