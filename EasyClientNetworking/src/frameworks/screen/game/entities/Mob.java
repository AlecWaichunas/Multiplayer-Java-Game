package frameworks.screen.game.entities;

import java.awt.Graphics2D;

import frameworks.screen.PixelManager;

public abstract class Mob {

	protected String name;
	protected int xPos, yPos;
	
	public Mob(String name, int xPos, int yPos){
		this.name = name;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public abstract void render(Graphics2D g);
	public abstract void render(PixelManager pm);
	
	public void move(int velX, int velY){
		this.xPos += velX;
		this.yPos += velY;
	}

	public void updatePos(int newX, int newY){
		this.xPos = newX;
		this.yPos = newY;
	}
	
	public int getX(){
		return xPos;
	}
	
	public int getY(){
		return yPos;
	}
	
	public String getName(){
		return name;
	}
	
}
