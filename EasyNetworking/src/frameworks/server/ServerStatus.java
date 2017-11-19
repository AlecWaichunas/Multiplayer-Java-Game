package frameworks.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;

@SuppressWarnings("serial")
public class ServerStatus extends Panel{

	private Label 	portLabel = new Label(),
					serverStatus = new Label("Server Status: Down"),
					host = new Label("Host: localhost");
	
	public ServerStatus(int width, int port){
		Dimension size = new Dimension(width, 75);
		Font f = new Font(Font.DIALOG, Font.PLAIN, 18);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		setLayout(new BorderLayout());
		this.setBackground(Color.LIGHT_GRAY);
		
		portLabel.setFont(f);
		host.setFont(f);
		serverStatus.setFont(f);
		portLabel.setText("Port: " + port);
		
		add(portLabel, BorderLayout.NORTH);
		add(host, BorderLayout.CENTER);
		add(serverStatus, BorderLayout.SOUTH);
	}
	
	public void updatePort(int port){
		portLabel.setText("Port: " + port);
	}
	
}
