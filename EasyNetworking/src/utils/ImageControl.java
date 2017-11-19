package utils;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageControl {

	public static BufferedImage LoadImage(String name){
		BufferedImage img = null;
		
		try {
			File f = new File(String.valueOf(ImageControl.class.getResourceAsStream("/res")));
			System.out.println(f.isFile());
			img = ImageIO.read(ImageControl.class.getResourceAsStream("" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return img;
	}
	
	public static int[] GetPixelsFromImg(BufferedImage img){
		
		return img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
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
