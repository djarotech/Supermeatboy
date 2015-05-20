package character;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import animation.Animation;
import platform.DisappearPlat;
import platform.BuzzSaw;
import platform.Platform;
import level.MeatBoyLevel;
import input.MeatBoyInput;


public class MeatBoy {
	
	//Communicating with:
	private MeatBoyLevel level;
	private MeatBoyInput input;
	private ArrayList<Platform> platforms;
	private ArrayList<BuzzSaw> saws;
	private ArrayList<DisappearPlat> dplist;
	private Animation deathanimation;
	//MeatBoy properties:
	private final int MEATBOY_WIDTH =20;
	private final int MEATBOY_HEIGHT =20;
	private Rectangle hitbox;
	private Rectangle leftHitBox;
	private Rectangle rightHitBox;
	private Rectangle botHitBox;
	private boolean alive;
	private Image meatboy;
	private Image sprintleft;
	private Image sprintright;
	private Image currentState;
	private boolean inAir;
	private int xPos;
	private int yPos;
	private int initXPos;
	private int initYPos;
	private double xVel;
	private double yVel;
	private double xAcceleration;
	private final int MAX_SPEED;
	private final int MAX_FALLING_SPEED;
	private int xscroll; 
	private int yscroll; 
	private int standingLeft;
	private int standingRight;
	private long lastwalljump;
	private long walljumpdelay;
	private boolean spaceflag;
	//touching wall restrictions
	private boolean cannotLeft;
	private boolean cannotRight;
	private boolean touchingBottom;
	

    private double gravity;
	
    
	public MeatBoy(Component c,MeatBoyLevel lev,int x, int y) {
		cannotLeft = false;
		cannotRight = false;
		touchingBottom = false;
		alive=true;
		inAir=true;
		initXPos=xPos=xscroll=x;
		initYPos=yPos=yscroll=y;
		gravity =1.1;
		xAcceleration = 2;
		MAX_SPEED = 17;
		MAX_FALLING_SPEED = 20;
		lastwalljump=0;
		walljumpdelay=200;
		
		level=lev;
		platforms = level.getPlatforms();
		saws=level.getSaws();
		dplist=level.getDPs();

		hitbox = new Rectangle(xPos,yPos,MEATBOY_WIDTH,MEATBOY_HEIGHT);
		leftHitBox = new Rectangle(xPos-4,yPos+4,8,MEATBOY_HEIGHT-4);
		rightHitBox = new Rectangle(xPos+MEATBOY_WIDTH -4,yPos+4,8,MEATBOY_HEIGHT-4);
		botHitBox = new Rectangle(xPos,yPos+MEATBOY_HEIGHT-4,20,4);
		
		deathanimation = new Animation();
		try{
		BufferedImage[] deathframes = new BufferedImage[7];
		BufferedImage bigimage = ImageIO.read(new File("resources/deathanim.png"));
			for(int i=6;i>=0;i--){
				deathframes[i]=bigimage.getSubimage(0, i*110, 148, 110);
			}
			deathanimation.setFrames(deathframes);
			deathanimation.setDelay(0);
		meatboy =ImageIO.read(new File("resources/meatboystanding.png"));
		sprintleft =ImageIO.read(new File("resources/sprintLeft.png"));
		sprintright =ImageIO.read(new File("resources/sprintRight.png"));
		}catch (IOException e) {e.printStackTrace();}
		
		currentState=meatboy;
		input= new MeatBoyInput(c);
	}
	public void move(){
		currentState=meatboy;
		if(!alive){
			restart();
		}
		if(input.isKeyPressed(KeyEvent.VK_R)){
			restart();
		}
		if(!inAir){
			if(input.isKeyPressed(KeyEvent.VK_SPACE)){
				yVel=-14;
				spaceflag = false;
				inAir=true;
			}
			if(input.isKeyPressed(KeyEvent.VK_SPACE)&&input.isKeyPressed(KeyEvent.VK_F))
			{
				yVel=-18;
				spaceflag = false;
			}
			if(input.isKeyPressed(KeyEvent.VK_RIGHT)){
				currentState=sprintright;
				if (cannotRight)
					xVel=0;
				else if(xVel<MAX_SPEED*1.5&&input.isKeyPressed(KeyEvent.VK_F)&&xVel>=0)
					xVel+=xAcceleration;
				else if(xVel<MAX_SPEED)
					xVel +=xAcceleration;
				else if(input.isKeyPressed(KeyEvent.VK_F))
					xVel = MAX_SPEED*1.5;
				else
					xVel=MAX_SPEED;
			}
			else if(input.isKeyPressed(KeyEvent.VK_LEFT)){
				currentState=sprintleft;
				if(cannotLeft)
					xVel = 0;
				else if(xVel>-MAX_SPEED*1.5 && input.isKeyPressed(KeyEvent.VK_F)&&xVel<=0)
					xVel-=xAcceleration;
				else if(xVel>-MAX_SPEED)
					xVel-=xAcceleration;
				else if(input.isKeyPressed(KeyEvent.VK_F))
					xVel = -MAX_SPEED*1.5;
				else 
					xVel=-MAX_SPEED;
			}
			else
				xVel=0;	
		}
		else{
			
			if(input.isKeyPressed(KeyEvent.VK_RIGHT)){
				currentState=sprintright;
				if (cannotRight)
					xVel=0;
				else if(xVel<MAX_SPEED*1.5&&input.isKeyPressed(KeyEvent.VK_F)&&xVel>=0)
					xVel+=xAcceleration;
				else if(xVel<MAX_SPEED)
					xVel +=xAcceleration;
				else if(input.isKeyPressed(KeyEvent.VK_F))
					xVel = MAX_SPEED*1.5;
				else
					xVel=MAX_SPEED;
			}
			else if(input.isKeyPressed(KeyEvent.VK_LEFT)){
				currentState=sprintleft;
				if(cannotLeft)
					xVel = 0;
				else if(xVel>-MAX_SPEED*1.5 && input.isKeyPressed(KeyEvent.VK_F)&&xVel<=0)
					xVel-=xAcceleration;
				else if(xVel>-MAX_SPEED){
					xVel-=xAcceleration;
				}
				else if(input.isKeyPressed(KeyEvent.VK_F))
					xVel = -MAX_SPEED*1.5;
				else 
					xVel=-MAX_SPEED;
			}
			else if(xVel>2)
				xVel-=xAcceleration;
			else if(xVel<-2)
				xVel+=xAcceleration;
			else
				xVel=0;
			if(!input.isKeyPressed(KeyEvent.VK_SPACE)){
				spaceflag=true;
			}
			if(spaceflag&&input.isKeyPressed(KeyEvent.VK_SPACE)&&System.currentTimeMillis()-lastwalljump>walljumpdelay)
			{
				spaceflag=false;
				if(cannotRight)
				{
					if(input.isKeyPressed(KeyEvent.VK_F)){
						xVel = -16;
						yVel = -18;
					}	
					else {
						xVel = -12;
						yVel = -14;
					}
				}
				else if(cannotLeft)
				{	if(input.isKeyPressed(KeyEvent.VK_F)){
						xVel = 16;
						yVel = -18;
					}	
					else {
						xVel = 12;
						yVel = -14;
					}
				}
				lastwalljump=System.currentTimeMillis();
			}
			if(yVel<=MAX_FALLING_SPEED)
				yVel+=gravity;
		}
		xPos+=xVel;
		yPos+=yVel;
		cannotRight = false;
		cannotLeft = false;
		touchingBottom=false;
		hitbox = new Rectangle(xPos,yPos,MEATBOY_WIDTH,MEATBOY_HEIGHT );
		leftHitBox = new Rectangle(xPos-4,yPos+4,8,MEATBOY_HEIGHT-4);
		rightHitBox = new Rectangle(xPos+MEATBOY_WIDTH -4,yPos+4,8,MEATBOY_HEIGHT-4);
		botHitBox = new Rectangle(xPos,yPos+MEATBOY_HEIGHT-4,20,4);
		checkCollisions();
		if(yPos>level.getHeight()){
			restart();
		}
		if(xPos<0){
			xPos=0;
			xVel=0;
		}
		if(xPos>level.getWidth()-MEATBOY_WIDTH){
			xPos=level.getWidth()-MEATBOY_WIDTH;
			xVel=0;
		}
		if(yPos<0){
			yPos=0; 
			yVel=0;
		}
		xscroll=xPos;
		yscroll=yPos;
		
	}
	public void draw(Graphics g) {
		g.drawImage(currentState,xPos-xscroll,yPos-yscroll,MEATBOY_WIDTH,MEATBOY_HEIGHT, null);
	}
	public void checkCollisions(){
		dplist=level.getDPs();
		alive=true;
		for(int i=0;i<saws.size()&&alive;i++){
			boolean hitsaw = checkCircleCollision(saws.get(i));
			if(hitsaw){
				alive=false;
			}
		}
		if(alive){
			ArrayList<Platform> tmplist = new ArrayList<Platform>();
			tmplist.addAll(platforms);
			tmplist.addAll(dplist);
			for(int i=0;i<tmplist.size();i++){
				Platform temp = tmplist.get(i);
				if(hitbox.intersects(temp.getHitbox())){
					if(temp instanceof DisappearPlat){
						((DisappearPlat) temp).setTouched();
					}
					if (Math.abs(xPos+MEATBOY_WIDTH-temp.getLeft())<=xVel+5 && yPos>temp.getTop()-MEATBOY_HEIGHT && yPos<temp.getBottom() && rightHitBox.intersects(temp.getHitbox()))
					{
						xPos = temp.getLeft()-MEATBOY_WIDTH;
						cannotRight = true;
					}
					else if (Math.abs(xPos-temp.getRight())<=Math.abs(xVel-5) && yPos>temp.getTop()-MEATBOY_HEIGHT && yPos<temp.getBottom() && leftHitBox.intersects(temp.getHitbox()))
					{
						xPos = temp.getRight();
						cannotLeft = true;
					}
					else if (yVel>=0 && yPos+MEATBOY_HEIGHT<temp.getBottom())//(xPos>temp.getLeft()-MEATBOY_WIDTH && xPos<temp.getRight() && yPos+MEATBOY_HEIGHT-temp.getTop()<=yVel)
					{
							yVel = 0;
							standingLeft = temp.getLeft();
							standingRight = temp.getRight();
							yPos = temp.getTop()-MEATBOY_HEIGHT;
							inAir=false;
					}
					else//(xPos>=temp.getLeft()-MEATBOY_WIDTH && xPos<=temp.getRight() && (yPos+MEATBOY_HEIGHT>temp.getBottom()-1 && yPos<temp.getBottom()+1))
					{
						touchingBottom = true;
						yPos = temp.getBottom();
						yVel = 0;
					}
				}	
				else{
					if(!inAir && (xPos+MEATBOY_WIDTH<standingLeft+5 || xPos>standingRight-5))
					{	
						inAir = true;
					}
					if(inAir && leftHitBox.intersects(temp.getHitbox()) && !touchingBottom)
					{
						if(temp instanceof DisappearPlat){
							((DisappearPlat) temp).setTouched();
						}
						cannotLeft= true;
					}
					else if (inAir && rightHitBox.intersects(temp.getHitbox()) && !touchingBottom)
					{
						if(temp instanceof DisappearPlat){
							((DisappearPlat) temp).setTouched();
						}
						cannotRight = true;
					}
				}
			}
		}
	}
	public boolean checkCircleCollision(BuzzSaw s){
		double closestx = clamp(s.getXMiddle(),hitbox.x, hitbox.x+MEATBOY_WIDTH);
		double closesty = clamp(s.getYMiddle(),hitbox.y, hitbox.y+MEATBOY_HEIGHT);
		double dx=s.getXMiddle()-closestx;
		double dy=s.getYMiddle()-closesty;
		double distance = (Math.pow(dx,2)+Math.pow(dy, 2));
		return distance<=(s.getRadius()*s.getRadius());
	}
	private double clamp(double value, int min, int max){
		return Math.max(min, Math.min(max,value));
	}
	public void restart(){
		dplist=level.getOriginalDPs();
		level.resetDPs();
		deathanimation.resetAnimation();
		xPos = initXPos;
		yPos = initYPos;
		inAir = true;
	}
	public Rectangle getHitbox(){
		return hitbox;
	}
	public Rectangle getBotHitbox(){
		return botHitBox;
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
	public void setInAir(){
		inAir=true;
	}
	public int getXScroll(){
		return xscroll;
	}
	public int getYScroll(){
		return yscroll;
	}
	public void setXScroll(int x ){
		xscroll=x;
	}
	public void setYScroll(int y){
		yscroll=y;
	}
	public Animation getAnimation(){
		return deathanimation;
	}
}
