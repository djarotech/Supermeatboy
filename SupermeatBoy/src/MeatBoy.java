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
	
	private Graphics offgc;
    private BufferedImage offscreen;
    private double gravity;
    private int player_ground; // y position, where the meatboy is, is he on a platform?
    private int stage_ground; // where the bottom of the stage is
    
	public MeatBoy(Component c,MeatBoyLevel lev) {
		gravity =2.5;
		offscreen = new BufferedImage(MEATBOY_HEIGHT,MEATBOY_WIDTH,BufferedImage.TYPE_INT_RGB);
		offgc = offscreen.getGraphics();
		level=lev;
		platforms = level.getPlatforms();
		offscreen=null;
		xPos=200;
		yPos=500;
		player_ground=stage_ground=yPos;
		alive=true;
		inAir=false;
		hitbox = new Rectangle(xPos,yPos,MEATBOY_WIDTH,MEATBOY_HEIGHT);
		meatboy =Toolkit.getDefaultToolkit().createImage("src/meatboy.jpg");
		input= new MeatBoyInput(c);
	}
	public void move(){
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
					yVel=-30;
					inAir=true;
				}
				if(input.isKeyPressed(KeyEvent.VK_RIGHT)){
					xVel=10;
				}
				else if(input.isKeyPressed(KeyEvent.VK_LEFT)){
					xVel=-10;
				}
				else
					xVel=0;	
			}
			else if(yPos+yVel>=stage_ground){
				inAir=false;
				yVel=0;
				yPos=stage_ground;
			}
			else{
				
				if(input.isKeyPressed(KeyEvent.VK_RIGHT)){
					xVel=10;
				}
				else if(input.isKeyPressed(KeyEvent.VK_LEFT)){
					xVel=-10;
				}
				else
					xVel=0;
				yVel+=gravity;
			}
			xPos+=xVel;
			yPos+=yVel;
			hitbox = new Rectangle(xPos,yPos,MEATBOY_HEIGHT, MEATBOY_WIDTH); //hitboxs moves with it
			
			for(int i=0;i<platforms.size();i++){
				if(hitbox.intersects(platforms.get(i).getHitbox())){ //for collision detection, people use intersect, of the rectangle class
					player_ground=platforms.get(i).getTop();	//This section assumes that the collision occurred on the top
					yPos=player_ground-MEATBOY_HEIGHT;			// of the platform. This part needs a lot of work, check the sides and bottoms
					yVel=0;										// also, currently, meatboy is able to jump off a platform but not walk off.
					inAir=false;								
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
