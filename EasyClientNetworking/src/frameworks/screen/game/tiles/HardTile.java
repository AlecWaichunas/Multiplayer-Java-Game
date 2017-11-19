package frameworks.screen.game.tiles;

import frameworks.screen.PixelManager;

public class HardTile extends Tile{

	public HardTile(int width, int height, int[] pixels) {
		super(width, height, pixels);
	}
	
	public HardTile(int width, int height){
		super(width, height);
	}

	public void render(PixelManager pm, int x, int y) {
		pm.renderTile(x, y, width, height, pixels);
	}
	
	public boolean canCollide(){
		return true;
	}

}
