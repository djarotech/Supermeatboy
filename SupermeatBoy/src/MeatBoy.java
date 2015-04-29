import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class MeatBoy implements KeyListener{
	private int xPos;
	private int yPos;
	private int xVel;
	private int yVel;
	private Rectangle hitbox;
	public MeatBoy(Component c){
		xPos=50;
		yPos=50;
		hitbox = new Rectangle(xPos,yPos,20,20);
		c.addKeyListener(this);
	}
	public void move(){
		if(xPos+xVel<0||xPos+xVel>600){
			xVel=0;
			xPos=0;
		}
		if(yPos+yVel<0||yPos+yVel>600){
			yVel=0;
			yPos=0;
		}
		xPos+=xVel;
		yPos+=yVel;
	}
	public void draw(Graphics g) throws IOException{
		Image meatboy =ImageIO.read(new File("src/meatboy.jpg"));
		g.drawImage(meatboy,xPos,yPos,20,20, null);
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key==KeyEvent.VK_UP){
			yVel=1;
		}
		if(key==KeyEvent.VK_LEFT){
			xVel=-1; 
		}
		if(key==KeyEvent.VK_RIGHT){
			xVel=1;
		}
		if(key==KeyEvent.VK_DOWN){
			yVel=-1;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key==KeyEvent.VK_UP){
			yVel=0;
		}
		if(key==KeyEvent.VK_LEFT){
			xVel=0; 
		}
		if(key==KeyEvent.VK_RIGHT){
			xVel=0;
		}
		if(key==KeyEvent.VK_DOWN){
			yVel=0;
		}
	}
}
