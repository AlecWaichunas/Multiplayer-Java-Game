package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageControl {

	public static BufferedImage GetImage(String name){
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(ImageControl.class.getResourceAsStream("/" + name + ".png"));
		} catch (IOException e) {
			System.err.println("Couldn't Find Image " + name + "!");
			System.exit(-1);
		}
		
		return img;
	}
	
	public static int[] GetImagePixels(BufferedImage img){
		return img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
	}
	
	public static int[] GetSubImage(int x, int y, int width, int height, int[] pixels, int imgWidth){
		int[] subPixels = new int[width * height];
		
		for(int xx = 0; xx < width; xx++){
			for(int yy = 0; yy < height; yy++){
				subPixels[xx + yy * width] = pixels[(xx + x) + (yy + y) * imgWidth];
			}
		}
		
		return subPixels;
	}
	
	public static int GetHSGColor(int pixelColor){
		int r = (pixelColor >> 16) & 0xFF;
		int g = (pixelColor >> 8) & 0xFF;
		int b = (pixelColor) & 0xFF;
		return (r << 16) | (g << 8) | b;
	}
	
	public static int GetHSGColor(int r, int g, int b){
		return (r << 16) | (g << 8) | b;
	}
	
}
