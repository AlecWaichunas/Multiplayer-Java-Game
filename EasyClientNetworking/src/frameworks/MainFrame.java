package frameworks;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JOptionPane;

import frameworks.events.FrameEvents;
import frameworks.screen.MainScreen;

@SuppressWarnings("serial")
public class MainFrame extends Frame{
	
	private static final Dimension SIZE = new Dimension(800, 800 * 9 / 16);

	public MainFrame(){
		String name = JOptionPane.showInputDialog("What is your name?");
		if(name == null) return;
		setTitle("Client - " + name);
		setResizable(false);
		
		MainScreen ms = new MainScreen(SIZE, name);
		Engine e = new Engine(ms);
		
		add(ms, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
		addWindowListener(new FrameEvents());
		setVisible(true);
		
		e.start();
	}
	
}
