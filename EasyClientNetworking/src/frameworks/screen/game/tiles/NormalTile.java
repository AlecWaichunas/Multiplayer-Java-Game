package frameworks.screen.game.tiles;

import frameworks.screen.PixelManager;

public class NormalTile extends Tile{

	public NormalTile(int width, int height, int[] pixels) {
		super(width, height, pixels);
	}

	public void render(PixelManager pm, int x, int y){
		pm.renderTile(x, y, width, height, pixels);
	}

}
