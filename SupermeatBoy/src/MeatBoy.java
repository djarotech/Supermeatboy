import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
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
	private int frame_width;
	private int frame_height;
	private MeatBoyInput input;
	private boolean alive;
	private Image meatboy;
	private boolean inAir;
	public MeatBoy(Component c) throws IOException{
		frame_height=600;
		frame_width=600;
		xPos=500;
		yPos=500;
		alive=true;
		inAir=false;
		hitbox = new Rectangle(xPos,yPos,MEATBOY_WIDTH,MEATBOY_HEIGHT);
		//meatboy =Toolkit.getDefaultToolkit().createImage("meatboy.jpg");
		meatboy =ImageIO.read(new File("src/meatboy.jpg"));
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
			yPos=40; //appx position below the title panel
		}
		else if(yPos+yVel>frame_height-MEATBOY_HEIGHT){
			yPos=frame_height-MEATBOY_HEIGHT;
		}
		else{
			xPos+=xVel;
			yPos+=yVel;
		}
	} 
	public void draw(Graphics g) throws IOException {
		//Image meatboy =Toolkit.getDefaultToolkit().createImage("src/meatboy.jpg");
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
