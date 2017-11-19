package frameworks.screen.game.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import utils.ImageControl;
import frameworks.screen.PixelManager;

public class Player extends Mob {
	
	private static final BufferedImage CHARACTER_SHEET = ImageControl.GetImage("CharacterSheet");
	private static final int[] PIXEL_SHEET = ImageControl.GetImagePixels(CHARACTER_SHEET);
	
	private Font f = new Font(Font.MONOSPACED, Font.PLAIN, 16);
	private int ignoreColor = 0xFF0A9D;
	private int replaceColor = 0x0000FF;
	private int colorInt;
	private PlayerFrames pf;
	private int[][] frames = new int[12][];
	private int[] hitPixels = new int[32 * 32];

	public Player(String name, int xPos, int yPos, int color) {
		super(name, xPos, yPos);
		colorInt = color;
		setFrame(PlayerFrames.UP, ImageControl.GetSubImage(0, 0, 32, 32, PIXEL_SHEET, CHARACTER_SHEET.getWidth()));
		setFrame(PlayerFrames.DOWN, ImageControl.GetSubImage(0, 32, 32, 32, PIXEL_SHEET, CHARACTER_SHEET.getWidth()));
		setFrame(PlayerFrames.LEFT, ImageControl.GetSubImage(0, 96, 32, 32, PIXEL_SHEET, CHARACTER_SHEET.getWidth()));
		setFrame(PlayerFrames.RIGHT, ImageControl.GetSubImage(0, 64, 32, 32, PIXEL_SHEET, CHARACTER_SHEET.getWidth()));
		RunFrame(PlayerFrames.DOWN);
		for(int x = 0; x < 32; x++){
			for(int y = 0; y < 8; y++){
				hitPixels[x + (y + 23) * 32] = 1;
			}
		}
	}
	
	public void setFrame(PlayerFrames pf, int[] pixels){
		frames[pf.getFrame()] = pixels;
	}
	
	public void RunFrame(PlayerFrames pf){
		this.pf = pf;
	}

	public void render(Graphics2D g) {		
		g.setColor(Color.BLACK);
		g.setFont(f);
		g.drawString(name, xPos * 800 / 420 + 10, yPos * (800 * 9 / 16) / (420 * 9 / 16) - 16);
	}
	
	public void render(PixelManager pm){
		for(int x = 0; x < 32; x++){
			for(int y = 0; y < 32; y++){
				int color = frames[pf.getFrame()][x + y * 32];
				
				if(ImageControl.GetHSGColor(color) == ignoreColor) continue;
				if(ImageControl.GetHSGColor(color) == replaceColor)
					pm.setPixel((x + xPos), (y + yPos), this.colorInt);
				else
					pm.setPixel((x + xPos), (y + yPos), color);
			}
		}
	}
	
	public void changeSprite(int x, int y){
		if(y < 0)
			RunFrame(PlayerFrames.UP);
		else if(y > 0)
			RunFrame(PlayerFrames.DOWN);
		if(x < 0)
			RunFrame(PlayerFrames.LEFT);
		else if(x > 0)
			RunFrame(PlayerFrames.RIGHT);
	}
	
	public int getIgnoreColor(){
		return ignoreColor;
	}
	
	public int getPixel(int pixel){
		return frames[pf.getFrame()][pixel];
	}
	
	public int getColorInt(){
		return colorInt;
	}

}
