package level;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import platform.BuzzSaw;
import platform.DisappearPlat;
import platform.Platform;
import platform.SawShooter;
import tile.TileMap;

import javax.swing.*;

import app.MeatBoyFrame;
import character.MeatBoy;
import character.BandageGirl;

/**
 * The MeatBoyLevel class reads in a .tmx file and creates a level using the platform locations and tilesets specified within the tmx file
 */
public class MeatBoyLevel extends JPanel implements ActionListener{
	private Timer time;
	private MeatBoy player;
	private int mbxstart;
	private int mbystart;
	private BandageGirl destination;
	private int width;
	private int height;
	private int frame_width;
	private int frame_height;
	private ArrayList<Platform> platformList;
	private ArrayList<BuzzSaw> sawlist;
	private ArrayList<DisappearPlat> originaldplist;
	private ArrayList<DisappearPlat> dplist;
	private ArrayList<SawShooter> sslist;
	private int xscroll;
	private int yscroll;
	private BufferedImage entirebackground;
	private BufferedImage subbackground;
	private TileMap tmap;
	private boolean finished;
	private int deathCounter;
	public static int BUFFER=40; //buffer of two tiles for spawning purposes
	
	/**
	 * Creates a new level based on a specified tmx file 
	 * @param frame The MeatBoyFrame to place the level on
	 */
	public MeatBoyLevel(MeatBoyFrame frame)   {
		deathCounter=0;
		frame_height=frame.getHeight()-40;
 		frame_width=frame.getWidth();
		String src = "resources/forest5.tmx";	//change this to try other levels
		tmap = new TileMap(new File(src));
		entirebackground = tmap.drawMap();
		destination = tmap.getBandageGirl();
		mbxstart = tmap.getXStart();
		mbystart = tmap.getYStart();
		subbackground=null;
		width=tmap.getNumCols()*TileMap.TILE_SIZE;
		height=tmap.getNumRows()*TileMap.TILE_SIZE;
 		
		platformList=tmap.getPlatforms();
		sawlist=tmap.getSaws();
		originaldplist=new ArrayList<DisappearPlat>(tmap.getDPs());
		dplist=new ArrayList<DisappearPlat>(tmap.getDPs());
		sslist=tmap.getSS(); //ss means sawshooter
		player = new MeatBoy(frame,this, mbxstart,mbystart);	
		destination = tmap.getBandageGirl();
		time=new Timer(40,this);
		//start updating this level
		start();
	}
	/**
	 * Starts the level by beginning the timer, which will advance the level 
	 */
	public void start(){
		time.start(); 

	}
	/**
	 * Updates the status of all current objects in the level, such as MeatBoy, platforms, disappearing platforms, saws, and saw shooters
	 */
	public void update(){
		if(!finished){
			if(player.getHitbox().intersects(destination.getHitbox())){
				finished=true;
			}
			player.move();
			xscroll=player.getXScroll()-frame_width/2;
			yscroll=player.getYScroll()-frame_height/2;
			if(xscroll<0||width<frame_width)
				xscroll=0;
			else if(xscroll>width-frame_width)
				xscroll=width-frame_width;
			if(yscroll<0||height<frame_height)
				yscroll=0;
			else if(yscroll>height-frame_height)
				yscroll=height-frame_height;
			player.setXScroll(xscroll);
			player.setYScroll(yscroll);
			for(int i=0;i<sslist.size();i++){
				sslist.get(i).setXScroll(xscroll);
				sslist.get(i).setYScroll(yscroll);
				if(sslist.get(i).refresh()){
					sawlist.add(new BuzzSaw(sslist.get(i).getX()+5,
							sslist.get(i).getY()+5,
							30,
							sslist.get(i).getXVel(),
							sslist.get(i).getYVel())
					);
				}
			}
			
			for(int i=0;i<sawlist.size();i++){
				sawlist.get(i).getAnimation().update();
				sawlist.get(i).setXScroll(xscroll);
				sawlist.get(i).setYScroll(yscroll);
				if(sawlist.get(i).isMoving()){
					sawlist.get(i).move();
					boolean removed = false;
					if(sawlist.get(i).canRemove()){
						if(sawlist.get(i).getX()>width||sawlist.get(i).getY()>height){
							sawlist.remove(i);
							removed=true;
							System.out.println("sup");
						}
						else{
							ArrayList<Platform> tmplist = new ArrayList<Platform>();
							tmplist.addAll(platformList);
							tmplist.addAll(dplist);
							for(int j=0;j<tmplist.size()&&!removed;j++){
								if(checkCircleCollision(sawlist.get(i), tmplist.get(j))){
									sawlist.remove(i);
									removed=true;
								}
							}
							
						}
					}
					
				}
			}
			for(int i=0;i<dplist.size();i++){
				dplist.get(i).setXScroll(xscroll);
				dplist.get(i).setYScroll(yscroll);
				if(dplist.get(i).isTouched()){
					dplist.get(i).getAnimation().update();
				}
			}
		}
	}
	/**
	 * Draws all necessary objects onto the level
	 * @param g The graphics object to draw with
	 */
	public void paintComponent(Graphics g){
			super.paintComponent(g);
			subbackground = entirebackground.getSubimage(xscroll,yscroll, width<frame_width?width:frame_width, height<frame_height?height:frame_height)	;
			g.drawImage(subbackground, 0, 0,null);
			player.draw(g);
			for(int i=0;i<sslist.size();i++){
				if(sslist.get(i).getXScrolled()<frame_width&&sslist.get(i).getYScrolled()<frame_height){
					g.drawImage(sslist.get(i).getImage(),
							sslist.get(i).getXScrolled(), 
							sslist.get(i).getYScrolled(), 
							null
					);
				}
			}
			for(int i=0;i<sawlist.size();i++){
				if(sawlist.get(i).getX()<frame_width&&sawlist.get(i).getY()<frame_height){
					g.drawImage(
						sawlist.get(i).getAnimation().getImage(),
						sawlist.get(i).getX(),
						sawlist.get(i).getY(),
						null
					);
				}
			}	
			Iterator<DisappearPlat> iter = dplist.iterator();//using iterator so we can safely remove while traversing
			while(iter.hasNext()){
				DisappearPlat tmp = iter.next();
				if(tmp.getX()<frame_width&&tmp.getY()<frame_height&&!tmp.getAnimation().hasLooped()){
					g.drawImage(
							tmp.getAnimation().getImage(),
							tmp.getX(),
							tmp.getY(),
							null
						);
				}
				else if(tmp.getAnimation().hasLooped()){
					iter.remove();
				}
			}
			g.setColor(Color.red);
			g.drawString("Deaths: "+deathCounter,550,40);
	}
	/**
	 * Checks for player collisions with the circular buzzsaws
	 * @param s The BuzzSaw to check collisions with
	 * @param p The Platform to check collisions with
	 * @return
	 */
	public boolean checkCircleCollision(BuzzSaw s, Platform p){
		double closestx = clamp(s.getXMiddle(),p.getLeft(), p.getRight());
		double closesty = clamp(s.getYMiddle(),p.getTop(),p.getBottom());
		double dx=s.getXMiddle()-closestx;
		double dy=s.getYMiddle()-closesty;
		double distance = (Math.pow(dx,2)+Math.pow(dy, 2));
		return distance<=(s.getRadius()*s.getRadius());
	}
	/**
	 * A helper method to calculate more accurate hitboxes
	 * @param value The value to compare with
	 * @param min The minimum value
	 * @param max The maximum value
	 * @return The most accurate value
	 */
	private double clamp(double value, int min, int max){
		return Math.max(min, Math.min(max,value));
	}
	/**
	 * Increments the death counter by one
	 */
	public void incrementDeathCounter(){
		deathCounter++;
	}
	/**
	 * Returns an ArrayList of the platforms in the level
	 * @return The platforms in the level
	 */
	public ArrayList<Platform> getPlatforms(){
		return platformList;
	}
	/**
	 * Returns an ArrayList of the buzzsaws in the level
	 * @return The buzzsaws in the level
	 */
	public ArrayList<BuzzSaw> getSaws(){
		return sawlist;
	}	
	/**
	 * Returns an ArrayList of the disappearing platforms in the level
	 * @return The disappearing platforms in the level
	 */
	public ArrayList<DisappearPlat> getDPs(){
		return dplist;
	}
	/**
	 * Returns an ArrayList of the saw shooters in the level
	 * @return The saw shooters in the level
	 */
	public ArrayList<SawShooter> getSS(){
		return sslist;
	}
	/**
	 * Returns an ArrayList of the original list of disappearing platforms in the level, before they had disappeared
	 * @return The original list of disappearing platforms
	 */
	public ArrayList<DisappearPlat> getOriginalDPs(){
		return new ArrayList<>(originaldplist);
	}	
	/**
	 * Resets the disappearing platforms, causing disappeared ones to come back
	 */
	public void resetDPs(){
		for(DisappearPlat d:originaldplist){
			d.getAnimation().resetAnimation();
			d.resetTouched();
		}
		dplist=new ArrayList<DisappearPlat>(originaldplist);
	}
	/**
	 * When called, updates the level and repaints it
	 * @param e The ActionEvent that is called (not used)
	 */
	public void actionPerformed(ActionEvent e){
		update();
		repaint();
	}
	/**
	 * Returns the width of the level
	 * @return The width of the level
	 */
	public int getWidth(){
		return width;
	}
	/**
	 * Returns the height of the level
	 * @return The height of the level
	 */
	public int getHeight(){
		return height;
	}
	/**
	 * Returns the horizontal scrolling for the level
	 * @return The horizontal scrolling for the level
	 */
	public int getXScroll(){
		return xscroll;
	}
	/**
	 * Returns the vertical scrolling for the level
	 * @return The vertical scrolling for the level
	 */
	public int getYScroll(){
		return yscroll;
	}
}