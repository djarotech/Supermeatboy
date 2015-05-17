package animation;
import java.awt.image.BufferedImage;

public class Animation {
	private BufferedImage[] frames;
	private int currentFrame;
	private int delay;
	private int startTime;
	private boolean haslooped;
	public Animation(){
		currentFrame=-1;
		delay=-1;
		startTime=(int)System.currentTimeMillis();
	}
	public void update(){
		if(delay!=-1){
			int elapsed = (int)System.currentTimeMillis()-startTime;
			if(elapsed>delay){
				currentFrame++;
				startTime=(int)System.currentTimeMillis();
			}
			if(currentFrame==frames.length){
				currentFrame=0; //restart animation	
				haslooped=true;
			}
		}
		else
			System.out.println("You have not set a valid delay.");
	}
	public void setDelay(int d){
		this.delay=d;
	}
	public void setFrames(BufferedImage[] someframes){
		this.frames=someframes;
		currentFrame=0;
	}
	public int getFrame(){
		return currentFrame;
	}
	public boolean hasLooped(){
		return haslooped;
	}
	public void resetAnimation(){
		haslooped=false;
		currentFrame=0;
	}
	public BufferedImage getImage(){
		if(currentFrame==-1){
			System.out.println("You have not set the frames for this animation ");
			return null;
		}			
		return frames[currentFrame];
	}
}