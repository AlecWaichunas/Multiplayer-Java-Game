package frameworks;

import frameworks.screen.MainScreen;


public class Engine implements Runnable{

	private Thread t;
	private boolean isRunning = false;
	private MainScreen ms;
	
	public Engine(MainScreen ms){
		this.ms = ms;
	}
	
	public void start(){
		t = new Thread(this, "Game");
		isRunning = true;
		t.start();
	}
	
	public void stop(){
		if(t == null) return;
		try {
			isRunning = false;
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		long nowTime = System.nanoTime();
		double delta = 0, ns = 1000000000/60D;
		
		while(isRunning){
			long lastTime = System.nanoTime();
			delta += (lastTime - nowTime)/ns;
			nowTime = lastTime;
			while(delta >= 0){
				delta--;
				ms.update();
			}
			ms.render();
		}
	}

}
