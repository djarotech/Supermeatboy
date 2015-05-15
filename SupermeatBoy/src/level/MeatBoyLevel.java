package level;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import platform.BuzzSaw;
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
	private ArrayList<BuzzSaw> saws;
	private int xscroll;
	private int yscroll;
	private BufferedImage entirebackground;
	private BufferedImage subbackground;
	private TileMap tmap;
	private boolean finished;

	public MeatBoyLevel(Component c)   {
		xscroll=0;
		yscroll=0;
		frame_height=c.getHeight()-40;
		frame_width=c.getWidth();
		//loading a level.
		System.out.println("There is 2400 tiles x 5 layers.. It takes 30 seconds to load at the moment.");
		System.out.println("There are two more levels in this project atm, forest1.tmx and forest2.tmx");
		System.out.println("Press F to sprint. F also increased your jump. Use arrow keys to move around.");
		String src = "resources/factory1.tmx";	//change this to try other levels
		tmap = new TileMap(new File(src));
		entirebackground = tmap.drawMap();
		destination = tmap.getBandageGirl();
		mbxstart = tmap.getXStart();
		mbystart = tmap.getYStart();
		subbackground=null;
		width=tmap.getNumCols()*tmap.TILE_SIZE;
		height=tmap.getNumRows()*tmap.TILE_SIZE;
		
		platformList=tmap.getPlatforms();
		saws=tmap.getSaws();
		
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
			if(xscroll<0)
				xscroll=0;
			if(yscroll<0)
				yscroll=0;
			if(xscroll>width-frame_width)
				xscroll=width-frame_width;
			if(yscroll>height-frame_height)
				yscroll=height-frame_height;
			player.setXScroll(xscroll);
			player.setYScroll(yscroll);
			for(int i=0;i<saws.size();i++){
				saws.get(i).getAnimation().update();
				saws.get(i).setXScroll(xscroll);
				saws.get(i).setYScroll(yscroll);
			}
		}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		subbackground = entirebackground.getSubimage(xscroll,yscroll, width<frame_width?width:frame_width, height<frame_height?height:frame_height)	;
		g.drawImage(subbackground, 0, 0,null);
		player.draw(g);
		for(int i=0;i<saws.size();i++){
			g.drawImage(
				saws.get(i).getAnimation().getImage(),
				saws.get(i).getX(),
				saws.get(i).getY(),
				null
			);
		}
	}
	public ArrayList<Platform> getPlatforms(){
		return platformList;
	}
	public ArrayList<BuzzSaw> getSaws(){
		return saws;
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