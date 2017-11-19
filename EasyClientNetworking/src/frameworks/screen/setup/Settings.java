package frameworks.screen.setup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import frameworks.screen.MainScreen;
import frameworks.screen.SubScreen;

public class Settings extends SubScreen{

	private Font titleFont = new Font(Font.DIALOG, Font.PLAIN, 30);
	//private Font optionsFont = new Font(Font.DIALOG_INPUT, Font.PLAIN, 30);
	private FontMetrics fm;
	
	public Settings(Dimension size, MainScreen ms) {
		super(size, ms);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(int button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(char key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, (int) size.getWidth(), (int) size.getHeight());
		g.setColor(Color.WHITE);
		g.setFont(titleFont);
		fm = g.getFontMetrics();
		g.drawString("Settings", (int) (size.getWidth()/2 - fm.stringWidth("Settings")/2), 
									titleFont.getSize() + 20);
	}

}
