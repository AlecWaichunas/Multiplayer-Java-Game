package frameworks.serverworks;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import frameworks.server.PlayerConnections;
import frameworks.server.ServerStatus;
import frameworks.serverworks.game.Map;

public class Server {

	private static ServerSocket server;
	private static List<ClientWorker> cw = new ArrayList<ClientWorker>();
	
	public static void startServer(int port, ServerStatus ss){
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Server is active or not available; exiting...");
			System.exit(0);
		}
	}
	
	public static boolean isUp(){
		return (server != null);
	}
	
	public static void closeServer(){
		try {
			server.close();
			server = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void searchForClient(PlayerConnections pc, Map map){
		try {
			cw.add(new ClientWorker(server.accept(), pc, map));
		} catch (IOException e) {
			System.out.println("Could not connect to client!");
		}
	}
	
	public static void shutDownAllClients(){
		for(int i = 0; i < cw.size(); i++)
			cw.get(i).stop();
	}
	
	
}
