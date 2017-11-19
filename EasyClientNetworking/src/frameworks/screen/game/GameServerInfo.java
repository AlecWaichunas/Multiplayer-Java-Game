package frameworks.screen.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import client.ClientSocket;
import frameworks.screen.game.entities.Player;

public class GameServerInfo {
	
	private Player mainPlayer;
	private Player[] otherPlayers = new Player[100];
	
	private int mapWidth, mapHeight;
	private int[] mapTilesInfo;
	private List<Point> trees = new ArrayList<Point>();
	private List<Point> bushes = new ArrayList<Point>();
	private List<Point> berries = new ArrayList<Point>();
	private List<Point> webs = new ArrayList<Point>();
	private List<Point> sticks = new ArrayList<Point>();
	
	
	public void readFromServer(GameScreen gs){
		String content = ClientSocket.readMessages();
		if(content.contains("Players"))	readEntityString(gs, content);
		else if(content.contains("Map")) readMapString(content);
		else if(content.contains("Resources")) readObjectString(content);
	}	

	//Entity CRAP!	
	public void setMainPlayer(Player mainPlayer){
		this.mainPlayer = mainPlayer;
	}
	
	public Player getMainPlayer(){
		return mainPlayer;
	}
	
	public void updateMainPlayer(int x, int y){
		mainPlayer.updatePos(x, y);
	}
	
	public void renderPlayers(Graphics2D g){
		for(int i = 0; i < otherPlayers.length; i++){
			if(otherPlayers[i] == null) break;
			otherPlayers[i].render(g);
		}
		
		mainPlayer.render(g);
	}
	
	public Player[] getPlayers(){
		Player[] players = new Player[otherPlayers.length + 1];
		players[0] = mainPlayer;
		for(int i = 0; i < otherPlayers.length; i++){
			players[i + 1] = otherPlayers[i];
		}
		return players;
	}
	
	private void readEntityString(GameScreen gs, String entities){
		String[] entity = entities.split("\n");
		for(int i = 0; i < entity.length; i++){
			if(entity[i].startsWith("P:")){
				//look for players
				boolean found = false;
				String[] playerStats = entity[i].split(",");
				if(playerStats[0].substring(2).equals(mainPlayer.getName())) continue;
				for(int c = 0; c < otherPlayers.length; c++){
					if(otherPlayers[c] != null && 
							otherPlayers[c].getName().equals(playerStats[0].substring(2))){
						otherPlayers[c].updatePos(Integer.parseInt(playerStats[1]) - gs.getXOffset(), 
												Integer.parseInt(playerStats[2]) - gs.getYOffset());	
						found = true;
						break;
					}else if(otherPlayers[c] == null && !found){
						//System.out.println("Added new player: " + playerStats[0].substring(2));
						if(otherPlayers[0] != null) System.out.println("Exists!");
						otherPlayers[c] = new Player(playerStats[0].substring(2),
													Integer.parseInt(playerStats[1]), 
													Integer.parseInt(playerStats[2]),
													Integer.parseInt(playerStats[3]));
						break;
					}
				}
			}else if(entity[i].startsWith("D:")){
				//disconnect client!
				//System.out.println("Disconnected Client");
				int nullPointer = -1;
				for(int c = 0; c < otherPlayers.length; c++){
					if(otherPlayers[c] != null && 
							otherPlayers[c].getName().equals(entity[i].substring(2))){
						otherPlayers[c] = null;
						nullPointer = c;
					}
				}
				
				if(nullPointer >= 0){
					for(; nullPointer < otherPlayers.length - 1; nullPointer++){
						otherPlayers[nullPointer]  = otherPlayers[nullPointer + 1];
						otherPlayers[nullPointer + 1] = null;
					}
				}
			}else if(entity[i].startsWith("Z:")){
				//ZOMBIE!
				
			}
		}
	}
	
	private void readObjectString(String objects){
		berries.clear();
		webs.clear();
		sticks.clear();
		String[] objectList = objects.split("\n");
		for(int i = 0; i < objectList.length; i ++){
			String[] coords = objectList[i].split(",");
			if(objectList[i].startsWith("R:"))
				berries.add(new Point(Integer.parseInt(coords[0].substring(2)), Integer.parseInt(coords[1])));
			else if(objectList[i].startsWith("W:"))
				webs.add(new Point(Integer.parseInt(coords[0].substring(2)), Integer.parseInt(coords[1])));
			else if(objectList[i].startsWith("S:"))
				sticks.add(new Point(Integer.parseInt(coords[0].substring(2)), Integer.parseInt(coords[1])));
		}
	}
	
	//MAP CRAP
	private void readMapString(String map){
		System.out.println("Reading Map!");
		String[] mapContent = map.split("\n");
		mapWidth = Integer.parseInt(mapContent[1]);
		mapHeight = Integer.parseInt(mapContent[2]);
		mapTilesInfo = new int[mapWidth * mapHeight];
		String[] mapInfo = mapContent[3].split(",");
		for(int i = 0; i < mapInfo.length; i++){
			mapTilesInfo[i] = Integer.parseInt(mapInfo[i]);
		}
		for(int i = 3; i < mapContent.length; i++){
			String[] split = mapContent[i].split(", ");
			if(mapContent[i].startsWith("B:")){
				//put in bush
				bushes.add(new Point(Integer.parseInt(split[0].substring(2)), Integer.parseInt(split[1])));
			}else if(mapContent[i].startsWith("T:")){
				//put in tree
				System.out.println("Got TREE");
				trees.add(new Point(Integer.parseInt(split[0].substring(2)), Integer.parseInt(split[1])));
			}
		}
		System.out.println("Done Reading Map");
	}
	
	public int getMapWidth(){
		return mapWidth;
	}
	
	public int getMapHeight(){
		return mapHeight;
	}
	
	public int[] getMapInfo(){
		return mapTilesInfo;
	}
	
	public Point[] getTrees(){
		Point[] trees = new Point[this.trees.size()];
		for(int i = 0; i < this.trees.size(); i++){
			trees[i] = this.trees.get(i);
		}
		return trees;
	}
	
	public Point[] getBushes(){
		Point[] bushes = new Point[this.bushes.size()];
		for(int i = 0; i < this.bushes.size(); i++){
			bushes[i] = this.bushes.get(i);
		}
		return bushes;
	}
	
	public Point[] getBerries(){
		Point[] berries = new Point[this.berries.size()];
		for(int i = 0; i < this.berries.size(); i++){
			berries[i] = this.berries.get(i);
		}
		return berries;
	}
	
	public Point[] getSticks(){
		Point[] sticks = new Point[this.sticks.size()];
		for(int i = 0; i < this.sticks.size(); i++){
			sticks[i] = this.sticks.get(i);
		}
		return sticks;
	}
	
	public Point[] getWebs(){
		Point[] webs = new Point[this.webs.size()];
		for(int i = 0; i < this.webs.size(); i++){
			webs[i] = this.webs.get(i);
		}
		return webs;
	}
	
}
