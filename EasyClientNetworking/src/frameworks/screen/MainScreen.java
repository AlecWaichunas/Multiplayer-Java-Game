package frameworks.screen;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import frameworks.events.Keyboard;
import frameworks.events.MouseEvents;
import frameworks.screen.setup.TitleScreen;

@SuppressWarnings("serial")
public class MainScreen extends Canvas{

	//private Dimension size;
	private Font fpsFont = new Font(Font.MONOSPACED, Font.PLAIN, 12);
	private long timer = System.currentTimeMillis();
	private int fps = 0, ups = 0, staticFps, staticUps;
	
	private BufferedImage game = new BufferedImage(420, 420 * 9 / 16, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) game.getRaster().getDataBuffer()).getData();
	private PixelManager pm = new PixelManager(420, 420 * 9 / 16);
	
	private SubScreen screen;
	private Keyboard kb = new Keyboard();
	private MouseEvents me = new MouseEvents();
	
	public MainScreen(Dimension size, String name){
		//this.size = size;
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		addKeyListener(kb);
		addMouseListener(me);
		addMouseMotionListener(me);
		changeScreen(new TitleScreen(size, this, name));
	}
	
	public void changeScreen(SubScreen screen){
		this.screen = screen;
		kb.setScreen(screen);
		me.changeScreen(screen);
	}
	
	public PixelManager getPixelManager(){
		return pm;
	}
	
	public Dimension getGameSize(){
		return new Dimension(game.getWidth(), game.getHeight());
	}
	
	public void update(){
		screen.update();
		ups++;
	}
	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(2);
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		for(int i = 0; i < pixels.length; i++)
			pixels[i] = pm.getPixel(i);
		g.drawImage(game, 0, 0, this.getWidth(), this.getHeight(), null);

		screen.render(g);
		
		g.setColor(Color.WHITE);
		g.setFont(fpsFont);
		g.drawString("UPS: " + staticUps + " | FPS: " + staticFps, 10, 50);
		g.dispose();
		bs.show();
		fps++;
		if(System.currentTimeMillis() - timer >= 1000){
			timer = System.currentTimeMillis();
			staticFps = fps;
			staticUps = ups;
			ups = 0;
			fps = 0;
			//System.out.println(":)");
		}
	}
	
}
