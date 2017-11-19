package frameworks.screen.setup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import client.ClientSocket;
import frameworks.screen.MainScreen;
import frameworks.screen.SubScreen;

public class TitleScreen extends SubScreen{

	private static final String TITLE = "ZOMBIE GAME";
	
	private Font titleFont = new Font(Font.DIALOG, Font.PLAIN, 30);
	private Font optionsFont = new Font(Font.DIALOG_INPUT, Font.PLAIN, 30);
	private FontMetrics fm;
	
	private String[] options = {"Play", "Settings", "Exit"};
	private int selected = -1;
	
	private String name;
	
	
	public TitleScreen(Dimension size, MainScreen ms, String name) {
		super(size, ms);
		this.name = name;
	}

	protected void init() {}

	public void mouseClicked(int button) {
		if(selected == 0){
			//Play
			if(ClientSocket.createConnection())
				ms.changeScreen(new LoadingScreen(size, ms, name)); 
		}else if(selected == 1){
			//settings
		}else if(selected == 2){
			ClientSocket.closeConnection();
			System.exit(0);
		}
	}
	
	public void mouseMoved(){
		if(mousePos.getY() < ((optionsFont.getSize() + 20) * 2) + size.height/3 && 
				mousePos.getY() > (((optionsFont.getSize() + 20) * 2) + size.height/3) - optionsFont.getSize())
			selected = 2;
		else if(mousePos.getY() > ((optionsFont.getSize() + 20) + size.height/3) - optionsFont.getSize() &&
				mousePos.getY() < optionsFont.getSize() + 20 + size.height/3)
			selected= 1;
		else if(mousePos.getY() > (size.height/3) - optionsFont.getSize() && 
				mousePos.getY() < size.height/3)
			selected = 0;
		else
			selected = -1;
	}
	
	public void keyTyped(char key){
		System.out.println(key);
		if(key == 'w' || key == 'W') selected = (selected - 1 < 0) ? 2 : selected - 1;
		if(key == 's' || key == 'S') selected = (selected + 1 > 2) ? 0 : selected + 1;
	}

	public void update() {
		if(keys[KeyEvent.VK_ENTER]) mouseClicked(0);
	}

	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.setFont(titleFont);
		fm = g.getFontMetrics();
		g.drawString(TITLE, (int) (size.getWidth()/2 - fm.stringWidth(TITLE)/2), titleFont.getSize() + 20);
		g.setFont(optionsFont);
		fm = g.getFontMetrics();
		g.setColor(Color.RED);
		for(int i = 0; i < options.length; i++){
			if(selected == i) g.setColor(Color.RED); else g.setColor(Color.WHITE);
			g.drawString(options[i], (int) (size.getWidth()/2 - fm.stringWidth(options[i])/2), 
									((optionsFont.getSize() + 20) * i) + size.height/3);
		}
	}

}
