package frameworks.server;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;

@SuppressWarnings("serial")
public class PlayerPanel extends Panel{

	private String name;
	private int x, y;
	private int color;
	
	private Label 	nameLabel,
					xPos,
					yPos,
					colorLabel;
	
	public PlayerPanel(int width, String name, int x, int y, int color){
		this.color = color;
		this.x = x;
		this.y = y;
		this.name = name;
		Dimension size = new Dimension(width, 25);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		setLayout(new GridLayout(1, 4));
		
		nameLabel = new Label(name);
		xPos = new Label(String.valueOf(x));
		yPos = new Label(String.valueOf(y));
		colorLabel = new Label(String.valueOf(color));
		add(nameLabel);
		add(xPos);
		add(yPos);
		add(colorLabel);
		
		Font f = new Font(Font.DIALOG, Font.PLAIN, 14);
		for(int i = 0; i < this.getComponentCount(); i++){
			((Label) getComponent(i)).setAlignment(Label.CENTER);
			((Label) getComponent(i)).setFont(f);
		}
		this.validate();
	}
	
	public String getPlayerName() {
		return name;
	}
	
	public void setPlayerName(String name) {
		this.name = name;
	}
	
	public int getPlayerX() {
		return x;
	}
	
	public void setX(String x) {
		try{
			this.x = Integer.parseInt(x);
		}catch (NumberFormatException e){
			this.x = 0;
		}
		xPos.setText(x);
	}
	
	public int getPlayerY() {
		return y;
	}
	
	public void setY(String y) {
		try{
			this.y = Integer.parseInt(y);
		}catch (NumberFormatException e){
			this.y = 0;
		}
		yPos.setText(y);
	}
	
	public int getColor() {
		return color;
	}
	
	public void setColor(String color) {
		try{
			this.color = Integer.parseInt(color);
		}catch (NumberFormatException e){
			this.color = 0;
		}
		colorLabel.setText(color);
	}
	
}
