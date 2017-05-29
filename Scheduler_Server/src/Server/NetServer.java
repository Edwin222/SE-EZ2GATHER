package Server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;


public class NetServer {
	
	//singleton
	private static class Singleton {
		private static final NetServer Instance = new NetServer();
	}
	
	public static NetServer getInstance(){
		return Singleton.Instance;
	}
	
	//constant
	private final int THREAD_NUM = 8;
	private final int PORT_NUM = 5000;
	
	//implementation
	private ExecutorService threadPool;
	private ServerSocket server;
	
	//Server DB
	private ScheduleServer sServer;
	
	private NetServer(){
		threadPool = Executors.newFixedThreadPool(THREAD_NUM);
		sServer = new ScheduleServer();
	}
	
	public ScheduleServer getServer(){
		return sServer;
	}
	
	public void openServer(){
		try {
			server = new ServerSocket(PORT_NUM);
		} catch(Exception e){
			System.err.println("Server open error.");
			e.printStackTrace();
		}
		
		Runnable task = new Runnable(){
			@Override
			public void run(){
				System.out.println("Waiting Client in " + Thread.currentThread().getName() + "...");
				try {
					Socket client_socket = server.accept();
					//after connection
					System.out.println("Client Connected : " + client_socket.getInetAddress());
					
					DataOutputStream sock_out = new DataOutputStream(client_socket.getOutputStream());
					DataInputStream sock_in = new DataInputStream(client_socket.getInputStream());
					
					//in & out execute
					String msg = sock_in.readUTF();
					
					if(msg.equals("LOGIN")){
						// LOGIN : check date & send table information						

						String ID = sock_in.readUTF();
						
						if(sServer.checkID(ID)){ //login s
							sock_out.writeUTF("SUCCESS");
							sServer.nextDay();
							
							ObjectOutputStream obj_out = new ObjectOutputStream(client_socket.getOutputStream());
							
							obj_out.writeObject(sServer.getCommonSchedule());
							obj_out.writeObject(sServer.getIdList());
							
							obj_out.close();
							
						} else { //login f
							sock_out.writeUTF("FAIL");
						}
						
					} else if(msg.equals("REFRESH")){
						// REFRESH : check date & send table information
						
					} else if(msg.equals("SAVE")){
						// SAVE : check date & modify table information & send table information
						
					}
					
					sock_out.close();
					sock_in.close();
				
					//end connection
					client_socket.close();
					
					System.out.println("Client Disconnected : " + client_socket.getInetAddress());
					
				} catch(Exception e){
					e.printStackTrace();
					
				}
			}
		};
		
		for(int i=0;i<THREAD_NUM;i++){
			threadPool.submit(task);
		}
	}
	
	public void closeServer(){
		try {
			threadPool.shutdown();
			server.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
