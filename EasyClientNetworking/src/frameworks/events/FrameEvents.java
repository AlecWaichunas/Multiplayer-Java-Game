package frameworks.events;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import client.ClientSocket;

public class FrameEvents implements WindowListener{

	
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	
	public void windowClosing(WindowEvent arg0) {
		System.exit(0);
		ClientSocket.closeConnection();
	}

	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}

}
