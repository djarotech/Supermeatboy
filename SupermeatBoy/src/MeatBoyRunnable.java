import java.awt.event.KeyEvent;


public class MeatBoyRunnable implements Runnable {
	private MeatBoyInput input;
	private MeatBoy meatboy;
	public MeatBoyRunnable(MeatBoyInput i, MeatBoy m){
		input=i;
		meatboy =m;
	}
	//@Override
	public void run() {
		while(meatboy.isAlive()){
			if(input.isKeyPressed(KeyEvent.VK_LEFT)){
				meatboy.setXVel(-2);
			}
			else if(input.isKeyPressed(KeyEvent.VK_RIGHT)){
				meatboy.setXVel(2);
			}
			else{
				meatboy.setXVel(0);
			}
			if(input.isKeyPressed(KeyEvent.VK_UP)){		//if up is pressed, set inAir to true, and do the physics/gravity stuff. 
				meatboy.setYVel(-2);
			}
			else if(input.isKeyPressed(KeyEvent.VK_DOWN)){
				meatboy.setYVel(2);
			}
			else{
				meatboy.setYVel(0);
			}
			try {
				Thread.sleep(100);	//this should be the frame rate, right now it is 10 fps
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
