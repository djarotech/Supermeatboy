package input;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

/**
 * A generic class for reading user input
 */
public class MeatBoyInput implements KeyListener {
	private boolean[] keys = new boolean[256];	//contains the status of all keyboard keys
	
	/**
	 * Creates a new MeatBoyInput object that listens to a specified java component
	 * @param c The component to listen to 
	 */
	public MeatBoyInput(Component c){
		c.addKeyListener(this);
		Arrays.fill(keys,false);
	}
	
	/**
	 * Returns true if a specified key is pressed
	 * @param key The key to check
	 * @return Whether or not the specified key is pressed
	 */
	public boolean isKeyPressed( int key )
	{
		if( key >= 0 && key < keys.length )
			return keys[key];
		else
			return false;
	}
	//@Override
	public void keyTyped(KeyEvent e) {}
	//@Override
	/**
	 * Changes a key's status in the keys array to true when a key has been pressed
	 */
	public void keyPressed(KeyEvent e) {
		if( e.getKeyCode() >= 0 && e.getKeyCode() < keys.length )
			keys[e.getKeyCode()] = true;
	}
	//@Override
	/**
	 * Changes a key's status in the keys array to false when a key has been released
	 */
	public void keyReleased(KeyEvent e) {
		if( e.getKeyCode() >= 0 && e.getKeyCode() < keys.length )
			keys[e.getKeyCode()] = false;
	}
	/**
	 * Returns which keys are currently being pressed
	 * @return Which keys are being pressed
	 */
	public String toString(){
		String s="";
		for(int i=0;i<keys.length;i++){
			if(keys[i])
				s+="Key with value"+i+"is being pressed";
		}
		return s;
	}
}
