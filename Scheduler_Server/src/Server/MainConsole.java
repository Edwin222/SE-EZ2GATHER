package Server;

import java.util.Scanner;

public class MainConsole {
	
	public static void main(String args[]){
		NetServer.getInstance().getServer().makeID("ÃÖ±¤¿ø");
		
		NetServer.getInstance().openServer();
		
		
		//NetServer.getInstance().closeServer();
		
	}

}
