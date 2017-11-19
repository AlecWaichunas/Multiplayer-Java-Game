package frameworks.events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import frameworks.screen.SubScreen;

public class Keyboard implements KeyListener{

	private SubScreen ss;
	
	public void setScreen(SubScreen ss){
		this.ss = ss;
	}
	
	public void keyPressed(KeyEvent e) {
		ss.toggleKey(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		ss.toggleKey(e.getKeyCode(), false);
	}

	public void keyTyped(KeyEvent e) {
		ss.keyTyped(e.getKeyChar());
	}

}
