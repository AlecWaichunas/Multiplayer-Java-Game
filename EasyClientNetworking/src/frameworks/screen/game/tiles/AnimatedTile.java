package frameworks.screen.game.tiles;

import frameworks.screen.PixelManager;

public class AnimatedTile extends Tile{

	private int frameIndex = 0;
	private int[][] frames;
	private long timer, time = System.currentTimeMillis();
	
	public AnimatedTile(int width, int height, int maxFrames, int[] pixels, long timer) {
		super(width, height);
		this.timer = timer;
		frames = new int[maxFrames][];
		frames[0] = pixels;
	}
	
	public void addFrame(int[] pixels){
		for(int i = 0; i < frames.length; i++){
			if(frames[i] == null){
				frames[i] = pixels;
				break;
			}
		}
	}

	public void render(PixelManager pm, int x, int y){
		if(System.currentTimeMillis() - time >= timer){
			frameIndex = (frameIndex + 1 == frames.length) ? 0 : frameIndex + 1;
			time = System.currentTimeMillis();
		}
		pm.renderTile(x, y, width, height, frames[frameIndex]);
	}
	
	public boolean canCollide(){
		return true;
	}

}
