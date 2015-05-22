package platform;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SawShooter extends Platform{
	
	private static final int HEIGHT = 40;
	private static final int WIDTH = 40;
	private long delay;
	private long lastSpawn;
	private int x;
	private int y;
	private int xVel;
	private int yVel;
	private Rectangle hitbox;
	private int xscroll;
	private int yscroll;
	private BufferedImage image;
	public SawShooter(int x,int y, long delay, int xVel, int yVel){
		this.x=x;	
		this.y=y;
		this.xVel=xVel;
		this.yVel=yVel;
		this.delay=delay;
		this.lastSpawn=0;
		try {
			image = ImageIO.read(new File("resources/sawshooter.png"));
		} catch (IOException e) {e.printStackTrace();}
		xscroll=0;
		yscroll=0;
		hitbox = new Rectangle(x,y,40,40);
	}
	public boolean refresh(){
		if(System.currentTimeMillis()-lastSpawn>delay){
			lastSpawn = System.currentTimeMillis();
			return true;
		}
		return false;
	}
	public int getXVel(){
		return xVel;
	}
	public int getYVel(){
		return yVel;
	}
	public BufferedImage getImage(){
		return image;
	}
	public int getTop(){
		return hitbox.y;
	}
	public int getLeft(){
		return hitbox.x;
	}
	public int getRight(){
		return hitbox.x+40;
	}
	public int getBottom(){
		return hitbox.y+40;
	}
	public int getXScrolled(){
		return x-xscroll;
	}
	public int getYScrolled(){
		return y-yscroll;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public Rectangle getHitbox(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	public void setXScroll(int x){
		xscroll=x;
	}
	public void setYScroll(int y){
		yscroll=y;
	}
}
