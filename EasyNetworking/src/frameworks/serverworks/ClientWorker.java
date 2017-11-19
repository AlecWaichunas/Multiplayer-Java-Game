package frameworks.serverworks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import frameworks.server.PlayerConnections;
import frameworks.serverworks.game.Map;

public class ClientWorker implements Runnable{

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	private Thread t;
	private PlayerConnections pc;
	private Map map;
	
	private String playerName;
	public boolean hasString = false, 
					hasStick = false, 
					hasBerry = false;
	
	public ClientWorker(Socket socket, PlayerConnections pc, Map map){
		this.socket = socket;
		this.pc = pc;
		this.map = map;
		boolean start = true;
		try {
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Could not connet I/O ... not starting");
			start = false;
		}
		if(start){
			t = new Thread(this, "Server - ClientWorker");
			t.start();
		}
	}
	
	public void stop(){
		out.close();
		try {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			in.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("Could not shut down thread!");
		}
	}
	
	public void sendLines(String line){
		String[] lines = line.split("\n");
		for(int i = 0; i < lines.length; i++){
			out.println(lines[i]);
		}
		lines = pc.getDisconnectedPlayers().split("\n");
		for(int i = 0; i < lines.length; i++){
			out.println(lines[i]);
		}
	}
	
	
	public void run() {
		String line;
		while(socket.isConnected()){
			try {
				line = in.readLine();
				if(line == null || line.equals("")) continue;
				if(line.startsWith("P:")){
					String[] playerStats = line.split(",");
					//update player
					if(playerName != null || pc.playerExists(playerStats[0].substring(2))){
						pc.updatePlayer(playerStats[0].substring(2), 
								Integer.parseInt(playerStats[1]),
								Integer.parseInt(playerStats[2]),
								Integer.parseInt(playerStats[3]));
					}else{
						//create player!
						this.playerName = playerStats[0].substring(2);
						System.out.println(playerName);
						pc.addPlayer(playerStats[0].substring(2), 
								Integer.parseInt(playerStats[1]),
								Integer.parseInt(playerStats[2]),
								Integer.parseInt(playerStats[3]));
					}
					out.println("Players");
					sendLines(pc.returnPlayersToClient());
					out.flush();
				}else if(line.equalsIgnoreCase("Map")){
					out.println("Map");
					sendLines(map.GetClientMapData());
					System.out.println("Sent map lines");
					out.flush();
				}else if(line.equalsIgnoreCase("collected")){
					//collected resource
					//update server
					//update client
				}else if(line.equalsIgnoreCase("Update")){
					//send back resources && zombie movement
					out.println("Resources");
					sendLines(map.GetObjectData());
					out.flush();
				}else if(line.startsWith("PickUp")){
					String[] lines = line.split("`");
					System.out.println(lines.length);
					System.out.println(lines[0]);
					System.out.println(lines[1]);
					System.out.println(lines[2]);
					String[] coords = lines[2].split(",");
					if(lines[1].equalsIgnoreCase("Berry")){
						hasBerry = map.pickUpBerry(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
					}else if(lines[1].equalsIgnoreCase("Web")){
						hasString = map.pickUpWeb(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
					}else if(lines[1].equalsIgnoreCase("Stick")){
						hasStick = map.pickUpStick(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
					}	
				}else if(line.equalsIgnoreCase("Names")){
					//send back names
				}
			} catch (IOException e) {
				System.out.println("Server - Caught an I/O exception shutting down client!");
				pc.removePlayer(playerName);
				this.stop();
			}
			
		}
		out.close();
		try {
			in.close();
			if(!socket.isClosed()) socket.close();
		} catch (IOException e) {
			System.out.println("Could not close server client worker!");
		}
	}

}
