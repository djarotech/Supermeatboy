package character;

import java.awt.Rectangle;

/**
 * Bandage Girl is the goal for each level.
 * She has her own hitbox.
 */
public class BandageGirl {
	private int x;
	private int y;
	private int size;
	private Rectangle hitbox;
	/**
	 * Creates a new BandageGirl with a specified position in the frame and hitbox size
	 * @param x The x position of Bandage Girl within the level
	 * @param y The y position of Bandage Girl within the level
	 * @param size The size of the hitbox that Bandage Girl will have; Bandage Girl will always have a square hitbox
	 */
	public BandageGirl(int x,int y, int size){
		this.x=x;
		this.y=y;
		this.size=size;
		hitbox=new Rectangle(x,y,size,size);
	}
	/**
	 * Returns the hitbox of Bandage Girl
	 * @return The hitbox of Bandage Girl
	 */
	public Rectangle getHitbox(){
		return hitbox;
	}
	/**
	 * Returns Bandage Girl's x position 
	 * @return Bandage Girl's x position
	 */
	public int getX(){
		return x;
	}
	/**
	 * Returns Bandage Girl's y position
	 * @return Bandage Girl's y position
	 */
	public int getY(){
		return y;
	}
	/**
	 * Returns the size of Bandage Girl's hitbox
	 * @return The size of Bandage Girl's hitbox
	 */
	public int getSize(){
		return size;
	}
}
