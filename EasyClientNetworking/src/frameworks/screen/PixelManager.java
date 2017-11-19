package frameworks.screen;

import utils.ImageControl;

public class PixelManager {

	private int width, height;
	private int[] pixels;
	
	private int xOffset = 0, yOffset = 0;
	
	public PixelManager(int width, int height){
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	public void clear(){
		for(int i = 0; i < pixels.length; i++)
			pixels[i] = 0;
	}
	
	public int getPixel(int i){
		return pixels[i];
	}
	
	public void setOffset(int xOffset, int yOffset){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public void renderTile(int x, int y, int width, int height, int[] pixels){
		x -= xOffset;
		y -= yOffset;
		for(int xx = 0; xx < width; xx++){
			if(xx + x >= this.width || xx + x < 0) continue;
			for(int yy = 0; yy < height; yy++){
				if(yy + y >= this.height || yy + y < 0) continue;
				this.pixels[(xx + x) + (yy + y) * this.width] = pixels[xx + yy * width];
			}
		}
	}
	
	public void renderTile(int x, int y, int width, int height, int[] pixels, int ignoreColor){
		x -= xOffset;
		y -= yOffset;
		for(int xx = 0; xx < width; xx++){
			if(xx + x >= this.width || xx + x < 0) continue;
			for(int yy = 0; yy < height; yy++){
				if(yy + y >= this.height || yy + y < 0 || 
						ImageControl.GetHSGColor(pixels[xx + yy * width]) == ignoreColor) continue;
				this.pixels[(xx + x) + (yy + y) * this.width] = pixels[xx + yy * width];
			}
		}
	}
	
	public boolean ignoreColor(int pixel, int ignoreColor){
		return (ImageControl.GetHSGColor(pixel) == ignoreColor);
	}
	
	public void setPixel(int x, int y, int color){
		if(x < 0 || x >= width || y < 0 || y >= height) return; 
		this.pixels[x + y * width] = color;
	}
	
}
