package Server;
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======

>>>>>>> 63605bbe462af245219024846fee4fbfc62faa7f

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
>>>>>>> bf30a74a48db1c8b1a75417333d6bca6a2387f8a

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
	
	private NetServer(){
		threadPool = Executors.newFixedThreadPool(THREAD_NUM);
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
						
					} else if(msg.equals("REFRESH")){
						// REFRESH : check date & send table information
						
					} else if(msg.equals("SAVE")){
						// SAVE : check date & modify table information & send table information
						
					}
					
					sock_out.close();
					sock_in.close();
					
					//end connection
					client_socket.close();
					
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