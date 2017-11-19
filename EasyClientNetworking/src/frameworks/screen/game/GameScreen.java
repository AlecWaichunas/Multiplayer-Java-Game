package frameworks.screen.game;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;

import client.ClientSocket;
import frameworks.screen.MainScreen;
import frameworks.screen.SubScreen;
import frameworks.screen.game.entities.Player;

public class GameScreen extends SubScreen{
	

	public Player mainPlayer;
	private GameServerInfo gsi;
	private MapManager mm;
	
	public GameScreen(Dimension size, MainScreen ms, String name, MapManager mm, GameServerInfo gsi) {
		super(size, ms);
		this.gsi = gsi;
		this.mm = mm;
		mainPlayer = new Player(name, ms.getGameSize().width/2 - 16, 
				ms.getGameSize().height/2 - 16, (new Random()).nextInt());
		gsi.setMainPlayer(mainPlayer);
	}

	protected void init() {
	}

	public void mouseClicked(int button) {
		
	}
	
	public void mouseMoved(){
		
	}
	
	public void keyTyped(char key){
		
	}

	public void update() {
		
		if(keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP]) mm.move(this, 0, -2);
		if(keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]) mm.move(this, 0, 2);
		if(keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]) mm.move(this, -2, 0);
		if(keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]) mm.move(this, 2, 0);
		
		ms.getPixelManager().setOffset(mm.getX(), mm.getY());
		ms.getPixelManager().clear();
		
		mm.updateResources(gsi.getBerries(), gsi.getSticks(), gsi.getWebs()); 
		mm.renderMap(ms.getPixelManager());
		mm.renderObjects(gsi.getPlayers(), ms.getPixelManager());
		mm.renderPickedUpItems(ms.getPixelManager());
		ClientSocket.sendMessage("P:" + mainPlayer.getName() + "," + 
				(mm.getX() + mainPlayer.getX()) + "," + 
				(mm.getY() + mainPlayer.getY()) + "," +
				mainPlayer.getColorInt());
		gsi.readFromServer(this);
		updateObjects();
	}
	
	public void updateObjects(){
		ClientSocket.sendMessage("Update");
		gsi.readFromServer(this);
	}

	public void render(Graphics2D g) {
		gsi.renderPlayers(g);
	}
	
	public int getXOffset(){
		return mm.getX();
	}
	
	public int getYOffset(){
		return mm.getY();
	}

}
