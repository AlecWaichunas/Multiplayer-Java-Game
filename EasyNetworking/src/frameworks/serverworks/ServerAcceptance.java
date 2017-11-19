package frameworks.serverworks;

import frameworks.server.PlayerConnections;
import frameworks.serverworks.game.Map;

public class ServerAcceptance implements Runnable{

	private PlayerConnections pc;
	private Map map;
	private Thread t;
	
	public ServerAcceptance(PlayerConnections pc, Map map){
		this.pc = pc;
		this.map = map;
	
		t = new Thread(this, "Server - Client Accpentance");
		t.start();
	}
	
	public void stop(){
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(Server.isUp()){
			Server.searchForClient(pc, map);
		}
	}

}
