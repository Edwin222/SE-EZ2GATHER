package client;

import java.net.*;
import java.io.*;


public class NetClient {
	private String IP;
	private int port;
	private Socket socket;
	private UserManager manager;
	
	public NetClient(String IP, int port, String ID){
		this.IP = IP;
		this.port = port;
		
		manager = new UserManager(ID);
	}
	
	public UserManager getManager(){
		return manager;
	}
	public void connectToServer(){
		try {
			socket = new Socket(IP, port);
			
		} catch(Exception e){
			System.err.println("Connection error");
			e.printStackTrace();
		}
		
	}
	
	public boolean sendMessage(String msg){
		try {
			
			DataOutputStream sock_out = new DataOutputStream(socket.getOutputStream());
			DataInputStream sock_in = new DataInputStream(socket.getInputStream());
			
			//in & out execute
			sock_out.writeUTF(msg);
			
			sock_out.close();
			sock_in.close();
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return true;
	}
	
	public void endConnection() {
		try {
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}