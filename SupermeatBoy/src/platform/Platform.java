package platform;

import java.awt.Rectangle;

/**
 * The Platform class is a solid object that MeatBoy cannot pass through
 */
public class Platform  {
	private int xCoord;
	private int yCoord;
	private int width;
	private int height;
	private Rectangle platformHitbox;
	private int xscroll;
	private int yscroll;
	
	/**
	 * Creates a Platform object with all instance fields set to 0 and null
	 */
	public Platform(){
		xCoord=0;
		yCoord=0;
		width=0;
		height=0;
		xscroll=0;
		yscroll=0;
		platformHitbox=null;
	}
	/**
	 * Creates a Platform object with a specified position and size
	 * @param x The x position of the Platform
	 * @param y The y position of the Platform
	 * @param w The width of the Platform
	 * @param h The height of the Platform
	 */
	public Platform(int x, int y, int w, int h){
		xscroll=0;
		yscroll=0;
		xCoord = x;
		yCoord = y;
		width = w;
		height = h;
		platformHitbox = new Rectangle(xCoord,yCoord,width,height);

	}
	/**
	 * Returns a Rectangle object that is the hitbox of the platform
	 * @return A Rectangle object that is the hitbox of the platform
	 */
	public Rectangle getHitbox(){
		return platformHitbox;
	}
	/**
	 * Returns the y position of the top part of the platform
	 * @return The y position of the top part of the platform 
	 */
	public int getTop(){
		return platformHitbox.y;
	}
	/**
	 * Returns the x position of the left part of the platform
	 * @return The x position of the left part of the platform 
	 */
	public int getLeft(){
		return platformHitbox.x;
	}
	/**
	 * Returns the x position of the right part of the platform
	 * @return The x position of the right part of the platform 
	 */
	public int getRight(){
		return platformHitbox.x+width;
	}
	/**
	 * Returns the y position of the bottom part of the platform
	 * @return The y position of the bottom part of the platform 
	 */
	public int getBottom(){
		return platformHitbox.y+height;
	}
	/**
	 * Sets the horizontal and vertical scrolling to specified values
	 * @param dx The specified horizontal scrolling
	 * @param dy The specified vertical scrolling
	 */
	public void setScroll(int dx,int dy){
		xscroll=dx;
		yscroll=dy;
	}
}