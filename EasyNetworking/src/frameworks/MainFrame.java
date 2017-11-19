package frameworks;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.ScrollPane;

import frameworks.events.FrameEvents;
import frameworks.server.PlayerConnections;
import frameworks.server.ServerStatus;
import frameworks.serverworks.Server;
import frameworks.serverworks.ServerAcceptance;
import frameworks.serverworks.game.Map;

import javax.swing.JOptionPane;


@SuppressWarnings("serial")
public class MainFrame extends Frame{

	private Dimension size = new Dimension(400, 600);
	private int port;
	
	public MainFrame(){
		port = Integer.parseInt(JOptionPane.showInputDialog("What port would you like to go off of?"));
		setTitle("Game - Server");
		setResizable(false);
		
		ServerStatus ss = new ServerStatus(400, port);
		PlayerConnections pc = new PlayerConnections(400);
		ScrollPane sp = new ScrollPane();
		sp.add(pc);
		
		add(ss, BorderLayout.NORTH);
		add(sp, BorderLayout.CENTER);
		setSize(size);		
		setLocationRelativeTo(null);
		addWindowListener(new FrameEvents());
		setVisible(true);
		System.out.println("Everything is a go!");
		
		Server.startServer(port, ss);
		new ServerAcceptance(pc, new Map("TestMap"));
		
	}
	
}
