/*Super Meat Boy
 *Kevin Mao
 *Ritchie Chen
 *Daniel Moore
 *CS3 Final project
 *Gallatin-3rd
 */
package app;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
/**
 * A subclass of JPanel used for panels with a specified background
 */
public class MyPanel extends JPanel {

	  private BufferedImage img;
	  /**
	   * Instantiates a panel with a background
	   * @param img
	   */
	  public MyPanel(BufferedImage img) {
	    this.img = img;
	    Dimension size = new Dimension(640,480);
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	  }
	  /**
	   * Overrides JPanel's paint component and draws a background for the panel.
	   * @param g the graphics object to draw with.
	   */
	  public void paintComponent(Graphics g) {
		  super.paintComponent(g);
		  g.drawImage(img, 0, 0, null);
	  }
}
