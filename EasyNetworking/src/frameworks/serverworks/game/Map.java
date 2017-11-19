package frameworks.serverworks.game;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utils.ImageControl;

public class Map implements Runnable{

	//GameLoop
	private Thread t;
	
	private int width, height;
	private int[] map;
	private List<Point> trees = new ArrayList<Point>();
	private List<Point> bushes = new ArrayList<Point>();
	private List<Point> bridge = new ArrayList<Point>();
	private List<Point> berries = new ArrayList<Point>();
	private List<Point> webs = new ArrayList<Point>();
	private List<Point> sticks = new ArrayList<Point>();
	private Random random = new Random();
	
	public Map(String name){
		BufferedImage img = ImageControl.LoadImage(name);
		this.width = img.getWidth();
		this.height = img.getHeight();
		map = new int[width * height];
		loadMap(ImageControl.GetPixelsFromImg(img));
		t = new Thread(this, "Server Game Loop");
		t.start();
	}
	
	private void loadMap(int[] pixels){
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				int i = x + y * width;
				if(ImageControl.GetHSGColor(pixels[i]) == ImageControl.GetHSGColor(0, 255, 0)) map[i] = 0;
				else if(ImageControl.GetHSGColor(pixels[i]) == ImageControl.GetHSGColor(0, 0, 255)) map[i] = 1;
				else if(ImageControl.GetHSGColor(pixels[i]) == ImageControl.GetHSGColor(255, 0, 0)) map[i] = 2;
				else if(ImageControl.GetHSGColor(pixels[i]) == ImageControl.GetHSGColor(0, 0, 0)){
					map[i] = 0;
					trees.add(new Point(x << 4, y << 4));
				}else if(ImageControl.GetHSGColor(pixels[i]) == ImageControl.GetHSGColor(255, 255, 255)){
					map[i] = 0;
					bushes.add(new Point(x << 4, y << 4));
				}
			}
		}
		getSpiderWebSpots();
	}
	
	private void getSpiderWebSpots(){
		int[] forbiddenSpots = new int[width * height];
	
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				if(map[x + y * width] == 2 && forbiddenSpots[x + y * width] != 1){
					if(map[(x + 1) + y * width] == 2 && map[x + (y + 1) * width] == 2 &&
						map[(x + 1) + (y + 1) * width] == 2)
						if(forbiddenSpots[(x + 1) + y * width] != 1 && forbiddenSpots[x + (y + 1) * width] != 1 &&
								forbiddenSpots[(x + 1) + (y + 1) * width] != 1){
							forbiddenSpots[x + y * width] = 1;
							forbiddenSpots[(x + 1) + y * width] = 1;
							forbiddenSpots[x + (y + 1) * width] = 1;
							forbiddenSpots[(x + 1) + (y + 1) * width] = 1;
							bridge.add(new Point(x << 4, y << 4));
						}
				}
			}
		}
	}
	
	public boolean pickUpWeb(int x, int y){
		for(int i = 0; i < webs.size(); i++){
			if(webs.get(i).x == x && webs.get(i).y == y){
				webs.remove(i);		
				return true;
			}
		}
		return false;
	}	
	
	public boolean pickUpBerry(int x, int y){
		for(int i = 0; i < berries.size(); i++){
			if(berries.get(i).x == x && berries.get(i).y == y){
				berries.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean pickUpStick(int x, int y){
		for(int i = 0; i < sticks.size(); i++){
			if(sticks.get(i).x == x && sticks.get(i).y == y){
				sticks.remove(i);		
				return true;
			}
		}
		return false;
	}	
	
	public String GetClientMapData(){
		String line = "";
		line += String.valueOf(width) + "\n";
		line += String.valueOf(height) + "\n";
		for(int i = 0; i < map.length; i++){
			line += map[i] + ",";
		}
		line += "\n";
		for(int i = 0; i < bushes.size(); i++)
			line += "B:" + bushes.get(i).x + ", " + bushes.get(i).y + "\n";
		for(int i = 0; i < trees.size(); i++)
			line += "T:" + trees.get(i).x + ", " + trees.get(i).y + "\n";
		return line;
	}
	
	public String GetObjectData(){
		String line = "";
		for(int i = 0; i < berries.size(); i++){
			line += "R:" + berries.get(i).x + "," + berries.get(i).y + "\n";
		}
		for(int i = 0; i < webs.size(); i++){
			line += "W:" + webs.get(i).x + "," + webs.get(i).y + "\n";
		}
		for(int i = 0; i < sticks.size(); i++){
			line += "S:" + sticks.get(i).x + "," + sticks.get(i).y + "\n";
		}
		return line;
	}
	
	public void shutDown(){
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long berryGeneration = (random.nextInt(5) + 1) * 60 * 1000;
		long webGeneration = (random.nextInt(6) + 3) * 60 * 1000;
		long stickGeneration = (random.nextInt(7) + 2) * 60 * 1000;
		long berryTimer = System.currentTimeMillis();
		long webTimer = berryTimer;
		long stickTimer = berryTimer;
		while(t.isAlive()){
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(System.currentTimeMillis() - berryTimer >= berryGeneration){
				System.out.println("BERRY ADDED!");
				berryTimer = System.currentTimeMillis();
				berryGeneration = (random.nextInt(5) + 1) * 60 * 1000;
				Point bush = this.bushes.get(random.nextInt(bushes.size()));
				this.berries.add(new Point(
						bush.x + (((random.nextInt(2) == 0) ? -1 : 1) * 32) + random.nextInt(30),
						bush.y + (((random.nextInt(2) == 0) ? -1 : 1) * 16) + random.nextInt(20)));
			}
			if(System.currentTimeMillis() - stickTimer >= stickGeneration){
				System.out.println("STICK ADDED!");
				stickTimer = System.currentTimeMillis();
				stickGeneration = (random.nextInt(7) + 2) * 60 * 1000;
				Point tree = this.trees.get(random.nextInt(trees.size()));
				this.sticks.add(new Point(
						tree.x + (((random.nextInt(2) == 0) ? -1 : 1) * 8) + random.nextInt(60),
						tree.y + (((random.nextInt(2) == 0) ? -1 : 1) * 16) + 32 + random.nextInt(20)));
			}
			if(System.currentTimeMillis() - webTimer >= webGeneration){
				System.out.println("WEB ADDED!");
				webTimer = System.currentTimeMillis();
				webGeneration = (random.nextInt(6) + 3) * 60 * 1000;
				Point spot = this.bridge.get(random.nextInt(bridge.size()));
				boolean create = true;
				for(int i = 0; i < this.webs.size(); i++){
					if(webs.get(i).x == spot.x && webs.get(i).y == spot.y) create = false;
				}
				if(create)
					this.webs.add(new Point(spot.x, spot.y));
			}
		}
	}
	
}
