package tile;

import java.awt.image.BufferedImage;

public class Tile {
	
	private int x;
	private int y;
	private BufferedImage img;
	
	public Tile(int x, int y, BufferedImage b){
		this.x=x;
		this.y=y;
		img=b;
	}
	public BufferedImage getImage(){
		return img;
	}
	
}
