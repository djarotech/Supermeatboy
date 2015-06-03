/*Super Meat Boy
 *Kevin Mao
 *Ritchie Chen
 *Daniel Moore
 *CS3 Final project
 *Gallatin-3rd
 */
package platform;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import animation.Animation;

/**
 * BuzzSaw objects act as an obstacle to the player. When MeatBoy touches them, he dies and is forced to restart the level
 */
public class BuzzSaw {
	private BufferedImage bigimage;
	private Animation rotateAnimation;
	private int x;
	private int y;
	private double diameter;
	private double radius;
	private int xVel;
	private int yVel;
	private int xscroll;
	private int yscroll;
	private BufferedImage[] arr;
	private boolean moving;
	private int removable; // should start being removed after it moves 3 ticks because it could spawn inside of a wall.
	
	/**
	 * Creates a new stationary BuzzSaw object with a specified location, and size
	 * @param x The x position of the saw's top left corner
	 * @param y The y position of the saw's top left corner
	 * @param diameter The diameter of the saw
	 */
	public BuzzSaw(int x,int y, double diameter){
		xscroll=0;
		yscroll=0;
		bigimage = null;
		
		try {
			bigimage = ImageIO.read(new File("resources/buzzsaw2.png"));
		} catch (IOException e) {e.printStackTrace();}
		
		rotateAnimation = new Animation();
		moving=false;
		removable=0;
		xVel=0;
		yVel=0;
		arr = new BufferedImage[3];
		BufferedImage orig1 =bigimage.getSubimage(0, 0, 230, 230);
		BufferedImage orig2 =bigimage.getSubimage(230, 0, 230, 230);
		BufferedImage orig3 = bigimage.getSubimage(460, 0, 230, 230);
		double ratiox = diameter/230;
		double ratioy = diameter/230;
		BufferedImage resized1= new BufferedImage((int)(ratiox*230), (int)(ratioy*230), BufferedImage.TYPE_INT_ARGB);
		BufferedImage resized2= new BufferedImage((int)(ratiox*230), (int)(ratioy*230), BufferedImage.TYPE_INT_ARGB);
		BufferedImage resized3= new BufferedImage((int)(ratiox*230), (int)(ratioy*230), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g1 = (Graphics2D) resized1.getGraphics();
		Graphics2D g2 = (Graphics2D) resized2.getGraphics();
		Graphics2D g3 = (Graphics2D) resized3.getGraphics();
		g1.scale(ratiox,ratioy);
		g2.scale(ratiox,ratioy);
		g3.scale(ratiox,ratioy);
		g1.drawImage(orig1,0,0,null);
		g2.drawImage(orig2,0,0,null);
		g3.drawImage(orig3,0,0,null);		
		arr[0]=resized1;
		arr[1]=resized2;
		arr[2]=resized3;
		rotateAnimation.setFrames(arr);
		rotateAnimation.setDelay(40);
		this.x=x;
		this.y=y;
		this.diameter=diameter;
		this.radius=this.diameter/2;
	}
	/**
	 * Creates a new moving BuzzSaw object with a specified location, size, and speed
	 * @param x The x position of the saw's top left corner
	 * @param y The y position of the saw's top left corner
	 * @param diameter The diameter of the saw
	 * @param xVel The horizontal velocity of the saw
	 * @param yVel The vertical velocity of the saw
	 */
	public BuzzSaw(int x,int y, double diameter, int xVel, int yVel){
		xscroll=0;
		yscroll=0;
		bigimage = null;
		this.xVel=xVel;
		this.yVel=yVel;
		moving=true;
		try {
			bigimage = ImageIO.read(getClass().getResource("/resources/buzzsaw2.png"));
		} catch (IOException e) {e.printStackTrace();}
		rotateAnimation = new Animation();
		arr = new BufferedImage[3];
		BufferedImage orig1 =bigimage.getSubimage(0, 0, 230, 230);
		BufferedImage orig2 =bigimage.getSubimage(230, 0, 230, 230);
		BufferedImage orig3 = bigimage.getSubimage(460, 0, 230, 230);
		double ratiox = diameter/230;
		double ratioy = diameter/230;
		BufferedImage resized1= new BufferedImage((int)(ratiox*230), (int)(ratioy*230), BufferedImage.TYPE_INT_ARGB);
		BufferedImage resized2= new BufferedImage((int)(ratiox*230), (int)(ratioy*230), BufferedImage.TYPE_INT_ARGB);
		BufferedImage resized3= new BufferedImage((int)(ratiox*230), (int)(ratioy*230), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g1 = (Graphics2D) resized1.getGraphics();
		Graphics2D g2 = (Graphics2D) resized2.getGraphics();
		Graphics2D g3 = (Graphics2D) resized3.getGraphics();
		g1.scale(ratiox,ratioy);
		g2.scale(ratiox,ratioy);
		g3.scale(ratiox,ratioy);
		g1.drawImage(orig1,0,0,null);
		g2.drawImage(orig2,0,0,null);
		g3.drawImage(orig3,0,0,null);		
		arr[0]=resized1;
		arr[1]=resized2;
		arr[2]=resized3;
		rotateAnimation.setFrames(arr);
		rotateAnimation.setDelay(40);
		this.x=x;
		this.y=y;
		this.diameter=diameter;
		this.radius=this.diameter/2;
	}
	/**
	 * Moves the BuzzSaw 
	 */
	public void move(){
		removable++;
		x+=xVel;
		y+=yVel;
	}
	/**
	 * Returns true if the buzzsaw is removable
	 * @return Whether or not the buzzsaw is removable
	 */
	public boolean canRemove(){
		return removable>3;
	}
	/**
	 * Returns the Animation object related to the saw
	 * @return The Animation object related to the saw
	 */
	public Animation getAnimation(){
		return rotateAnimation;
	}
	/**
	 * Returns the x position of the saw's top left corner
	 * @return The x position of the saw's top left corner
	 */
	public int getX(){
		return x-xscroll;
	}
	/**
	 * Returns the y position of the saw's top left corner
	 * @return The y position of the saw's top left corner
	 */
	public int getY(){
		return y-yscroll;
	}
	/**
	 * Returns the x position of the middle of the saw
	 * @return The x postion of the middle of the saw
	 */
	public double getXMiddle(){
		return x+radius;
	}
	/**
	 * Returns the y position of the middle of the saw
	 * @return The y position of the middle of the saw
	 */
	public double getYMiddle(){
		return y+radius;
	}
	/**
	 * Returns the radius of the saw
	 * @return The radius of the saw
	 */
	public double getRadius(){
		return radius;
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
	 * Returns true if the saw is currently moving
	 * @return Whether or not the saw is moving
	 */
	public boolean isMoving(){
		return moving;
	}
}