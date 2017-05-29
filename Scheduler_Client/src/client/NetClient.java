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
		manager.loadData();
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
		boolean result = false;
		
		try {
			
			DataOutputStream sock_out = new DataOutputStream(socket.getOutputStream());
			DataInputStream sock_in = new DataInputStream(socket.getInputStream());
			
			//in & out execute
			sock_out.writeUTF(msg);
			
			if(msg.equals("LOGIN") || msg.equals("REFRESH")){
				sock_out.writeUTF(manager.getID());
				
				msg = sock_in.readUTF();
				if(msg.equals("SUCCESS")) {

					//load information from server
					ObjectInputStream obj_in = new ObjectInputStream(socket.getInputStream());
					
					short[][] table = (short[][]) obj_in.readObject();
					String[] idList = (String[]) obj_in.readObject();
					String notice = sock_in.readUTF();
					
					manager.setOTable(table);
					manager.setIDlist(idList);
					manager.setNotice(notice);
					
					obj_in.close();
					
					result = true;
				} else {
					result = false;
				}
				
			} else if(msg.equals("SAVE")){
				sock_out.writeUTF(manager.getID());
				
				msg = sock_in.readUTF();
				if(msg.equals("SUCCESS")){
					
					ObjectOutputStream obj_out = new ObjectOutputStream(socket.getOutputStream());
					
					obj_out.writeObject(manager.getFixedSchedule());
					obj_out.writeObject(manager.getPTable());
					
					obj_out.close();
					
					ObjectInputStream obj_in = new ObjectInputStream(socket.getInputStream());
					
					short[][] table = (short[][]) obj_in.readObject();
					String[] idList = (String[]) obj_in.readObject();
					String notice = sock_in.readUTF();
					
					manager.setOTable(table);
					manager.setIDlist(idList);
					manager.setNotice(notice);
					
					obj_in.close();
					
					result = true;
				} else {
					result = false;
				}
			}
			
			sock_out.close();
			sock_in.close();
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void endConnection() {
		try {
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}