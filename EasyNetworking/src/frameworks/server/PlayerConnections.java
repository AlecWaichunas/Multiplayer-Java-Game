package frameworks.server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;

@SuppressWarnings("serial")
public class PlayerConnections extends Panel{
	
	private Dimension size;
	private String[] disconnectedPlayers = new String[10];
	
	public PlayerConnections(int width){
		size = new Dimension(width - 50, 500);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		setLayout(new FlowLayout());
		size = new Dimension(width, 25);
		this.setBackground(Color.LIGHT_GRAY);
		
		PlayerPanel titleTags = new PlayerPanel(400, "Name", 0, 0, 0);
		titleTags.setX("X");
		titleTags.setY("Y");
		titleTags.setColor("Color");
		
		add(titleTags);
	}
	
	private void addDisconnectedPlayer(String player){
		for(int i = 0; i < disconnectedPlayers.length - 1; i++){
			if(disconnectedPlayers[i] == null) break;
			disconnectedPlayers[i + 1] = disconnectedPlayers[i];
		}
		disconnectedPlayers[0] = player;
	}
	
	public String getDisconnectedPlayers(){
		String plrs = "";
		if(disconnectedPlayers[0] == null) return plrs;
		for(int i = 0; i < disconnectedPlayers.length; i++){
			plrs += "D:" + disconnectedPlayers[i] + "\n";
		}
		return plrs;
	}
	
	public void addPlayer(String name, int x, int y, int color){
		System.out.println("Got new Player!");
		PlayerPanel p = new PlayerPanel(400, name, x, y, color);
		p.setBackground(Color.DARK_GRAY);
		if(this.getComponentCount() % 2 == 1){
			p.setBackground(Color.GRAY);
		}
		add(p);
		Dimension panelSize = new Dimension(this.getWidth(), this.getComponentCount() * 35);
		setPreferredSize(panelSize);
		setMaximumSize(panelSize);
		setMinimumSize(panelSize);
		this.validate();
	}

	public void updatePlayer(String name, int x, int y, int color){
		for(int i = 0; i < this.getComponentCount(); i++){
			if(((PlayerPanel)this.getComponent(i)).getPlayerName().startsWith(name)){
				((PlayerPanel) getComponent(i)).setX(String.valueOf(x));
				((PlayerPanel) getComponent(i)).setY(String.valueOf(y));
				((PlayerPanel) getComponent(i)).setColor(String.valueOf(color));
				break;
			}
		}
		this.validate();
	}
	
	public boolean playerExists(String playerName){
		for(int i = 0; i < this.getComponentCount(); i++){
			if(((PlayerPanel)this.getComponent(i)).getPlayerName().startsWith(playerName))
				return true;
		}
		return false;
	}
	
	public String returnPlayersToClient(){
		String players = "";
		for(int i = 0; i < this.getComponentCount(); i++){
			PlayerPanel pp = (PlayerPanel) getComponent(i);
			if(pp.getPlayerName().equals("Name")) continue;
			players += "P:" + 	pp.getPlayerName() + "," + 
								pp.getPlayerX() + "," + 
								pp.getPlayerY() + "," + 
								pp.getColor();
			players += "\n";
		}
		
		return players;
	}
	
	
	public void removePlayer(String name){
		if(name == null || name.equals("")) return;
		for(int i = 0; i < this.getComponentCount(); i++){
			if(((PlayerPanel) this.getComponent(i)).getPlayerName().startsWith(name)){
				this.remove(i);
				break;
			}
		}
		
		addDisconnectedPlayer(name);
		
		for(int i = 0; i < this.getComponentCount(); i++){
			((PlayerPanel) this.getComponent(i)).setLocation(0, i * 25);
		}
		this.validate();
	}
	
}
