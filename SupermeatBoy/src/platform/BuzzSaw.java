package platform;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import animation.Animation;

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
	public BuzzSaw(int x,int y, double diameter){
		xscroll=0;
		yscroll=0;
		bigimage = null;
		
		try {
			bigimage = ImageIO.read(new File("resources/buzzsaw.png"));
		} catch (IOException e) {e.printStackTrace();}
		
		rotateAnimation = new Animation();
		moving=false;
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
	public BuzzSaw(int x,int y, double diameter, int xVel, int yVel){
		xscroll=0;
		yscroll=0;
		bigimage = null;
		this.xVel=xVel;
		this.yVel=yVel;
		moving=true;
		try {
			bigimage = ImageIO.read(new File("resources/buzzsaw.png"));
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
	public void move(){
		x+=xVel;
		y+=yVel;
	}
	public Animation getAnimation(){
		return rotateAnimation;
	}
	public int getX(){
		return x-xscroll;
	}
	public int getY(){
		return y-yscroll;
	}
	public double getXMiddle(){
		return x+radius;
	}
	public double getYMiddle(){
		return y+radius;
	}
	public double getRadius(){
		return radius;
	}
	public void setXScroll(int x){
		xscroll=x;
	}
	public void setYScroll(int y){
		yscroll=y;
	}
	public boolean isMoving(){
		return moving;
	}
}
