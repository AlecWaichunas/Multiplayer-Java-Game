package frameworks.screen.game;

import java.awt.Point;

import client.ClientSocket;
import frameworks.screen.PixelManager;
import frameworks.screen.game.entities.Player;
import frameworks.screen.game.tiles.Tile;

public class MapManager {

	private int[] mapTiles;
	private Point[] trees;
	private Point[] bushes;
	private Point[] berries;
	private Point[] webs;
	private Point[] sticks;
	private boolean hasString = false;
	private boolean hasStick = false;
	private boolean hasBerry = false;
	private int width, height;
	private int xOffset = 0, yOffset = 0;
	private int screenWidth, screenHeight;

	
	public MapManager(int width, int height, int[] tiles, Point[] trees, Point[] bushes){
		this.width = width;
		this.height = height;
		this.trees = trees;
		this.bushes = bushes;
		mapTiles = tiles;
	}
	
	public MapManager(int width, int height){
		this.width = width;
		this.height = height;
		mapTiles = new int[width * height];
	}

	
	public void move(GameScreen gs, int velX, int velY){
		//mainPlayer.changeSprite(velX, velY);
		pickUpItem(gs);
		if(checkCollision(gs.mainPlayer, velX, velY)) return;
		if(gs.mainPlayer.getX() < screenWidth/2 - 16 || gs.mainPlayer.getX() > screenWidth/2 - 8)
			gs.mainPlayer.move(velX, 0);
		else
			moveX(gs.mainPlayer, velX);
		if(gs.mainPlayer.getY() < screenHeight/2 - 16 || gs.mainPlayer.getY() > screenHeight/2 - 8)
			gs.mainPlayer.move(0, velY);
		else
			moveY(gs.mainPlayer, velY);
	}
	
	public boolean checkCollision(Player mainPlayer, int velX, int velY){
		int xa = mainPlayer.getX() + 5 + xOffset + velX;
		int xb = mainPlayer.getX() + 26 + xOffset + velX;
		int ya = mainPlayer.getY() + 27 +  yOffset + velY;
		int yb = mainPlayer.getY() + 31 + yOffset + velY;
		//check for nature tiles
		for(int i = 0; i < trees.length; i ++){
			for(int x = xa; x < xb; x++){
				for(int y = ya; y < yb; y++){
					if(x > trees[i].x + Tile.TREE.getHitX() && 
							x < trees[i].x + Tile.TREE.getHitX() + Tile.TREE.getHitWidth() &&
							y < trees[i].y + Tile.TREE.getHitY() + Tile.TREE.getHitHeight() &&
							y > trees[i].y + Tile.TREE.getHitY())
						return true;
				}
			}
		}
		//Check for map tiles
		if(velX > 0) return getTile(xb >> 4, ya >> 4).canCollide() || getTile(xb >> 4, yb >> 4).canCollide();
		else if(velX < 0) return getTile(xa >> 4, ya >> 4).canCollide() || getTile(xa >> 4, yb >> 4).canCollide();
		if(velY > 0) return getTile(xb >> 4, yb >> 4).canCollide() || getTile(xa >> 4, yb >> 4).canCollide();
		else if(velY < 0) return getTile(xa >> 4, ya >> 4).canCollide() || getTile(xb >> 4, ya >> 4).canCollide();
		return false;
	}
	
	private void moveX(Player mainPlayer, int velX){
		for(int i = 0; i < ((velX < 0) ? -velX : velX); i ++){
			this.xOffset += (velX < 0) ? -1 : 1;
			if(xOffset < 0){
				xOffset++;
				mainPlayer.move(-1, 0);
			}else if(xOffset > (this.width << 4) - screenWidth){
				xOffset--;
				mainPlayer.move(1, 0);
			}
		}
	}
	
	private void moveY(Player mainPlayer, int velY){
		for(int i = 0; i < ((velY < 0) ? -velY : velY); i += velY/velY){
			this.yOffset += (velY < 0) ? -1 : 1;
			if(yOffset < 0){
				yOffset++;
				mainPlayer.move(0, -1);
			}else if(yOffset > (this.height << 4) - screenHeight){
				yOffset--;
				mainPlayer.move(0, 1);
			}
		}
	}
	
	public void renderMap(PixelManager pm){
		for(int x = xOffset >> 4; x < ((screenWidth + xOffset) >> 4) + 1; x++){
			if(x >= width || x < 0) continue;
			for(int y = yOffset >> 4; y < ((screenHeight + yOffset) >> 4) + 1; y++){
				if(y >= height || y < 0) continue;
				getTile(x, y).render(pm, x << 4, y << 4);
			}
		}
		
		for(int i = 0; i < berries.length; i++){
			Tile.BERRY.render(pm, berries[i].x, berries[i].y);
		}
		for(int i = 0; i < sticks.length; i++){
			Tile.STICK.render(pm, sticks[i].x, sticks[i].y);
		}
		for(int i = 0; i < webs.length; i++){
			Tile.WEB.render(pm, webs[i].x, webs[i].y);
		}
	}
	
	public int getBushesAmount(){
		return bushes.length;
	}
	
	public Point[] getObjectTilesHitBox(){
		Point[] nature = new Point[bushes.length + trees.length];
		for(int i = 0; i < bushes.length; i++)
			nature[i] = new Point(bushes[i].x + Tile.BUSH.getHitX(), 
					trees[i].y + Tile.BUSH.getHitY());
		for(int i = 0; i < trees.length; i++)
			nature[bushes.length + i] = new Point(trees[i].x + Tile.TREE.getHitX(), 
					trees[i].y + Tile.TREE.getHitY());
		return nature;
	}
	
	public Point[] getObjectTiles(){
		Point[] nature = new Point[bushes.length + trees.length];
		for(int i = 0; i < bushes.length; i++)
			nature[i] = bushes[i];
		for(int i = 0; i < trees.length; i++)
			nature[bushes.length + i] = trees[i];
		return nature;
	}
	
	public Tile getTile(int x, int y){
		if(x < 0 || x >= width || y < 0 || y >= height) return Tile.VOID_TILE;
		if(mapTiles[x + y * width] == 0) return Tile.GRASS_TILE;
		if(mapTiles[x + y * width] == 1) return Tile.WATER_TILE;
		return Tile.WOOD_TILE;
	}
	
	public void renderPickedUpItems(PixelManager pm){
		if(hasBerry)
			Tile.BERRY.render(pm, this.screenWidth - 48 - 15 + xOffset, 10 + yOffset);
		if(hasStick)
			Tile.STICK.render(pm, this.screenWidth - 32 - 10 + xOffset, 10 + yOffset);
		if(hasString)
			Tile.STRING.render(pm, this.screenWidth - 16 - 5 + xOffset, 10 + yOffset);
	}
	
	public void renderObjects(Player[] players, PixelManager pm){
		int bushesAmount = getBushesAmount();
		Point[] nature = getObjectTiles();
		Point[] natureHitBox = getObjectTilesHitBox();
		String[][] index = new String[players.length + nature.length][3];
		index[0][0] = "P";
		index[0][1] = "0";
		index[0][2] = "" + (this.yOffset + players[0].getY() + 32);
		for(int i = 1; i < players.length; i++){
			if(players[i] == null) break;
			for(int c = 0; c < index.length; c++){
				if(index[c][0] == null){
					index[c][0] = "P";
					index[c][1] = "" + i;
					index[c][2] = "" + (players[i].getY() + 32 + this.yOffset);
					break;
				}
				if(players[i].getY() + 32 + this.yOffset < Integer.parseInt(index[c][2])){
					for(int x = index.length - 1; x >= c; x--){
						if(index[x][0] == null) continue;
						index[x + 1][0] = index[x][0];
						index[x + 1][1] = index[x][1];
						index[x + 1][2] = index[x][2];
					}
					index[c][0] = "P";
					index[c][1] = "" + i;
					index[c][2] = "" + (players[i].getY() + 32 + this.yOffset);
					break;
				}
			}
		}
		
		for(int i = 0; i < nature.length; i++){
			int pixelHitOffset = (i < bushesAmount) ? Tile.BUSH.getHitY() : Tile.TREE.getHitY() + 3;
			for(int c = 0; c <= index.length; c++){
				if(index[c][0] == null){
					if(i < bushesAmount) index[c][0] = "B";
					else index[c][0] = "T";
					index[c][1] = i + "";
					index[c][2] = (nature[i].y + natureHitBox[i].y) + "";
					break;
				}
				if(nature[i].y + pixelHitOffset < Integer.parseInt(index[c][2])){
					for(int x = index.length - 1; x >= c; x--){
						if(index[x][0] == null) continue;
						index[x + 1][0] = index[x][0];
						index[x + 1][1] = index[x][1];
						index[x + 1][2] = index[x][2];
					}
					index[c][0] = (i < bushesAmount) ? "B" : "T";
					index[c][1] = "" + i;
					index[c][2] = "" + (nature[i].y + pixelHitOffset);
					break;
				}
			}
		}
		
		for(int i = 0; i < index.length; i++){
			if(index[i][0] == null) continue;
			if(index[i][0] == "P"){
				int playerIndex = Integer.parseInt(index[i][1]);
				players[playerIndex].render(pm);
			}else if(index[i][0] == "B"){	
				int natureIndex = Integer.parseInt(index[i][1]);
				Tile.BUSH.render(pm, nature[natureIndex].x, nature[natureIndex].y);
			}else if(index[i][0] == "T"){	
				int natureIndex = Integer.parseInt(index[i][1]);
				Tile.TREE.render(pm, nature[natureIndex].x, nature[natureIndex].y);
			}
		}
	}
	
	private void pickUpItem(GameScreen gs){
		int plrX = gs.mainPlayer.getX() + xOffset;
		int plrY = gs.mainPlayer.getY() + yOffset;
		if(!hasBerry)
			berryLoop: for(int i = 0; i < berries.length; i++){
				for(int x = 0; x < Tile.BERRY.getWidth(); x++){
					for(int y = 0; y < Tile.BERRY.getHeight(); y++){
						if(plrX < x + berries[i].x && plrX + 32 > x + berries[i].x &&
							plrY < y + berries[i].y && plrY + 32 > y + berries[i].y){
							//pick up berry
							ClientSocket.sendMessage("PickUp`Berry`" + berries[i].x + "," + berries[i].y);
							hasBerry = true;
							gs.updateObjects();
							break berryLoop;
						}
							
					}
				}
			}
		if(!hasString)
			webLoop: for(int i = 0; i < webs.length; i++){
				for(int x = 0; x < Tile.WEB.getWidth(); x++){
					for(int y = 0; y < Tile.WEB.getHeight(); y++){
						if(plrX < x + webs[i].x && x + webs[i].x < plrX + 32 &&
							plrY < y + webs[i].y && y + webs[i].y < plrY + 32){
							//pick up web
							ClientSocket.sendMessage("PickUp`Web`" + webs[i].x + "," + webs[i].y);
							hasString = true;
							gs.updateObjects();
							break webLoop;
						}
							
					}
				}
			}
		
		if(!hasStick)
			stickLoop: for(int i = 0; i < sticks.length; i++){
				for(int x = 0; x < Tile.STICK.getWidth(); x++){
					for(int y = 0; y < Tile.STICK.getHeight(); y++){
						if(plrX < x + sticks[i].x && x + sticks[i].x < plrX + 32 &&
							plrY < y + sticks[i].y && y + sticks[i].y < plrY + 32){
							//pick up stick
							ClientSocket.sendMessage("PickUp`Stick`" + sticks[i].x + "," + sticks[i].y);
							hasStick = true;
							gs.updateObjects();
							break stickLoop;
						}
							
					}
				}
			}
	}
	
	public void updateResources(Point[] berries, Point[] sticks, Point[] webs){
		this.berries = berries;
		this.sticks = sticks;
		this.webs = webs;
	}
	
	public int getX(){
		return this.xOffset;
	}
	
	public int getY(){
		return this.yOffset;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public boolean getString(){
		return this.hasString;
	}
	
	public boolean getStick(){
		return this.hasStick;
	}
	
	public boolean getBerry(){
		return this.hasBerry;
	}
	
	public void setScreenSize(int screenWidth, int screenHeight){
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
	}
	
}
