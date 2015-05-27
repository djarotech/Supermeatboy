/*Super Meat Boy
 *Kevin Mao
 *Ritchie Chen
 *Daniel Moore
 *CS3 Final project
 *Gallatin-3rd
 */
package platform;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A SawShooter regularly shoots outs moving saws, which will kill the player upon contact and act as further obstacles
 */
public class SawShooter extends Platform{
	public static final int HORIZONTAL_DIRECTION=1;
	public static final int VERTICAL_DIRECTION=2;
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
	
	/**
	 * Creates a SawShooter object with a specified position, saw velocity, and direction
	 * @param x The x position of the SawShooter
	 * @param y The y position of the SawShooter
	 * @param delay The delay in between saw shots
	 * @param xVel The horizontal velocity of the saws that will be shot
	 * @param yVel The vertical velocity of the saws that will be shot
	 * @param whichdir The direction that the saws will be shot in
	 */
	public SawShooter(int x,int y, long delay, int xVel, int yVel, int whichdir){
		this.x=x;	
		this.y=y;
		this.xVel=xVel;
		this.yVel=yVel;
		this.delay=delay;
		this.lastSpawn=0;
		
		try {
			if(whichdir==1)
			image = ImageIO.read(new File("resources/sawshooter.png"));
			else
			image = ImageIO.read(new File("resources/sawshooter2.png"));	
		} catch (IOException e) {e.printStackTrace();}
		xscroll=0;
		yscroll=0;
		hitbox = new Rectangle(x,y,40,40);
	}
	/**
	 * Refreshes the saw shooter, allowing it to shoot another saw if enough time has passed
	 * @return True if the saw shooter has not yet shot a saw and will refresh; returns false otherwise
	 */
	public boolean refresh(){
		if(System.currentTimeMillis()-lastSpawn>delay){
			lastSpawn = System.currentTimeMillis();
			return true;
		}
		return false;
	}
	/**
	 * Returns the horizontal velocity of the saws shot by the SawShooter
	 * @return The horizontal velocity of the saws shot by the SawShooter
	 */
	public int getXVel(){
		return xVel;
	}
	/**
	 * Returns the vertical velocity of the saws shot by the SawShooter
	 * @return The vertical velocity of the saws shot by the SawShooter
	 */
	public int getYVel(){
		return yVel;
	}
	/**
	 * Returns the image of the SawShooter
	 * @return The image of the SawShooter
	 */
	public BufferedImage getImage(){
		return image;
	}
	/**
	 * Returns the y position of the top part of the saw shooter
	 * @return The y position of the top part of the saw shooter 
	 */
	public int getTop(){
		return hitbox.y;
	}
	/**
	 * Returns the x position of the left part of the saw shooter
	 * @return The x position of the left part of the saw shooter 
	 */
	public int getLeft(){
		return hitbox.x;
	}
	/**
	 * Returns the x position of the right part of the saw shooter
	 * @return The x position of the right part of the saw shooter 
	 */
	public int getRight(){
		return hitbox.x+40;
	}
	/**
	 * Returns the y position of the bottom part of the saw shooter
	 * @return The y position of the bottom part of the saw shooter 
	 */
	public int getBottom(){
		return hitbox.y+40;
	}
	/**
	 * Returns the horizontal scrolling
	 * @return The horizontal scrolling
	 */
	public int getXScrolled(){
		return x-xscroll;
	}
	/**
	 * Returns the vertical scrolling
	 * @return The vertical scrolling
	 */
	public int getYScrolled(){
		return y-yscroll;
	}
	/**
	 * Returns the x position of the top left corner of the saw shooter
	 * @return The x position of the top left corner of the saw shooter
	 */
	public int getX(){
		return x;
	}
	/**
	 * Returns the y position of the top left corner of the saw shooter
	 * @return The y position of the top left corner of the saw shooter
	 */
	public int getY(){
		return y;
	}
	/**
	 * Returns a Rectangle object that is the saw shooter's hitbox
	 * @return A Rectangle object that is the saw shooter's hitbox
	 */
	public Rectangle getHitbox(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	/**
	 * Sets the horizontal scrolling to a specified value
	 * @param x The specified horizontal scrolling
	 */
	public void setXScroll(int x){
		xscroll=x;
	}
	/**
	 * Sets the vertical scrolling to a specified value
	 * @param y The specified vertical scrolling
	 */
	public void setYScroll(int y){
		yscroll=y;
	}
}