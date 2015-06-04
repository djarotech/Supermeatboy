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

import animation.Animation;

/**
 * Disappearing platforms play a disappearing animation after MeatBoy touches them, after which they disappear and cease to function as a platform
 */
public class DisappearPlat extends Platform {
	private boolean touched;
	private static final int WIDTH =40;
	private static final int HEIGHT =40;
	private BufferedImage bigimage;
	private Animation animation;
	private int x;
	private int y;
	private int xscroll;
	private int yscroll;
	private BufferedImage[] arr;
	private Rectangle hitbox;
	/**
	 * Creates a new Disappearing Platform with a specified position
	 * @param x The x position of the disappearing platform's top left corner
	 * @param y The y position of the disappearing platforms's top left corner
	 */
	public DisappearPlat(int x, int y){
		yscroll=0;
		xscroll=0;
		this.x=x;
		this.y=y;
		hitbox=new Rectangle(x,y,WIDTH,HEIGHT);
		bigimage = null;
		animation = new Animation();
		arr = new BufferedImage[11];	
		try {
			bigimage = ImageIO.read(new File("resources/disappearing.png"));
		} catch (IOException e) {e.printStackTrace();}
		for(int i=0;i<11;i++){
			arr[i]=bigimage.getSubimage(i*41, 0, 41, 80);
		}
		animation.setFrames(arr);
		animation.setDelay(70);
	}
	/**
	 * Returns the Animation object related to the disappearing platform
	 * @return The Animation object related to the disappearing platform
	 */
	public Animation getAnimation(){
		return animation;
	}
	/**
	 * Returns the y position of the top part of the disappearing platform
	 * @return The y position of the top part of the disappearing platform 
	 */
	public int getTop(){
		return hitbox.y;
	}
	/**
	 * Returns the x position of the left part of the disappearing platform
	 * @return The x position of the left part of the disappearing platform 
	 */
	public int getLeft(){
		return hitbox.x;
	}
	/**
	 * Returns the x position of the right part of the disappearing platform
	 * @return The x position of the right part of the disappearing platform 
	 */
	public int getRight(){
		return hitbox.x+WIDTH;
	}
	/**
	 * Returns the y position of the bottom part of the disappearing platform
	 * @return The y position of the bottom part of the disappearing platform 
	 */
	public int getBottom(){
		return hitbox.y+HEIGHT;
	}
	/**
	 * Returns the x position of the disappearing platform's top left corner
	 * @return The x position of the disappearing platform's top left corner
	 */
	public int getX(){
		return x-xscroll;
	}
	/**
	 * Returns the y position of the disappearing platform's top left corner
	 * @return The y position of the disappearing platform's top left corner
	 */
	public int getY(){
		return y-yscroll;
	}
	/**
	 * Returns a Rectangle object that is the hitbox of the disappearing platform
	 * @return A Rectangle object that is the hitbox of the disappearing platform
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
	/**
	 * Returns true if a disappearing platform has been touched by the player
	 * @return Whether or not a disappearing platform has been touched
	 */
	public boolean isTouched(){
		return touched;
	}
	/**
	 * Sets a disappearing platform to have been touched by the player
	 */
	public void setTouched(){
		touched=true;
	}
	/**
	 * Sets a disappearing platform to have not been touched by the player
	 */
	public void resetTouched(){
		touched=false;
	}
}