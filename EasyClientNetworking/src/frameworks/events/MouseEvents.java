package frameworks.events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import frameworks.screen.SubScreen;

public class MouseEvents implements MouseListener, MouseMotionListener{

	private SubScreen screen;
	
	public void changeScreen(SubScreen screen){
		this.screen = screen;
	}
	
	public void mouseDragged(MouseEvent e) {
		screen.updateMouse(e.getPoint());
		screen.mouseMoved();
	}

	public void mouseMoved(MouseEvent e) {
		screen.updateMouse(e.getPoint());
		screen.mouseMoved();
	}

	public void mouseClicked(MouseEvent e) {
		screen.mouseClicked(e.getButton());
	}

	public void mouseEntered(MouseEvent e) {
		screen.updateMouse(e.getPoint());
		screen.mouseMoved();
	}

	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	
}
