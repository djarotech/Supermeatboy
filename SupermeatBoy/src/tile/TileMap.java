package tile;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import character.BandageGirl;
import platform.BuzzSaw;
import platform.DisappearPlat;
import platform.Platform;
import platform.SawShooter;

public class TileMap {
	public static final int TILE_SIZE= 20;
	private TileSet ts;
	private ArrayList<Tile> alltiles;
	private Tile[][] background;
	private Tile[][] foreground;
	private Tile[][] stationary;
	private File tmxfile;
	private ArrayList<Platform> stationaryplats;
	private ArrayList<BuzzSaw> saws;
	private ArrayList<DisappearPlat> dplist;
	private int numRows;
	private int numCols;
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private XPathFactory xpfactory;
	private XPath path;
	private Document doc;
	//information about the start and end of the map.
	private BandageGirl bandagegirl;
	private int mbxstart;
	private int mbystart;
	private ArrayList<SawShooter> sslist;
	
	public TileMap(File tmx){
		stationaryplats=new ArrayList<Platform>();
		saws=new ArrayList<BuzzSaw>();
		dplist=new ArrayList<DisappearPlat>();
		sslist=new ArrayList<SawShooter>();
		tmxfile=tmx;
		
		try{
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			xpfactory = XPathFactory.newInstance();
	      	path = xpfactory.newXPath();
	      	doc = builder.parse(tmxfile);
			ts = new TileSet(TILE_SIZE,TILE_SIZE,tmx);
			alltiles=ts.getTiles();
			numRows=Integer.parseInt(path.evaluate("/map/@height",doc));
			numCols=Integer.parseInt(path.evaluate("/map/@width",doc));
		}catch(Exception e){e.printStackTrace();}
		background = new Tile[numRows][numCols];
		stationary = new Tile[numRows][numCols];
		foreground = new Tile[numRows][numCols];
		try {
			loadMap();
		} catch (XPathExpressionException | SAXException| ParserConfigurationException | IOException e) {e.printStackTrace();}
	}
	public BufferedImage drawMap(){
		BufferedImage bfImage=new BufferedImage(numCols*TILE_SIZE,numRows*TILE_SIZE,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)bfImage.getGraphics();
		for(int r=0;r<numRows;r++){
			for(int c=0;c<numCols;c++){
				if(background[r][c]!=null)
				g.drawImage(background[r][c].getImage(), c*TILE_SIZE, r*TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
			}
		}
		
		
		if(tmxfile.getAbsolutePath().contains("factory")){
			float opacity = 0.6f;
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			for(int r=0;r<numRows;r++){
				for(int c=0;c<numCols;c++){
					if(foreground[r][c]!=null)
					g.drawImage(foreground[r][c].getImage(), c*TILE_SIZE, r*TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
				}
			}
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
			for(int r=0;r<numRows;r++){
				for(int c=0;c<numCols;c++){
					if(stationary[r][c]!=null)
					g.drawImage(stationary[r][c].getImage(), c*TILE_SIZE, r*TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
				}
			}
		}
		else{
			for(int r=0;r<numRows;r++){
				for(int c=0;c<numCols;c++){
					if(stationary[r][c]!=null)
					g.drawImage(stationary[r][c].getImage(), c*TILE_SIZE, r*TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
				}
			}
			for(int r=0;r<numRows;r++){
				for(int c=0;c<numCols;c++){
					if(foreground[r][c]!=null)
					g.drawImage(foreground[r][c].getImage(), c*TILE_SIZE, r*TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
				}
			}
		}
		g.dispose();
		return bfImage;
	}
	public void loadMap() throws XPathExpressionException, SAXException, ParserConfigurationException, IOException{
		int numLayers = Integer.parseInt(path.evaluate("count(/map/layer)", doc));
		int r,c,gidNumber,whichTile;
		for(int i=1;i<=numLayers;i++){
			String name = path.evaluate("/map/layer["+i+"]/@name",doc);
			switch(name){
			case "background":
				gidNumber=1;
				whichTile=0;
				for(r=0;r<background.length;r++){
					for(c=0;c<background[r].length;c++){
						whichTile =Integer.parseInt(path.evaluate("/map/layer["+i+"]/data[1]/tile["+gidNumber+"]/@gid",doc));
						if(whichTile>0){
							background[r][c]=alltiles.get(whichTile-1);	
						}
						gidNumber++;
					}
				}
				
			break;
			case "foreground":
				gidNumber=1;
				whichTile=0;
				for(r=0;r<foreground.length;r++){
					for(c=0;c<foreground[r].length;c++){
						whichTile =Integer.parseInt(path.evaluate("/map/layer["+i+"]/data[1]/tile["+gidNumber+"]/@gid",doc));
						if(whichTile>0){
							foreground[r][c]=alltiles.get(whichTile-1);	
						}
						gidNumber++;
					}
				}
				
			break;
			case "stationary":
				gidNumber=1;
				whichTile=0;
				for(r=0;r<stationary.length;r++){
					for(c=0;c<stationary[r].length;c++){
						whichTile =Integer.parseInt(path.evaluate("/map/layer["+i+"]/data[1]/tile["+gidNumber+"]/@gid",doc));
						if(whichTile>0){
							
							if(whichTile==2){
								bandagegirl=new BandageGirl(c*TILE_SIZE,r*TILE_SIZE,TILE_SIZE);	
								stationary[r][c]=alltiles.get(whichTile-1);	
							}
							else if(whichTile==3){
								DisappearPlat p = new DisappearPlat(c*TILE_SIZE,r*TILE_SIZE);
								dplist.add(p);
								
							}
							else if(whichTile==13){
								SawShooter sh= new SawShooter(c*TILE_SIZE, r*TILE_SIZE, 1000,15,0, SawShooter.HORIZONTAL_DIRECTION);
								sslist.add(sh);
							}
							else if(whichTile==14){
								SawShooter sh= new SawShooter(c*TILE_SIZE, r*TILE_SIZE, 1000,-15,0, SawShooter.HORIZONTAL_DIRECTION);
								sslist.add(sh);
							}
							else if(whichTile==15){
								SawShooter sh= new SawShooter(c*TILE_SIZE, r*TILE_SIZE, 1000,0,-15, SawShooter.VERTICAL_DIRECTION);
								sslist.add(sh);
							}
							else if(whichTile==16){
								SawShooter sh= new SawShooter(c*TILE_SIZE, r*TILE_SIZE, 1000,0,15, SawShooter.VERTICAL_DIRECTION);
								sslist.add(sh);
							}
							else if(whichTile==1){	//this gets meatboy because you are supposed to load the character.png
								mbxstart=c*TILE_SIZE;			//tileset last when making levels in Tiled
								mbystart=r*TILE_SIZE;
								
							}
							else 
								stationary[r][c]=alltiles.get(whichTile-1);	
						}
						gidNumber++;
					}
				}
			break;
			default: System.out.println("You entered an unrecognizable layer. Known layers: \nStationary\nForeground\nBackground");
			}
		}
		int numplats = Integer.parseInt(path.evaluate("count(/map/objectgroup[@name=\"rectangle\"]/object)", doc));
		for(int j=1;j<=numplats;j++){
			int x = Integer.parseInt(trim(path.evaluate("/map/objectgroup[@name=\"rectangle\"]/object["+j+"]/@x", doc)));
			int y= Integer.parseInt(trim(path.evaluate("/map/objectgroup[@name=\"rectangle\"]/object["+j+"]/@y", doc)));
			int w = Integer.parseInt(trim(path.evaluate("/map/objectgroup[@name=\"rectangle\"]/object["+j+"]/@width", doc)));
			int h= Integer.parseInt(trim(path.evaluate("/map/objectgroup[@name=\"rectangle\"]/object["+j+"]/@height", doc)));
			stationaryplats.add(new Platform(x,y,w,h));
		}
		int numsaws = Integer.parseInt(path.evaluate("count(/map/objectgroup[@name=\"saws\"]/object)", doc));
		for(int j=1;j<=numsaws;j++){
			int x = Integer.parseInt(trim(path.evaluate("/map/objectgroup[@name=\"saws\"]/object["+j+"]/@x", doc)));
			int y= Integer.parseInt(trim(path.evaluate("/map/objectgroup[@name=\"saws\"]/object["+j+"]/@y", doc)));
			int w = Integer.parseInt(trim(path.evaluate("/map/objectgroup[@name=\"saws\"]/object["+j+"]/@width", doc)));
			int h= Integer.parseInt(trim(path.evaluate("/map/objectgroup[@name=\"saws\"]/object["+j+"]/@height", doc)));
			if(w!=h){
				System.out.println("You entered a lobsided saw. Failure. ");
			}
			else{
				saws.add(new BuzzSaw(x,y,w));
			}
		}
	}
	private String trim(String s){
		if(s.contains(".")){
			s=s.substring(0,s.indexOf("."));
		}
		return s;
	}
	public ArrayList<Platform> getPlatforms(){
		return stationaryplats;
	}
	public ArrayList<BuzzSaw> getSaws(){
		return saws;
	}
	public ArrayList<DisappearPlat> getDPs(){
		return dplist;
	}
	public ArrayList<SawShooter> getSS(){
		return sslist;
	}
	public int getNumCols(){
		return numCols;
	}
	public int getNumRows(){
		return numRows;
	}
	public int getXStart() {
		return mbxstart;
	}
	public int getYStart() {
		return mbystart;
	}
	public BandageGirl getBandageGirl() {
		// TODO Auto-generated method stub
		return bandagegirl;
	}
}