package animation;

import java.awt.image.BufferedImage;
import org.omg.PortableServer.CurrentOperations;

/**
 * This is the class used to animate objects
 */
public class Animation {
	private BufferedImage[] frames;
	private int currentFrame;
	private int delay;
	private long startTime;
	private boolean haslooped;
	/**
	 * Creates a new Animation object and sets the default times 
	 */
	public Animation(){
		currentFrame=-1;
		delay=-1;
		startTime=System.currentTimeMillis();
		haslooped=false;
	}
	/**
	 * Updates the animation in sync with the updating of the level 
	 * @return
	 */
	public boolean update(){
		if(delay!=-1){
			long elapsed = System.currentTimeMillis()-startTime;
			if(elapsed>delay){
				currentFrame++;
				startTime=System.currentTimeMillis();
				if(currentFrame==frames.length){
					currentFrame=0; //restart animation	
					haslooped=true;
				}
				return true;
			}
		}
		else
			System.out.println("You have not set a valid delay.");
		return false;
	}
	/**
	 * Sets the delay between animation frames
	 * @param d The delay that is to be set
	 */
	public void setDelay(int d){
		this.delay=d;
	}
	/**
	 * Sets the frames to a specified sprite sheet
	 * @param someframes The sprite sheet that the animation will use
	 */
	public void setFrames(BufferedImage[] someframes){
		this.frames=someframes;
		currentFrame=0;
	}
	/**
	 * Returns the current frame
	 * @return the current frame
	 */
	public int getFrame(){
		return currentFrame;
	}
	/**
	 * Returns true if an animation has already looped
	 * @return if an animation has already looped
	 */
	public boolean hasLooped(){
		return haslooped;
	}
	/**
	 * Resets the animation
	 */
	public void resetAnimation(){
		haslooped=false;
		currentFrame=0;
	}
	/**
	 * Returns the frame the animation is currently showing 
	 * @return the frame the animation is currently showing
	 */
	public BufferedImage getImage(){
		if(currentFrame==-1){
			System.out.println("You have not set the frames for this animation ");
			return null;
		}
		return frames[currentFrame];
	}
}