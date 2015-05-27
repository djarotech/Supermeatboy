/*Super Meat Boy
 *Kevin Mao
 *Ritchie Chen
 *Daniel Moore
 *CS3 Final project
 *Gallatin-3rd
 */

package tile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * The TileSet class holds the sprite sheets necessary for drawing the levels
 */
public class TileSet {
	private int tileHeight;
	private int tileWidth;
	private File tmxfile;
	private BufferedImage image;
	private static ArrayList<Tile> alltiles;
	/**
	 * Creates a new TileSet object with a specified tmx file with a specified tile width and height
	 * @param tileHeight The height of each individual tile
	 * @param tileWidth The width of each individual tile
	 * @param tmx The tmx file to parse
	 */
	public TileSet(int tileHeight, int tileWidth, File tmx){
		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;	
		tmxfile=tmx;
		alltiles= new ArrayList<Tile>();
		try {
			read();
		} catch (XPathExpressionException | SAXException
				| ParserConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Divides the sprite sheet into correct-sized tiles
	 * @param w The width of the image
	 * @param h The height of the image
	 * @param src The name of the image file to divide into tiles
	 * @throws IOException
	 */
	public void load(int w, int h,String src) throws IOException{
		image = ImageIO.read(new File(src));
		for(int r=0;r<h;r+=20){
			for(int c=0;c<w;c+=20){	
				Tile tmp = new Tile(r,c,image.getSubimage(c,r, tileWidth, tileHeight));
				alltiles.add(tmp);
			}
		}
	}
	/**
	 * Parses the tmx file to find the appropriate sprite sheets
	 * @throws XPathExpressionException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	public void read() throws XPathExpressionException, SAXException, ParserConfigurationException, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		XPathFactory xpfactory = XPathFactory.newInstance();
      	XPath path = xpfactory.newXPath();
		Document doc = builder.parse(tmxfile);
		int numTileSets = Integer.parseInt(path.evaluate("count(/map/tileset)", doc));
		
		for(int i=1;i<=numTileSets;i++){
			String src=path.evaluate("/map/tileset["+i+"]/image/@source", doc);
			int imgw=Integer.parseInt(path.evaluate("/map/tileset["+i+"]/image/@width", doc));
			int imgh=Integer.parseInt(path.evaluate("/map/tileset["+i+"]/image/@height", doc));
			load(imgw,imgh,"resources/" +src);
		}
		
	}
	/**
	 * Returns an ArrayList of all the tiles
	 * @return an ArrayList of all the tiles
	 */
	public ArrayList<Tile> getTiles(){
		return alltiles;
	}
}