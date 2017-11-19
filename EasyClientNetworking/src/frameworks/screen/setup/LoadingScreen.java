package frameworks.screen.setup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import client.ClientSocket;
import frameworks.screen.MainScreen;
import frameworks.screen.SubScreen;
import frameworks.screen.game.GameScreen;
import frameworks.screen.game.GameServerInfo;
import frameworks.screen.game.MapManager;

public class LoadingScreen extends SubScreen{

	private String[] coolLoadingSayings = {"Loading guns", "Picking Trees", "Boarding windows"};
	private Font f = new Font(Font.MONOSPACED, Font.PLAIN, 22);
	private FontMetrics fm;
	private int selectedSaying = 0;
	private long timer = System.currentTimeMillis();
	
	private MapManager mm;
	private GameServerInfo gsi;
	
	private String name;
	
	public LoadingScreen(Dimension size, MainScreen ms, String name) {
		super(size, ms);
		this.name = name;
		gsi = new GameServerInfo();
		ClientSocket.sendMessage("Map");
	}

	protected void init() {

	}

	public void mouseClicked(int button) {
	
	}

	public void mouseMoved() {

	}

	public void keyTyped(char key) {

	}

	public void update() {	
		if(gsi.getMapWidth() > 0){
			mm = new MapManager(gsi.getMapWidth(), gsi.getMapHeight(), gsi.getMapInfo(), 
					gsi.getTrees(), gsi.getBushes());
			mm.setScreenSize(ms.getGameSize().width, ms.getGameSize().height);
			ms.changeScreen(new GameScreen(size, ms, name, mm, gsi));
		}
	}

	public void render(Graphics2D g) {
		String addOn = "";
		g.setColor(Color.WHITE);
		for(int i = 0; i < (System.currentTimeMillis() - timer) / 500; i++) addOn += ".";
		g.setFont(f);
		fm = g.getFontMetrics();
		g.drawString(coolLoadingSayings[selectedSaying] + addOn, 
				size.width/2 - fm.stringWidth(coolLoadingSayings[selectedSaying] + addOn)/2, size.height/2);
		if(System.currentTimeMillis() - timer >= 2000){
			gsi.readFromServer(null);
			selectedSaying = (selectedSaying + 1 >= coolLoadingSayings.length) ? 0 : selectedSaying + 1;
			timer = System.currentTimeMillis();
		}
	}

}
