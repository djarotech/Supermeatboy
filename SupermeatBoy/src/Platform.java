import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Platform implements Runnable {
	private MeatBoy meatBoy;
	private int XCord;
	private int YCord;
	private int width;
	private int height;
	private Color color;
	private String MeatBoyStatus;
	
	public Platform(Component c, int w, int h, int x, int y){
		frame_height=c.getHeight();
		frame_width=c.getWidth();
		xCord = x;
		yCord = y;
		width = w;
		height = h;
		MeatBoyStatus = "notTouching";
		platformHitbox = new Rectangle(xCoord,yCoord,length,width);
	}
	public void draw(Graphics g) throws IOException{
		g.setColor(color);
		g.drawRect(XCord,YCord,width,height)
	}
		
	public void run() {
		//Checks if meatboy is within its parameters and stops velocity if so
		color = Color.RED;
		if ((MeatBoy.getXPos()==xCord || MeatBoy.getXPos()==xCord+length) && MeatBoy.getYPos()>yCord && MeatBoy.getYPos()<yCord+width)
		{
			MeatBoy.setXVel(0);
			if (MeatBoy.getXPos()==xCord)
				MeatBoyStatus = "LeftWall";
			else
				MeatBoyStatus = "RightWall";
		}
		else if (MeatBoy.getXPos()>=xCord && MeatBoy.getXPos()<=xCord+length && (MeatBoy.getYPos()==yCord || MeatBoy.getYPos()==yCord+width))
		{
			if (MeatBoy.getYPos()==YCord)
				MeatBoyStatus = "Grounded";
			MeatBoy.setYVel(0);
		}
		else
		{
			MeatBoyStatus = "notTouching";
			color = Color.green;
		}
		}
	}

}