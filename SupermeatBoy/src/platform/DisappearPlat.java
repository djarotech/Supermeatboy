package platform;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import animation.Animation;

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
	public DisappearPlat(int x, int y){
		yscroll=0;
		xscroll=0;
		this.x=x;
		this.y=y;
		hitbox=new Rectangle(x,y,40,40);
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
	public Animation getAnimation(){
		return animation;
	}
	public int getTop(){
		return hitbox.y;
	}
	public int getLeft(){
		return hitbox.x;
	}
	public int getRight(){
		return hitbox.x+40;
	}
	public int getBottom(){
		return hitbox.y+40;
	}
	public int getX(){
		return x-xscroll;
	}
	public int getY(){
		return y-yscroll;
	}
	public Rectangle getHitbox(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	public void setXScroll(int x){
		xscroll=x;
	}
	public void setYScroll(int y){
		yscroll=y;
	}
	public boolean isTouched(){
		return touched;
	}
	public void setTouched(){
		touched=true;
	}
	public void resetTouched(){
		touched=false;
	}
}
