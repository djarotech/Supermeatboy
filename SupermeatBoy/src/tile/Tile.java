/*Super Meat Boy
 *Kevin Mao
 *Ritchie Chen
 *Daniel Moore
 *CS3 Final project
 *Gallatin-3rd
 */
package tile;

import java.awt.image.BufferedImage;

/**
 * The Tile class represent the individual tiles used to draw almost everything in the level
 */
public class Tile {
	
	private int x;
	private int y;
	private BufferedImage img;
	
	/**
	 * Creates a new Tile object with a specified size and image
	 * @param x The width of the Tile
	 * @param y The height of the Tile
	 * @param b The image that the Tile will show
	 */
	public Tile(int x, int y, BufferedImage b){
		this.x=x;
		this.y=y;
		img=b;
	}
	/**
	 * Returns the tile as a BufferedImage
	 * @return The tile as a BufferedImage
	 */
	public BufferedImage getImage(){
		return img;
	}
	
}