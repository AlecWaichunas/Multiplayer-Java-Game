package frameworks.screen.game.entities;

public enum PlayerFrames {

	UP (1),
	DOWN (2),
	LEFT (3),
	RIGHT (4),
	WALKING_UP_1 (5),
	WALKING_UP_2 (6),
	WALKING_RIGHT_1 (7),
	WALKING_RIGHT_2 (8),
	WALKING_LEFT_1 (9),
	WALKING_LEFT_2 (10),
	WALKING_DOWN_1 (11),
	WALKING_DOWN_2 (12);
	
	private final int frameNumber;
	PlayerFrames(int frameNumber){
		this.frameNumber = frameNumber;
	}
	
	int getFrame(){
		return this.frameNumber;
	}
	
	
}
