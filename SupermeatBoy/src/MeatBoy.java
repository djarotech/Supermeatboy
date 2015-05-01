import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class MeatBoy {
	
	//Communicating with:
	private MeatBoyLevel level;
	private MeatBoyInput input;
	private ArrayList<Platform> platforms;
	//MeatBoy properties:
	private final int MEATBOY_WIDTH =30;
	private final int MEATBOY_HEIGHT =30;
	private Rectangle hitbox;
	private boolean alive;
	private Image meatboy;
	private boolean inAir;
	private int xPos;
	private int yPos;
	private double xVel;
	private double yVel;
	
	private int standingLeft;
	private int standingRight;
	
	//touching wall restrictions
	private boolean cannotLeft;
	private boolean cannotRight;
	
	
	private Graphics offgc;
    private BufferedImage offscreen;
    private double gravity;
    
	public MeatBoy(Component c,MeatBoyLevel lev) {
		cannotLeft = false;
		cannotRight = false;
		
		gravity =2.5;
		offscreen = new BufferedImage(MEATBOY_HEIGHT,MEATBOY_WIDTH,BufferedImage.TYPE_INT_RGB);
		offgc = offscreen.getGraphics();
		level=lev;
		platforms = level.getPlatforms();
		offscreen=null;
		xPos=100;
		yPos=100;
		alive=true;
		inAir=true;
		hitbox = new Rectangle(xPos,yPos,MEATBOY_WIDTH,MEATBOY_HEIGHT);
		meatboy =Toolkit.getDefaultToolkit().createImage("src/meatboy.jpg");
		input= new MeatBoyInput(c);
		Platform standingPlatform = new Platform(0,0,0,0);
	}
	public void move(){
		if(input.isKeyPressed(KeyEvent.VK_R)){
			xPos = 100;
			yPos = 100;
			inAir = true;
		}
		if(xPos+xVel<0){
			xPos=0;
		}
		else if(xPos+xVel>600-MEATBOY_WIDTH){
			xPos=level.WIDTH-MEATBOY_WIDTH;
		}
		else if(yPos+yVel<40){
			yPos=40; 
		}
		else if(yPos+yVel>600-MEATBOY_HEIGHT){
			yPos=level.HEIGHT-MEATBOY_HEIGHT;
		}
		else{
			if(!inAir){
				if(input.isKeyPressed(KeyEvent.VK_UP)){
					yVel=-22;
					inAir=true;
				}
				if(input.isKeyPressed(KeyEvent.VK_RIGHT)){
					xVel=10;
					if (cannotRight)
						xVel=0;
				}
				else if(input.isKeyPressed(KeyEvent.VK_LEFT)){
					xVel=-10;
					if(cannotLeft)
						xVel = 0;
				}
				else
					xVel=0;	
			}

			else{
				
				if(input.isKeyPressed(KeyEvent.VK_RIGHT)){
					xVel=10;
					if (cannotRight)
						xVel=0;
				}
				else if(input.isKeyPressed(KeyEvent.VK_LEFT)){
					xVel=-10;
					if(cannotLeft)
						xVel = 0;
				}
				else
					xVel=0;
				yVel+=gravity;
			}
			xPos+=xVel;
			yPos+=yVel;
			hitbox = new Rectangle(xPos,yPos,MEATBOY_HEIGHT, MEATBOY_WIDTH); //hitboxs moves with it
			cannotRight = false;
			cannotLeft = false;
			//System.out.println("" + inAir + xPos + " " + yPos);
			for(int i=0;i<platforms.size();i++){
				Platform temp = platforms.get(i);
				if(hitbox.intersects(temp.getHitbox())){ //for collision detection, people use intersect, of the rectangle class
					//left wall case
					if (Math.abs(xPos+MEATBOY_WIDTH-temp.getLeft())<=10 && yPos>temp.getTop()-MEATBOY_HEIGHT && yPos<temp.getBottom())
					{
						System.out.println("Left" + i);
						xPos = temp.getLeft()-MEATBOY_WIDTH;
						cannotRight = true;
						
					}
					//right wall case
					else if (Math.abs(xPos-temp.getRight())<=10 && yPos>temp.getTop()-MEATBOY_HEIGHT && yPos<temp.getBottom())
					{
						System.out.println("Right" + i);
						xPos = temp.getRight();
						cannotLeft = true;
						
					}
					//top case
					else if (xPos>temp.getLeft()-MEATBOY_WIDTH && xPos<temp.getRight() && Math.abs(yPos+MEATBOY_HEIGHT-temp.getTop())<=50)
					{						
						System.out.println("Top" + i);
						yPos = temp.getTop()-MEATBOY_HEIGHT;
						yVel = 0;
						standingLeft = temp.getLeft();
						standingRight = temp.getRight();
						inAir=false;	
					}
					//bottom case
					else if (xPos>=temp.getLeft()-MEATBOY_WIDTH && xPos<=temp.getRight() && (yPos+MEATBOY_HEIGHT>temp.getBottom()-1 && yPos<temp.getBottom()+1))
					{
						System.out.println("Bottom" + i);
						yPos = temp.getBottom();
						yVel = 0;
					}							
				}
				else
				{
					if(!inAir && (xPos<standingLeft || xPos>standingRight))
					{
						inAir = true;
						
					}
				}
			}
			
		}
	}

	public void draw(Graphics g) {
		offscreen = new BufferedImage(MEATBOY_HEIGHT,MEATBOY_WIDTH,BufferedImage.TYPE_INT_RGB);
		offgc = offscreen.getGraphics();
		offgc.drawImage(meatboy,0,0,MEATBOY_HEIGHT,MEATBOY_WIDTH, null);
		g.drawImage(offscreen,xPos,yPos,MEATBOY_HEIGHT,MEATBOY_WIDTH, null);
	}
	public boolean isAlive(){
		return alive;
	}
	public int getX(){
		return xPos;
	}
	public int getY(){
		return yPos;
	}
	public boolean isInAir(){
		return inAir;
	}
}
