package frameworks.screen.game.tiles;

import java.awt.image.BufferedImage;

import utils.ImageControl;
import frameworks.screen.PixelManager;

public abstract class Tile {

	private static final BufferedImage MAIN_SHEET = ImageControl.GetImage("Sprite Sheet");
	private static final int[] TILE_SHEET = ImageControl.GetImagePixels(MAIN_SHEET);
	//TILES
	public static final Tile GRASS_TILE = new NormalTile(16, 16, 
			ImageControl.GetSubImage(0, 0, 16, 16, TILE_SHEET, MAIN_SHEET.getWidth()));
	public static final Tile WOOD_TILE = new NormalTile(16, 16, 
			ImageControl.GetSubImage(0, 16, 16, 16, TILE_SHEET, MAIN_SHEET.getWidth()));
	
	public static final Tile WATER_TILE = new AnimatedTile(16, 16, 4,
			ImageControl.GetSubImage(16, 0, 16, 16, TILE_SHEET, MAIN_SHEET.getWidth()), 400);
	
	public static final Tile VOID_TILE = new HardTile(16, 16);
	//OBJECTS
	public static final Tile TREE = new ObjectTile(64, 64,
			ImageControl.GetSubImage(0, 96, 64, 64, TILE_SHEET, MAIN_SHEET.getWidth()),
			ImageControl.GetHSGColor(255, 255, 255));
	public static final Tile BUSH = new ObjectTile(64, 32,
			ImageControl.GetSubImage(64, 128, 64, 32, TILE_SHEET, MAIN_SHEET.getWidth()),
			ImageControl.GetHSGColor(255, 255, 255));
	public static final Tile BERRY = new ObjectTile(16, 16, 
			ImageControl.GetSubImage(80, 0, 16, 16, TILE_SHEET, MAIN_SHEET.getWidth()),
			ImageControl.GetHSGColor(0, 0, 0));
	public static final Tile STICK = new ObjectTile(16, 16, 
			ImageControl.GetSubImage(112, 0, 16, 16, TILE_SHEET, MAIN_SHEET.getWidth()),
			ImageControl.GetHSGColor(0, 0, 0));
	public static final Tile STRING = new ObjectTile(16, 16, 
			ImageControl.GetSubImage(96, 0, 16, 16, TILE_SHEET, MAIN_SHEET.getWidth()),
			ImageControl.GetHSGColor(0, 0, 0));
	public static final Tile WEB = new ObjectTile(32, 32, 
			ImageControl.GetSubImage(128, 128, 32, 32, TILE_SHEET, MAIN_SHEET.getWidth()),
			ImageControl.GetHSGColor(255, 255, 255));
	
	static {
		//enter tile controls here
		((AnimatedTile) WATER_TILE).addFrame(ImageControl.GetSubImage(32, 0, 16, 16, TILE_SHEET, MAIN_SHEET.getWidth()));
		((AnimatedTile) WATER_TILE).addFrame(ImageControl.GetSubImage(48, 0, 16, 16, TILE_SHEET, MAIN_SHEET.getWidth()));
		((AnimatedTile) WATER_TILE).addFrame(ImageControl.GetSubImage(64, 0, 16, 16, TILE_SHEET, MAIN_SHEET.getWidth()));
		TREE.setHitPixels(17, 51, 28, 13);
		BUSH.setHitPixels(0, 31, 64, 1);
		BERRY.setHitPixels(0, 0, 16, 16);
	}
	
	
	protected int[] pixels;
	protected int[] hitPixels;
	protected int x, y;
	protected int hitWidth, hitHeight;
	protected int width, height;
	
	public Tile(int width, int height, int[] pixels){
		this.width = width;
		this.height = height;
		this.pixels = pixels;
		hitPixels = new int[pixels.length];
	}
	
	public Tile(int width, int height){
		this.width = width;
		this.height = height;
		this.pixels = new int[width* height];
		hitPixels = new int[pixels.length];
	}
	
	public abstract void render(PixelManager pm, int x, int y);
	
	protected void setHitPixels(int color){
		for(int i = 0; i < pixels.length; i++){
			if(ImageControl.GetHSGColor(pixels[i]) == color)
				hitPixels[i] = 1;
		}
	}
	
	protected void setHitPixels(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		hitWidth = width;
		hitHeight = height;
		for(int xx = x; xx < width; xx++){
			for(int yy = y; yy < height; yy++){
				hitPixels[xx + yy * width] = 1;
			}
		}
	}
	
	public boolean canCollide(int plrX, int plrY, int tileX, int tileY){
		plrX -= tileX;
		plrY -= tileY;
		if(hitPixels[plrX + plrY * width] == 1) return true;
		return false;
	}
	
	public boolean canCollide(){
		return false;
	}
	
	public int getPixel(int i){
		return pixels[i];
	}
	
	public int getHitPixel(int i){
		return this.hitPixels[i];
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getHitWidth(){
		return hitWidth;
	}
	
	public int getHitHeight(){
		return hitHeight;
	}
	
	public int getHitX(){
		return x;
	}
	
	public int getHitY(){
		return y;
	}
	
}
