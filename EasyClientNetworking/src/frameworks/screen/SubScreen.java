package frameworks.screen;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

public abstract class SubScreen {

	protected Dimension size;
	protected Point mousePos = new Point(0, 0);
	protected boolean[] keys = new boolean[65536];
	protected MainScreen ms;
	
	public SubScreen(Dimension size, MainScreen ms){
		this.size = size;
		this.ms = ms;
		init();
	}
	
	protected abstract void init();
	public abstract void mouseClicked(int button);
	public abstract void mouseMoved();
	public abstract void keyTyped(char key);
	public abstract void update();
	public abstract void render(Graphics2D g);
	
	public void updateMousePos(Point p){
		mousePos.setLocation(p);
	}
	
	public void toggleKey(int keyCode, boolean down){
		keys[keyCode] = down;
	}
	
	public void updateMouse(Point p){
		mousePos.setLocation(p);
	}
	
}
