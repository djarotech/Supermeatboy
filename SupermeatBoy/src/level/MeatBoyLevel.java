package level;
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
import tile.TileMap;

import javax.swing.*;

import character.MeatBoy;
import character.BandageGirl;


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
	private int xscroll;
	private int yscroll;
	private BufferedImage entirebackground;
	private BufferedImage subbackground;
	private TileMap tmap;
	private boolean finished;
	private DisappearPlat dp;
	public MeatBoyLevel(Component c)   {
		xscroll=0;
		yscroll=0;
		frame_height=c.getHeight()-40;
		frame_width=c.getWidth();
		//loading a level. I think it makes more sense to add a source field on the constructor for the tmx file.
		//so we will have one level object for each level.
		String src = "resources/forest1.tmx";	//change this to try other levels
		tmap = new TileMap(new File(src));
		entirebackground = tmap.drawMap();
		destination = tmap.getBandageGirl();
		mbxstart = tmap.getXStart();
		mbystart = tmap.getYStart();
		subbackground=null;
		width=tmap.getNumCols()*tmap.TILE_SIZE;
		height=tmap.getNumRows()*tmap.TILE_SIZE;
		platformList=tmap.getPlatforms();
		sawlist=tmap.getSaws();
		originaldplist=tmap.getDPs();
		System.out.println(originaldplist.size()+"init");
		dplist=tmap.getDPs();
		player = new MeatBoy(c,this, mbxstart,mbystart);	
		destination = tmap.getBandageGirl();
		//start updating this level
		this.setOpaque(true);
		time=new Timer(40,this);
		time.start();
		
	}
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
			for(int i=0;i<sawlist.size();i++){
				sawlist.get(i).getAnimation().update();
				sawlist.get(i).setXScroll(xscroll);
				sawlist.get(i).setYScroll(yscroll);
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
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		subbackground = entirebackground.getSubimage(xscroll,yscroll, width<frame_width?width:frame_width, height<frame_height?height:frame_height)	;
		g.drawImage(subbackground, 0, 0,null);
		player.draw(g);
		for(int i=0;i<sawlist.size();i++){
			if(sawlist.get(i).getX()<frame_width){
				g.drawImage(
					sawlist.get(i).getAnimation().getImage(),
					sawlist.get(i).getX(),
					sawlist.get(i).getY(),
					null
				);
			}
		}
		
		Iterator<DisappearPlat> iter = dplist.iterator();
		while(iter.hasNext()){
			DisappearPlat tmp = iter.next();
			if(tmp.getX()<frame_width&&!tmp.getAnimation().hasLooped()){
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
	}
	public ArrayList<Platform> getPlatforms(){
		return platformList;
	}
	public ArrayList<BuzzSaw> getSaws(){
		return sawlist;
	}	
	public ArrayList<DisappearPlat> getDPs(){
		return dplist;
	}
	public ArrayList<DisappearPlat> getOriginalDPs(){
		System.out.println(originaldplist.size()+"get");
		return originaldplist;
	}	
	public void resetDPs(){
		System.out.println(originaldplist.size()+"reset");
		dplist=originaldplist;
	}
	public void actionPerformed(ActionEvent e){
		update();
		repaint();
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public int getXScroll(){
		return xscroll;
	}
	public int getYScroll(){
		return yscroll;
	}
}