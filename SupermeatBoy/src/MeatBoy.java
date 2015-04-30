import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class MeatBoy {
	private final int MEATBOY_WIDTH =20;
	private final int MEATBOY_HEIGHT =20;
	private int xPos;
	private int yPos;
	private double xVel;
	private double yVel;
	private Rectangle hitbox;
	private double slowToStop;
	private int frame_width;
	private int frame_height;
	private MeatBoyInput input;
	private boolean alive;
	
	private String meatBoyStatus; // is he on a wall?
	
	public MeatBoy(Component c){
		frame_height=c.getHeight();
		frame_width=c.getWidth();
		slowToStop=.2;
		xPos=500;
		yPos=500;
		alive=true;
		hitbox = new Rectangle(xPos,yPos,20,20);
		meatBoyStatus = "none";// starts airborne
		input= new MeatBoyInput(c);
		Thread movement = new Thread(new MeatBoyRunnable(input,this));
		movement.start();
	}
	public void move(){
		if(xPos+xVel<0){
			xPos=0;
		}
		else if(xPos+xVel>frame_width-MEATBOY_WIDTH){
			xPos=frame_width-MEATBOY_WIDTH;
		}
		else if(yPos+yVel<40){
			yPos=40;
		}
		else if(yPos+yVel>frame_height-MEATBOY_HEIGHT){
			yPos=frame_height-MEATBOY_HEIGHT;
		}
		else{
			xPos+=xVel;
			yPos+=yVel;
		}
	} 
	public void draw(Graphics g) throws IOException{
		Image meatboy =ImageIO.read(new File("src/meatboy.jpg"));
		g.drawImage(meatboy,xPos,yPos,MEATBOY_HEIGHT,MEATBOY_WIDTH, null);
	}
	public void setYVel(double newVel){
		yVel = newVel;
	}
	public void setXVel(double newVel){
		xVel=newVel;
	}
	public double getXVel(){
		return xVel;
	}
	public double getYVel(){
		return yVel;
	}
	
	public int getXPos(){
		return xPos;
	}
	
	public int getYPos(){
		return yPos;
	}
	
	// sets whether he is touching a wall or airborne
	public void setMeatBoyStatus(String s)
	{
		meatBoyStatus = s;
	}
	
	// gets whether he is touching a wall or airborne
	public String getMeatBoyStatus()
	{
		return meatBoyStatus;
	}
	
	public boolean isAlive(){
		return alive;
	}
}
