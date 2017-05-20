package Server;

import java.nio.channels.*;
import java.util.concurrent.*;

public class Server {
	//singleton
	private static Server instance;
	final int MAX_CLIENT = 8;
	ExecutorService threadPool;
	
	private Server(){
		threadPool = Executors.newFixedThreadPool(MAX_CLIENT);
	}
}
