package frameworks.screen.game.tiles;

import frameworks.screen.PixelManager;

public class ObjectTile extends Tile{

	private int ignoreColor;
	
	public ObjectTile(int width, int height, int[] pixels, int ignoreColor){
		super(width, height, pixels);
		this.ignoreColor = ignoreColor;
	}
	
	public void render(PixelManager pm, int x, int y){
		pm.renderTile(x, y, width, height, pixels, ignoreColor);
	}
	
}
